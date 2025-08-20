package my_interface.interface_demo2;

public class Bplayer extends Player{
    public Bplayer(){}
    public Bplayer(String name, int age){
        super(name, age);
    }
    @Override
    public void study(){
        System.out.println("学打篮球");
    }
}
