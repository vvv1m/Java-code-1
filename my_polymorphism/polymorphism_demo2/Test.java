package my_polymorphism.polymorphism_demo2;

public class Test {
    public static void main(String[] args) {
        //使用多态方式创建对象 
        //Fu f = new Zi();
        Animal a = new Dog();

        //多态调用成员对象，编译看左边，运行也看左边
        //编译看左边：javac编译的时候，会看左边的父类中有没有这个变量，有则编译成功，没有则编译失败
        //运行看左边：Java运行代码时，实际获取的是左边父类中成员变量的值
        System.out.println(a.name); // animal

        //调用成员方法：编译看左边，运行看右边
        //编译看左边：Javac编译代码的时候......同调用对象
        //运行看右边：Java运行代码的时候实际上运行的是子类中的方法
        a.show();
    }
}
class Animal {
    String name = "animal";
    public void show(){
        System.out.println("Animal ---- showFunction");
    }
}
class Dog extends Animal{
    String name = "dog";
    @Override   
    public void show(){
        System.out.println("Dog ---- showFunction");
    }
}
class Cat extends Animal{
    String name = "cat";
    @Override
    public void show(){
        System.out.println("Cat ---- showFunction");
    }
}