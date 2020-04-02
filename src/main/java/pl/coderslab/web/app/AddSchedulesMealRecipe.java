package pl.coderslab.web.app;

import pl.coderslab.dao.AdminDao;
import pl.coderslab.dao.DayNameDao;
import pl.coderslab.dao.PlanDAO;
import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.DayName;
import pl.coderslab.model.Plan;
import pl.coderslab.model.Recipe;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "AddSchedulesMealRecipe", urlPatterns = {"/app/recipe/plan/add"})
public class AddSchedulesMealRecipe extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();

        Map<String, String> recipePlan = getDataToSave(request);

        if (saveData(recipePlan) == null) {
            session.setAttribute("message", "Nie udało się dodać przepisu do planu");
        }
        response.sendRedirect("/app/recipe/plan/add");

    }

    private Integer saveData(Map<String, String> recipePlan) {
        PlanDAO planDAO = new PlanDAO();
        return planDAO.createRecipePlan(recipePlan);
    }

    private Map<String, String> getDataToSave(HttpServletRequest request) {
        Map<String, String> recipePlan = new HashMap<>();
        recipePlan.put("plan_id", request.getParameter("planId"));
        recipePlan.put("meal_name", request.getParameter("mealName"));
        recipePlan.put("display_order", request.getParameter("mealNo"));
        recipePlan.put("recipe_id", request.getParameter("recipeId"));
        recipePlan.put("day_name_id", request.getParameter("dayNameId"));
        return recipePlan;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        AdminDao adminDao = new AdminDao();
        String adminEmail = (String)session.getAttribute("authorised");
        int adminId = adminDao.findSingleAdminByEmail(adminEmail).getId();

        PlanDAO planDAO = new PlanDAO();
        List<Plan> plans = planDAO.findAllByAdminId(adminId);
        request.setAttribute("plans", plans);

        RecipeDao recipeDao = new RecipeDao();
        List<Recipe> recipes = recipeDao.findAllByAdminId(adminId);
        request.setAttribute("recipes", recipes);

        DayNameDao dayNameDao = new DayNameDao();
        List<DayName> dayNames = dayNameDao.findAll();
        request.setAttribute("dayNames", dayNames);

        getServletContext().getRequestDispatcher("/app/addSchedulesMealRecipe.jsp")
                .forward(request,response);

    }
}
