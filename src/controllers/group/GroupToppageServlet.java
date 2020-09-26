package controllers.group;

import java.io.IOException;
import java.util.Date;
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
        LinkedHashMap<Task,Integer> task_like = new LinkedHashMap<Task,Integer>();

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
        Date today = new Date();
        for (Task t:tasks) {
            Date deadline = t.getDeadline();
            long day_diff = (deadline.getTime()-today.getTime())/(1000 * 60 * 60 * 24 );//今日との差


            Long like_count = em.createNamedQuery("task_like",Long.class).setParameter("person", p).setParameter("task", t).getSingleResult();//そのtaskに対して自分がいいねをしているか

            if (like_count == 1 && day_diff <2) {
                //自分がいいねをしていて、かつ期限が1日以内
                task_like.put(t, 4);
            } else if (like_count == 0 && day_diff < 2) {
                //自分がいいねをしていなくて、かつ期限が1日以内
                task_like.put(t, 1);
            } else if (like_count == 1 && day_diff < 8) {
                //自分がいいねをしていて、期限が7日以内
                task_like.put(t, 2);
            } else if (like_count == 0 && day_diff <8) {
                //自分がいいねをしていなくて、期限が７日以内
                task_like.put(t, 5);
            } else if (like_count == 1 && day_diff > 7) {
                //自分がいいねをしていて、期限が7日以上
                task_like.put(t, 6);
            } else if (like_count == 0 && day_diff > 7) {
                //自分がいいねをしていなくて、期限が７日以上
                task_like.put(t, 3);
            }

         }

        request.setAttribute("task_like", task_like);


        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/groups/toppage.jsp");
        rd.forward(request, response);


    }

}
