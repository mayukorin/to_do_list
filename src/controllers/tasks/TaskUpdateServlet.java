package controllers.tasks;

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
import models.Group;
import models.Person;
import models.Show;
import models.Task;
import models.Validators.TaskValidator;
import utils.DBUtil;

/**
 * Servlet implementation class TaskUpdateServlet
 */
@WebServlet("/tasks/update")
public class TaskUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TaskUpdateServlet() {
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
            List<Group> groups;
            Person task_leader;
            List<String> errors;
            Boolean leader_check_flag = true;

            Account a = (Account)request.getSession().getAttribute("account");//編集しようとしているtaskのaccount
            Task t = em.find(Task.class, (Integer)(request.getSession().getAttribute("task_id")));//変更しようとしているtask
            Person p = (Person)request.getSession().getAttribute("login_person");//loginしている人

            if (a instanceof Person) {
                //自分自身のtaskを編集しようとしている時

               //ログインしている人が所属しているグループ
                groups = em.createNamedQuery("getGroupsBelong",Group.class).setParameter("person", p).getResultList();

                t.setTitle(request.getParameter("title"));
                t.setMemo(request.getParameter("memo"));

                Timestamp currentTime = new Timestamp(System.currentTimeMillis());
                t.setCreated_at(currentTime);
                t.setUpdated_at(currentTime);
                String date = request.getParameter("deadline");

                leader_check_flag = false;
                //入力内容エラーチェック
                errors = TaskValidator.validate(t, date, null, null, leader_check_flag);

                if (errors.size() > 0) {
                    //入力内容にエラーがある時、編集画面にリダイレクト


                    request.setAttribute("task", t);
                    request.setAttribute("_token", request.getSession().getId());
                    request.setAttribute("errors", errors);


                    //その仕事を公開しているグループ
                    List<Group> shows_group = em.createNamedQuery("getGroupShow",Group.class).setParameter("task",t).getResultList();
                    groups.removeAll(shows_group);//所属しているグループから、公開しているグループを引く

                    request.setAttribute("groups", groups);
                    request.setAttribute("shows_group", shows_group);

                    em.close();

                    RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/tasks/edit.jsp");
                    rd.forward(request, response);

                } else {
                    //入力内容にエラーがない時は、保存

                  //taskを保存する。
                    em.getTransaction().begin();
                    em.getTransaction().commit();


                    //taskの公開範囲を設定
                    for(Group group:groups) {

                        Show show_flag;
                        String id = (group.getId()).toString();
                        //更新しようとしているtaskとgroupをつなくshowがあるか確認する
                        try {
                            show_flag = em.createNamedQuery("getShowGroupTask",Show.class).setParameter("group", group).setParameter("task", t).getSingleResult();
                          //更新しようとしているtaskとgroupをつなくshowがある(そのgroupは、元々taskを見ることができていた時)

                            if (request.getParameter(id) == null) {
                                //そのgroupが見ることができなくしたい時、そのshowを消す

                                em.getTransaction().begin();
                                em.remove(show_flag);
                                em.getTransaction().commit();

                            }
                        } catch(NoResultException e) {
                            //そのgroupはtaskを見ることができない時
                            if (request.getParameter(id) != null && request.getParameter(id).equals(id)) {
                                //チェックボックスにチェックがついている時、そのgroupとtaskに関するshowを登録する。
                                //そのtaskをgroupが見れるようにする
                                Show show = new Show();
                                show.setTask(t);
                                show.setGroup(group);//そのグループは、task内容を見ることができる。

                                em.getTransaction().begin();
                                em.persist(show);
                                em.getTransaction().commit();
                            }
                        }
                    }

                    em.close();

                    request.getSession().removeAttribute("acount");
                    request.getSession().setAttribute("flush", "taskの更新が完了しました。");


                    response.sendRedirect(request.getContextPath()+"/toppage/index");

                }
            } else {
                //groupのtaskを編集しようとしている時
                //①新たにtaskインスタンスを生成する
                //②taskインスタンスのorigin_task_idに、元々のtaskのidを設定する
                //③showインスタンスを①で更新したtaskに設定

                //①新たにtaskインスタンスを生成する/////////////////////////////
                Task tt = new Task();

                tt.setTitle(request.getParameter("title"));
                tt.setMemo(request.getParameter("memo"));

                Timestamp currentTime = new Timestamp(System.currentTimeMillis());
                tt.setCreated_at(currentTime);
                tt.setUpdated_at(currentTime);
                tt.setUpdate_person(p);
                tt.setAccount(a);


                String date = request.getParameter("deadline");


                String task_leader_code = request.getParameter("task_leader");


                //入力内容エラーチェック（リーダーもチェック）
                errors = TaskValidator.validate(tt, date, (Group)a, task_leader_code, leader_check_flag);
                //////////////////////////////////////////////////////////////////

                if (errors.size() > 0) {
                    //入力内容にエラーがある時
                    em.close();

                    request.setAttribute("task", t);
                    request.setAttribute("_token", request.getSession().getId());
                    request.setAttribute("errors", errors);

                    RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/tasks/edit.jsp");
                    rd.forward(request, response);
                } else {
                    //入力内容にエラーがない時
                    //②taskインスタンスのorigin_task_idに、元々のtaskのidを設定する///////////////////////////////////////////////

                    if (t.getOrigin_task_id() == null) {
                        //始めての更新の時
                        //origin_task_idに、自身のtaskのidを設定する
                        t.setOrigin_task_id(t.getId());

                    }
                    //taskインスタンスのorigin_task_idに、元々のtaskのid（更新前のtaskのorigin_task_idの値)を設定する///////
                    tt.setOrigin_task_id(t.getOrigin_task_id());

                    /////////////////////////////////////////////////////////////////////////////////////////////////////////////

                    //taskのleaderを設定
                    task_leader = (Person) em.createNamedQuery("getAccount",Account.class).setParameter("code",task_leader_code).getSingleResult();
                    tt.setTask_leader(task_leader);

                   //taskを保存する。
                    em.getTransaction().begin();
                    em.persist(tt);
                    em.getTransaction().commit();

                   //③showインスタンスを①で更新したtaskに設定//////////////////////////////////////////////////////////
                    Show show = em.createNamedQuery("getShowGroupTask",Show.class).setParameter("task", t).setParameter("group", (Group)a).getSingleResult();
                    show.setTask(tt);

                    em.getTransaction().begin();

                    em.getTransaction().commit();

                    em.close();

                    request.getSession().setAttribute("flush", "taskの更新が完了しました。");



                    //グループ画面からtaskを追加した時
                    response.sendRedirect(request.getContextPath()+"/groups/member");//taskを追加したメンバーのtask一覧画面に戻る
                }
            }
        }
    }
}



