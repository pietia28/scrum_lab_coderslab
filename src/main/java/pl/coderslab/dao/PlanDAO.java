package pl.coderslab.dao;

import pl.coderslab.exception.NotFoundException;
import pl.coderslab.model.Admin;
import pl.coderslab.model.Plan;
import pl.coderslab.model.PlanDetails;
import pl.coderslab.utils.DbUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class PlanDAO {

    private static final String CREATE_PLAN_QUERY = "INSERT INTO plan(name,description,created,admin_id) VALUES (?,?,?,?);";
    private static final String DELETE_PLAN_QUERY = "DELETE FROM plan where id = ?;";
    private static final String FIND_ALL_PLANS_QUERY = "SELECT * FROM plan;";
    private static final String READ_PLAN_QUERY = "SELECT * from plan where id = ?;";
    private static final String UPDATE_PLAN_QUERY = "UPDATE	plan SET name = ? , description = ? WHERE id = ?;";
    private static final String FIND_ALL_PLANS_BY_ADMIN_ID_QUERY = "SELECT * FROM plan WHERE admin_id = ? ORDER BY created DESC;";
    private static final String FIND_NEWEST_PLAN_BY_ADMIN_ID_QUERY = "SELECT recipe_plan.id as recipe_plan_id, day_name.name as day_name,  meal_name,  recipe.id as recipe_id, plan_id FROM recipe_plan JOIN day_name on day_name.id=day_name_id JOIN recipe on recipe.id=recipe_id WHERE recipe_plan.plan_id =\n" +
            "                                                                                                                                (SELECT MAX(plan.id) from plan\n" +
            "                                                                                                                                                              RIGHT JOIN recipe_plan on plan.id = recipe_plan.plan_id WHERE admin_id = ?)ORDER BY day_name.display_order, recipe_plan.display_order;";
    private static final String FIND_PLAN_DETAILS_QUERY = "SELECT recipe_plan.id as recipe_plan_id, " +
            "day_name.name as day_name,  meal_name,  recipe.id as recipe_id, plan_id\n" +
            "FROM `recipe_plan`\n" +
            "         JOIN day_name on day_name.id=day_name_id\n" +
            "         JOIN recipe on recipe.id=recipe_id WHERE\n" +
            "        recipe_plan.plan_id = ?\n" +
            "ORDER by day_name.display_order, recipe_plan.display_order;";
    private static final String CREATE_RECIPE_PLAN_QUERY = "INSERT INTO recipe_plan(" +
            "recipe_id, " +
            "meal_name, " +
            "display_order, " +
            "day_name_id, " +
            "plan_id) " +
            "VALUES (?,?,?,?,?)";

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");


    public Plan read(Integer PlanId) {
        Plan plan = new Plan();
        AdminDao adminRead = new AdminDao();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(READ_PLAN_QUERY);
        ) {
            statement.setInt(1, PlanId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    plan.setId(resultSet.getInt("id"));
                    plan.setName(resultSet.getString("name"));
                    plan.setDescription(resultSet.getString("description"));
                    plan.setCreated(LocalDateTime.parse(resultSet.getString("created").substring(0, 16),formatter));
                    plan.setAdmin(adminRead.read(resultSet.getInt("admin_id")));

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return plan;

    }

    public List<Plan> findAll() {
        List<Plan> PlanList = new ArrayList<>();
        AdminDao adminFindAll = new AdminDao();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_PLANS_QUERY);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Plan planToAdd = new Plan();
                planToAdd.setId(resultSet.getInt("id"));
                planToAdd.setName(resultSet.getString("name"));
                planToAdd.setDescription(resultSet.getString("description"));
                planToAdd.setCreated(LocalDateTime.parse(resultSet.getString("created").substring(0, 16),formatter));
                planToAdd.setAdmin(adminFindAll.read(resultSet.getInt("admin_id")));
                PlanList.add(planToAdd);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return PlanList;

    }

    public List<Plan> findAllByAdminId (int adminId) {
        List<Plan> adminRecipesList = new ArrayList<>();
        AdminDao adminPlans = new AdminDao();

        try (Connection connect = DbUtil.getConnection();
             PreparedStatement statement = connect.prepareStatement(FIND_ALL_PLANS_BY_ADMIN_ID_QUERY)){
            statement.setInt(1, adminId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Plan adminPlan = new Plan();

                adminPlan.setId(resultSet.getInt("id"));
                adminPlan.setName((resultSet.getString("name")));
                adminPlan.setDescription(resultSet.getString("description"));
                adminPlan.setCreated(LocalDateTime.parse(resultSet.getString("created").substring(0, 16), formatter));
                adminPlan.setAdmin(adminPlans.read(resultSet.getInt("admin_id")));
                adminRecipesList.add(adminPlan);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return adminRecipesList;
    }


    public Plan create(Plan plan) {

        LocalDateTime dateTime = LocalDateTime.now();

        try (Connection connection = DbUtil.getConnection();
             PreparedStatement insertStm = connection.prepareStatement(CREATE_PLAN_QUERY,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            insertStm.setString(1, plan.getName());
            insertStm.setString(2, plan.getDescription());
            insertStm.setString(3, String.valueOf(dateTime));
            insertStm.setInt(4, plan.getAdmin().getId());


            int result = insertStm.executeUpdate();

            if (result != 1) {
                throw new RuntimeException("Execute update returned " + result);
            }

            try (ResultSet generatedKeys = insertStm.getGeneratedKeys()) {
                if (generatedKeys.first()) {
                    plan.setId(generatedKeys.getInt(1));
                    return plan;
                } else {
                    throw new RuntimeException("Generated key was not found");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
    public void delete(Integer planId) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_PLAN_QUERY)) {
            statement.setInt(1, planId);
            statement.executeUpdate();

            boolean deleted = statement.execute();
            if (!deleted) {
                throw new NotFoundException("Plan not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void update(Plan plan) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_PLAN_QUERY)) {
            statement.setInt(3, plan.getId());
            statement.setString(1, plan.getName());
            statement.setString(2, plan.getDescription());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public int numberOfPlans (String email) {
        AdminDao adminDao = new AdminDao();
        PlanDAO planDao = new PlanDAO();
        List<Admin> admins = adminDao.findAdminsByEmail(email);
        List<Plan> plans = planDao.findAllByAdminId(admins.get(0).getId());
        return plans.size();
    }


    public List<PlanDetails> findAllRecipePlanDetails (int adminId) {
        List<PlanDetails> myPlanDetails = new ArrayList<>();
        RecipeDao recipeDao = new RecipeDao();
        PlanDAO planDAO = new PlanDAO(); //czy tu nie wystarczy string z nazwą


        try (Connection connection = DbUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(FIND_NEWEST_PLAN_BY_ADMIN_ID_QUERY)) {
            statement.setInt(1, adminId);
            try (ResultSet resultSet = statement.executeQuery()){
                while (resultSet.next()){
                    PlanDetails planDetails = new PlanDetails();

                    planDetails.setId(resultSet.getInt("recipe_plan_id"));
                    planDetails.setDayName(resultSet.getString("day_name")); //pobieramy Stringa gdyż nie ma metody read dla DAO;
                    planDetails.setMealName(resultSet.getString("meal_name"));
                    planDetails.setRecipe(recipeDao.read(resultSet.getInt("recipe_id")));
                    planDetails.setPlan(planDAO.read(resultSet.getInt("plan_id")));
                    myPlanDetails.add(planDetails);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return myPlanDetails;
    }

    public List<PlanDetails> findPlanDetails(int planId) {
        List<PlanDetails> myPlanDetails = new ArrayList<>();
        RecipeDao recipeDao = new RecipeDao();
        PlanDAO planDAO = new PlanDAO();


        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_PLAN_DETAILS_QUERY)) {
            statement.setInt(1, planId);
            try (ResultSet resultSet = statement.executeQuery()){
                while (resultSet.next()){
                    PlanDetails planDetails = new PlanDetails();

                    planDetails.setId(resultSet.getInt("recipe_plan_id"));
                    planDetails.setDayName(resultSet.getString("day_name"));
                    planDetails.setMealName(resultSet.getString("meal_name"));
                    planDetails.setRecipe(recipeDao.read(resultSet.getInt("recipe_id")));
                    planDetails.setPlan(planDAO.read(resultSet.getInt("plan_id")));
                    myPlanDetails.add(planDetails);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return myPlanDetails;
    }

    public Integer createRecipePlan (Map<String, String> recipePlan){

        try (Connection connection = DbUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(CREATE_RECIPE_PLAN_QUERY, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, recipePlan.get("recipe_id"));
            statement.setString(2, recipePlan.get("meal_name"));
            statement.setString(3, recipePlan.get("display_order"));
            statement.setString(4, recipePlan.get("day_name_id"));
            statement.setString(5, recipePlan.get("plan_id"));

            int result = statement.executeUpdate();

            if (result != 1) {
                throw new RuntimeException("Execute update returned: " + result);
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.first()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new RuntimeException("Generated key not found.");
                }
            }


        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }



}
