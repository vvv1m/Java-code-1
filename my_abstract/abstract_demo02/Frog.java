package my_abstract.abstract_demo02;

public class Frog extends Animal{
    public Frog(){

    }
    public Frog(String name, int age){
        super(name, age);
    }
    @Override
    public void action(){
        System.out.println("青蛙吃虫子");
    }
}
