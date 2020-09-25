package controllers.hearts;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Account;
import models.Group;
import models.Like;
import models.Person;
import models.Task;
import utils.DBUtil;

/**
 * Servlet implementation class HeartCreateServlet
 */
@WebServlet("/hearts/create")
public class HeartCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public HeartCreateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub


        EntityManager em = DBUtil.createEntityManager();

        Like like = new Like();

        //いいねをした人
        Person p = (Person) request.getSession().getAttribute("login_person");
        //いいねするtask
        Task t = em.find(Task.class,Integer.parseInt(request.getParameter("id")));

        like.setPerson(p);
        like.setTask(t);

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        like.setCreated_at(currentTime);
        like.setUpdated_at(currentTime);

        em.getTransaction().begin();
        em.persist(like);
        em.getTransaction().commit();


        if (request.getSession().getAttribute("group") == null) {
            //自身のホームページからlikeにきた時
            if (Integer.parseInt(request.getParameter("flag")) == 0) {
                response.sendRedirect(request.getContextPath()+"/toppage/index");
            } else if ((Integer.parseInt(request.getParameter("flag"))) == 1) {
                System.out.println("二宮和也");
                request.getSession().setAttribute("liked_task", t);
                response.sendRedirect(request.getContextPath()+"/tasks/persons/show");
            }
        } else if (request.getSession().getAttribute("account") == null) {
            //グループメンバー全員のtask一覧ページからlikeしにきた時
            if (Integer.parseInt(request.getParameter("flag")) == 0) {
                response.sendRedirect(request.getContextPath()+"/groups/toppage");
            } else  if (Integer.parseInt(request.getParameter("flag")) == 1) {
                //task詳細ページからlikeしにきた時

                request.getSession().setAttribute("liked_task", t);
                if (t.getAccount() instanceof Group) {
                    response.sendRedirect(request.getContextPath()+"/groups/tasks/show");
                } else {
                    response.sendRedirect(request.getContextPath()+"/members/tasks/show");
                }
            }
        } else {
            //メンバー個人またはgroupのtask一覧ページからlikeしにきた時
            Account a = (Account)request.getSession().getAttribute("account");
            Group g = (Group)request.getSession().getAttribute("group");

            if (a.getId().equals(g.getId())) {
                //groupのtask一覧ページからlikeしにきた時
                if (Integer.parseInt(request.getParameter("flag")) == 0) {
                    response.sendRedirect(request.getContextPath()+"/group/toppage");
                } else if (Integer.parseInt(request.getParameter("flag")) == 1) {
                  //groupのtask詳細ページからlikeしにきた時
                        request.getSession().setAttribute("liked_task", t);
                        response.sendRedirect(request.getContextPath()+"/groups/tasks/show");

                }
            } else {

                if (Integer.parseInt(request.getParameter("flag")) == 0) {
                  //メンバー個人のtask一覧ページからlikeしにきた時
                    response.sendRedirect(request.getContextPath()+"/members/toppage");
                } else if (Integer.parseInt(request.getParameter("flag")) == 1) {
                  //メンバー個人のtask詳細ページからlikeしにきた時
                    request.getSession().setAttribute("liked_task", t);
                    response.sendRedirect(request.getContextPath()+"/members/tasks/show");
                }
            }

        }

    }

}
