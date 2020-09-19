package controllers.group;

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
import models.Task;
import utils.DBUtil;

/**
 * Servlet implementation class GroupMemberTaskIndexServlet
 */
@WebServlet("/groups/member")
public class GroupMemberTaskIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GroupMemberTaskIndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        EntityManager em = DBUtil.createEntityManager();



        Account a = (Account) em.find(Account.class, Integer.parseInt(request.getParameter("id")));//クエリパラメータから得たインスタンス

        Group g = (Group) em.find(Group.class, (Integer)request.getSession().getAttribute("group_id"));



        List<Task> tasks = em.createNamedQuery("openGroupTask",Task.class).setParameter("account",a).setParameter("group",g).getResultList();//今見ようとしている人の、そのgroupで公開されているTask

        request.setAttribute("tasks", tasks);
        request.setAttribute("account", a);
        request.setAttribute("g", g);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/groups/taskIndex.jsp");
        rd.forward(request, response);


    }



}
