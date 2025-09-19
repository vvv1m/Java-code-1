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
    }
}
