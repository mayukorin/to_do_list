package controllers.group;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Belong;
import models.Group;
import models.Person;
import utils.DBUtil;

/**
 * Servlet implementation class GroupCreateServlet
 */
@WebServlet("/groups/create")
public class GroupCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GroupCreateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        String _token = (String)request.getParameter("_token");

        if(_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();

            Person p = (Person)request.getSession().getAttribute("login_person");

            ///groupの新規登録///////////////////
            Group g = (Group) request.getSession().getAttribute("new_group");//BelongCreateから持ってきた、new.jspで入力されていたgroup

            g.setLeader(p);//新しく作るgroupのリーダーをloginしてる人（groupを新規作成した人）にする。
            em.getTransaction().begin();
            em.persist(g);
            em.getTransaction().commit();
            /////////////////////////////////////
            ////belongの新規登録////////////////////
            Belong b = new Belong();
            b.setGroup(g);
            b.setPerson(p);
            b.setPosition("リーダー");

            em.getTransaction().begin();
            em.persist(b);
            em.getTransaction().commit();
            //////////////////////////////////////

            em.close();
            String message = "新しく"+g.getName()+"を作成しました。";

            request.getSession().setAttribute("flush", message);

            response.sendRedirect(request.getContextPath()+"/toppage/index");//ホーム画面に戻る

        }
    }
}

