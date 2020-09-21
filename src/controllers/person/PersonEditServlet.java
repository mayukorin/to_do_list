package controllers.person;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Account;
import models.Person;

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



        RequestDispatcher rd;

        //編集しようとしているアカウント情報
        Account a = (Account) request.getSession().getAttribute("account");

        request.setAttribute("_token", request.getSession().getId());

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
