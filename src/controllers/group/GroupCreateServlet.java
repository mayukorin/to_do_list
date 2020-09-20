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

import models.Belong;
import models.Group;
import models.Person;
import models.Validators.GroupValidator;
import utils.DBUtil;
import utils.EncryptUtil;

/**
 * Servlet implementation class GroupCreateServlet
 */
@WebServlet("/groups/create")
public class GroupCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GroupCreateServlet() {
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



            String leader_code = null;
            Boolean code_duplicate_check_flag = true;
            Boolean password_check_flag = true;
            Boolean leader_check_flag = false;
            List<String> errors = GroupValidator.validate(g, leader_code, code_duplicate_check_flag, password_check_flag, leader_check_flag);

            if (errors.size() > 0) {
                em.close();

                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("account", g);
                request.setAttribute("errors", errors);

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/groups/new.jsp");
                rd.forward(request, response);
            } else {
                Person p = (Person)request.getSession().getAttribute("login_person");


                Timestamp currentTime = new Timestamp(System.currentTimeMillis());
                g.setUpdated_at(currentTime);

                g.setLeader(p);//新しく作るgroupのリーダーを、loginしてる人（groupを新規作成した人）にする。
                em.getTransaction().begin();
                em.persist(g);
                em.getTransaction().commit();
                /////////////////////////////////////
                ////belongの新規登録////////////////////
                Belong b = new Belong();
                b.setGroup(g);
                b.setPerson(p);
                b.setPosition("リーダー");

                b.setUpdated_at(currentTime);

                em.getTransaction().begin();
                em.persist(b);
                em.getTransaction().commit();
                //////////////////////////////////////

                em.close();

                String message = "新しく"+g.getName()+"を作成しました。";

                request.getSession().setAttribute("flush", message);

                response.sendRedirect(request.getContextPath()+"/toppage/index");//ホーム画面に戻る

            }

        }
    }
}

