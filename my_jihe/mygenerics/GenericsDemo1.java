package my_jihe.mygenerics;

import java.util.ArrayList;

public class GenericsDemo1 {
    public static void main(String[] args) {
        //泛型不具备继承性，但数据具备
        //创建集合的对象
        ArrayList<Ye> list1 = new ArrayList<>();
        ArrayList<Fu> list2 = new ArrayList<>();
        ArrayList<Zi> list3 = new ArrayList<>();
        //调用method方法
        method(list1);//不会报错
        //method(list2);
        //method(list3); //传入子类时会报错，说明泛型不具备继承性

        list1.add(new Ye());
        list1.add(new Fu());
        list1.add(new Zi());
        
    }
    public static void method(ArrayList<Ye> list){

    }
}
class Ye{}
class Fu extends Ye{}
class Zi extends Fu{}
