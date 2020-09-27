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
 * Servlet implementation class TaskFinishServlet
 */
@WebServlet("/tasks/finish")
public class TaskFinishServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TaskFinishServlet() {
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

        //完了済にする
        task.setFinish_flag(1);

        //保存する
        em.getTransaction().begin();
        em.getTransaction().commit();

        //完了させたセッションスコープに格納
        request.getSession().setAttribute("finish_task", task);

        request.getSession().setAttribute("flush", "taskを完了済にしました");

        if (request.getSession().getAttribute("group") == null) {
            //ホーム画面のtask詳細ページから来た時
            response.sendRedirect(request.getContextPath()+"/tasks/persons/show");
        } else if (task.getAccount() instanceof Group) {
            //groupのtask詳細ページからきた時
            response.sendRedirect(request.getContextPath()+"/groups/tasks/show");
        } else {
            //memberのtask詳細ページからきた時
            response.sendRedirect(request.getContextPath()+"/members/tasks/show");
        }


    }

}
