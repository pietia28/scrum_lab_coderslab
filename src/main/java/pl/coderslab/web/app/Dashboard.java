package pl.coderslab.web.app;

import pl.coderslab.dao.AdminDao;
import pl.coderslab.dao.PlanDAO;
import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.Admin;
import pl.coderslab.model.Plan;
import pl.coderslab.model.PlanDetails;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "HomePageLogin",urlPatterns = "/app/dashboard")
public class Dashboard extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String email = String.valueOf(session.getAttribute("authorised")); // pobranie maila z sesji i przypisanie go do Stringa do dalszego użycia

        RecipeDao recipeDao = new RecipeDao();
        request.setAttribute("numberofrecipes" ,String.valueOf(recipeDao.numberOfRecipes(email))); // liczba przepisów

        PlanDAO planDAO = new PlanDAO();
        request.setAttribute("numberofplans" , String.valueOf(planDAO.numberOfPlans(email))); // liczba planów

        AdminDao adminDao = new AdminDao();
        List<Admin> adminList = adminDao.findAdminsByEmail(email);
        Admin admin = new Admin();
        admin = adminList.get(0);

        Plan plan = new Plan();
        PlanDetails planDetails = new PlanDetails();

        List<PlanDetails> planList = planDAO.findAllRecipePlanDetails(admin.getId());
        if (planList.size() > 0 ) {

            planDetails = planList.get(0); // błąd

            plan = planDetails.getPlan();

            request.setAttribute("plan", plan); // Ostatni dodany plan nazwa

            request.setAttribute("planDetails", planList);
        }

        getServletContext().getRequestDispatcher("/app/dashboard.jsp")
                .forward(request, response);

    }
}


