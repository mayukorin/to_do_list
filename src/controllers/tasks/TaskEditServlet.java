package controllers.tasks;

import java.io.IOException;

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
 * Servlet implementation class TaskEditServlet
 */
@WebServlet("/tasks/edit")
public class TaskEditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TaskEditServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        EntityManager em = DBUtil.createEntityManager();

        Task t = em.find(Task.class, Integer.parseInt(request.getParameter("id")));//編集しようとしているtask

        em.close();

        Person p = (Person)request.getSession().getAttribute("login_person");

        if (t!= null && p.getId() == t.getAccount().getId()) {

            request.setAttribute("task", t);
            request.setAttribute("_token", request.getSession().getId());
            request.getSession().setAttribute("task_id", t.getId());

        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/tasks/edit.jsp");
        rd.forward(request, response);


    }

}
