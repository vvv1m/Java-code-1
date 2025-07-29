package my_extends.practice01;

public class Animal {
    public Animal(){}
    //权限修饰符
    //若使用private: 子类就无法访问 
    //private代表私有，只能在本类中访问

    //注意事项： 子类只能访问父类中非私有的成员
    public void eatRice(){
        System.out.println("chifan");
    }
    public void drinkWater(){
        System.out.println("heshui");
    }

}
