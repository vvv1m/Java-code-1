package my_interclass.interclass_demo2;

public class Test2 {
    public static void main(String[] args) {

        //整体可以理解为一个Swim接口的实现类对象



        //接口多态
        Swim s = new Swim() {
            @Override
            public void swim(){
                System.out.println("重写了游泳方法");
            }
        };

        //编译看左边，运行看右边
        s.swim();

        new Swim() {
            @Override
            public void swim(){
                System.out.println("重写了游泳方法");
            }
        }.swim(); //可以之间调用内部类中的方法
    }
}
