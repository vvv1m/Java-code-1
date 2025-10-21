package my_functioncite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Collectors;

public class Demo2 {
    
    public static void main(String[] args) {
        //如何引用静态方法
        //格式 类名::静态方法
        //例 集合中有一些字符串数字，将他们变成int类型
        ArrayList<String> list = new ArrayList<>();
        Collections.addAll(list, "1", "2", "3", "4", "5", "6");
        list.stream()
            .map(Integer::parseInt)
            .forEach(s->System.out.println(s));

        //如何引用成员方法
        //格式 对象::成员方法
        //其他类 其他类::方法名
        //本类 this::方法名
        //父类 super::方法名
        //注意 在静态方法中是没有this的，那么在引用成员方法的时候就需要先创建成员对象，再引用成员方法

        //如何引用构造方法
        //格式 类名::new
        //例子 集合里存储姓名和年龄，要求封装成Student对象并收集到List集合中
        ArrayList<String> list1 = new ArrayList<>();
        Collections.addAll(list1, "aaa,15", "bbb,14", "ccc,14", "ddd,20", "eee,21");
        List<Student> slist = list1.stream()
            .map(Student::new).collect(Collectors.toList());
        System.out.println(slist);


        //如何使用类名引用成员方法
        //格式 类名::成员方法
        //例 集合里一些字符，要求变成大写后输出
        ArrayList<String> list2 = new ArrayList<>();
        Collections.addAll(list2, "aaa", "bbb", "ccc", "ddd", "eee");
        //匿名内部类写法
        // list2.stream()
        //     .map(new Function<String,String>() {
        //         @Override
        //         public String apply(String t) {
        //             return t.toUpperCase();
        //         }
        //     })
        //     .forEach(s->System.out.println(s));
        //方法引用写法
        list2.stream().map(String::toUpperCase)
            .forEach(s->System.out.println(s)); 
        //观察源码可以发现 toUpperCase方法并没有形参，这与通常的方法引用规则不同
        //所以使用类名引用成员方法有自己的规则
        //  1 需要有函数式接口
        //  2 被引用的方法必须已存在
        //  3 被引用的方法的形参，需要跟抽象方法的第二个形参到最后一个形参保持一致 返回值需要保持一致
        //  4 被引用方法的功能需要满足当前需求
        //抽象方法形参详解
        //  第一个参数：表示被引用方法的调用者，决定了可以引用哪些类中的方法
        //      在Stream流中，第一个参数一般都表示流里面的每一个数据
        //      假设流中的数据是字符串，那么使用这种方法进行方法引用，只能引用String类中的方法
        //  第二个参数到最后一个参数：跟被引用方法的形参保持一致，如    果没有第二个参数，说明被引用的方法需要是无参的成员方法
        //  map(String::toUpperCase)就相当于拿着流里面的每一个数据，去调用String类中的toUpperCase方法
        // 通过类名引用具有一定的局限性，就是只能引用抽象方法第一个参数对应的成员方法，不能引用所有的成员方法



        //引用数组类型的构造方法
        //格式：数据类型[]::new
        //细节：数组的类型，需要跟流中的数据类型保持一致
        ArrayList<Integer> list3 = new ArrayList<>();
        Collections.addAll(list3, 1, 2, 3, 4, 5, 6);
        //把数据收到数组当中,匿名内部类写法
        // Integer[] arr = list3.stream()
        //     .toArray(new IntFunction<Integer[]>() {

        //         @Override
        //         public Integer[] apply(int value) {
                    
        //             return new Integer[value];
        //         }
                
        //     });
        // System.out.println(Arrays.toString(arr)); 
        // 方法引用写法
        Integer[] arr = list3.stream().toArray(Integer[]::new);
        System.out.println(Arrays.toString(arr));
    }
}
