package my_static.a01demo3;

public class Student {
    private String name;
    private int age;
    private String gender;
    static String teachername;
    public Student(){}
    public Student(String name, int age, String gender){
        this.name = name;
        this.age = age;
        this.gender = gender;
    }
    public String getName(){
        return name;
    }
    public int getAge(){
        return age;
    }
    public String getGender(){
        return gender;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setAge(int age){
        this.age = age;
    }
    public void setGender(String gender){
        this.gender = gender;
    }
    public void show1(Student this){ // this表示当前方法调用者的地址
        //这个this是由虚拟机赋值的
        System.out.println("this:" + this);
        System.out.println(name + ", " + age + ", " + teachername); // name等变量前有个隐含的this
        //调用其他方法
        show2();//前面也有一个隐含的this.，即拿着调用show1的对象调用show2方法
    }
    public void show2(){
        System.out.println("show2");
    }
    public static void method(){ //不能在()中添加this，会报错，这表明静态方法中没有this关键字
        System.out.println("静态方法");
    }
}
