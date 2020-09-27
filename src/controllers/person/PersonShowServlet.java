package controllers.person;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.DBUtil;

/**
 * Servlet implementation class PersonShowServlet
 */
@WebServlet("/persons/show")
public class PersonShowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PersonShowServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub


        EntityManager em = DBUtil.createEntityManager();

        if (request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        if (request.getSession().getAttribute("group") != null) {
            request.getSession().removeAttribute("group");
        }

        if (request.getSession().getAttribute("account") != null) {
            request.getSession().removeAttribute("account");
        }

        if (request.getSession().getAttribute("ps") != null) {

            request.getSession().removeAttribute("ps");
        }

        if (request.getSession().getAttribute("account") != null) {

            request.getSession().removeAttribute("account");
        }

        if (request.getSession().getAttribute("updated_task") != null) {

            request.getSession().removeAttribute("updated_task");
        }



        em.close();

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/persons/show.jsp");
        rd.forward(request, response);

    }

}
