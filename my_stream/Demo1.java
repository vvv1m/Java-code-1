package my_stream;

import java.util.ArrayList;

public class Demo1 {
    public static void main(String[] args) {
        //创建集合添加元素，要求如下
        //  1 所有以张开头的元素存储到新集合中
        //  2 以张开头的，长度为三的元素再存到新集合中
        //  3 遍历打印最终结果
        ArrayList<String> list1 = new ArrayList<>();
        list1.add("张aa");
        list1.add("张2");
        list1.add("aaa");
        list1.add("bbb");
        // ArrayList<String> list2 = new ArrayList<>();
        // for (String i : list1) {
        //     if(i.startsWith("张")){
        //         list2.add(i);
        //     }
        // }
        // System.out.println(list2);
        // ArrayList<String> list3 = new ArrayList<>();
        // for (String i : list2) {
        //     if(i.length() == 3){
        //         list3.add(i);
        //     }
        // }
        // System.out.println(list3);



        //上述代码过于麻烦，如果使用stream流就会非常简单
        list1.stream()
            .filter(name->name.startsWith("张"))
            .filter(name->name.length() == 3)
            .forEach(name->System.out.println(name));

    }
}
