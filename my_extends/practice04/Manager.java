package my_extends.practice04;

public class Manager extends Employee{
    double managesalary;
    public Manager(){
        super();
    }
    public Manager(String id, String name, double salary, int managesalary){
        super(id, name, salary);
        this.managesalary = managesalary;
    }
    public double getManagesalary() {
        return managesalary;
    }
    public void setManagesalary(double managesalary) {
        this.managesalary = managesalary;
    }
    @Override
    public void Work(){
        System.out.println("经理要管理其他人");
    }
    @Override
    public void Eat(){
        System.out.println("经理吃米饭");
    }
    
}
