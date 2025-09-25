package my_jihe.my_map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Map.Entry;

public class MyHashMap {
    //HashMap中的方法与Map中的方法并无不同
    //HashMap的特点：是Map的一个实现类，特点都是键决定的---无序、不重复、无索引
    //      HashMap跟HashSet的底层原理一样，都是哈希表结构，JDK8的时候为了提高性能又加入了红黑树
    //创建对象底层会默认创建一个长度为16，加载因子为0.75的数组
    //再利用put方法添加数据
    //  put方法的底层会先创建一个Entry对象，记录键和值，然后利用键计算哈希值，然后根据公式计算出存入的索引
    //如果该位置为null，则添加进去，如果不是，会调用equals方法比较键的数据
    //  如果相同，新数据就会覆盖原数据（put隐含的覆盖功能）
    //  如果不同，新元素直接挂在老元素下面形成链表，链表长度超过8且数组长度大于等于64转成红黑树
    
    //如果键存储自定义对象，要重写hashCode和equals方法，如果值存储自定义对象，则不需要


    public static void main(String[] args) {
        //案例一 存并遍历
        HashMap<Student, String> hs = new HashMap<>();
        Student s1 = new Student("zhangsan", 23);
        Student s2 = new Student("lisi", 24);
        Student s3 = new Student("wangwu", 25);
        Student s4 = new Student("zhangsan", 23);
        hs.put(s1, "aaa");
        hs.put(s2, "bbb");
        hs.put(s3, "ccc");
        hs.put(s4, "ddd");
        System.out.println(hs);
        hs.forEach((key, value) ->{
            System.out.println(key + " " + value);
        });

        //案例二 统计投票人数
        //利用map存储景点和次数 每次存入判断集合是否存在该景点
        //定义一个数组，存储四个景点
        String[] arr = {"A", "B", "C", "D"};
        //用随机数模拟80个同学投票，并存储投票的结果
        ArrayList<String> list = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < 80; i++) {
            int index = r.nextInt(arr.length);
            list.add(arr[index]);
        }
        //如果要统计的东西比较多，不方便使用计数器思想 
        HashMap<String, Integer> hs1 = new HashMap<>();
        for (String name : list) {
            if(hs1.containsKey(name)){
                int count = hs1.get(name);
                count++;
                hs1.put(name, count);
            }else{
                hs1.put(name, 1);
            }
        }
        System.out.println(hs1);
        //求最大值
        int max = 0;
        Set<Map.Entry<String, Integer> > entries = hs1.entrySet();
        for (Entry<String,Integer> entry : entries) {
            if(max < entry.getValue()){
                max = entry.getValue();
            }
        }
        System.out.println(max);
        //判断哪个景点的次数和最大值一样，如果一样打印出来
        for (Entry<String,Integer> entry : entries) {
            if(max == entry.getValue()){
                System.out.println(entry.getKey());
            }
        }
    }
}
