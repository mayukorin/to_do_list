package controllers.tasks.group_tasks;

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

import models.Account;
import models.Group;
import models.Person;
import models.Show;
import models.Task;
import models.Validators.TaskValidator;
import utils.DBUtil;

/**
 * Servlet implementation class GroupTaskCreateServlet
 */
@WebServlet("/tasks/groups/create")
public class GroupTaskCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GroupTaskCreateServlet() {
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

            Person task_leader;

            Account a = (Account)request.getSession().getAttribute("account");//作成しようとしているtaskのaccount

            Task t = new Task();

            t.setAccount(a);

            t.setTitle(request.getParameter("title"));
            t.setMemo(request.getParameter("memo"));

            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            t.setCreated_at(currentTime);
            t.setUpdated_at(currentTime);


            String date = request.getParameter("deadline");

            Boolean leader_check_flag = true;
            List<String> errors;


                    String task_leader_code = request.getParameter("task_leader");
                    errors = TaskValidator.validate(t, date, (Group)a, task_leader_code, leader_check_flag);




            if (errors.size() > 0) {


                request.setAttribute("task", t);
                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("errors", errors);

                em.close();
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/tasks/new.jsp");
                rd.forward(request, response);
            } else {
                //エラーがないとき


                Person p = (Person)request.getSession().getAttribute("login_person");

                t.setUpdate_person(p);//今loginしている人がこのtaskを作った



                    task_leader = (Person) em.createNamedQuery("getAccount",Account.class).setParameter("code",request.getParameter("task_leader")).getSingleResult();
                    t.setTask_leader(task_leader);
                    //taskを保存する。
                    em.getTransaction().begin();
                    em.persist(t);
                    em.getTransaction().commit();
                    //groupのメンバー全員にtaskをに公開するために、Showを設定
                    Show show = new Show();
                    show.setTask(t);
                    show.setGroup((Group)a);

                    em.getTransaction().begin();
                    em.persist(show);
                    em.getTransaction().commit();

                    em.close();


                    request.getSession().setAttribute("flush", "taskの登録が完了しました。");
                    response.sendRedirect(request.getContextPath()+"/group/toppage");
            }
        }
    }
}
