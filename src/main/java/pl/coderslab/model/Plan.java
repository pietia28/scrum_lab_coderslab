package pl.coderslab.model;

import java.time.LocalDateTime;

public class Plan {
    private int id;
    private String name;
    private String description;
    private LocalDateTime created;
    private Admin admin;

    @Override
    public String toString() {
        return "Plan{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", created='" + created + '\'' +
                ", admin=" + admin +
                '}';
    }
    public Plan() {
    }

    public Plan(String name, String description, LocalDateTime created, Admin admin) {
        this.name = name;
        this.description = description;
        this.created = created;
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

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
}



