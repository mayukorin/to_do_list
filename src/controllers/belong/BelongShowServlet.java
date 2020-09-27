package controllers.belong;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Belong;
import models.Group;
import models.Person;
import utils.DBUtil;

/**
 * Servlet implementation class BelongShowServlet
 */
@WebServlet("/belongs/show")
public class BelongShowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public BelongShowServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        EntityManager em = DBUtil.createEntityManager();



        //ログインしている人
        Person p = (Person)request.getSession().getAttribute("login_person");
        //所属しているgroup
        Group g = em.find(Group.class,Integer.parseInt(request.getParameter("id")));
        //その人と、groupを結びつけているbelongインスタンス
        Belong b = em.createNamedQuery("getGroupPersonBelong",Belong.class).setParameter("group",g).setParameter("person",p).getSingleResult();

        request.setAttribute("belong", b);
        request.getSession().setAttribute("belong_id", b.getId());

        request.setAttribute("_token", request.getSession().getId());

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/belongs/show.jsp");
        rd.forward(request, response);


    }

}
