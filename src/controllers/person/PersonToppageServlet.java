package controllers.person;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controllers.account.AccountToppageServlet;
import models.Account;
import models.Group;
import models.Task;
import utils.DBUtil;

/**
 * Servlet implementation class PersonToppageServlet
 */
@WebServlet("/persons/toppage")
public class PersonToppageServlet extends AccountToppageServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PersonToppageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub

        ////////////////////13///////////////////////////////////////////////////////////////////////////
        EntityManager em = DBUtil.createEntityManager();
        Account a;

        if (request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }




        if (request.getParameter("id") != null) {
            a = (Account) em.find(Account.class, Integer.parseInt(request.getParameter("id")));//クエリパラメータから得たインスタンス
            request.getSession().setAttribute("account", a);

        }

        Group g = (Group) request.getSession().getAttribute("group");



        List<Task> tasks = em.createNamedQuery("openGroupTask",Task.class).setParameter("account",(Account)request.getSession().getAttribute("account")).setParameter("group",g).getResultList();//今見ようとしている人の、そのgroupで公開されているTask

        request.setAttribute("tasks", tasks);



        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/groups/taskIndex.jsp");
        rd.forward(request, response);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////




    }

}
