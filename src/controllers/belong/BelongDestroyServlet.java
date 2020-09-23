package controllers.belong;

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
import models.Show;
import utils.DBUtil;

/**
 * Servlet implementation class BelongDestroyServlet
 */
@WebServlet("/belongs/destroy")
public class BelongDestroyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public BelongDestroyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub

        String _token = request.getParameter("_token");
        System.out.println("奈々ななn");
        if(_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();

            //削除しようとしているbelong
            Belong b = em.find(Belong.class, (Integer)request.getSession().getAttribute("belong_id"));

            //退会しようとしているgroup
            Group g = b.getGroup();

            if (g.getLeader().getId().equals(b.getPerson().getId())) {
                //退会しようとしているGroupのリーダーだったら退会できない

                String error = g.getName()+"のリーダーのため、退会できません";

                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("error", error);
                request.setAttribute("belong", b);


                em.close();
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/belongs/show.jsp");
                rd.forward(request, response);
            } else {

                //groupの仕事で、その人がリーダのものがあるか
                List<Show> leader_task_flag = em.createNamedQuery("getShowLeader",Show.class).setParameter("group",g).setParameter("person",b.getPerson()).getResultList();

                if (leader_task_flag.size() != 0) {
                   //groupの仕事で、その人がリーダのものがあるか

                    String error = "リーダーとなっているtaskがあるため、退会できません";

                    request.setAttribute("_token", request.getSession().getId());
                    request.setAttribute("error", error);
                    request.setAttribute("belong", b);


                    em.close();
                    RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/belongs/show.jsp");
                    rd.forward(request, response);

                } else {
                    //退会できる

                    //その人自身の仕事で、退会するgroupに公開するために設定していたshow
                    List<Show> shows = em.createNamedQuery("getShowOpenGroup",Show.class).setParameter("group", g).setParameter("person", b.getPerson()).getResultList();

                    if (shows.size() != 0) {

                        for (Show s:shows) {
                            em.getTransaction().begin();
                            em.remove(s);
                            em.getTransaction().commit();
                        }
                    }

                    em.getTransaction().begin();
                    em.remove(b);
                    em.getTransaction().commit();

                    //セッションスコープのGroupBelongも更新/////////////
                    Person p = (Person) request.getSession().getAttribute("login_person");

                    //Personインスタンスが所属しているグループ
                    List<Group> groups = em.createNamedQuery("getGroupsBelong",Group.class).setParameter("person",p).getResultList();
                    request.getSession().setAttribute("GroupBelong", groups);
                    ///////////////////////////////////////////////////

                    em.close();


                    String message = b.getGroup().getName()+"の退会が完了しました。";
                    request.getSession().setAttribute("flush", message);


                    response.sendRedirect(request.getContextPath() + "/persons/show");
                }


            }

        }
    }
}