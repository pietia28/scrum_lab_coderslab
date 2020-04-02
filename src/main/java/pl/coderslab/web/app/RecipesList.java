package pl.coderslab.web.app;

import pl.coderslab.dao.AdminDao;
import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.Recipe;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "RecipesList", urlPatterns = {"/app/recipe/list"})
public class RecipesList extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {



        HttpSession session = request.getSession();
        String adminEmail = (String)session.getAttribute("authorised");
        AdminDao adminDao = new AdminDao();
        int adminId = adminDao.findSingleAdminByEmail(adminEmail).getId();

        RecipeDao recipeDao = new RecipeDao();
        List<Recipe> adminRecipes = recipeDao.findAllByAdminId(adminId);
        session.setAttribute("adminRecipes", adminRecipes);

        getServletContext().getRequestDispatcher("/app/recipesList.jsp")
                .forward(request, response);

    }
}
