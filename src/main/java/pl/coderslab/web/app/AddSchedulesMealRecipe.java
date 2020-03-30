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
import java.util.List;

@WebServlet(name = "AddSchedulesMealRecipe", urlPatterns = {"/app/recipe/plan/add"})
public class AddSchedulesMealRecipe extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        AdminDao adminDao = new AdminDao();
        String adminEmail = (String)session.getAttribute("authorised");
        int adminId = adminDao.findSingleAdminByEmail(adminEmail).getId();

        PlanDAO planDAO = new PlanDAO();
        List<Plan> plans = planDAO.findAllByAdminId(adminId);
        session.setAttribute("plans", plans);

        RecipeDao recipeDao = new RecipeDao();
        List<Recipe> recipes = recipeDao.findAllByAdminId(adminId);
        session.setAttribute("recipes", recipes);

        DayNameDao dayNameDao = new DayNameDao();
        List<DayName> dayNames = dayNameDao.findAll();
        session.setAttribute("dayNames", dayNames);

        getServletContext().getRequestDispatcher("/app/addSchedulesMealRecipe.jsp")
                .forward(request,response);

    }
}
