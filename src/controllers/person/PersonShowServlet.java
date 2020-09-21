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

import models.Account;
import models.Group;
import models.Person;
import utils.DBUtil;

/**
 * Servlet implementation class PersonShowServlet
 */
@WebServlet("/persons/show")
public class PersonShowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PersonShowServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        //今ログインしている人が、詳細を見ようとしているgroupのleaderなのかを確認
        Integer leader_flag = 0;

        EntityManager em = DBUtil.createEntityManager();


        //詳細を見ようとしているアカウント
        Account a = em.find(Account.class, Integer.parseInt(request.getParameter("id")));


        if (a instanceof Person) {
            //Personインスタンスの詳細を見ようとしている時

            //Personインスタンスが所属しているグループ
            List<Group> groups = em.createNamedQuery("getGroupsBelong",Group.class).setParameter("person",(Person)a).getResultList();
            request.setAttribute("groups", groups);
        } else if (a instanceof Group) {
            //Groupインスタンスのアカウント詳細を見ようとしている時


            List<Person> persons = em.createNamedQuery("getMembers",Person.class).setParameter("group",(Group)a).getResultList();//そのgroupに属している人
            request.setAttribute("persons", persons);

            if (((Group)a).getLeader().getId() == ((Person)request.getSession().getAttribute("login_person")).getId()) {
                //今ログインしている人がそのgroupのleaderである時
                leader_flag = 1;
            }
        }

        em.close();

        request.setAttribute("account", a);
        request.setAttribute("leader_flag", leader_flag);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/persons/show.jsp");
        rd.forward(request, response);

    }

}
