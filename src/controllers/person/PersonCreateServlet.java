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

import models.Person;
import models.Validators.PersonValidator;
import utils.DBUtil;
import utils.EncryptUtil;

/**
 * Servlet implementation class PersonCreateServlet
 */
@WebServlet("/persons/create")
public class PersonCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PersonCreateServlet() {
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

            Person p = new Person();

            //入力された名前・アカウント番号・パスワード
            p.setName(request.getParameter("name"));
            p.setCode(request.getParameter("code"));
            p.setPassword(
                    EncryptUtil.getPasswordEncrypt(
                            request.getParameter("password"),
                            (String)this.getServletContext().getAttribute("pepper")));

            Boolean code_duplicate_check_flag = true;
            Boolean password_check_flag = true;

            //入力内容のエラーチェック
            List<String> errors = PersonValidator.validate(p, code_duplicate_check_flag, password_check_flag);


            if (errors.size() > 0) {
                //もし入力内容にエラーがあったら
                ///perwons/new.jspに戻る
                em.close();

                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("person", p);
                request.setAttribute("errors", errors);

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/persons/new.jsp");
                rd.forward(request, response);
            } else {
                //入力内容にエラーがなければ
                //personインスタンスを保存し
                //ログイン画面に戻る
                em.getTransaction().begin();
                em.persist(p);
                em.getTransaction().commit();
                em.close();


                request.getSession().setAttribute("flush", "アカウントの登録が完了しました。");

                response.sendRedirect(request.getContextPath()+"/login");//ログイン画面に戻る

            }
        }
    }

}
