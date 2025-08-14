package my_polymorphism.polymorphism_demo1;

public class Test {
    public static void main(String[] args) {
        Student s = new Student();
        s.setAge(18);
        s.setName("zhangsan");
        Teacher t = new Teacher();
        t.setAge(30);
        t.setName("xiaoyun");
        Administrator ad = new Administrator();
        ad.setAge(25);
        ad.setName("fangyuan");
        register(s);
        register(t);
        register(ad);


    }
    //使方法能够接受三种身份的对象，只能把参数写成三者的父类
    public static void register(Person p){
        p.show();
    }
}
