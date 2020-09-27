package controllers.tasks.group_tasks;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Task;
import utils.DBUtil;

/**
 * Servlet implementation class GroupTaskEditServlet
 */
@WebServlet("/tasks/groups/edit")
public class GroupTaskEditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GroupTaskEditServlet() {
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

        if (t!= null) {
            request.setAttribute("task", t);
            request.getSession().setAttribute("task_id", t.getId());
            request.setAttribute("_token", request.getSession().getId());


        }



        em.close();

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/tasks/group_tasks/edit.jsp");
        rd.forward(request, response);

    }

}
