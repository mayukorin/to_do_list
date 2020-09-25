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
        } else {
            task = em.find(Task.class,Integer.parseInt(request.getParameter("id")));//クエリパラメーターから選択したtaskを取り出す
        }
        request.setAttribute("task",task);

        Person p = (Person)request.getSession().getAttribute("login_person");

        Long like_count = em.createNamedQuery("task_like",Long.class).setParameter("person",p).setParameter("task",task).getSingleResult();

        task_like.put(task, like_count);


        if (task.getOrigin_task_id() != null) {
            //過去に更新してきているtaskの時
            //過去のtaskを全てとってくる
            List<Task> tasks_history = em.createNamedQuery("GetTaskHistroy",Task.class).setParameter("origin_task_id",task.getOrigin_task_id()).getResultList();
            tasks_history.remove(tasks_history.size()-1);//最新のtaskだけ省く（最新のtaskは変数taskに格納ずみ）
            request.setAttribute("tasks_history", tasks_history);
        }

        em.close();

        request.setAttribute("task_like", task_like);

        request.setAttribute("_token", request.getSession().getId());

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/tasks/group_tasks/show.jsp");
        rd.forward(request, response);
    }

}
