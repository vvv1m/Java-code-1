package Javashiyan;

import java.util.Scanner;

public class Student extends User{
    private String name;
    private String classname;
    public Student() {
    }
    public Student(String name, String password, String id, String classname) {
        super(id, password);
        this.name = id;
        this.classname = classname;
    }
    public String getClassname() {
        return classname;
    }
    public void setClassname(String classname) {
        this.classname = classname;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;   
    }

    public void show(){
        System.out.println("姓名：" + getName() + " 学号：" + getId() + " 班级：" + getClassname());
    }
    public boolean login(){
        Scanner sc = new Scanner(System.in);
        System.out.println("学生请输入学号");
        String id = sc.nextLine();
        System.out.println("请输入密码");
        String password = sc.nextLine();
        if(id.equals(getId()) && password.equals(getPassword())){
            System.out.println("登录成功");
            return true;
        }else{
            System.out.println("密码错误，登录失败");
            return false;
        }
    }
}