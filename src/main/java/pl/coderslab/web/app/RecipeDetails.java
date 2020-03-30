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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "RecipeDetails", urlPatterns = {"/app/recipe/details"})
public class RecipeDetails extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        String email = String.valueOf(session.getAttribute("authorised")); // pobranie maila z sesji i przypisanie go do Stringa do dalszego użycia
        AdminDao adminDao = new AdminDao();
        List<Admin> adminList = adminDao.findAdminsByEmail(email);
        Admin admin = new Admin();
        admin = adminList.get(0);

        request.setAttribute("admin",admin); //Imię użytkownika na stronie po zalogowaniu w prawym górnym rogu
        int id = Integer.parseInt(request.getParameter("id"));
        RecipeDao recipeDao = new RecipeDao();
        Recipe recipe = recipeDao.read(id);
        List<String> ingredients = new ArrayList<>(Arrays.asList(recipe.getIngredients().split(", ")));
        request.setAttribute("recipe", recipe);
        request.setAttribute("ingredients", ingredients);
        getServletContext().getRequestDispatcher("/app/recipedetails.jsp").forward(request, response);

    }
}
