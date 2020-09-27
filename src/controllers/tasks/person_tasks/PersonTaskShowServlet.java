package controllers.tasks.person_tasks;

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
import models.Group;
import models.Person;
import models.Task;
import utils.DBUtil;

/**
 * Servlet implementation class PersonTaskShowServlet
 */
@WebServlet("/tasks/persons/show")
public class PersonTaskShowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PersonTaskShowServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub

        HashMap<Task,Long> task_like = new HashMap<Task,Long>();
        Task task;

        if (request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        if (request.getSession().getAttribute("origin_comment_id") != null) {
            request.getSession().removeAttribute("origin_comment_id");
        }


        EntityManager em = DBUtil.createEntityManager();


        //詳細を見ようとしているtaskを取得する////////////////////////////////////////////////////////////////////////////////
        if (request.getSession().getAttribute("liked_task") != null) {
            //HeartDetroyServlet・HeartCreateServletからきた場合

            task = (Task)request.getSession().getAttribute("liked_task");
            request.getSession().removeAttribute("liked_task");

        } else if (request.getSession().getAttribute("commented_task") != null) {
            //CommentCreateServletからきた場合

            task = (Task)request.getSession().getAttribute("commented_task");
            request.getSession().removeAttribute("commented_task");

        } else if (request.getSession().getAttribute("finish_task") != null){
            //TaskFinishServlet・TaskNoFinishServletからきた場合

            task = (Task)request.getSession().getAttribute("finish_task");
            request.getSession().removeAttribute("finish_task");
        } else {
            task = em.find(Task.class,Integer.parseInt(request.getParameter("id")));//クエリパラメーターから選択したtaskを取り出す
        }
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



        Person p = (Person)request.getSession().getAttribute("login_person");

        request.setAttribute("task",task);

        //そのtaskに対していいねをしていたら1、していなければ0
        Long like_count = em.createNamedQuery("task_like",Long.class).setParameter("person",p).setParameter("task",task).getSingleResult();

        task_like.put(task, like_count);


        //taskを公開しているgroupを表示する
        List<Group> shows_group = em.createNamedQuery("getGroupShow",Group.class).setParameter("task",task).getResultList();
        request.setAttribute("shows_group", shows_group);

        //taskに対するコメントを表示する
        List<Comment> comments = em.createNamedQuery("getComments",Comment.class).setParameter("task",task).getResultList();
        request.setAttribute("comments", comments);

        em.close();

        request.setAttribute("task_like", task_like);

        request.setAttribute("_token", request.getSession().getId());

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/tasks/person_tasks/show.jsp");
        rd.forward(request, response);
    }

}
