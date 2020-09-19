package toppage;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Group;
import models.Person;
import models.Task;
import utils.DBUtil;

/**
 * Servlet implementation class TopPageIndexServlet
 */
@WebServlet("/toppage/index")
public class TopPageIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TopPageIndexServlet() {
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

        if (request.getSession().getAttribute("group_id") != null) {
            //groupのtask一覧から戻ってきた時
            request.getSession().removeAttribute("group_id");
        }

        EntityManager em = DBUtil.createEntityManager();

        Person p = (Person) request.getSession().getAttribute("login_person");//ログインしている人

        List<Task> tasks = em.createNamedQuery("getPersonsTask",Task.class).setParameter("account",p).getResultList();//ログインしている人のTask

      //ログインしている人が所属しているグループ
        List<Group> groups = em.createNamedQuery("getGroupsBelong",Group.class).setParameter("person", p).getResultList();

        em.close();

        request.setAttribute("tasks", tasks);
        request.setAttribute("groups", groups);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/topPage/index.jsp");
        rd.forward(request, response);

    }

}
