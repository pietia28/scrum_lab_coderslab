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
import java.util.Map;

@WebServlet(name = "Add", urlPatterns = {"/app/recipe/add"})
public class Add extends HttpServlet {
    private String email;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        this.email = (String) session.getAttribute("authorised");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/app/add-recipe.jsp")
                .forward(request, response);
    }

    private boolean saveData(Map params) {
        RecipeDao recipeDao = new RecipeDao();
        Recipe recipe = new Recipe();
        recipe.setName((String) params.get("name"));
        recipe.setDescription((String) params.get("decription"));
        recipe.setPreparationTime((Integer) params.get("preparation_time"));
        recipe.setPreparation((String) params.get("preparation"));
        recipe.setIngredients((String) params.get("ingredients"));

        return recipeDao.create(recipe) != null;
    }

    private Admin getAdmin() {
        AdminDao adminDao = new AdminDao();

    }
}
