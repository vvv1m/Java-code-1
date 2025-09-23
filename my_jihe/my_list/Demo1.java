package my_jihe.my_list;

import java.util.ArrayList;
import java.util.List;

public class Demo1 {
    public static void main(String[] args) {
        //List是Collection的子类，所以Collection中的方法List都可以使用
        //List有索引，所以有一些特有的通过索引操作的方法

        //1 void add(int index, E element) 在集合指定位置插入指定元素
        //2 E remove(int index) 删除索引处的指定元素，返回被删除的元素
        //3 E set(int index, E element) 修改指定索引处的元素，返回被修改的元素
        //4 E get(int index) 返回指定索引处的元素

        //创建一个集合
        List<String> list = new ArrayList<>();
        //添加元素
        list.add("aaa");
        list.add("bbb");
        list.add("ccc");
        System.out.println(list);
        //1 插入指定元素，原来索引的元素会依次后移
        list.add(1,"dadaf");
        System.out.println(list);
        //2 删除指定元素
        String s = list.remove(1);
        System.out.println(s);

        //删除的小细节
        List<Integer> list1 = new ArrayList<>();
        list1.add(542);
        list1.add(24);
        list1.add(1513);
        list1.add(1);

        list1.remove(1);//此时删除的是元素1还是索引1的元素？
        //很显然是索引1的元素，因为在调用方法时，方法出现重载，将优先调用实参和形参类型一致的方法
        //那么如果我就想删除1，该如何做
        //手动装箱，这样就会优先匹配对象为形参的方法
        Integer i = Integer.valueOf(1);
        list1.remove(i);
        System.out.println(list1);


        //修改指定索引处的元素
        String result = list.set(0,"RTYUI");
        System.out.println(list);
        System.out.println(result);

        //获取索引处的元素
        String ss = list.get(0);
        System.out.println(ss);

    }
}
