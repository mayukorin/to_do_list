package controllers.group;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Group;

/**
 * Servlet implementation class GroupEditServlet
 */
@WebServlet("/group/edit")
public class GroupEditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GroupEditServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub

        //編集しようとしているgroup
        Group g = (Group) request.getSession().getAttribute("account");

        request.setAttribute("_token", request.getSession().getId());

        request.setAttribute("group", g);


        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/groups/edit.jsp");


         rd.forward(request, response);

    }

}
