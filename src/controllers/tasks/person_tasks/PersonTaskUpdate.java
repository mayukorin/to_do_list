package controllers.tasks.person_tasks;

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

import models.Group;
import models.Person;
import models.Show;
import models.Task;
import models.Validators.TaskValidator;
import utils.DBUtil;

/**
 * Servlet implementation class PersonTaskUpdate
 */
@WebServlet("/tasks/persons/update")
public class PersonTaskUpdate extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PersonTaskUpdate() {
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

            Person p = (Person)request.getSession().getAttribute("login_person");
            //ログインしている人が所属しているグループ
             List<Group> groups = em.createNamedQuery("getGroupsBelong",Group.class).setParameter("person", p).getResultList();
            List<String> errors;
            Boolean leader_check_flag = false;


            Task t = em.find(Task.class, (Integer)(request.getSession().getAttribute("task_id")));//変更しようとしているtask


            t.setTitle(request.getParameter("title"));
            t.setMemo(request.getParameter("memo"));

            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            t.setCreated_at(currentTime);
            t.setUpdated_at(currentTime);
            String date = request.getParameter("deadline");


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

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/tasks/person_tasks/edit.jsp");
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


                request.getSession().setAttribute("flush", "taskの更新が完了しました。");
                request.getSession().removeAttribute("task_id");

                if (request.getSession().getAttribute("group") == null) {
                    //自身のホームページから編集しにきた時

                    response.sendRedirect(request.getContextPath()+"/toppage/index");
                } else if (request.getSession().getAttribute("account") == null) {
                    //グループメンバー全員のtask一覧ページからtaskをクリックして編集しにきた時

                    response.sendRedirect(request.getContextPath()+"/groups/toppage");
                } else {
                    //メンバー個人のtask一覧ページからtaskをクリックして編集しにきた時

                    response.sendRedirect(request.getContextPath()+"/members/toppage");
                }

            }
        }
    }
}