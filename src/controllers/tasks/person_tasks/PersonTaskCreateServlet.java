package controllers.tasks.person_tasks;

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

import models.Group;
import models.Person;
import models.Show;
import models.Task;
import models.Validators.TaskValidator;
import utils.DBUtil;

/**
 * Servlet implementation class PersonTaskCreateServlet
 */
@WebServlet("/tasks/persons/create")
public class PersonTaskCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PersonTaskCreateServlet() {
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

            //ログインしている人物が所属しているグループ
            @SuppressWarnings("unchecked")
            List<Group> groups = (List<Group>) request.getSession().getAttribute("GroupBelong");
            //ログインしている人
            Person p = (Person)request.getSession().getAttribute("login_person");

            Task t = new Task();

            t.setAccount(p);
            t.setTitle(request.getParameter("title"));
            t.setMemo(request.getParameter("memo"));

            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            t.setCreated_at(currentTime);
            t.setUpdated_at(currentTime);

            t.setFinish_flag(0);

            String date = request.getParameter("deadline");

            Boolean leader_check_flag = false;
            List<String> errors;

            //入力内容のエラーチェック
            errors = TaskValidator.validate(t, date, null, null, leader_check_flag);



            if (errors.size() > 0) {
                //入力内容にエラーがある時

                request.setAttribute("task", t);
                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("errors", errors);
                request.setAttribute("groups", groups);

                em.close();
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/tasks/person_tasks/new.jsp");
                rd.forward(request, response);
            } else {
                //エラーがないとき

                t.setUpdate_person(p);//今loginしている人がこのtaskを作った

                t.setTask_leader(p);//自分自身がTask_leader

                //taskを保存する。
                em.getTransaction().begin();
                em.persist(t);
                em.getTransaction().commit();


                //taskを所属groupに公開するためのshowインスタンスを作成する
                for(Group group:groups) {
                    String id = (group.getId()).toString();


                    if (request.getParameter(id)!= null) {
                        //チェックボックスにチェックがついている時、そのgroupとtaskに関するshowを登録する。
                        Show show = new Show();
                        show.setTask(t);
                        show.setGroup(group);//そのグループは、task内容を見ることができる。

                        em.getTransaction().begin();
                        em.persist(show);
                        em.getTransaction().commit();

                    }
                }

                request.getSession().removeAttribute("account");
                em.close();
                request.getSession().setAttribute("flush", "taskの登録が完了しました。");

                response.sendRedirect(request.getContextPath()+"/toppage/index");//ホーム画面に戻る
            }
        }
    }
}
