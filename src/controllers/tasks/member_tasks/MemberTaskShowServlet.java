package controllers.tasks.member_tasks;

import java.io.IOException;
import java.util.HashMap;
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
import models.Task;
import utils.DBUtil;

/**
 * Servlet implementation class MemberTaskShowServlet
 */
@WebServlet("/members/tasks/show")
public class MemberTaskShowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberTaskShowServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        EntityManager em = DBUtil.createEntityManager();
        HashMap<Task,Long> task_like = new HashMap<Task,Long>();
        Task task;

        if (request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        if (request.getSession().getAttribute("liked_task") != null) {
            task = (Task)request.getSession().getAttribute("liked_task");
            request.getSession().removeAttribute("liked_task");
        } else if (request.getSession().getAttribute("commented_task") != null) {
            //CommentCreateServletからきた場合
            task = (Task)request.getSession().getAttribute("commented_task");
            request.getSession().removeAttribute("commented_task");
        } else if (request.getSession().getAttribute("finish_task") != null){
            //TaskFinishServletからきた場合
            task = (Task)request.getSession().getAttribute("finish_task");
            request.getSession().removeAttribute("finish_task");
        } else {

            task = em.find(Task.class,Integer.parseInt(request.getParameter("id")));//クエリパラメーターから選択したtaskを取り出す
        }
        request.setAttribute("task",task);

/*
        if (request.getSession().getAttribute("account") == null) {

            //②メンバーのtask一覧からtask詳細に来た場合

            Account a = task.getAccount();
            request.getSession().setAttribute("account", a);
        }
*/

        Person p = (Person)request.getSession().getAttribute("login_person");

        Long like_count = em.createNamedQuery("task_like",Long.class).setParameter("person",p).setParameter("task",task).getSingleResult();

        task_like.put(task, like_count);

        //taskに対するコメントを表示する
        List<Comment> comments = em.createNamedQuery("getComments",Comment.class).setParameter("task",task).getResultList();
        request.setAttribute("comments", comments);

        if (request.getSession().getAttribute("origin_comment_id") != null) {
            request.getSession().removeAttribute("origin_comment_id");
        }




        em.close();

        request.setAttribute("task_like", task_like);

        request.setAttribute("_token", request.getSession().getId());

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/tasks/member_tasks/show.jsp");
        rd.forward(request, response);
    }
}

