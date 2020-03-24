package pl.coderslab.model;

public class DayName {

    private int id;
    private String name;
    private int displayOrder;

    @Override
    public String toString() {
        return "DayName [id=" + id + ", name=" + name + ", displayOrder=" + displayOrder + "]";
    }

    public DayName() {
    }

    public void setId(int id) { this.id = id; }

    public void setName(String name) { this.name = name; }

    public void setDisplayOrder(int displayOrder) { this.displayOrder = displayOrder; }
}
