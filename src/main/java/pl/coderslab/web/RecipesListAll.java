package pl.coderslab.web;

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

@WebServlet(name = "RecipesListAll", urlPatterns = {"/recipelistall"})
public class RecipesListAll extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Recipe recipe = new Recipe();
        RecipeDao recipeDao = new RecipeDao();
        List<Recipe> recipesAll = recipeDao.findAll();
        request.setAttribute("recipes",recipesAll);

        getServletContext().getRequestDispatcher("/recipelistall.jsp")
                .forward(request, response);

    }
}
