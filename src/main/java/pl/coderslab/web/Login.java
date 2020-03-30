package pl.coderslab.web;

import pl.coderslab.dao.AdminDao;
import pl.coderslab.model.Admin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "Login", urlPatterns = {"/login"})
public class Login extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        AdminDao adminDao = new AdminDao();
        if (adminDao.isAuthorised(password, email)) {
            HttpSession session = request.getSession();
            session.setAttribute("authorised", email);

           response.sendRedirect(request.getContextPath() + "/app/dashboard");

        } else {
            request.setAttribute("message", "Niepoprawne dane logowania");
            getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {




        getServletContext().getRequestDispatcher("/login.jsp")
                    .forward(request, response);

    }
}
