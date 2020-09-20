package controllers.belong;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Belong;
import models.Group;
import models.Person;
import models.Validators.AccountValidator;
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
            EntityManager em = DBUtil.createEntityManager();

            Group g = new Group();


            g.setName(request.getParameter("name"));
            g.setCode(request.getParameter("code"));
            g.setPassword(
                    EncryptUtil.getPasswordEncrypt(
                            request.getParameter("password"),
                            (String)this.getServletContext().getAttribute("pepper")));


            //まずは、アカウント番号・グループ名・パスワードが全て入力されているかチェックする。
            List<String> error_input = AccountValidator.validate(g, null, true, false, true,null);

            if (error_input.size() > 0) {
                //アカウント番号・グループ名・パスワードが全て入力されていない時

                em.close();

                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("group", g);
                request.setAttribute("errors", error_input);

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/belongs/new.jsp");
                rd.forward(request, response);
            } else {
              //アカウント番号・グループ名・パスワードが全て入力されていた時

                Person p = (Person) request.getSession().getAttribute("login_person");
                List<String> group_error = AccountValidator.validate(p, g, false, false, false,null);

                if (group_error.size() == 0) {
                    //何もエラーがない（すでに存在しているグループに新しく所属するとき）

                    Group group = em.createNamedQuery("Group",Group.class).setParameter("code",g.getCode()).setParameter("pass",g.getPassword()).getSingleResult();

                ////belongの新規登録////////////////////
                    Belong b = new Belong();
                    b.setGroup(group);
                    b.setPerson(p);
                    if (request.getParameter("position") != null && !request.getParameter("position").equals("")) {
                        b.setPosition(request.getParameter("position"));
                    }

                    em.getTransaction().begin();
                    em.persist(b);
                    em.getTransaction().commit();
                    //////////////////////////////////////

                    em.close();
                    String message = "新しく"+g.getName()+"へ所属しました。";
                    request.getSession().setAttribute("flush", message);

                    response.sendRedirect(request.getContextPath()+"/toppage/index");//ホーム画面に戻る


                } else if (group_error.get(0).equals("そのグループにはすでに所属しています。")) {
                    //そのグループにはすでに所属しているとき
                    em.close();

                    request.setAttribute("_token", request.getSession().getId());
                    request.setAttribute("group", g);
                    request.setAttribute("errors", group_error);

                    RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/belongs/new.jsp");
                    rd.forward(request, response);
                } else if (group_error.get(0).equals("入力したグループは存在しません。")) {
                    //入力したグループが存在しないとき
                    em.close();

                    request.setAttribute("_token", request.getSession().getId());
                    request.getSession().setAttribute("new_group",g);//新しく登録しようとしているグループ
                    request.setAttribute("new_flag", "new_group");//新しいグループを登録しようとしているフラッグ
                    request.setAttribute("group", g);

                    RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/belongs/new.jsp");
                    rd.forward(request, response);
                }
            }
        }
    }
}

