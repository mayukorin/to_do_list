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
 * Servlet implementation class GroupToppageServlet
 */
@WebServlet("/groups/toppage")
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
        if (request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        EntityManager em = DBUtil.createEntityManager();
        Group group;

        if (request.getParameter("id") != null) {
            //groupのログインから来ていない時
            group = em.find(Group.class, Integer.parseInt(request.getParameter("id")));
            Person p = (Person) request.getSession().getAttribute("login_person");

            Belong b = em.createNamedQuery("getGroupB",Belong.class).setParameter("person",p).setParameter("group",group).getSingleResult();

            System.out.println("ええええ"+b.getUpdated_at());
            System.out.println("ああああ"+group.getUpdated_at());

            if (b.getUpdated_at().before(group.getUpdated_at())) {
                //groupが後に更新されていたら、グループのログインページにリダイレクト
                String message = group.getName()+"の情報が変更されています。グループへのログインをし直してください";
                request.getSession().setAttribute("flush", message);
                response.sendRedirect(request.getContextPath() + "/groups/login");
            } else {
                //groupのログインからきている時
                List<Task> tasks = em.createNamedQuery("GroupMemberAllTask",Task.class).setParameter("group",group).getResultList();//そのGroupのShowを取り出す
                request.setAttribute("tasks", tasks);

                request.setAttribute("group", group);

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/groups/toppage.jsp");
                rd.forward(request, response);

            }

            request.getSession().setAttribute("group_id",group.getId());
        } else {
            //groupに新しくログインし直した時（groupの情報が変わったので、ログインし直した時)・group詳細ページから戻ってきた時
            group = em.find(Group.class, (Integer)request.getSession().getAttribute("group_id"));

            List<Task> tasks = em.createNamedQuery("GroupMemberAllTask",Task.class).setParameter("group",group).getResultList();//そのGroupのShowを取り出す

            request.setAttribute("tasks", tasks);

            request.setAttribute("group", group);

            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/groups/toppage.jsp");
            rd.forward(request, response);

        }




    }

}
