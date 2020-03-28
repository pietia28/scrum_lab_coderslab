package pl.coderslab.model;

public class PlanDetails {
    private int id;
    private String mealName;
    private String dayName;
    private Recipe recipe;
    private Plan plan;

    public PlanDetails() {
    }

    public PlanDetails(int id, String mealName, String dayName, Recipe recipe, Plan plan) {
        this.id = id;
        this.mealName = mealName;
        this.dayName = dayName;
        this.recipe = recipe;
        this.plan = plan;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }


    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    @Override
    public String toString() {
        return "PlanDetails{" +
                "id=" + id +
                ", mealName='" + mealName + '\'' +
                ", dayName='" + dayName + '\'' +
                ", recipe=" + recipe +
                ", plan=" + plan +
                '}';
    }
}
