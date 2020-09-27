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
 * Servlet implementation class BelongUpdateServlet
 */
@WebServlet("/belongs/update")
public class BelongUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public BelongUpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub

        String _token = (String)request.getParameter("_token");
        if (_token != null && _token.equals(request.getSession().getId())) {

            EntityManager em = DBUtil.createEntityManager();



            //編集しようとしているbelong
            Belong b = em.find(Belong.class, (Integer)request.getSession().getAttribute("belong_id"));

            if (request.getParameter("position") == null || request.getParameter("position").equals("")) {
                //ポジションに入力がない時

                em.close();

                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("belong", b);

                request.setAttribute("hasError", true);


                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/belongs/edit.jsp");
                rd.forward(request, response);

            } else {
                //ポジションが入力されていた時
                b.setPosition(request.getParameter("position"));

                em.getTransaction().begin();
                em.getTransaction().commit();
                em.close();

                String message = b.getGroup().getName()+"のポジションの更新が完了しました。";
                request.getSession().setAttribute("flush", message);


                response.sendRedirect(request.getContextPath() + "/persons/show");
            }
        }
    }
}

