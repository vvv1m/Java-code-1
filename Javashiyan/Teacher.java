package Javashiyan;

import java.util.Scanner;

public class Teacher extends User{
    private String name;
    private String level;
    public Teacher() {
    }
    public Teacher(String id, String password, String name, String level) {
        super(id, password);
        this.name = name;
        this.level = level;
    }
    public String getLevel() {
        return level;
    }
    public void setLevel(String level) {
        this.level = level;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;   
    }

    public void show(){
        System.out.println("姓名：" + getName() + " 工号：" + getId() + " 系别：" + getLevel());
    }
    public boolean login(){
        Scanner sc = new Scanner(System.in);
        System.out.println("教师请输入工号");
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
