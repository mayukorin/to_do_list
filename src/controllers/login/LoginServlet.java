package controllers.login;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
import utils.EncryptUtil;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
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

        request.setAttribute("_token", request.getSession().getId());
        request.setAttribute("hasError", false);

        if (request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

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

        //入力されたアカウント番号とパスワード
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

            if(!check_result) {
                // 認証できなかったらログイン画面に戻る
                em.close();
                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("hasError", true);
                request.setAttribute("code", code);

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/login/login.jsp");
                rd.forward(request, response);
            } else {
                //認証できたらログイン状態にしてトップページへリダイレクト
                request.getSession().setAttribute("login_person", p);

                //そのPersonインスタンスが所属しているグループを取得し、セッションスコープ GroupBelongに格納
                List<Group> groups = em.createNamedQuery("getGroupsBelong",Group.class).setParameter("person",p).getResultList();
                request.getSession().setAttribute("GroupBelong", groups);


                em.close();
                request.getSession().setAttribute("flush", "ログインしました。");
                response.sendRedirect(request.getContextPath() + "/toppage/index");
            }
        }
    }
}

