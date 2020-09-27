package controllers.tasks.group_tasks;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Task;
import utils.DBUtil;

/**
 * Servlet implementation class GroupTaskHistoryServlet
 */
@WebServlet("/tasks/groups/history")
public class GroupTaskHistoryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GroupTaskHistoryServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub

        EntityManager em = DBUtil.createEntityManager();

        Task task = em.find(Task.class, Integer.parseInt(request.getParameter("id")));

        //taskの更新履歴
        List<Task> tasks_history = em.createNamedQuery("GetTaskHistroy",Task.class).setParameter("origin_task_id",task.getOrigin_task_id()).getResultList();
        tasks_history.remove(tasks_history.size()-1);//最新のtaskだけ省く（最新のtaskは変数taskに格納ずみ）


        em.close();

        request.setAttribute("task_history", tasks_history);
        //最新のtaskをupdated_taskにセッションスコープで格納
        request.getSession().setAttribute("updated_task", task);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/tasks/group_tasks/history.jsp");
        rd.forward(request, response);

    }

}
