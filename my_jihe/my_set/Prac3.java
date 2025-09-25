package my_jihe.my_set;

import java.util.TreeSet;

public class Prac3 {
    public static void main(String[] args) {
        Student s1 = new Student("zhangsan", 23, 89, 48, 100);
        Student s2 = new Student("lisi", 24, 89, 48, 100);
        Student s3 = new Student("wangwu", 25, 80, 90, 96);
        Student s4 = new Student("zhaoliu", 23, 80, 78, 99);
        Student s5 = new Student("zhangsan", 23, 89, 48, 100);
        TreeSet<Student> ts = new TreeSet<>((o1,o2)->{
            int result = o1.getEnglish() + o1.getChinese() + o1.getMath() - 
                            o2.getChinese() - o2.getEnglish() - o2.getMath();
            result = result == 0 ? o1.getChinese() - o2.getChinese() : result;
            result = result == 0 ? o1.getMath() - o1.getMath() : result;
            result = result == 0 ? o1.getEnglish() - o2.getEnglish() : result;
            result = result == 0 ? o1.getAge() - o2.getAge() : result;
            result = result == 0 ? o1.compareTo(o2) : result;
            return -result;
        });
        ts.add(s1);
        ts.add(s2);
        ts.add(s3);
        ts.add(s4);
        ts.add(s5);
        System.out.println(ts);
    }
}
