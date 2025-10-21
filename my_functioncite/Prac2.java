package my_functioncite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;

public class Prac2 {
    public static void main(String[] args) {
        ArrayList<Student> list = new ArrayList<>();
        list.add(new Student("zhangsan", 23));
        list.add(new Student("lisi", 24));
        list.add(new Student("wangwu", 25));
        //要求获取姓名并放到数组中
        // list.stream().map(new Function<Student,String>() {

        //     @Override
        //     public String apply(Student t) {
                
        //         return t.getName();
        //     }
            
        // }).toArray(String[]::new);
        //调用类的内部方法时有两种情况
        //1 对象::方法名 要求形参和返回值都一样
        //2 类名::方法名 仅要求被引用方法形参是跟抽象方法第二个参数后面的参数保持一致
        //观察得到抽象方法没有第二个参数，所以引用的方法只能是空参的
        String[] arr = list.stream().map(Student::getName).toArray(String[]::new);
        System.out.println(Arrays.toString(arr));

        //方法引用的小技巧
        //1 有没有符合要求的方法
        //2 方法符不符合规则
    }
}
