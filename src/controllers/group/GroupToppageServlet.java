package controllers.group;

import java.io.IOException;
import java.util.LinkedHashMap;
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
 * Servlet implementation class GroupToppageServlet
 */
@WebServlet("/group/toppage")
public class GroupToppageServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GroupToppageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        EntityManager em = DBUtil.createEntityManager();
        Account a;
      //taskとtaskに対していいねしているかを確認
        LinkedHashMap<Task,Long> task_like = new LinkedHashMap<Task,Long>();

      //ログインしている本人
        Person p = (Person) request.getSession().getAttribute("login_person");


        if (request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }




        if (request.getParameter("id") != null) {
            //⑫からきた場合
            a = (Account) em.find(Account.class, Integer.parseInt(request.getParameter("id")));//クエリパラメータから得たインスタンス

            request.getSession().setAttribute("account", a);

        }

        Group g = (Group) request.getSession().getAttribute("group");



        List<Task> tasks = em.createNamedQuery("openGroupTask",Task.class).setParameter("account",(Account)request.getSession().getAttribute("account")).setParameter("group",g).getResultList();//今見ようとしている人の、そのgroupで公開されているTask

        for (Task t:tasks) {
            Long like_count = em.createNamedQuery("task_like",Long.class).setParameter("person", p).setParameter("task", t).getSingleResult();
            task_like.put(t, like_count);
         }

        request.setAttribute("task_like", task_like);


        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/groups/toppage.jsp");
        rd.forward(request, response);


    }

}
