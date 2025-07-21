package my_static.a01demo01;
public class StudentTest{
    public static void main(String[] args){
        Student s1 = new Student();
        s1.setName("zhangsan");
        s1.setAge(20);
        s1.setGender("nan");
        //s1.teachername = "xiaoyun"; //实例名调用
        Student.teachername = "xiaoyun";//类名调用
        Student s2 = new Student();
        s2.show();//即使s2没有实例化，也可以输出老师名字

    }
}