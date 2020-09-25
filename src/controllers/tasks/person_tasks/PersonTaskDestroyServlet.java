package controllers.tasks.person_tasks;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Show;
import models.Task;
import utils.DBUtil;

/**
 * Servlet implementation class PersonTaskDestroyServlet
 */
@WebServlet("/tasks/persons/destroy")
public class PersonTaskDestroyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PersonTaskDestroyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub

        String _token = request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();

            Task t = em.find(Task.class, (Integer)request.getSession().getAttribute("task_id"));

            //そのtaskを公開しているshowを取り出す
            List<Show> shows = em.createNamedQuery("getShows",Show.class).setParameter("task", t).getResultList();

            if (shows != null) {
                //そのtaskを公開しているshowがあれば
                //そのshowを一つずつ削除する

                for (Show show:shows) {
                    em.getTransaction().begin();
                    em.remove(show);
                    em.getTransaction().commit();
                }

                //taskを削除する
                em.getTransaction().begin();
                em.remove(t);
                em.getTransaction().commit();

            }

            em.close();
            request.getSession().removeAttribute("task_id");

            request.getSession().setAttribute("flush", "taskの削除が完了しました。");


            if (request.getSession().getAttribute("group") == null) {
                //自身のホーム画面からtaskを編集した時
                response.sendRedirect(request.getContextPath()+"/toppage/index");//ホーム画面に戻る
            } else if (request.getSession().getAttribute("account") == null) {
                response.sendRedirect(request.getContextPath()+"/groups/toppage");
            } else {
                response.sendRedirect(request.getContextPath()+"/members/toppage");
            }

        }
    }
}