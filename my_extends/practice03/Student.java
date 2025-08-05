package my_extends.practice03;

public class Student {
    String name;
    int age;
    String school;
    public Student(){
        this("null", 0, "东北大学");//使学校默认为东北大学
    }
    public Student(String name, int age, String school){
        this.name = name;
        this.age = age;
        this.school = school;
    }
}   
