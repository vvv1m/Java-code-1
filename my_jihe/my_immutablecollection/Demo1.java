package my_jihe.my_immutablecollection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Demo1 {
    //不可变集合就是不可以被修改的集合
    //应用场景：如果某个数据不能被修改，把它防御性地拷贝到不可变集合中是一个很好的实践
    //          当集合对象被不可信的库调用，不可变的形式是安全的 
    //static <E> List<E> of (E...elements)    创建一个具有指定元素的List集合对象
    //static <E> Set<E> of (E...elements)     创建一个具有指定元素的Set集合对象
    //static <K,V> Map<K,V> of (E...elements) 创建一个具有指定元素的Map集合对象 
    public static void main(String[] args) {
        List<String> list = List.of("zhangsan", "lisi", "wangwu");
        //创建set不可变的细节 键是不能重复的



        //创建Map不可变集合的细节：键是不能重复的
        //Map里面的of方法参数是有上限的，最多传递20个参数，也就是10个键值对
        //因为Map如果要传可变参数，就要传两个可变参数，而且K，V不能配对，但是一个形参只能穿一个可变参数
        //那么如果想要传多个键值对该怎么传， Map中有另一个方法
        //static <K,V> Map<K,V> ofEntries(Entry<? extends K, ? extends V>...entries)
        HashMap<String, String> hm = new HashMap<>();
        hm.put("aaa", "111");
        hm.put("bbb", "111");
        hm.put("ccc", "111");
        hm.put("ddd", "111");
        hm.put("eee", "111");
        hm.put("fff", "111");
        hm.put("ggg", "111");
        hm.put("hhh", "111");
        hm.put("iii", "111");
        hm.put("jjj", "111");
        hm.put("kkk", "111");
        Set<Map.Entry<String, String>> entries = hm.entrySet();
        // //由于可变参就相当于传递一个数组，所以要把entries变成一个数组
        // Map.Entry[] arr1 = new Map.Entry[0];
        // //toArray方法在底层会比较集合的长度跟数组的长度两者的大小
        // //如果集合的长度 > 数组的长度（[0]） : 数据在数组中放不下，此时会根据实际数据的个数重新创建数组
        // //集合的长度<数组长度，就不会创建新数组，而是直接用
        // Map.Entry[] arr2 = entries.toArray(arr1);
        // Map m= Map.ofEntries(arr2);
        // System.out.println(m);
        // // m.put("kkk", "222");


        //上述代码太过繁琐，现提供一个简化的方法
        //Map<String, String> m = Map.ofEntries(hm.entrySet().toArray(new Map.Entry[0]));

        //但但但是，由于这个代码还是太繁琐了，Java又提供了另一种方法
        //JDK10的时候出现了copeOf方法
        Map<String, String> map = Map.copyOf(hm);
        System.out.println(map);
        //map.put("lll", "111");
    }
}
