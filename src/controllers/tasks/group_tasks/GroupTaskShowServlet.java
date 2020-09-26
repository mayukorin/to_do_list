package controllers.tasks.group_tasks;

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
 * Servlet implementation class GroupTaskShowServlet
 */
@WebServlet("/groups/tasks/show")
public class GroupTaskShowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GroupTaskShowServlet() {
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

        if (request.getSession().getAttribute("liked_task") != null) {
            task = (Task)request.getSession().getAttribute("liked_task");
            request.getSession().removeAttribute("liked_task");
        } else if (request.getSession().getAttribute("commented_task") != null) {
            //CommentCreateServletからきた場合
            task = (Task)request.getSession().getAttribute("commented_task");
            request.getSession().removeAttribute("commented_task");
        } else {
            task = em.find(Task.class,Integer.parseInt(request.getParameter("id")));//クエリパラメーターから選択したtaskを取り出す
        }



        request.setAttribute("task",task);
        if (request.getParameter("iid") == null) {
            if (request.getSession().getAttribute("updated_task") != null) {
                //task更新履歴ページから戻ってきた場合
                //task更新履歴ページから昔のtaskを表示させたい場合はupdated_taskを残しておく

                System.out.println("だよねー");

                request.getSession().removeAttribute("updated_task");
            }
        } else {
            System.out.println("あああああああああ");
            System.out.println(((Task)request.getSession().getAttribute("updated_task")).getTitle());
        }

        Person p = (Person)request.getSession().getAttribute("login_person");

        Long like_count = em.createNamedQuery("task_like",Long.class).setParameter("person",p).setParameter("task",task).getSingleResult();

        task_like.put(task, like_count);

        //taskに対するコメントを表示する
        List<Comment> comments = em.createNamedQuery("getComments",Comment.class).setParameter("task",task).getResultList();
        request.setAttribute("comments", comments);


        if (request.getSession().getAttribute("updated_task") == null) {
            //現在のtaskを詳細表示させようとしている時
            if (task.getOrigin_task_id() != null) {
                //過去に更新してきているtaskの時
                //過去のtaskを全てとってくる
                List<Task> tasks_history = em.createNamedQuery("GetTaskHistroy",Task.class).setParameter("origin_task_id",task.getOrigin_task_id()).getResultList();
                tasks_history.remove(tasks_history.size()-1);//最新のtaskだけ省く（最新のtaskは変数taskに格納ずみ）
                System.out.println(tasks_history.size());
                if (tasks_history.size() != 0) {
                    request.setAttribute("tasks_history", 1);
                }
            }
        } else {
            //過去のtaskを詳細表示させようとしている時
            Task origin_task = em.find(Task.class, task.getOrigin_task_id());
            request.setAttribute("origin_task", origin_task);
        }

        if (request.getSession().getAttribute("origin_comment_id") != null) {
            request.getSession().removeAttribute("origin_comment_id");
        }



        em.close();

        request.setAttribute("task_like", task_like);

        request.setAttribute("_token", request.getSession().getId());

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/tasks/group_tasks/show.jsp");
        rd.forward(request, response);
    }

}
