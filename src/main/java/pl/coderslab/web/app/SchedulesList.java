package pl.coderslab.web.app;

import pl.coderslab.dao.AdminDao;
import pl.coderslab.dao.PlanDAO;
import pl.coderslab.model.Plan;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "PlanList", urlPatterns = {"/app/plan/list"})
public class SchedulesList extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //TODO zmiana nagłówka w widoku
        //TODO obsługa przycisków akcji: USUŃ, EDYTUJ, SZCZEGÓŁY

        HttpSession session = request.getSession();

        String adminEmail = (String) session.getAttribute("authorised");
        AdminDao adminDao = new AdminDao();
        int adminId = adminDao.findSingleAdminByEmail(adminEmail).getId();

        PlanDAO planDAO = new PlanDAO();
        List<Plan> adminSchedules = planDAO.findAllByAdminId(adminId);

        session.setAttribute("adminSchedules", adminSchedules);

        getServletContext().getRequestDispatcher("/app/schedulesList.jsp")
                .forward(request, response);
    }
}
