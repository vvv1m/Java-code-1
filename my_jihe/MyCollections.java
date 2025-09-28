package my_jihe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MyCollections {
    //Collections是集合的工具类
    //常用APi
    //public static <T> boolean addAll(Collection<T> c, T...elements) 批量添加元素
    //public static void shuffle(List<?> list) 打乱List集合数据
    //<T> void sort(List<T> list) 排序
    //<T> void sort(List<T> list, Comparator<T> c) 根据指定的规则排序
    //<T> int binarySearch(List<T> list, T key) 以二分查找查找元素
    //<T> copy(List<T> dest, List<T> src) 拷贝集合中的元素
    //<T> int fill(List<T> list, T obj) 使用指定元素填充集合
    //<T> void max/min(Collection<T> coll) 根据默认的自然排序获取最大/小值
    //<T> void swap(List<?> list, int i, int j) 交换集合中指定元素的位置
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();

        //批量添加
        Collections.addAll(list, "aaa","bbb","ccc","ddd","jgn", "uijfa", "jsif");
        System.out.println(list);
        Collections.shuffle(list,new Random());
        System.out.println(list);

    }
}
