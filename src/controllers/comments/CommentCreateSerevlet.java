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
import models.Group;
import models.Person;
import models.Task;
import utils.DBUtil;

/**
 * Servlet implementation class CommentCreateSerevlet
 */
@WebServlet("/comments/create")
public class CommentCreateSerevlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommentCreateSerevlet() {
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

            Comment c = new Comment();

            //入力欄から入力されたコメント
            String content = request.getParameter("content");

            if (content == null || content.equals("")) {
                //コメントが未入力の時
                List<String> errors = new ArrayList<String>();
                errors.add("コメントを入力してください");

                request.setAttribute("comment", c);
                request.setAttribute("_token", request.getSession().getId());

                request.setAttribute("errors", errors);

                em.close();
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/comments/new.jsp");
                rd.forward(request, response);
            } else {
                //コメントが入力されていた時


                //コメントするtask
                Task t = em.find(Task.class, Integer.parseInt(request.getParameter("id")));

                c.setContent(content);
                c.setDelete_flag(0);
                c.setFor_task(t);

                Timestamp currentTime = new Timestamp(System.currentTimeMillis());
                c.setCreated_at(currentTime);
                c.setUpdated_at(currentTime);

                c.setComment_person(login_person);

                request.getSession().setAttribute("commented_task", t);

                if (request.getSession().getAttribute("origin_comment_id") != null) {

                    //返信のコメントを追加しようとしている時

                    //返信元のコメント
                    Comment origin_comment = em.find(Comment.class, (Integer)request.getSession().getAttribute("origin_comment_id"));
                    c.setFor_comment(origin_comment);

                  //コメントを保存
                    em.getTransaction().begin();
                    em.persist(c);
                    em.getTransaction().commit();

                    request.getSession().setAttribute("flush", "返信の投稿が完了しました。");

                    response.sendRedirect(request.getContextPath()+"/comments/show");
                } else {

                  //コメントを保存
                    em.getTransaction().begin();
                    em.persist(c);
                    em.getTransaction().commit();

                    request.getSession().setAttribute("flush", "コメントの投稿が完了しました。");


                    if (request.getSession().getAttribute("group") == null) {
                        //ホーム画面のtask一覧からtaskを選んでコメントした時
                        response.sendRedirect(request.getContextPath()+"/tasks/persons/show");
                    } else if (t.getAccount().getId().equals(((Group)request.getSession().getAttribute("group")).getId())) {
                        //groupのtaskについてのコメント

                        response.sendRedirect(request.getContextPath()+"/groups/tasks/show");

                    } else {
                        //memberのtaskについてのコメント

                        response.sendRedirect(request.getContextPath()+"/members/tasks/show");
                    }

                }

            }

        }
    }

}
