package pl.coderslab.dao;

import pl.coderslab.exception.NotFoundException;
import pl.coderslab.model.Admin;
import pl.coderslab.model.Recipe;
import pl.coderslab.utils.DbUtil;


import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RecipeDao {

    //ZAPYTANIA SQL
    private static final String CREATE_RECIPE_QUERY = "INSERT INTO recipe(" +
                                                                    "name, " +
                                                                    "ingredients, " +
                                                                    "description," +
                                                                    "created, " +
                                                                    "updated, " +
                                                                    "preparation_time, " +
                                                                    "preparation, " +
                                                                    "admin_id) " +
                                                        "VALUES(?,?,?,?,?,?,?,?)";

    private static final String READ_RECIPE_QUERY = "SELECT * FROM recipe WHERE id = ?;";
    //Zgodnie z ProjectBacklog #6, aktualizować recipe może jedynie ich twórca (admin_id i created nie są aktualizowane).
    private static final String UPDATE_RECIPE_QUERY = "UPDATE recipe SET name = ?, " +
                                                                        "ingredients = ?, " +
                                                                        "description = ?, " +
                                                                        "updated = ?, " +
                                                                        "preparation_time = ?, " +
                                                                        "preparation = ?" +
                                                        "WHERE id = ?;";
    private static final String DELETE_RECIPE_QUERY = "DELETE FROM recipe WHERE id = ?;";
    private static final String FIND_ALL_RECIPES_QUERY = "SELECT * FROM recipe ORDER BY updated DESC;";
    private static final String FIND_ALL_RECIPES_BY_ADMIN_ID_QUERY = "SELECT * FROM recipe WHERE admin_id = ? ORDER BY updated DESC;";
    private static final String FIND_ALL_RECIPES_BY_NAME_QUERY = "SELECT * FROM recipe WHERE name LIKE ? ;";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");


    public Recipe create(Recipe recipe) {

        LocalDateTime dateTime = LocalDateTime.now();

        try (Connection connect = DbUtil.getConnection();
            PreparedStatement statement = connect.prepareStatement(CREATE_RECIPE_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1,recipe.getName());
            statement.setString(2, recipe.getIngredients());
            statement.setString(3, recipe.getDescription());
            statement.setString(4, String.valueOf(dateTime));
            statement.setString(5, String.valueOf(dateTime));
            statement.setInt(6, recipe.getPreparationTime());
            statement.setString(7, recipe.getPreparation());
            statement.setInt(8, recipe.getAdmin().getId());

            int result = statement.executeUpdate();

            if (result != 1) {
                throw new RuntimeException("Execute update returned: " + result);
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.first()) {
                    recipe.setId(generatedKeys.getInt(1));
                    return recipe;
                } else {
                    throw new RuntimeException("Generated key not found.");
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public Recipe read(Integer recipeId) {
        Recipe recipe = new Recipe();
        AdminDao adminRecipeOwnerDAO = new AdminDao();

        try (Connection connect = DbUtil.getConnection();
            PreparedStatement statement = connect.prepareStatement(READ_RECIPE_QUERY)) {
                statement.setInt(1, recipeId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        recipe.setId(resultSet.getInt("id"));
                        recipe.setName(resultSet.getString("name"));
                        recipe.setIngredients(resultSet.getString("ingredients"));
                        recipe.setDescription(resultSet.getString("description"));
                        recipe.setCreated(LocalDateTime.parse(resultSet.getString("created").substring(0, 16), formatter));
                        recipe.setUpdated(LocalDateTime.parse(resultSet.getString("updated").substring(0, 16), formatter));
                        recipe.setPreparationTime(resultSet.getInt("preparation_time"));
                        recipe.setPreparation(resultSet.getString("preparation"));
                        recipe.setAdmin(adminRecipeOwnerDAO.read(resultSet.getInt("admin_id")));
                    }
                }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return recipe;
    }

    public void update(Recipe recipe) {
        try (Connection connect = DbUtil.getConnection();
            PreparedStatement statement = connect.prepareStatement(UPDATE_RECIPE_QUERY)){
                statement.setString(1,recipe.getName());
                statement.setString(2, recipe.getIngredients());
                statement.setString(3, recipe.getDescription());
                statement.setString(4, String.valueOf(LocalDateTime.now()));
                statement.setInt(5, recipe.getPreparationTime());
                statement.setString(6, recipe.getPreparation());
                statement.setInt(7, recipe.getId());

                statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void delete(Integer recipeId) {
        try (Connection connect = DbUtil.getConnection();
            PreparedStatement statement = connect.prepareStatement(DELETE_RECIPE_QUERY)){
                statement.setInt(1, recipeId);
                statement.executeUpdate();

                boolean deleted = statement.execute();
                if(!deleted) {
                    throw new NotFoundException("Produkt nie został znaleziony");
                }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public List<Recipe> findAll() {
        List<Recipe> recipeList = new ArrayList<>();
        AdminDao adminRecipeOwner = new AdminDao();

        try (Connection connect = DbUtil.getConnection()){
            PreparedStatement statement = connect.prepareStatement(FIND_ALL_RECIPES_QUERY);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Recipe oneRecipe = new Recipe();
                oneRecipe.setId(resultSet.getInt("id"));
                oneRecipe.setName((resultSet.getString("name")));
                oneRecipe.setIngredients(resultSet.getString("ingredients"));
                oneRecipe.setDescription(resultSet.getString("description"));
                oneRecipe.setCreated(LocalDateTime.parse(resultSet.getString("created").substring(0, 16), formatter));
                oneRecipe.setUpdated(LocalDateTime.parse(resultSet.getString("updated").substring(0, 16), formatter));
                oneRecipe.setPreparationTime(resultSet.getInt("preparation_time"));
                oneRecipe.setPreparation(resultSet.getString("preparation"));
                oneRecipe.setAdmin(adminRecipeOwner.read(resultSet.getInt("id")));
                recipeList.add(oneRecipe);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return recipeList;
    }

    public List<Recipe> findAllByAdminId (int adminId) {
        List<Recipe> adminRecipesList = new ArrayList<>();
        AdminDao adminRecipeOwner = new AdminDao();

        try (Connection connect = DbUtil.getConnection();
            PreparedStatement statement = connect.prepareStatement(FIND_ALL_RECIPES_BY_ADMIN_ID_QUERY)){
            statement.setInt(1, adminId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Recipe adminRecipe = new Recipe();

                adminRecipe.setId(resultSet.getInt("id"));
                adminRecipe.setName((resultSet.getString("name")));
                adminRecipe.setIngredients(resultSet.getString("ingredients"));
                adminRecipe.setDescription(resultSet.getString("description"));
                adminRecipe.setCreated(LocalDateTime.parse(resultSet.getString("created").substring(0, 16), formatter));
                adminRecipe.setUpdated(LocalDateTime.parse(resultSet.getString("updated").substring(0, 16), formatter));
                adminRecipe.setPreparationTime(resultSet.getInt("preparation_time"));
                adminRecipe.setPreparation(resultSet.getString("preparation"));
                adminRecipe.setAdmin(adminRecipeOwner.read(resultSet.getInt("id")));
                adminRecipesList.add(adminRecipe);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return adminRecipesList;
    }

    public List<Recipe> findAllByName (String name) {
        List<Recipe> recipesFiltered = new ArrayList<>();
        AdminDao adminRecipeOwner = new AdminDao();

        try(Connection connect = DbUtil.getConnection()){
            PreparedStatement statement = connect.prepareStatement(FIND_ALL_RECIPES_BY_NAME_QUERY);
            statement.setString(1, "%"+name+"%");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Recipe adminRecipe = new Recipe();
                adminRecipe.setId(resultSet.getInt("id"));
                adminRecipe.setName((resultSet.getString("name")));
                adminRecipe.setIngredients(resultSet.getString("ingredients"));
                adminRecipe.setDescription(resultSet.getString("description"));
                adminRecipe.setCreated(LocalDateTime.parse(resultSet.getString("created").substring(0, 16), formatter));
                adminRecipe.setUpdated(LocalDateTime.parse(resultSet.getString("updated").substring(0, 16), formatter));
                adminRecipe.setPreparationTime(resultSet.getInt("preparation_time"));
                adminRecipe.setPreparation(resultSet.getString("preparation"));
                adminRecipe.setAdmin(adminRecipeOwner.read(resultSet.getInt("id")));
                recipesFiltered.add(adminRecipe);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return recipesFiltered;
    }

    public int numberOfRecipes (String email) {
        AdminDao adminDao = new AdminDao();
        List<Admin> admins = adminDao.findAdminsByEmail(email);
        RecipeDao recipeDao = new RecipeDao();
        List<Recipe> recipes = recipeDao.findAllByAdminId(admins.get(0).getId());
        return recipes.size();
    }


}
