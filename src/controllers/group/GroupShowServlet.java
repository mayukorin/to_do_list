package controllers.group;

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
import utils.DBUtil;

/**
 * Servlet implementation class GroupShowServlet
 */
@WebServlet("/groups/show")
public class GroupShowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GroupShowServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        EntityManager em = DBUtil.createEntityManager();

        Group group = (Group)request.getSession().getAttribute("group");//今見ようとしているgroup情報
        List<Person> persons = em.createNamedQuery("getPersons",Person.class).setParameter("group",group).getResultList();//そのgroupに属している人

        em.close();

        request.setAttribute("persons", persons);


        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/groups/show.jsp");
        rd.forward(request, response);

    }

}
