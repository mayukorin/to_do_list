package controllers.hearts;

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

import models.Group;
import models.Like;
import models.Person;
import models.Task;
import utils.DBUtil;

/**
 * Servlet implementation class HeartIndexServlet
 */
@WebServlet("/hearts/index")
public class HeartIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public HeartIndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub

        //そのtaskにいいねした人と、その人の属するグループと、そのグループにログインしている人が属しているのか
        LinkedHashMap<Like,LinkedHashMap<Group,Long>> l_p_g = new LinkedHashMap<Like,LinkedHashMap<Group,Long>>();
        Person login_person = (Person)request.getSession().getAttribute("login_person");

        EntityManager em = DBUtil.createEntityManager();

        Task t = em.find(Task.class,Integer.parseInt(request.getParameter("id")));

        //そのtaskにいいねした人
        List<Like> likes = em.createNamedQuery("get_likes",Like.class).setParameter("task",t).getResultList();
        for (Like l:likes) {
            LinkedHashMap<Group,Long> like_persons_group = new LinkedHashMap<Group,Long>();
            List<Group> groups = em.createNamedQuery("getGroupsBelong",Group.class).setParameter("person",l.getPerson()).getResultList();

            for (Group g:groups) {

                Long belong_count = em.createNamedQuery("getGroupBelong",Long.class).setParameter("group",g).setParameter("person",login_person).getSingleResult();
                like_persons_group.put(g, belong_count);
            }
            l_p_g.put(l, like_persons_group);
        }

        request.setAttribute("l_p_g", l_p_g);
        request.setAttribute("liked_task", t);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/likes/index.jsp");

        rd.forward(request, response);


     }

}
