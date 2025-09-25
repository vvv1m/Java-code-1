package my_jihe.my_map;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.BiConsumer;

public class Demo2 {
    public static void main(String[] args) {
        //map遍历方式 --- 键找值
        //1 创建对象
        Map<String, String> map = new HashMap<>();
        //2 添加元素
        map.put("111", "aaa");
        map.put("222", "bbb");
        map.put("333", "ccc");
        //3 通过值找键
        //      先获取所有的键，把这些键放到一个单列集合中
        Set<String> keys = map.keySet();
        //      再遍历单列集合，获得每一个键
        for (String k : keys) {
            //System.out.println(k);
            //  利用键获得每一个值
            String value = map.get(k);
            System.out.println(k + " = " + value);
        }
        //4 通过键值对遍历
        //      通过方法获取所有的键值对
        //      由于Entry是Map的接口，所以使用的时候需要Map.
        Set<Map.Entry<String, String> > entries = map.entrySet();
        //      遍历entries这个集合，得到每一个键值对对象
        for (Entry<String,String> entry : entries) {
            //  用entry调用get方法得到每一个键和值
            String key = entry.getKey();
            String value = entry.getValue();
            System.out.println(key + " = " + value);
        }
        //5 利用lambda表达式遍历
        // map.forEach(new BiConsumer<String,String>() {
        //     @Override
        //     public void accept(String key, String value){
        //         System.out.println(key + " = " + value);
        //     }
        // }); 
        map.forEach((key, value) -> System.out.println(key + " = " + value));
    }
}
