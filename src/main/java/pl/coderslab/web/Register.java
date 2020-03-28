package pl.coderslab.web;

import pl.coderslab.dao.AdminDao;
import pl.coderslab.model.Admin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "Register", urlPatterns={"/register"})
public class Register extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(comparePasswords(request.getParameter("password").trim(), request.getParameter("repassword").trim())) {
            Map<String, String> params = new HashMap<>();
            params.put("name", request.getParameter("name"));
            params.put("surname", request.getParameter("surname"));
            params.put("email", request.getParameter("email").trim());
            params.put("password", request.getParameter("password").trim());
            saveData(params);
            response.sendRedirect(request.getContextPath() + "/login");
        } else {
            request.setAttribute("passWarring", "Hasła nie są zgodne!");
            getServletContext().getRequestDispatcher("/register.jsp")
                    .forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/register.jsp")
                .forward(request, response);
    }

    private boolean saveData(Map params) {
        AdminDao adminDao = new AdminDao();
        Admin admin = new Admin();
        admin.setFirst_name((String) params.get("name"));
        admin.setLast_name((String) params.get("surname"));
        admin.setEmail((String) params.get("email"));
        admin.setPassword((String) params.get("password"));
        return adminDao.create(admin) != null;
    }

    private boolean comparePasswords(String password, String repassword) {
        return password.equals(repassword);
    }
}
