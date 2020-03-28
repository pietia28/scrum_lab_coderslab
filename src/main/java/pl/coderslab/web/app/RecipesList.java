package pl.coderslab.web.app;

import pl.coderslab.dao.AdminDao;
import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.Admin;
import pl.coderslab.model.Recipe;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "RecipesList", urlPatterns = {"/app/recipe/list"})
public class RecipesList extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setCharacterEncoding("UTF8");
        response.setContentType("text/html");

        HttpSession session = request.getSession();
        String adminEmail = (String)session.getAttribute("authorised");
        AdminDao adminDao = new AdminDao();
        List<Admin> admin = adminDao.findAdminsByEmail(adminEmail);

        if (admin.size()>0) {
            int adminId = admin.get(0).getId();

            RecipeDao recipeDao = new RecipeDao();
            List<Recipe> adminRecipes = recipeDao.findAllByAdminId(adminId);
            session.setAttribute("adminRecipes", adminRecipes);

            getServletContext().getRequestDispatcher("/app/recipesList.jsp")
                    .forward(request, response);
        } else  {
            request.setAttribute("message", "Zaloguj sie aby wyświetlić stronę");
            getServletContext().getRequestDispatcher("/login")
                    .forward(request, response);
        }

    }
}
