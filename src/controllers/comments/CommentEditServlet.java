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
 * Servlet implementation class CommentEditServlet
 */
@WebServlet("/comments/edit")
public class CommentEditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommentEditServlet() {
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

        //編集しようとしているコメント
        Comment c = em.find(Comment.class, Integer.parseInt(request.getParameter("id")));


        //コメントしようとしているtask
        Task t = c.getFor_task();

        request.setAttribute("task", t);
        request.setAttribute("comment", c);
        request.getSession().setAttribute("comment_id", c.getId());

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/comments/edit.jsp");
        rd.forward(request,response);
    }

}
