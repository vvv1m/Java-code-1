package my_abstract.abstract_demo02;

public abstract class Animal {
    private String name;
    private int age;
    public Animal() {
    }
    public Animal(String name, int age) {
        this.name = name;
        this.age = age;
    }
    public abstract void action();
    public void trink(){
        System.out.println(name + "喝水");
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
