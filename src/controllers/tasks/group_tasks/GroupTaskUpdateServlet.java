package controllers.tasks.group_tasks;

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
 * Servlet implementation class GroupTaskUpdateServlet
 */
@WebServlet("/tasks/groups/update")
public class GroupTaskUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GroupTaskUpdateServlet() {
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

            Person task_leader;
            List<String> errors;
            Boolean leader_check_flag = true;


            Task t = em.find(Task.class, (Integer)(request.getSession().getAttribute("task_id")));//変更しようとしているtask
            Person p = (Person)request.getSession().getAttribute("login_person");//loginしている人

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
        tt.setAccount(t.getAccount());


        String date = request.getParameter("deadline");


        String task_leader_code = request.getParameter("task_leader");


        //入力内容エラーチェック（リーダーもチェック）
        errors = TaskValidator.validate(tt, date, (Group)t.getAccount(), task_leader_code, leader_check_flag);
        //////////////////////////////////////////////////////////////////

        if (errors.size() > 0) {
            //入力内容にエラーがある時
            em.close();

            request.setAttribute("task", t);
            request.setAttribute("_token", request.getSession().getId());
            request.setAttribute("errors", errors);
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/tasks/group_tasks/edit.jsp");
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
            Show show = em.createNamedQuery("getShowGroupTask",Show.class).setParameter("task", t).setParameter("group", (Group)tt.getAccount()).getSingleResult();
            show.setTask(tt);

            em.getTransaction().begin();

            em.getTransaction().commit();

            em.close();

            request.getSession().setAttribute("flush", "taskの更新が完了しました。");
            request.getSession().removeAttribute("task_id");


            if (request.getSession().getAttribute("account") == null) {
                //⑪の画面に戻る
                response.sendRedirect(request.getContextPath()+"/groups/toppage");
            } else {
                //⑭の画面に戻る
                response.sendRedirect(request.getContextPath()+"/group/toppage");
            }


        }
        }
    }
}