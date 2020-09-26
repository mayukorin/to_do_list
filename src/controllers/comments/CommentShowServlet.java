package controllers.comments;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Comment;
import models.Group;
import utils.DBUtil;

/**
 * Servlet implementation class CommentShowServlet
 */
@WebServlet("/comments/show")
public class CommentShowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommentShowServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        EntityManager em = DBUtil.createEntityManager();
        Comment origin_comment;

        if (request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        //元々のコメント
        if (request.getParameter("id") != null) {
            //task詳細、comment/edit.jspからコメント詳細ページに来た時
            origin_comment = em.find(Comment.class, Integer.parseInt(request.getParameter("id")));
            request.getSession().setAttribute("origin_comment_id", origin_comment.getId());
        } else {
            //それ以外からコメント詳細ページに来た場合
            origin_comment = em.find(Comment.class, (Integer)request.getSession().getAttribute("origin_comment_id"));

        }


        if (origin_comment.getFor_task().getAccount() instanceof Group) {
            request.setAttribute("group_flag", 1);
        }

        //そのコメントに対して返信されたコメント
        List<Comment> return_comments = em.createNamedQuery("getReturnComments",Comment.class).setParameter("comment", origin_comment).getResultList();

        request.setAttribute("origin_comment", origin_comment);
        request.setAttribute("return_comments", return_comments);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/comments/show.jsp");
        rd.forward(request, response);

    }

}
