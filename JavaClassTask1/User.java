package JavaClassTask1;

public class User extends Person {
    private String role;
    
    public User() {
        super();
        this.role = "管理员";
    }
    
    public User(String name, String id, String password) {
        super(name, id, password);
        this.role = "管理员";
    }
    
    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    @Override
    public String toString() {
        return "User{" +
                "name='" + getName() + '\'' +
                ", id='" + getId() + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}