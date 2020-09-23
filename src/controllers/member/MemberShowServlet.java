package controllers.member;

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
 * Servlet implementation class MemberShowServlet
 */
@WebServlet("/members/show")
public class MemberShowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberShowServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub

        EntityManager em = DBUtil.createEntityManager();


        //詳細を見ようとしているアカウント
        Person p = (Person) request.getSession().getAttribute("account");

        //Personインスタンスが所属しているグループ
        List<Group> groups = em.createNamedQuery("getGroupsBelong",Group.class).setParameter("person",p).getResultList();
        request.setAttribute("groups", groups);



        em.close();

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/members/show.jsp");
        rd.forward(request, response);
    }

}
