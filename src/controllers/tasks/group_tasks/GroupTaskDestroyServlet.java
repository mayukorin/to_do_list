package controllers.tasks.group_tasks;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Look;
import models.Show;
import models.Task;
import utils.DBUtil;

/**
 * Servlet implementation class GroupTaskDestroyServlet
 */
@WebServlet("/tasks/groups/destroy")
public class GroupTaskDestroyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GroupTaskDestroyServlet() {
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



            //②Lookと過去のtask履歴を全て削除する/////////////////////////////////////////////////////////////
            if (t.getOrigin_task_id() != null) {
                //Groupのtaskかつ過去に更新してきているtaskの時

                Task origin_task = em.find(Task.class, t.getOrigin_task_id());
                //Lookを全て取ってきて、削除する///////////////////////////////////////////////////

                List<Look> looks = em.createNamedQuery("getAllLooks",Look.class).setParameter("task",origin_task).getResultList();

                if (looks.size() != 0) {
                    for (Look look:looks) {
                        em.getTransaction().begin();
                        em.remove(look);
                        em.getTransaction().commit();
                    }
                }
                ////////////////////////////////////////////////////////////////////////////////

                //過去のtaskを全てとってきて、削除する//////////////////////////////////////////////////
                List<Task> tasks_history = em.createNamedQuery("GetTaskHistroy",Task.class).setParameter("origin_task_id",t.getOrigin_task_id()).getResultList();

                //task履歴を全て削除
                for (Task task:tasks_history) {
                    em.getTransaction().begin();
                    em.remove(task);
                    em.getTransaction().commit();
                }
                ///////////////////////////////////////////////////////////////////////////////
            } else {
                //過去のtask履歴は存在しない時

                //Lookを全て取ってきて、削除する///////////////////////////////////////////////////

                List<Look> looks = em.createNamedQuery("getAllLooks",Look.class).setParameter("task",t).getResultList();

                if (looks.size() != 0) {
                    for (Look look:looks) {
                        em.getTransaction().begin();
                        em.remove(look);
                        em.getTransaction().commit();
                    }
                }
                em.getTransaction().begin();
                em.remove(t);
                em.getTransaction().commit();
            }
            ////////////////////////////////////////////////////////////////////////////////////////////////




            em.close();
            request.getSession().removeAttribute("task_id");

            request.getSession().setAttribute("flush", "taskの削除が完了しました。");

            if (request.getSession().getAttribute("account") != null) {
                response.sendRedirect(request.getContextPath()+"/group/toppage");
            } else {

                response.sendRedirect(request.getContextPath()+"/groups/toppage");
            }


        }
    }
}
