package Javashiyan;

import java.util.Scanner;

public class User {
    private String id;
    private String password;
    public User() {
    }
    public User(String id, String password) {
        this.id = id;
        this.password = password;
    }
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setPassword() {
        Scanner sc = new Scanner(System.in);
        while(true){
            System.out.println("请输入密码");
            String password1 = sc.nextLine();
            System.out.println("请再次输入密码");
            String password2 = sc.nextLine();
            if(password1.equals(password2)){
                this.password = password1;
                System.out.println("密码设置成功");
                break;
            }else{
                System.out.println("两次密码不一致，请重新输入");
            }
        }
    }
    public boolean login(){
        Scanner sc = new Scanner(System.in);
        System.out.println("管理员请输入密码");
        String password = sc.nextLine();
        if(password.equals(this.password)){
            System.out.println("登录成功");
            return true;
        }else{
            System.out.println("密码错误，登录失败");
            return false;
        } 
    }
}
