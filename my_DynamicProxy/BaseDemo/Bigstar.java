package my_DynamicProxy.BaseDemo;

public class Bigstar implements Star{
    String name;

    public Bigstar() {
    }

    public Bigstar(String name) {
        this.name = name;
    }
    @Override
    public String sing(String name){
        System.out.println(this.name + "is singing" + name);
        return "Thanks";
    }
    @Override
    public void dance(){
        System.out.println(this.name + "is dancing");
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
