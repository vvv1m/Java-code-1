package my_jihe.my_map;

import java.util.Comparator;
import java.util.TreeMap;

public class MyTreeMap {
    //TreeMap在底层和TreeSet相同，都是红黑树结构
    //由键决定特性 不重复 无索引 可排序（对键进行排序）
    //排序默认按照键的从小到大排序，也可以规定排序规则
    //两种排序规则
    //方式一 实现Comparable接口，指定比较规则
    //方式二 创建集合时传递Comparator比较器对象，指定比较规则
    //都写以方式二为准

    public static void main(String[] args) {
        //需求一 键为整数id，值为字符串商品名称，要求按照id升序排列，降序排列
        //升序排列
        // TreeMap<Integer, String> tm1 = new TreeMap<>();
        // tm1.put(3, "aaa");
        // tm1.put(2, "bbb");
        // tm1.put(4, "ccc");
        // tm1.put(5, "ddd");
        // tm1.put(1, "eee");
        // System.out.println(tm1);
        TreeMap<Integer, String> tm1 = new TreeMap<>((o1, o2)->{
            return o2 - o1;
        });
        tm1.put(3, "aaa");
        tm1.put(2, "bbb");
        tm1.put(4, "ccc");
        tm1.put(5, "ddd");
        tm1.put(1, "eee");
        System.out.println(tm1);


        //练习二 存入三个学生对象，按年龄升序，年龄相同按名字首字母升序排
        TreeMap<Student, String> tm2 = new TreeMap<>();
        Student s1 = new Student("zhangsan", 23);
        Student s2 = new Student("lisi", 12);
        Student s3 = new Student("wangwu", 12);
        tm2.put(s1, "天津");
        tm2.put(s2, "上海");
        tm2.put(s3, "北京");
        System.out.println(tm2);


        //练习三 字符串aababcabcdabcde 统计字符串中每一个字符出现的次数，并按格式输出
        //统计 --- 先想计数器思想 
        //如果统计的东西比较多，就不能用计数器思想
        //还可以使用map集合进行统计
        // HashMap TreeMap 都用键表示要统计的对象，值表示对象出现的次数
        //如果题目没有要求对结果排序，默认用HashMap
        //如果要求对结果排序，则使用TreeMap

        //1 定义字符串
        String str = "aababcabcdabcde";

        //创建集合
        TreeMap<Character, Integer> tm3 = new TreeMap<>();

        //2 遍历字符串得到字符
        for(int i = 0; i < str.length(); i++){
            char c = str.charAt(i);
            if(tm3.containsKey(c)){
                int count = tm3.get(c);
                count++;
                tm3.put(c, count);
            }else{
                tm3.put(c, 1);
            }
        }
        System.out.println(tm3);
        StringBuilder sb = new StringBuilder();
        tm3.forEach((key, value)->{
            sb.append(key).append("(").append(value).append(")");
            //System.out.print(key+"("+value+")");
        });
        System.out.println(sb);
    }

}
