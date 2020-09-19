package controllers.tasks;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Group;
import models.Person;
import models.Show;
import models.Task;
import models.Validators.TaskValidator;
import utils.DBUtil;

/**
 * Servlet implementation class TaskUpdateServlet
 */
@WebServlet("/tasks/update")
public class TaskUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TaskUpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub

        String _token = (String)request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();

            Person p = (Person)request.getSession().getAttribute("login_person");
           //ログインしている人が所属しているグループ
            List<Group> groups = em.createNamedQuery("getGroupsBelong",Group.class).setParameter("person", p).getResultList();

            Task t = em.find(Task.class, (Integer)(request.getSession().getAttribute("task_id")));//変更しようとしているtask
            System.out.println("A眞そ"+t.getTitle());

            t.setTitle(request.getParameter("title"));
            t.setMemo(request.getParameter("memo"));

            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            t.setCreated_at(currentTime);
            t.setUpdated_at(currentTime);


            String date = request.getParameter("deadline");

            List<String> errors = TaskValidator.validate(t,date);//入力内容にエラーがあるか確認
            System.out.println("ゲラにちゃ"+errors.size());

            if (errors.size() > 0) {
                System.out.println("あいうあいう");
                em.close();

                request.setAttribute("task", t);
                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("errors", errors);
                request.setAttribute("groups", groups);

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/tasks/edit.jsp");
                rd.forward(request, response);
            } else {
                //エラーがないとき
                System.out.println("フワちゃん"+t.getTitle());
              //taskを保存する。
                em.getTransaction().begin();
                em.getTransaction().commit();



                for(Group group:groups) {

                    Show show_flag;
                    String id = (group.getId()).toString();
                    //更新しようとしているtaskとgroupをつなくshowがあるか確認する
                    try {
                        show_flag = em.createNamedQuery("getShowGroupTask",Show.class).setParameter("group", group).setParameter("task", t).getSingleResult();
                      //更新しようとしているtaskとgroupをつなくshowがある
                        //そのgroupは、元々taskを見ることができていた時

                        if (!request.getParameter(id).equals(id)) {
                            //そのgroupが見ることができなくしたい時

                            em.getTransaction().begin();
                            em.remove(show_flag);
                            em.getTransaction().commit();

                        }
                    } catch(NoResultException e) {
                        //そのgroupはtaskを見ることができない時
                        if (request.getParameter(id).equals(id)) {
                            //チェックボックスにチェックがついている時、そのgroupとtaskに関するshowを登録する。
                            //そのtaskをgroupが見れるようにする
                            Show show = new Show();
                            show.setTask(t);
                            show.setGroup(group);//そのグループは、task内容を見ることができる。

                            em.getTransaction().begin();
                            em.persist(show);
                            em.getTransaction().commit();



                        }
                    }

                }
                em.close();

                request.getSession().setAttribute("flush", "taskの更新が完了しました。");

                response.sendRedirect(request.getContextPath()+"/toppage/index");//ホーム画面に戻る
            }
        }
    }
}
