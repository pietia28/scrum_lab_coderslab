package pl.coderslab.web;

import pl.coderslab.dao.AdminDao;
import pl.coderslab.dao.PlanDAO;
import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.Admin;
import pl.coderslab.model.Plan;
import pl.coderslab.model.PlanDetails;
import pl.coderslab.model.Recipe;

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
        int numberOfRecipes = recipeDao.numberOfRecipes(email);

        request.setAttribute("numberofrecipes" ,String.valueOf(numberOfRecipes)); // liczba przepisów

        PlanDAO planDAO = new PlanDAO();
        int numberOfPlans = planDAO.numberOfPlans(email);

        request.setAttribute("numberofplans" , String.valueOf(numberOfPlans)); // liczba planów

        AdminDao adminDao = new AdminDao();
        List<Admin> adminList = adminDao.findAdminsByEmail(email);
        Admin admin = new Admin();
        admin = adminList.get(0);

        request.setAttribute("admin",admin); //Imię użytkownika na stronie po zalogowaniu w prawym górnym rogu

        Plan plan = new Plan();
        PlanDAO planDAO1 = new PlanDAO();
        PlanDetails planDetails = new PlanDetails();

        List<PlanDetails> planList = planDAO1.findAllRecipePlanDetails(admin.getId());
        planDetails = planList.get(0);
        plan = planDetails.getPlan();

        request.setAttribute("plan",plan); // Ostatni dodany plan nazwa



        getServletContext().getRequestDispatcher("/dashboard.jsp")
                .forward(request, response);


    }

}