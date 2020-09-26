package controllers.tasks;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Group;
import models.Task;
import utils.DBUtil;

/**
 * Servlet implementation class TaskNoFinishServlet
 */
@WebServlet("/tasks/nofinish")
public class TaskNoFinishServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TaskNoFinishServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        EntityManager em = DBUtil.createEntityManager();

        //完了済にしようとしているtask
        Task task = em.find(Task.class, Integer.parseInt(request.getParameter("id")));

        //未完了にする
        task.setFinish_flag(0);

        //保存する
        em.getTransaction().begin();
        em.getTransaction().commit();

        //セッションスコープに格納
        request.getSession().setAttribute("finish_task", task);

        request.getSession().setAttribute("flush", "taskを未完了にしました");

        if (request.getSession().getAttribute("group") == null) {

            response.sendRedirect(request.getContextPath()+"/tasks/persons/show");//ホーム画面に戻る
        } else if (task.getAccount() instanceof Group) {
            response.sendRedirect(request.getContextPath()+"/groups/tasks/show");//ホーム画面に戻る
        } else {
            response.sendRedirect(request.getContextPath()+"/members/tasks/show");//ホーム画面に戻る
        }

    }

}
