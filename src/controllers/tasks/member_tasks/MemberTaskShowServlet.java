package controllers.tasks.member_tasks;

import java.io.IOException;
import java.util.HashMap;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Person;
import models.Task;
import utils.DBUtil;

/**
 * Servlet implementation class MemberTaskShowServlet
 */
@WebServlet("/members/tasks/show")
public class MemberTaskShowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberTaskShowServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        EntityManager em = DBUtil.createEntityManager();
        HashMap<Task,Long> task_like = new HashMap<Task,Long>();
        Task task;

        if (request.getSession().getAttribute("liked_task") != null) {
            task = (Task)request.getSession().getAttribute("liked_task");
            request.getSession().removeAttribute("liked_task");
        } else {
            task = em.find(Task.class,Integer.parseInt(request.getParameter("id")));//クエリパラメーターから選択したtaskを取り出す
        }
        request.setAttribute("task",task);

/*
        if (request.getSession().getAttribute("account") == null) {

            //②メンバーのtask一覧からtask詳細に来た場合

            Account a = task.getAccount();
            request.getSession().setAttribute("account", a);
        }
*/

        Person p = (Person)request.getSession().getAttribute("login_person");

        Long like_count = em.createNamedQuery("task_like",Long.class).setParameter("person",p).setParameter("task",task).getSingleResult();

        task_like.put(task, like_count);



        em.close();

        request.setAttribute("task_like", task_like);

        request.setAttribute("_token", request.getSession().getId());

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/tasks/member_tasks/show.jsp");
        rd.forward(request, response);
    }
}

