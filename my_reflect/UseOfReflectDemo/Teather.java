package my_reflect.UseOfReflectDemo;

public class Teather {
    String name;
    int age;
    public Teather() {
    }
    public Teather(String name, int age) {
        this.name = name;
        this.age = age;
    }
    public void teach(){
        System.out.println("老师在教书");
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    
}
