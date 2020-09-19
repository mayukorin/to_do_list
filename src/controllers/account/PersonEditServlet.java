package controllers.account;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Account;
import models.Person;
import utils.DBUtil;

/**
 * Servlet implementation class PersonEditServlet
 */
@WebServlet("/persons/edit")
public class PersonEditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PersonEditServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub

        EntityManager em = DBUtil.createEntityManager();

        RequestDispatcher rd;

        //編集しようとしているアカウント情報
        Account a = em.find(Account.class, Integer.parseInt(request.getParameter("id")));

        request.setAttribute("_token", request.getSession().getId());
        request.setAttribute("account", a);

        if (a instanceof Person) {
            //personを編集しようとしている時
            rd = request.getRequestDispatcher("/WEB-INF/views/persons/edit.jsp");

        } else  {
            //groupを編集しようとしている時
            rd = request.getRequestDispatcher("/WEB-INF/views/groups/edit.jsp");

        }
        rd.forward(request, response);

    }

}
