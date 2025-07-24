package my_static.a01demo3;
import java.util.ArrayList;
public class Test {
    public static void main(String[] args) {
        ArrayList<Student> StuList = new ArrayList<>(); 
        Student.teachername = "xiaoyun";
        Student s1 = new Student("zhangsan", 12, "nan");
        Student s2 = new Student("lisi", 19, "nv");
        Student s3 = new Student("wangwu", 20, "nan");
        StuList.add(s1);
        StuList.add(s2);
        StuList.add(s3);
        int maxAge = StudentUtil.getMaxAge(StuList);
        System.out.println(maxAge);
        System.out.println("s1:" + s1);
        s1.show1();//不能自己调用，即s1.show1(s1);这样是错误的
        System.out.println("s2:" + s2);
        s2.show1();
    }
}
