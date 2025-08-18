package my_abstract.abstract_demo02;

public class Dog extends Animal{
    public Dog(){

    }
    public Dog(String name, int age){
        super(name, age);
    }
    @Override
    public void action(){
        System.out.println("吃骨头");
    }
}
