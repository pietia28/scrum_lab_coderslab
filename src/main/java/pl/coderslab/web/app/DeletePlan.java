package pl.coderslab.web.app;

import pl.coderslab.dao.AdminDao;
import pl.coderslab.dao.PlanDAO;
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

@WebServlet(name = "DelConfirmation", urlPatterns = {"/app/plan/delete"})
public class DeletePlan extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        boolean deleteButton = Boolean.parseBoolean(request.getParameter("submit"));
        int planId =Integer.parseInt(request.getParameter("id"));

        if (deleteButton) {
            PlanDAO planDAO = new PlanDAO();
            deleteRecipesInPlan(planId, planDAO);
            planDAO.delete(planId);
        }
        response.sendRedirect("/app/plan/list");
    }

    private void deleteRecipesInPlan(int planId, PlanDAO planDAO) {
        List<PlanDetails> planDetails = planDAO.findPlanDetails(planId);

        //usuń powiązanie przepisów z planem
        for (PlanDetails planDetail : planDetails) {
            planDAO.deleteRecipePlan(planDetail.getId());
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        int itemId = Integer.parseInt(request.getParameter("id"));
        String adminEmail = (String)session.getAttribute("authorised");

        PlanDAO planDAO = new PlanDAO();
        Plan plan = planDAO.read(itemId);

        if (plan.getId()>0) { //czy plan istnieje - ochrona przed ręcznym wywołaniem
            if (isPlanBelongsToAdmin(adminEmail, plan)) {
                String typeMsg = "plan o nazwie: " + plan.getName();
                request.setAttribute("typeMsg", typeMsg);
                request.getServletContext().getRequestDispatcher("/app/delConfirmation.jsp")
                        .forward(request, response);
            }
        }
        response.sendRedirect("/app/plan/list");
    }

    private boolean isPlanBelongsToAdmin (String adminEmail, Plan plan){
        AdminDao adminDao = new AdminDao();
        int adminId = adminDao.findSingleAdminByEmail(adminEmail).getId();
        int planAdminId = plan.getAdmin().getId();
        return (planAdminId == adminId && adminId > 0);
    }
}
