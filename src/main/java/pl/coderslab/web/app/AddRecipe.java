package pl.coderslab.web.app;

import pl.coderslab.dao.AdminDao;
import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.Admin;
import pl.coderslab.model.Recipe;git
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "AddRecipe", urlPatterns = {"/app/recipe/add"})
public class AddRecipe extends HttpServlet {
    private String email;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        saveData(getRecipesAsHashMap(request));
        response.sendRedirect(request.getContextPath() + "/app/recipe/list");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/app/add-recipe.jsp")
                .forward(request, response);
    }

    private boolean saveData(Map params) {
        LocalDateTime dateTime = LocalDateTime.now();
        RecipeDao recipeDao = new RecipeDao();
        Recipe recipe = new Recipe();
        recipe.setName((String) params.get("name"));
        recipe.setIngredients((String) params.get("ingredients"));
        recipe.setDescription((String) params.get("description"));
        recipe.setPreparationTime(Integer.parseInt((String) params.get("preparation_time")));
        recipe.setPreparation((String) params.get("preparation"));
        recipe.setAdmin((Admin) params.get("admin"));
        return recipeDao.create(recipe) != null;
    }

    private Map<String, Object> getRecipesAsHashMap(HttpServletRequest request) {
        HttpSession session = request.getSession();
        this.email = (String) session.getAttribute("authorised");
        LocalDateTime dateTime = LocalDateTime.now();
        Map<String, Object> params = new HashMap<>();
        params.put("name", request.getParameter("name"));
        params.put("ingredients", request.getParameter("ingredients"));
        params.put("description", request.getParameter("description"));
        params.put("preparation_time", request.getParameter("preparation_time"));
        params.put("preparation", request.getParameter("preparation"));
        params.put("admin", getAdmin());
        return params;
    }

    private Admin getAdmin() {
        AdminDao adminDao = new AdminDao();
        return adminDao.findSingleAdminByEmail(this.email);
    }
}
