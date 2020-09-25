package controllers.comments;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Comment;
import models.Person;
import utils.DBUtil;

/**
 * Servlet implementation class CommentUpdateServlet
 */
@WebServlet("/comments/update")
public class CommentUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommentUpdateServlet() {
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

            Person login_person = (Person)request.getSession().getAttribute("login_person");

            //編集しようとしているコメント
            Comment c = em.find(Comment.class, (Integer)request.getSession().getAttribute("comment_id"));

            String content = request.getParameter("content");

            if (content == null || content.equals("")) {
                //コメントが未入力の時
                List<String> errors = new ArrayList<String>();
                errors.add("コメントを入力してください");

                request.setAttribute("comment", c);
                request.setAttribute("task", c.getFor_task());
                request.setAttribute("_token", request.getSession().getId());

                request.setAttribute("errors", errors);

                em.close();
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/comments/edit.jsp");
                rd.forward(request, response);
            } else {
                //コメントが入力されていた時



                c.setContent(content);
                c.setDelete_flag(0);


                Timestamp currentTime = new Timestamp(System.currentTimeMillis());

                c.setUpdated_at(currentTime);

                c.setComment_person(login_person);

                //コメントを保存
                em.getTransaction().begin();
                em.getTransaction().commit();
                em.close();



                request.getSession().setAttribute("flush", "コメントの修正が完了しました。");
                response.sendRedirect(request.getContextPath()+"/comments/show");

            }
        }
    }
}

