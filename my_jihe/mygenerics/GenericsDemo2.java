package my_jihe.mygenerics;

import java.util.ArrayList;

public class GenericsDemo2 {
    public static void main(String[] args) {
        //定义一个方法，形参是一个集合，集合中的数据类型不确定
    }

    //方法一 利用泛型方法
    //弊端：此时可以接受任意的数据类型
    // Ye Fu Zi Student
    //希望：本方法虽然不确定类型，但以后希望只能传递Ye，Fu，Zi
    //此时我们就可以使用泛型的通配符
    //   ?也表示不确定的类型
    //   但是它可以进行类型的限定
    //      表示方式：? extends E: 表示可以传递E和E所有的子类类型
    //               ? super E: 表示可以传递E和E所有的父类类型

    //应用场景：
    //      1如果我们在定义类，接口，方法时，如果类型不确定，就可以定义泛型类，泛型方法，泛型接口。
    //      2如果类型不确定，但是能知道以后只能传递某个继承体系中的，就可以使用泛型的通配符
    //泛型的通配符：关键点在于可以限定类型的范围
    public static<E> void method1(ArrayList<E> list){

    }
    public static void method2(ArrayList<? extends Ye> list){

    }
    public static void method3(ArrayList<? super Zi> list){

    }
}
