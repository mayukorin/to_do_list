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
import models.Show;
import utils.DBUtil;

/**
 * Servlet implementation class GroupToppageServlet
 */
@WebServlet("/groups/toppage")
public class GroupToppageServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GroupToppageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        if (request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        EntityManager em = DBUtil.createEntityManager();

        Group group = em.find(Group.class, Integer.parseInt(request.getParameter("id")));

        List<Show> shows = em.createNamedQuery("getShows",Show.class).setParameter("group",group).getResultList();//そのGroupのShowを取り出す

        request.setAttribute("shows", shows);
        request.getSession().setAttribute("group_id",group.getId());
        request.setAttribute("group", group);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/groups/toppage.jsp");
        rd.forward(request, response);


    }

}
