package login;

import java.io.IOException;
import java.sql.Timestamp;
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
            EntityManager em = DBUtil.createEntityManager();

            Group g = new Group();


            g.setName(request.getParameter("name"));
            g.setCode(request.getParameter("code"));
            g.setPassword(
                    EncryptUtil.getPasswordEncrypt(
                            request.getParameter("password"),
                            (String)this.getServletContext().getAttribute("pepper")));


            //まずは、アカウント番号・パスワードが全て入力されているかチェックする。
            List<String> error_input = AccountValidator.validate(g, null, false, false, true,null);

            if (error_input.size() > 0) {
                //アカウント番号・パスワードが全て入力されていない時

                em.close();

                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("hasError", error_input);

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/groups/login.jsp");
                rd.forward(request, response);
            } else {
              //アカウント番号・パスワードが全て入力されている時
                Person p = (Person) request.getSession().getAttribute("login_person");
                List<String> group_error = AccountValidator.validate(p, g, false, false, false,null);

                if (group_error.get(0).equals("入力したグループは存在しません。")) {
                    //アカウント番号・パスワードが間違っている時

                    em.close();

                    request.setAttribute("_token", request.getSession().getId());
                    request.setAttribute("hasError", true);

                    RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/login/group_login.jsp");
                    rd.forward(request, response);
                } else if (group_error.get(0).equals("そのグループにはすでに所属しています。"))  {
                    //ログインに成功した時

                    Group group = em.createNamedQuery("Group",Group.class).setParameter("code",g.getCode()).setParameter("pass",g.getPassword()).getSingleResult();

                    Belong b = em.createNamedQuery("getGroupB",Belong.class).setParameter("person",p).setParameter("group",group).getSingleResult();

                    Timestamp currentTime = new Timestamp(System.currentTimeMillis());
                    b.setUpdated_at(currentTime);//belongの時間を更新

                    //belongを保存
                    em.getTransaction().begin();
                    em.getTransaction().commit();

                    request.getSession().setAttribute("group_id",group.getId());

                    String message = group.getName()+"にログインし直しました";
                    request.getSession().setAttribute("flush", message);
                    response.sendRedirect(request.getContextPath() + "/groups/toppage");

                }
            }
        }
    }
}
