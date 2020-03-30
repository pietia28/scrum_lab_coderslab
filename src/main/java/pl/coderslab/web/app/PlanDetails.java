package pl.coderslab.web.app;

import pl.coderslab.dao.PlanDAO;
import pl.coderslab.model.Plan;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "PlanDetails", urlPatterns = {"/app/plan/details"})
public class PlanDetails extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));

        PlanDAO planDAO = new PlanDAO();
        List<pl.coderslab.model.PlanDetails> planDetailsList = new ArrayList<>();
        planDetailsList = planDAO.findPlanDetails(id);
        Plan plan = planDAO.read(id);

        request.setAttribute("planDetailsList", planDetailsList);
        request.setAttribute("plan", plan);

        getServletContext().getRequestDispatcher("/app/planDetails.jsp").forward(request, response);

    }
}
