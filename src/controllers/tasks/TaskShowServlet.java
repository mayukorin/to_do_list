package controllers.tasks;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Group;
import models.Person;
import models.Task;
import utils.DBUtil;

/**
 * Servlet implementation class TaskShowServlet
 */
@WebServlet("/tasks/show")
public class TaskShowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TaskShowServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub

        EntityManager em = DBUtil.createEntityManager();
        Person p = (Person) request.getSession().getAttribute("login_person");//ログインしている人


        Task task = em.find(Task.class,Integer.parseInt(request.getParameter("id")));//クエリパラメーターから選択したtaskを取り出す

        if ( task.getAccount().getId() == p.getId()) {
            //自分のtaskを見ている時のみ、公開しているgroupを表示する
            List<Group> shows_group = em.createNamedQuery("getGroupShow",Group.class).setParameter("task",task).getResultList();
            request.setAttribute("shows_group", shows_group);

        }

        if (task.getOrigin_task_id() != null) {
            //Groupのtaskかつ過去に更新してきているtaskの時
            //過去のtaskを全てとってくる
            List<Task> tasks_history = em.createNamedQuery("GetTaskHistroy",Task.class).setParameter("origin_task_id",task.getOrigin_task_id()).getResultList();
            tasks_history.remove(tasks_history.size()-1);//最新のtaskだけ省く（最新のtaskは変数taskに格納ずみ）
            request.setAttribute("tasks_history", tasks_history);
        }

        em.close();

        request.setAttribute("task", task);

        request.setAttribute("_token", request.getSession().getId());

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/tasks/show.jsp");
        rd.forward(request, response);
    }

}
