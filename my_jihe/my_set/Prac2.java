package my_jihe.my_set;

import java.util.HashSet;
import java.util.Iterator;

public class Prac2 {    
    public static void main(String[] args) {
        HashSet<Student> hs = new HashSet<>();
        Student s1 = new Student("zhangsan", 20);
        Student s2 = new Student("lisi", 30);
        Student s3 = new Student("wangwu", 15);
        Student s4 = new Student("zhaoliu", 20);
        Student s5 = new Student("aaa", 30);
        Student s6 = new Student("zhangsan", 20);
        Student s7 = new Student("lisi", 65);
        Student s8 = new Student("wangwu", 30);
        Student s9 = new Student("zhangsan", 20);
        addAll(hs,s1,s2,s3,s4,s5,s6,s7,s8,s9);
        Iterator<Student> it = hs.iterator();
        while(it.hasNext()){
            Student ss = it.next();
            System.out.println(ss.getName() + " " + ss.getAge());
        }
    }
    public static void addAll(HashSet<Student> hs, Student...s){
        for (Student ss : s) {
            hs.add(ss);
        }
    }
}
