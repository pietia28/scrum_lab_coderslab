package pl.coderslab.model;

import org.mindrot.jbcrypt.BCrypt;

public class Admin {
    private int id;
    private String first_name;
    private String last_name;
    private String email;
    private String password;
    private byte superAdmin;
    private byte enable;

    public Admin() {
    }

    @Override
    public String toString() {
        return "Admin [id=" + this.id + ", first_name=" + this.first_name
                + ", last_name=" + this.last_name + ", email=" + this.email
                + ", password=" + this.password + "superadmin=" + this.superAdmin
                + ", enable " + this.enable + "]";
    }

    public Admin(int id, String first_name, String last_name, String email, String password, byte superAdmin, byte enable) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = this.hashPassword(password);
        this.superAdmin = superAdmin;
        this.enable = enable;
    }

    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
    
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return this.first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return this.last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = this.hashPassword(password);
    }

    public void setHashPassword(String password) {
        this.password = password;
    }

    public byte getSuperAdmin() {
        return this.superAdmin;
    }

    public void setSuperAdmin(byte superadmin) {
        this.superAdmin = superadmin;
    }

    public byte getEnable() {
        return this.enable;
    }

    public void setEnable(byte enable) {
        this.enable = enable;
    }
}
