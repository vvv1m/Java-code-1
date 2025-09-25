package my_jihe.my_map;

import java.util.HashMap;
import java.util.Map;

public class Demo1 {
    public static void main(String[] args) {
        //V put(K key, V value) 添加元素
        //V remove(Object key) 根据键删除键值对元素
        //void clear() 移除所有键值对元素
        //boolean containsKey(Object key) 判断集合是否包含指定的键
        //boolean containsValue(Object value) 判断集合是否包含指定的值
        //boolean isEmpty() 判断集合是否为空
        //int size() 集合的长度，也就是集合中键的个数

        //1 创建集合
        Map<String, String> m = new HashMap<>();
        //2 添加元素
        //put方法的细节：
        //添加 / 覆盖
        //在添加数据的时候，如果键不存在，那么直接把键值对对象添加到map集合中
        //在添加数据的时候，如果键是存在的，那么会把原有的键值对对象覆盖，并把被覆盖的值进行返回
        m.put("aaa", "111");
        m.put("bbb", "222");
        m.put("ccc", "333");

        //String value = m.put("bbb", "444");//会把第二次添加的数据覆盖
        //System.out.println(value);//222

        //删除 删除的时候会把删除的键值对的值返回
        String result = m.remove("aaa");
        System.out.println(result); 
        System.out.println(m);

        //清空
        //m.clear();
        //System.out.println(m); // {}

        //判断是否包含
        boolean keyresult = m.containsKey("bbb");
        System.out.println(keyresult);
        boolean valueresult = m.containsValue("333");
        System.out.println(valueresult);
    }
}
