package controllers.account;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Person;
import models.Validators.PersonValidator;
import utils.DBUtil;
import utils.EncryptUtil;

/**
 * Servlet implementation class PersonUpdate
 */
@WebServlet("/persons/update")
public class PersonUpdate extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PersonUpdate() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        String _token = (String)request.getParameter("_token");
        if (_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();

            Person p = em.find(Person.class, ((Person)(request.getSession().getAttribute("login_person"))).getId());//ログインしている人

            //現在の値と異なるアカウント番号が入力されていたら
            //重複チェックを行う指定をする
            Boolean code_duplicate_check_flag = true;
            if (p.getCode().equals(request.getParameter("code"))) {
                code_duplicate_check_flag = false;
            } else {
                p.setCode(request.getParameter("code"));
            }

            //パスワード欄に入力があったら
            // パスワードの入力値チェックを行う指定をする
            Boolean password_check_flag = true;
            String password = request.getParameter("password");
            if(password == null || password.equals("")) {
                password_check_flag = false;
            } else {
                p.setPassword(
                        EncryptUtil.getPasswordEncrypt(
                                password,
                                (String)this.getServletContext().getAttribute("pepper")
                                )
                        );
            }


            p.setName(request.getParameter("name"));


            List<String> errors = PersonValidator.validate(p, code_duplicate_check_flag, password_check_flag);
            if (errors.size() > 0) {
                em.close();

                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("account", p);
                request.setAttribute("errors", errors);

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/persons/edit.jsp");
                rd.forward(request, response);
            } else {
                em.getTransaction().begin();
                em.getTransaction().commit();
                em.close();
                request.getSession().setAttribute("flush", "アカウント情報の更新が完了しました。");

                response.sendRedirect(request.getContextPath()+"/logout");
            }

        }
    }

}
