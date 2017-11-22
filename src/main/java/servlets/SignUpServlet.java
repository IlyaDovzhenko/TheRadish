package servlets;


import accounts.AccountService;
import accounts.UserProfile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignUpServlet extends HttpServlet {
    private final AccountService accountService;

    public SignUpServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        if (login.equals("") | password.equals("")) {
            response.getWriter().println("Bad request!");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        UserProfile userProfile = new UserProfile(login, password);
        accountService.addNewUser(userProfile);
        accountService.addSession(request.getSession().getId(), userProfile);

        response.getWriter().println("Hello: " + login);
        response.setStatus(HttpServletResponse.SC_OK);

    }
}
