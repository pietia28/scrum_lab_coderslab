package pl.coderslab.model;


import java.time.LocalDateTime;

public class Recipe {

    private int id;
    private String name;
    private String ingredients;
    private String description;
    private LocalDateTime created;
    private LocalDateTime updated;
    private int preparationTime;
    private String preparation;
    private Admin admin;

    public Recipe() {
    }


    public Recipe(String name, String ingredients, String description, LocalDateTime created, LocalDateTime updated,
                  int preparationTime, String preparation, Admin admin, int adminId) {
        this.name = name;
        this.ingredients = ingredients;
        this.description = description;
        this.created = created;
        this.updated = updated;
        this.preparationTime = preparationTime;
        this.preparation = preparation;
        this.admin = admin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    public int getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(int preparationTime) {
        this.preparationTime = preparationTime;
    }

    public String getPreparation() {
        return preparation;
    }

    public void setPreparation(String preparation) {
        this.preparation = preparation;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    @Override
    public String toString() {
        return "Recipe [id = " + this.id
                        + ", name = " + name
                        + ", ingredirnts = " + ingredients
                        + ", description" + description
                        + ", created = " + created
                        + ", updated = " + updated
                        + ", preparation time = " + preparationTime
                        + ", preparation = " + preparation
                        + ", admin = " + admin
                        + "]";
    }
}
