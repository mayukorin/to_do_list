package controllers.tasks.person_tasks;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Group;
import models.Task;

/**
 * Servlet implementation class PersonTaskNewServlet
 */
@WebServlet("/tasks/persons/new")
public class PersonTaskNewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PersonTaskNewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub

        request.setAttribute("_token", request.getSession().getId());


        //ログインしている人が所属しているグループ
        @SuppressWarnings("unchecked")
        List<Group> groups = (List<Group>) request.getSession().getAttribute("GroupBelong");
        request.setAttribute("groups", groups);


        request.setAttribute("task", new Task());


        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/tasks/person_tasks/new.jsp");
        rd.forward(request, response);
    }
}
