package my_extends.practice04;

public class Test {
    public static void main(String[] args) {
        Manager m = new Manager("dongbei001", "zhangsan", 15000, 8000);
        System.out.println(m.getId() + ", " + m.getName() + ", " + m.getSalary()
            + ", " + m.getManagesalary());
        m.Work();  
        m.Eat();
        Chef c = new Chef("dongbei002", "lisi", 8000);
        System.out.println(c.getId() + ", " + c.getName() + ", " + c.getSalary());
    }
}
