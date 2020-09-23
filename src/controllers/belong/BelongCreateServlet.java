package controllers.belong;

import java.io.IOException;
import java.sql.Timestamp;
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
import models.Belong;
import models.Group;
import models.Person;
import models.Validators.BelongValidator;
import utils.DBUtil;
import utils.EncryptUtil;

/**
 * Servlet implementation class BelongCreateServlet
 */
@WebServlet("/belongs/create")
public class BelongCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public BelongCreateServlet() {
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
            // 認証結果を格納する変数
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
                // 認証できなかったらgroup参加画面に戻る
                em.close();
                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("hasError", true);
                request.setAttribute("code", code);


                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/belongs/new.jsp");
                rd.forward(request, response);
            } else {
                // 認証できたら、そのgroupにすでに属していないかを確認する

                Person p = (Person) request.getSession().getAttribute("login_person");//ログインしている人
                String belong_error = BelongValidator.validate(g, p);

                if (!belong_error.equals("")) {
                    //すでにそのgroupに属している時

                    em.close();
                    request.getSession().setAttribute("flush", belong_error);
                    response.sendRedirect(request.getContextPath() + "/toppage/index");
                } else {
                    //新しくそのgroupに属する時
                    Belong b = new Belong();
                    b.setGroup(g);
                    b.setPerson(p);

                    Timestamp currentTime = new Timestamp(System.currentTimeMillis());
                    b.setUpdated_at(currentTime);

                    if (request.getParameter("position")!= null && !request.getParameter("position").equals("")) {
                        b.setPosition(request.getParameter("position"));
                    }

                    em.getTransaction().begin();
                    em.persist(b);
                    em.getTransaction().commit();

                    String message = g.getName()+"に新しく参加しました";


                    //セッションスコープのGroupBelongも更新/////////////
                    //Personインスタンスが所属しているグループ
                    List<Group> groups = em.createNamedQuery("getGroupsBelong",Group.class).setParameter("person",p).getResultList();
                    request.getSession().setAttribute("GroupBelong", groups);
                    //////////////////////////////////////////////

                    em.close();



                    request.getSession().setAttribute("flush", message);
                    response.sendRedirect(request.getContextPath() + "/toppage/index");
                }


            }

        }
    }
}
