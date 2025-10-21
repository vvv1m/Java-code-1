package my_stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.stream.Stream;

public class Demo2 {
    public static void main(String[] args) {
        // 1 单列集合获取Stream
        ArrayList<String> list = new ArrayList<>();
        Collections.addAll(list, "aaa", "bbb", "ccc", "ddd");
        //获取到一条流水线，并把集合中的数据放到流水线上
        // list.stream().forEach(name->System.out.println(name));

        //2双列集合获取Stream
        HashMap<String, Integer> hm = new HashMap<>();
        hm.put("aaa", 111);
        hm.put("bbb", 222);
        hm.put("ccc", 333);
        //第一种方法，获取键值
        // hm.keySet().stream().forEach(s->System.out.println(s+"="+hm.get(s)));
        //第二种方法
        // hm.entrySet().stream().forEach(s->System.out.println(s));

        //3数组获取Stream
        int[] arr = {1,2,3,4,5,6,7,8,9,10};
        String[] strarr = {"aaa", "bbb", "ccc", "ddd"};
        // Arrays.stream(arr).forEach(num->System.out.println(num));
        // Arrays.stream(strarr).forEach(str->System.out.println(str));

        //4 一堆零散数据
        Stream.of(1,2,3,4,5).forEach(num->System.out.println(num));
        //注意 该方法是可以传入数组的，但是只能传入引用类型的数组，不能传入基本类型的数组，传入基本类型会打印地址
        //      因为方法将基本类型数组当做了一个元素放到Stream中
    }
}
