package pl.coderslab.web.app;

import pl.coderslab.dao.PlanDAO;
import pl.coderslab.dao.RecipeDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "DeleteRecipe", urlPatterns = {"/app/recipe/delete"})
public class DeleteRecipe extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        boolean deleteButton = Boolean.parseBoolean(request.getParameter("submit"));
        int planId = Integer.parseInt(request.getParameter("id"));

        if (deleteButton) {
            RecipeDao recipeDao = new RecipeDao();
            recipeDao.delete(planId);
        }
        response.sendRedirect("/app/recipe/list");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int recipeId = Integer.parseInt(request.getParameter("id"));
        RecipeDao recipeDao = new RecipeDao();

        List<Integer> recipeList = recipeDao.findDistinctRecipesAddedToPlan();

        if (recipeList.contains(recipeId)) {
            request.setAttribute("message", "W pierwszej kolejności przepis należy usunąć z planu");
        } else {
            request.setAttribute("typeMsg", recipeDao.read(recipeId).getName());
        }
        request.getServletContext().getRequestDispatcher("/app/delConfirmation.jsp")
                .forward(request, response);

    }
}
