package login;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Account;
import models.Belong;
import models.Group;
import models.Person;
import utils.DBUtil;
import utils.EncryptUtil;

/**
 * Servlet implementation class GroupLoginServlet
 */
@WebServlet("/groups/login")
public class GroupLoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GroupLoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub

        request.setAttribute("_token", request.getSession().getId());
        request.setAttribute("hasError", false);
        if (request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/login/group_login.jsp");
        rd.forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub

        String _token = (String)request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId())) {
            Boolean check_result = false;

            String code = request.getParameter("code");
            String plain_pass = request.getParameter("password");

            Group g = null;
            EntityManager em = DBUtil.createEntityManager();

            if(code != null && !code.equals("") && plain_pass != null && !plain_pass.equals("")) {


                String password = EncryptUtil.getPasswordEncrypt(
                        plain_pass,
                        (String)this.getServletContext().getAttribute("pepper")
                        );

                // アカウント番号とパスワードが正しいかチェックする
                try {
                    g = (Group) em.createNamedQuery("checkLoginCodeAndPassword", Account.class)
                          .setParameter("code", code)
                          .setParameter("pass", password)
                          .getSingleResult();
                } catch(NoResultException ex) {}



                if(g != null) {
                    check_result = true;
                }
            }

            if(!check_result) {
                // 認証できなかったらgroupログイン画面に戻る
                em.close();
                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("hasError", true);
                request.setAttribute("code", code);


                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/groups/login.jsp");
                rd.forward(request, response);
            } else {
                //認証に成功した時

                Person p = (Person) request.getSession().getAttribute("login_person");

                Belong b = em.createNamedQuery("getGroupB",Belong.class).setParameter("person",p).setParameter("group",g).getSingleResult();

                Timestamp currentTime = new Timestamp(System.currentTimeMillis());
                b.setUpdated_at(currentTime);//belongの時間を更新

                //belongを保存
                em.getTransaction().begin();
                em.getTransaction().commit();

                request.getSession().setAttribute("group",g);

                String message = g.getName()+"にログインし直しました";
                request.getSession().setAttribute("flush", message);
                response.sendRedirect(request.getContextPath() + "/groups/toppage");
            }
        }
    }
}