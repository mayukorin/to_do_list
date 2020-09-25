package controllers.comments;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Comment;
import utils.DBUtil;

/**
 * Servlet implementation class CommentDestroyServlet
 */
@WebServlet("/comments/destroy")
public class CommentDestroyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommentDestroyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub

        String _token = request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();

            //削除しようとしているコメント
            Comment c = em.find(Comment.class, (Integer)request.getSession().getAttribute("comment_id"));
            c.setDelete_flag(1);
            em.getTransaction().begin();
            em.getTransaction().commit();

            em.close();
            request.getSession().removeAttribute("comment_id");

            request.getSession().setAttribute("flush", "コメントの削除が完了しました。");
            System.out.println("どれみドレミどれ美空氏ど");

            response.sendRedirect(request.getContextPath()+"/comments/show");



        }
    }

}
