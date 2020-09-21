package controllers.tasks;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Person;
import models.Show;
import models.Task;
import utils.DBUtil;

/**
 * Servlet implementation class TaskDestroyServlet
 */
@WebServlet("/destroy")
public class TaskDestroyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TaskDestroyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        String _token = request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();

            Task t = em.find(Task.class, (Integer)request.getSession().getAttribute("task_id"));

           //そのtaskを公開しているshowを取り出す
            List<Show> shows = em.createNamedQuery("getShows",Show.class).setParameter("task", t).getResultList();

            if (t.getAccount() instanceof Person) {
                //自分自身のtaskを削除しようとしている時



                if (shows != null) {
                    //そのtaskを公開しているshowがあれば
                    //そのshowを一つずつ削除する

                    for (Show show:shows) {
                        em.getTransaction().begin();
                        em.remove(show);
                        em.getTransaction().commit();
                    }
                }

                //taskを削除する
                em.getTransaction().begin();
                em.remove(t);
                em.getTransaction().commit();
            } else {
                //groupのtaskを削除しようとしている時
                //①showを削除する
                if (shows != null) {
                    //そのtaskを公開しているshowがあれば
                    //そのshowを一つずつ削除する

                    for (Show show:shows) {
                        em.getTransaction().begin();
                        em.remove(show);
                        em.getTransaction().commit();
                    }
                }

                //②過去のtask履歴を全て削除する/////////////////////////////////////////////////////////////
                if (t.getOrigin_task_id() != null) {
                    //Groupのtaskかつ過去に更新してきているtaskの時
                    //過去のtaskを全てとってくる
                    List<Task> tasks_history = em.createNamedQuery("GetTaskHistroy",Task.class).setParameter("origin_task_id",t.getOrigin_task_id()).getResultList();

                    //task履歴を全て削除
                    for (Task task:tasks_history) {
                        em.getTransaction().begin();
                        em.remove(task);
                        em.getTransaction().commit();
                    }
                } else {
                    //過去のtask履歴は存在しない時
                    em.getTransaction().begin();
                    em.remove(t);
                    em.getTransaction().commit();
                }
                ////////////////////////////////////////////////////////////////////////////////////////////////


            }

            em.close();
            request.getSession().removeAttribute("task_id");

            request.getSession().setAttribute("flush", "taskの削除が完了しました。");

            if (request.getSession().getAttribute("group") == null) {
                //グループ画面からtaskを追加していない時
                response.sendRedirect(request.getContextPath()+"/toppage/index");//ホーム画面に戻る
            } else {
                //グループ画面からtaskを追加した時
                response.sendRedirect(request.getContextPath()+"/groups/member");//taskを追加したメンバーのtask一覧画面に戻る

            }
        }
    }
}
