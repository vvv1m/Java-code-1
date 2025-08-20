package my_interface.interface_demo2;

public abstract class Coach extends Person{
    public Coach(){}
    public Coach(String name, int age){
        super(name, age);
    }
    public abstract void teach();
}   
