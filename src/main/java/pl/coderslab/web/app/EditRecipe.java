package pl.coderslab.web.app;

import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.Recipe;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "EditRecipe", urlPatterns = {"/app/recipe/edit"})
public class EditRecipe extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        updateRecipe(request);
        response.sendRedirect(request.getContextPath() + "/app/recipe/list");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int recipeId = Integer.parseInt(request.getParameter("id"));
        request.setAttribute("recipe", getRecipeById(recipeId));
        getServletContext().getRequestDispatcher("/app/edit-recipe.jsp")
                .forward(request, response);
    }

    private void updateRecipe (HttpServletRequest request) {
        RecipeDao recipeDao = new RecipeDao();
        Recipe recipe = recipeDao.read((Integer.parseInt(request.getParameter("recipeId"))));
        recipe.setName(request.getParameter("name"));
        recipe.setIngredients(request.getParameter("ingredients"));
        recipe.setDescription(request.getParameter("description"));
        recipe.setPreparationTime(Integer.parseInt(request.getParameter("preparationTime")));
        recipe.setPreparation(request.getParameter("preparation"));
        recipeDao.update(recipe);
    }

    private Recipe getRecipeById(int recipeId) {
        RecipeDao recipeDao = new RecipeDao();
        return recipeDao.read(recipeId);
    }
}
