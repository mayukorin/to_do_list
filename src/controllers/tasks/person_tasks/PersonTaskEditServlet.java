package controllers.tasks.person_tasks;

import java.io.IOException;
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
import models.Task;
import utils.DBUtil;

/**
 * Servlet implementation class PersonTaskEditServlet
 */
@WebServlet("/tasks/persons/edit")
public class PersonTaskEditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PersonTaskEditServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        EntityManager em = DBUtil.createEntityManager();

        //編集しようとしているtask
        Task t = em.find(Task.class, Integer.parseInt(request.getParameter("id")));

        //ログインしている人が所属しているグループ
        List<Group> groups = em.createNamedQuery("getGroupsBelong",Group.class).setParameter("person", (Person)request.getSession().getAttribute("login_person")).getResultList();

        //その仕事を公開しているグループ
        List<Group> shows_group = em.createNamedQuery("getGroupShow",Group.class).setParameter("task",t).getResultList();


        groups.removeAll(shows_group);//所属しているグループから、公開しているグループを引く

        if (t!= null) {
            request.setAttribute("task", t);
            request.getSession().setAttribute("task_id", t.getId());

            request.setAttribute("_token", request.getSession().getId());

            request.setAttribute("groups", groups);
            request.setAttribute("shows_group", shows_group);
        }

        em.close();

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/tasks/person_tasks/edit.jsp");
        rd.forward(request, response);

    }



}
