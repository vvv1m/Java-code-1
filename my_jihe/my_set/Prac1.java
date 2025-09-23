package my_jihe.my_set;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Prac1 {
    //利用Set系列集合，添加字符串，并使用多种方式遍历

    public static void main(String[] args) {
        Set<String> s = new HashSet<>();
        s.add("张三");
        s.add("张三");
        s.add("李四");
        s.add("王五");
        System.out.println(s);
        //迭代器遍历
        Iterator<String> it = s.iterator();
        while(it.hasNext()){
            String str = it.next();
            System.out.println(str);
        }
        //增强for
        for (String str : s) {
            System.out.println(str);
        }
        //lambda表达式
        // s.forEach((String str)->{
        //     System.out.println(str);
        // });
        //再简化一点
        s.forEach(str -> System.out.println(str));

        
    }
}
