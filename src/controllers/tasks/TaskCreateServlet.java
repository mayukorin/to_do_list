package controllers.tasks;

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
import models.Show;
import models.Task;
import models.Validators.TaskValidator;
import utils.DBUtil;

/**
 * Servlet implementation class TaskCreateServlet
 */
@WebServlet("/tasks/create")
public class TaskCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TaskCreateServlet() {
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
            List<Group> groups;
            Person task_leader;

            Account a = (Account)request.getSession().getAttribute("a");//作成しようとしているtaskのaccount

            Task t = new Task();

            t.setAccount(a);

            t.setTitle(request.getParameter("title"));
            t.setMemo(request.getParameter("memo"));

            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            t.setCreated_at(currentTime);
            t.setUpdated_at(currentTime);


            String date = request.getParameter("deadline");

            Boolean leader_check_flag = true;
            List<String> errors;

            if (a instanceof Group) {
                //groupのtaskを作成しようとしている時

                    String task_leader_code = request.getParameter("task_leader");
                    errors = TaskValidator.validate(t, date, (Group)a, task_leader_code, leader_check_flag);


            } else {
                //person（自分自身）のtaskを作成しようとしている時
                leader_check_flag = false;
                errors = TaskValidator.validate(t, date, null, null, leader_check_flag);
            }


            if (errors.size() > 0) {
                em.close();

                request.setAttribute("task", t);
                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("errors", errors);
                if (a instanceof Person) {
                   //Personのtaskを新規作成しようとしている時(ログインしている人物自身のtaskを作成しようとしている時)
                    //その人が所属しているグループを確認
                    groups = em.createNamedQuery("getGroupsBelong",Group.class).setParameter("person", (Person)a).getResultList();
                    request.setAttribute("groups", groups);
                }

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/tasks/new.jsp");
                rd.forward(request, response);
            } else {
                //エラーがないとき

                t.setNew_flag(1);//このtaskは新しい
                Person p = (Person)request.getSession().getAttribute("login_person");

                t.setUpdate_person(p);//今loginしている人がこのtaskを作った

                if (a instanceof Person) {
                  //person（自分自身）のtaskを作成しようとしている時
                    t.setTask_leader(p);//自分自身がTask_leader
                    //taskを保存する。
                    em.getTransaction().begin();
                    em.persist(t);
                    em.getTransaction().commit();

                    //taskの公開範囲を設定
                    groups = em.createNamedQuery("getGroupsBelong",Group.class).setParameter("person", (Person)a).getResultList();
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
                } else {
                  //groupのtaskを作成しようとしている時
                    task_leader = (Person) em.createNamedQuery("getAccount",Account.class).setParameter("code",request.getParameter("task_leader")).getSingleResult();
                    t.setTask_leader(task_leader);
                    //taskを保存する。
                    em.getTransaction().begin();
                    em.persist(t);
                    em.getTransaction().commit();
                    //groupのメンバー全員にtaskをに公開するために、Showを設定


                    Show show = new Show();
                    show.setTask(t);
                    show.setGroup((Group)a);

                    em.getTransaction().begin();
                    em.persist(show);
                    em.getTransaction().commit();


                }



                em.close();

                request.getSession().setAttribute("flush", "taskの登録が完了しました。");

                if (request.getSession().getAttribute("group") == null) {
                    //グループ画面からtaskを追加していない時
                    response.sendRedirect(request.getContextPath()+"/toppage/index");//ホーム画面に戻る
                } else {
                    //グループ画面からtaskを追加した時
                    response.sendRedirect(request.getContextPath()+"/groups/member");//taskを追加したメンバーのtask一覧画面に戻る

                }

            }


        }

    }

}
