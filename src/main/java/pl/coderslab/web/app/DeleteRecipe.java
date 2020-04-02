package pl.coderslab.web.app;

import pl.coderslab.dao.RecipeDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "DeleteRecipe", urlPatterns = {"/app/recipe/delete"})
public class DeleteRecipe extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int recipeId = Integer.parseInt(request.getParameter("id"));
        HttpSession session = request.getSession();
        RecipeDao recipeDao = new RecipeDao();

        List<Integer> recipeList = recipeDao.findDistinctRecipesAddedToPlan();
        if (recipeList.contains(recipeId)) {
            request.setAttribute("message", "W pierwszej kolejności przepis należy usunąć z planu");
            getServletContext().getRequestDispatcher("/app/recipesList.jsp")
                    .forward(request, response);
        } else {
            if (session.getAttribute("delete") != null && (int)session.getAttribute("delete") == recipeId) {
                recipeDao.delete(recipeId);
                session.removeAttribute("delete");
                response.sendRedirect(request.getContextPath() + "/app/recipe/list");
            } else {
                session.setAttribute("delete", recipeId);
                request.setAttribute("button", recipeId);
                getServletContext().getRequestDispatcher("/app/recipesList.jsp")
                        .forward(request, response);
            }
        }
    }
}
