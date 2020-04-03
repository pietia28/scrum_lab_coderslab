package pl.coderslab.web.app;
import pl.coderslab.model.Plan;
import pl.coderslab.dao.PlanDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebServlet(name = "EditSchedule", urlPatterns = {"/app/plan/edit"})
public class EditSchedule extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF8");
        updatePlan(request);
        response.sendRedirect(request.getContextPath() + "/app/plan/list");
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PlanDAO planDAO = new PlanDAO();
        Plan plan = planDAO.read((Integer.parseInt(request.getParameter("id"))));
        request.setAttribute("planName", plan.getName());
        request.setAttribute("planDescription", plan.getDescription());
        getServletContext().getRequestDispatcher("/app/editSchedule.jsp")
                .forward(request, response);
    }
    private void updatePlan (HttpServletRequest request) {
        PlanDAO planDAO = new PlanDAO();
        Plan plan = planDAO.read((Integer.parseInt(request.getParameter("id"))));
        plan.setName(request.getParameter("planName"));
        plan.setDescription(request.getParameter("planDescription"));
        planDAO.update(plan);
    }
}