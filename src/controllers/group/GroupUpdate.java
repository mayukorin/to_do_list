package controllers.group;

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

import models.Account;
import models.Group;
import models.Person;
import models.Validators.GroupValidator;
import utils.DBUtil;
import utils.EncryptUtil;

/**
 * Servlet implementation class GroupUpdate
 */
@WebServlet("/groups/update")
public class GroupUpdate extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GroupUpdate() {
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

            List<String> errors;

            Group g = em.find(Group.class, ((Group)request.getSession().getAttribute("group")).getId());

          //現在の値と異なるアカウント番号が入力されていたら
            //重複チェックを行う指定をする
            Boolean code_duplicate_check_flag = true;
            if (g.getCode().equals(request.getParameter("code"))) {
                code_duplicate_check_flag = false;
            } else {
                g.setCode(request.getParameter("code"));
            }

            //パスワード欄に入力があったら
            // パスワードの入力値チェックを行う指定をする
            Boolean password_check_flag = true;
            String password = request.getParameter("password");
            if(password == null || password.equals("")) {
                password_check_flag = false;
            } else {
                g.setPassword(
                        EncryptUtil.getPasswordEncrypt(
                                password,
                                (String)this.getServletContext().getAttribute("pepper")
                                )
                        );
            }


            g.setName(request.getParameter("name"));

            Boolean leader_check_flag=true;




            if (!g.getLeader().getCode().equals(request.getParameter("leader"))) {
              //そのgroupのleaderについてもチェック

                String leader_code = request.getParameter("leader");

                errors = GroupValidator.validate(g, leader_code, code_duplicate_check_flag, password_check_flag, leader_check_flag);

            } else {
                //groupのleaderは変わってないので、leaderについてはチェックする必要なし
                leader_check_flag=false;
                errors = GroupValidator.validate(g, null, code_duplicate_check_flag, password_check_flag, leader_check_flag);

            }

            if (errors.size() > 0) {
                em.close();

                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("account", g);
                request.setAttribute("errors", errors);

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/groups/edit.jsp");
                rd.forward(request, response);
            } else {
                Timestamp currentTime = new Timestamp(System.currentTimeMillis());
                g.setUpdated_at(currentTime);

                if (leader_check_flag == true) {
                    //leaderが新しく変わった
                    Person leader = (Person) em.createNamedQuery("getAccount",Account.class).setParameter("code",request.getParameter("leader")).getSingleResult();
                    g.setLeader(leader);
                }

                em.getTransaction().begin();
                em.getTransaction().commit();
                em.close();

                request.getSession().removeAttribute("group_id");
                request.getSession().setAttribute("flush", "アカウント情報の更新が完了しました。");

                response.sendRedirect(request.getContextPath()+"/logout");
            }




        }

    }

}
