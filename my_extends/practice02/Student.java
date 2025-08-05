package my_extends.practice02;

public class Student extends Person {
    public Student(){
        //this调用和super调用都必须在第一行，所以二者不能同时使用，可将super放在有参构造中 ，再用this调用
        //实现默认值为human和0
        this("human", 0); 

        //super(); //super()只能在构造方法中使用，不能放在类体中
        System.out.println("子类的无参构造");
    }
    public Student(String name, int age){
        super(name,age); // 子类如何调用父类的带参构造
        System.out.println(this.name + " " + this.age);
    }

}
