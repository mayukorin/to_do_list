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

import models.Belong;
import models.Group;
import models.Person;
import models.Task;
import utils.DBUtil;

/**
 * Servlet implementation class GroupMemberToppageServlet
 */
@WebServlet("/groups/toppage")
public class GroupMemberToppageServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GroupMemberToppageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        if (request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        EntityManager em = DBUtil.createEntityManager();
        Group group;

        if (request.getParameter("id") != null) {
            //そのgroupを始めて見る時

            //今から見ようとしているグループ
            group = em.find(Group.class, Integer.parseInt(request.getParameter("id")));
            //ログインしている本人
            Person p = (Person) request.getSession().getAttribute("login_person");

            //グループと本人をつなぐbelong
            Belong b = em.createNamedQuery("getGroupPersonBelong",Belong.class).setParameter("person",p).setParameter("group",group).getSingleResult();


            if (b.getUpdated_at().before(group.getUpdated_at())) {
                //groupがbelongより後に更新されていたら、GroupLoginSerevletにリダイレクト

                String message = group.getName()+"の情報が変更されています。グループへのログインをし直してください";

                request.getSession().setAttribute("flush", message);
                request.getSession().setAttribute("group",group);

                response.sendRedirect(request.getContextPath() + "/groups/login");
            } else {
                //groupがbelongの更新より前の時、⑪の画面へ

                //そのgroupで公開されているメンバー全員のtask
                List<Task> tasks = em.createNamedQuery("GroupMemberAllTask",Task.class).setParameter("group",group).getResultList();

                request.setAttribute("tasks", tasks);
                request.getSession().setAttribute("group",group);

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/groups/toppage.jsp");
                rd.forward(request, response);

            }


        } else {
            //⑫の画面・GroupLoginSerevletから来た時

            group = (Group)request.getSession().getAttribute("group");

            //そのgroupで公開されているメンバー全員のtask
            List<Task> tasks = em.createNamedQuery("GroupMemberAllTask",Task.class).setParameter("group",group).getResultList();

            request.setAttribute("tasks", tasks);

            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/groups/toppage.jsp");
            rd.forward(request, response);

        }




    }

}
