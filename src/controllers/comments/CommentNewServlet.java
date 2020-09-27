package controllers.comments;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Comment;
import models.Task;
import utils.DBUtil;


/**
 * Servlet implementation class CommentNewServlet
 */
@WebServlet("/comments/new")
public class CommentNewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommentNewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub

        request.setAttribute("_token", request.getSession().getId());
        EntityManager em = DBUtil.createEntityManager();


        //コメントしようとしているtask
        Task t = em.find(Task.class,Integer.parseInt(request.getParameter("task_id")));

        request.setAttribute("task", t);
        request.setAttribute("comment", new Comment());

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/comments/new.jsp");
        rd.forward(request,response);


    }

}
