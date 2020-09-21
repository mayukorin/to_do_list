package login;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controllers.account.AccountLoginServlet;
import models.Account;
import models.Person;
import utils.DBUtil;
import utils.EncryptUtil;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends AccountLoginServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub

        super.doGet(request, response);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/login/login.jsp");
        rd.forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
     // 認証結果を格納する変数
        Boolean check_result = false;

        String code = request.getParameter("code");
        String plain_pass = request.getParameter("password");

        Person p = null;
        EntityManager em = DBUtil.createEntityManager();

        if(code != null && !code.equals("") && plain_pass != null && !plain_pass.equals("")) {


            String password = EncryptUtil.getPasswordEncrypt(
                    plain_pass,
                    (String)this.getServletContext().getAttribute("pepper")
                    );

            // アカウント番号とパスワードが正しいかチェックする
            try {
                p = (Person) em.createNamedQuery("checkLoginCodeAndPassword", Account.class)
                      .setParameter("code", code)
                      .setParameter("pass", password)
                      .getSingleResult();
            } catch(NoResultException ex) {}



            if(p != null) {
                check_result = true;
            }
        }

        if(!check_result) {
            // 認証できなかったらログイン画面に戻る
            em.close();
            request.setAttribute("_token", request.getSession().getId());
            request.setAttribute("hasError", true);
            request.setAttribute("code", code);

            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/login/login.jsp");
            rd.forward(request, response);
        } else {
            // 認証できたらログイン状態にしてトップページへリダイレクト
            request.getSession().setAttribute("login_person", p);


            em.close();
            request.getSession().setAttribute("flush", "ログインしました。");
            response.sendRedirect(request.getContextPath() + "/toppage/index");
        }
    }

}
