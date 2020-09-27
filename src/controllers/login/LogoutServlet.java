package controllers.login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LogoutServlet
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogoutServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        request.getSession().removeAttribute("login_person");
        request.getSession().removeAttribute("GroupBelong");

        if (request.getSession().getAttribute("group") != null) {
            //group・member系のページからLogoutServletにきたた時
            request.getSession().removeAttribute("group");
        }

        if (request.getSession().getAttribute("ps") != null) {
            //group・member系のページからLogoutServletにきた時
            request.getSession().removeAttribute("ps");
        }

        if (request.getSession().getAttribute("account") != null) {
            //member系のページからLogoutServletに戻ってきた時
            request.getSession().removeAttribute("account");
        }

        if (request.getSession().getAttribute("updated_task") != null) {
            //Groupの過去のtaskページからLogoutServletに戻ってきた時
            request.getSession().removeAttribute("updated_task");
        }

        if (request.getSession().getAttribute("flush") == null) {
            request.getSession().setAttribute("flush", "ログアウトしました。");
        }

        if (request.getSession().getAttribute("group") != null) {
            //groupのtask一覧から戻ってきた時
            request.getSession().removeAttribute("group");
        }


        response.sendRedirect(request.getContextPath()+"/login");
    }

}
