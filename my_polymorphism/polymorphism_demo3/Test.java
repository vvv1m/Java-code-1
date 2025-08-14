package my_polymorphism.polymorphism_demo3;
import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        // 使用父类作为参数，可以接受所以子类对象，多态的便利
        StringBuilder sb;
        //sb.append(Object obj); // 向容器中添加对象的方法
        ArrayList list = new ArrayList();
        //list.add(Object e); //向容器中添加对象的方法

        Animal a = new Dog();
        //成员方法，编译看左边，运行看右边
        a.eat();

        //多态的弊端
        //不能调用子类的特有功能
        //a.lookHome();//编译错误
        //解决方案
        //变回子类类型
        // Dog d = (Dog)a;
        // d.lookHome();
        //细节---在转换的时候不能瞎转，转成其他类的类型会报错

        //例
        // Cat c = (Cat)a;
        // c.catchMouse(); //Exception in thread "main" java.lang.ClassCastException: class my_polymorphism.polymorphism_demo3.Dog cannot be cast to class my_polymorphism.polymorphism_demo3.Cat (my_polymorphism.polymorphism_demo3.Dog and my_polymorphism.polymorphism_demo3.Cat are in unnamed module of loader 'app')
        //                 //at my_polymorphism.polymorphism_demo3.Test.main(Test.java:24)
        //那么，如果原本a的类型为止，该如何转换
        //在需要类型转换的时候可以增加if判断
        // if(a instanceof Dog){ //类型判断，instanceof前为变量名，后为类名
        //     Dog d = (Dog)a;
        //     d.lookHome();
        // }else if(a instanceof Cat){
        //     Cat c = (Cat)a;
        //     c.catchMouse();
        // }else{
        //     System.out.println("没有这个类型，无法转换");
        // }
        //由于每次都要先判断再强转，过于麻烦，所以Java设计了一个新特性
        //在判断a的类型的符合条件后进行强转，如果a不符合则结果直接是false
        //注：只能在Java16+的版本才能使用
        if(a instanceof Dog d){
            d.lookHome();
        }else if(a instanceof Cat c){
            c.catchMouse();
        }else{
            System.out.println("没有这个类型，无法转换");
        }
    }
}
class Animal{
    public void eat(){
        System.out.println("动物在吃东西");
    }
}
class Dog extends Animal{
    @Override
    public void eat(){
        System.out.println("狗吃骨头");
    }
    public void lookHome(){
        System.out.println("狗看家");
    }
}
class Cat extends Animal{
    @Override
    public void eat(){
        System.out.println("猫吃小鱼干");
    }
    public void catchMouse(){
        System.out.println("猫抓老鼠");
    }
}