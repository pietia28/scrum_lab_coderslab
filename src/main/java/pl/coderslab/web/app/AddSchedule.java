package pl.coderslab.web.app;

import pl.coderslab.dao.AdminDao;
import pl.coderslab.dao.PlanDAO;
import pl.coderslab.model.Admin;
import pl.coderslab.model.Plan;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "AddSchedule" ,urlPatterns = {"/app/plan/add"})
public class AddSchedule extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();

        String adminEmail = (String)session.getAttribute("authorised");
        AdminDao adminDao = new AdminDao();
        Admin admin = adminDao.findSingleAdminByEmail(adminEmail);

        Plan plan= new Plan();
        plan.setName(request.getParameter("planName"));
        plan.setDescription(request.getParameter("planDescription"));
        plan.setAdmin(admin);

        PlanDAO planDAO = new PlanDAO();
        planDAO.create(plan);

        response.sendRedirect("/app/plan/list");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        getServletContext().getRequestDispatcher("/app/addSchedule.jsp")
                .forward(request,response);
    }
}
