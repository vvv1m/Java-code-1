package my_extends.practice04;

public class Chef extends Employee {
    public Chef(){}
    public Chef(String id, String name, double salary){
        super(id, name, salary);
    }
    @Override
    public void Work(){
        System.out.println("厨师的工作是炒菜");
    }
    @Override
    public void Eat(){
        System.out.println("厨师吃米饭");
    }
}
