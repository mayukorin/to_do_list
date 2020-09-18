package controllers.tasks;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
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
 * Servlet implementation class TaskCreateServlet
 */
@WebServlet("/tasks/create")
public class TaskCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TaskCreateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub

        String _token = (String)request.getParameter("_token");
        if (_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();

            Person p = (Person) request.getSession().getAttribute("login_person");

            Task t = new Task();

            t.setAccount(p);

            t.setTitle(request.getParameter("title"));
            t.setMemo(request.getParameter("memo"));

            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            t.setCreated_at(currentTime);
            t.setUpdated_at(currentTime);


            String date = request.getParameter("deadline");

            List<String> errors = TaskValidator.validate(t,date);//入力内容にエラーがあるか確認

            //その人が所属しているグループを確認
            List<Group> groups = em.createNamedQuery("getGroupsBelong",Group.class).setParameter("person", p).getResultList();

            if (errors.size() > 0) {
                em.close();

                request.setAttribute("task", t);
                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("errors", errors);
                request.setAttribute("groups", groups);

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/tasks/new.jsp");
                rd.forward(request, response);
            } else {
                //エラーがないとき

                //taskを保存する。
                em.getTransaction().begin();
                em.persist(t);
                em.getTransaction().commit();



                for(Group group:groups) {
                    String id = (group.getId()).toString();
                    System.out.println(id);

                    System.out.println(request.getParameter(id));
                    if (request.getParameter(id).equals(id)) {
                        //チェックボックスにチェックがついている時、そのgroupとtaskに関するshowを登録する。
                        Show show = new Show();
                        show.setTask(t);
                        show.setGroup(group);//そのグループは、task内容を見ることができる。

                        em.getTransaction().begin();
                        em.persist(show);
                        em.getTransaction().commit();
                        Show s = em.find(Show.class, show.getId());
                        System.out.println(s.getId());

                    }
                }
                em.close();

                request.getSession().setAttribute("flush", "taskの登録が完了しました。");

                response.sendRedirect(request.getContextPath()+"/index.html");//ホーム画面に戻る

            }


        }

    }

}
