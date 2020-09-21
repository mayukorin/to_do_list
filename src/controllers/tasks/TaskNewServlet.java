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

import models.Account;
import models.Group;
import models.Person;
import models.Task;
import utils.DBUtil;

/**
 * Servlet implementation class TaskNewServlet
 */
@WebServlet("/tasks/new")
public class TaskNewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TaskNewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub

        EntityManager em = DBUtil.createEntityManager();

        request.setAttribute("_token", request.getSession().getId());

        Person p = (Person)request.getSession().getAttribute("login_person");

        Account a;

        if (request.getSession().getAttribute("account") == null) {
            //ホーム画面からtask新規作成に来た場合
            a =  em.find(Account.class, Integer.parseInt(request.getParameter("id")));//詳細を見ようとしているaccount
            request.getSession().setAttribute("account",a);
        } else {
            //groups/taskIndex.jspからtask新規作成に来た場合

            a = (Account) request.getSession().getAttribute("account");
        }




        //ログインしている人が所属しているグループ
        if (a instanceof Person) {
            //もし、自分のtaskを追加しようとしていたら
            List<Group> groups = em.createNamedQuery("getGroupsBelong",Group.class).setParameter("person", p).getResultList();
            request.setAttribute("groups", groups);
        }



        Task task = new Task();

        request.setAttribute("task", task);


        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/tasks/new.jsp");
        rd.forward(request, response);
    }

}
