package controllers.group_member;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
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
import models.Task;
import utils.DBUtil;

/**
 * Servlet implementation class GroupMemberToppageServlet
 */
@WebServlet("/groups/toppage")
public class GroupMemberToppageServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GroupMemberToppageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        if (request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        EntityManager em = DBUtil.createEntityManager();
        Group group;
        //ログインしている本人
        Person p = (Person) request.getSession().getAttribute("login_person");


        if (request.getParameter("id") != null) {
            //そのgroupを始めて見る時

            //今から見ようとしているグループ
            group = em.find(Group.class, Integer.parseInt(request.getParameter("id")));

            //グループと本人をつなぐbelong
            Belong b = em.createNamedQuery("getGroupPersonBelong",Belong.class).setParameter("person",p).setParameter("group",group).getSingleResult();


            if (b.getUpdated_at().before(group.getUpdated_at())) {
                //groupがbelongより後に更新されていたら、
                //groupの情報が新たに更新されたということなので、
                //GroupLoginSerevletにリダイレクト

                String message = group.getName()+"の情報が変更されています。グループへのログインをし直してください";

                request.getSession().setAttribute("flush", message);
                request.getSession().setAttribute("origin_group",group);

                response.sendRedirect(request.getContextPath() + "/groups/login");
            } else {
                //belongの更新がgroupの更新より後の時

                //taskとtaskに対していいねしているかを確認
                LinkedHashMap<Task,Integer> task_like = new LinkedHashMap<Task,Integer>();

                //そのgroupで公開されているメンバー全員のtask
                List<Task> tasks = em.createNamedQuery("GroupMemberAllTask",Task.class).setParameter("group",group).getResultList();

                Date today = new Date();

                for (Task t:tasks) {
                    //そのtaskの期限
                    Date deadline = t.getDeadline();

                    long day_diff = (deadline.getTime()-today.getTime())/(1000 * 60 * 60 * 24 );//期限と今日との差


                    Long like_count = em.createNamedQuery("task_like",Long.class).setParameter("person", p).setParameter("task", t).getSingleResult();//そのtaskに対して自分がいいねをしているか

                    if (like_count == 1 && day_diff <2) {
                        //自分がいいねをしていて、かつ期限が1日以内
                        task_like.put(t, 4);
                    } else if (like_count == 0 && day_diff < 2) {
                        //自分がいいねをしていなくて、かつ期限が1日以内
                        task_like.put(t, 1);
                    } else if (like_count == 1 && day_diff < 8) {
                        //自分がいいねをしていて、期限が7日以内
                        task_like.put(t, 2);
                    } else if (like_count == 0 && day_diff <8) {
                        //自分がいいねをしていなくて、期限が７日以内
                        task_like.put(t, 5);
                    } else if (like_count == 1 && day_diff > 7) {
                        //自分がいいねをしていて、期限が7日以上
                        task_like.put(t, 6);
                    } else if (like_count == 0 && day_diff > 7) {
                        //自分がいいねをしていなくて、期限が７日以上
                        task_like.put(t, 3);
                    }

                }

                if (request.getSession().getAttribute("account") != null) {
                    //グループのメンバーのページなどからきている時

                    request.getSession().removeAttribute("account");
                }



                //そのグループのメンバー
                List<Person> persons = em.createNamedQuery("getMembers",Person.class).setParameter("group",group).getResultList();//そのgroupに属している人

                request.setAttribute("task_like", task_like);

                //セッションスコープに、今から見ようとしているgroupとそのgroupに属するメンバーを格納
                request.getSession().setAttribute("group",group);
                request.getSession().setAttribute("ps", persons);



                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/group_member/groupMembertoppage.jsp");
                rd.forward(request, response);

            }


        } else {


            if (request.getSession().getAttribute("account") != null) {


                request.getSession().removeAttribute("account");
            }

            group = (Group)request.getSession().getAttribute("group");
            //そのグループのメンバー
            List<Person> persons = em.createNamedQuery("getMembers",Person.class).setParameter("group",group).getResultList();

            //そのgroupで公開されているメンバー全員のtask
            //taskとtaskに対していいねしているかを確認
            LinkedHashMap<Task,Integer> task_like = new LinkedHashMap<Task,Integer>();

            //そのgroupで公開されているメンバー全員のtask
            List<Task> tasks = em.createNamedQuery("GroupMemberAllTask",Task.class).setParameter("group",group).getResultList();
            Date today = new Date();

            for (Task t:tasks) {
                //taskの期限
                Date deadline = t.getDeadline();
                long day_diff = (deadline.getTime()-today.getTime())/(1000 * 60 * 60 * 24 );//期限と今日との差


                Long like_count = em.createNamedQuery("task_like",Long.class).setParameter("person", p).setParameter("task", t).getSingleResult();//そのtaskに対して自分がいいねをしているか

                if (like_count == 1 && day_diff <2) {
                    //自分がいいねをしていて、かつ期限が1日以内
                    task_like.put(t, 4);
                } else if (like_count == 0 && day_diff < 2) {
                    //自分がいいねをしていなくて、かつ期限が1日以内
                    task_like.put(t, 1);
                } else if (like_count == 1 && day_diff < 8) {
                    //自分がいいねをしていて、期限が7日以内
                    task_like.put(t, 2);
                } else if (like_count == 0 && day_diff <8) {
                    //自分がいいねをしていなくて、期限が７日以内
                    task_like.put(t, 5);
                } else if (like_count == 1 && day_diff > 7) {
                    //自分がいいねをしていて、期限が7日以上
                    task_like.put(t, 6);
                } else if (like_count == 0 && day_diff > 7) {
                    //自分がいいねをしていなくて、期限が７日以上
                    task_like.put(t, 3);
                }

            }


            request.setAttribute("task_like", task_like);
            request.getSession().setAttribute("ps", persons);
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/group_member/groupMembertoppage.jsp");
            rd.forward(request, response);

        }




    }

}
