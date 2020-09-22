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
import utils.DBUtil;

/**
 * Servlet implementation class BelongEditServlet
 */
@WebServlet("/belongs/edit")
public class BelongEditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public BelongEditServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        EntityManager em = DBUtil.createEntityManager();

        Belong b = em.find(Belong.class, Integer.parseInt(request.getParameter("id")));//編集しようとしているbelong

        request.getSession().setAttribute("belong_id", b.getId());
        request.setAttribute("belong", b);
        request.setAttribute("_token", request.getSession().getId());

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/belongs/edit.jsp");
        rd.forward(request, response);


    }

}
