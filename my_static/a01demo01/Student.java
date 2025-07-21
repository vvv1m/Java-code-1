package my_static.a01demo01;

public class Student {
    private String name;
    private int age;
    private String gender;
    public static String teachername;//静态变量，类实例化的时候不改变他的值
    //未被static修饰的变量为实例变量，被static修饰的是类变量
    public Student(){};
    public Student(String name, int age, String gender){
        this.name = name;
        this.age = age;
        this.gender = gender;
    }
    public String getName(){
        return name;
    }
    public int getAge(){
        return age;
    }
    public String getGender(){
        return gender;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setAge(int age){
        this.age = age;
    }
    public void setGender(String gender){
        this.gender = gender;
    }
    public void show(){
        System.out.println(name + ", " + age + ", " + gender + ", " + teachername);
    }

}
