package pl.coderslab.web.app;

import pl.coderslab.dao.PlanDAO;
import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.Recipe;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DeleteRecipeFromPlan", urlPatterns = {"/app/plan/delete-recipe"})
public class DeleteRecipeFromPlan extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("submit").equals("true")) {
            deleteRecipe(Integer.parseInt(request.getParameter("id")));
        }
        String planId = request.getParameter("planId");
        response.sendRedirect(request.getContextPath() + "/app/plan/details?id=" + planId);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int recipeId = Integer.parseInt(request.getParameter("recipeId"));
        String planId = request.getParameter("planId");
        String recipeName = getRecipeName(recipeId);
        request.setAttribute("typeMsg"," przepis " + recipeName);
        request.setAttribute("planId", planId);
        getServletContext().getRequestDispatcher("/app/delConfirmation.jsp")
                .forward(request, response);
    }

    private String getRecipeName(int recipeId) {
        RecipeDao recipeDao = new RecipeDao();
        Recipe recipe = recipeDao.read(recipeId);
        return recipe.getName();
    }

    private void deleteRecipe(int recipeId) {
        PlanDAO planDAO = new PlanDAO();
        planDAO.deleteRecipeFromPlan(recipeId);
    }
}
