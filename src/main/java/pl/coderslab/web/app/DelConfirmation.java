package pl.coderslab.web.app;

import pl.coderslab.dao.PlanDAO;
import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.Plan;
import pl.coderslab.model.PlanDetails;
import pl.coderslab.model.Recipe;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "DelConfirmation", urlPatterns = {"/app/plan/delete"})
public class DelConfirmation extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        //TODO - usuń poniższy wiersz po obsłudze wszystkich DELETEów
        response.getWriter().append(request.getParameter("submit") + " - ")
        .append(request.getParameter("type") + " - ").append(request.getParameter("id"));

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String  delType = request.getParameter("type");
        int itemId = Integer.parseInt(request.getParameter("id"));

        //TODO - przenieść do prywatnej metody, jeśli pozostajemy przy oddzielnych servletach do obsługi DELETE to usunąć switch
        //TODO dodać link dla DELETE na liście planów, po decyzji odnośnie switcha
        switch (delType){
            case "plan":
                PlanDAO planDAO = new PlanDAO();
                Plan plan = planDAO.read(itemId);
                List<PlanDetails> recipesInPlans = planDAO.findPlanDetails(plan.getId()); //sprawdzamy czy do planu sa przypisane przepisy
                
                //TODO sprawdź czy do planu nie sa przypisane przepisy i jeśli sa to je usuń.
                //TODO sprawdź czy dany plan należy do admina, by nie zezwolić na usunięcie dowolnego planu
                if (recipesInPlans.size()>0) {
                    request.setAttribute("warning", "UWAGA!!! Ten plan ma przypisane przepisy, które także zostaną usunięte");
                }
                delType = "plan o nazwie: " + plan.getName();
                break;
            case "recipe":
                RecipeDao recipeDao = new RecipeDao();
                Recipe recipe = recipeDao.read(itemId);
                delType = "przepis o nazwie: " + recipe.getName();
                break;
            case "recipe_plan":
                delType = "przepis z planu";
                break;
        }

        request.setAttribute("typeMsg", delType);
        request.getServletContext().getRequestDispatcher("/app/delConfirmation.jsp")
        .forward(request,response);

    }
}
