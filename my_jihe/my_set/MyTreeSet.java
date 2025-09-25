package my_jihe.my_set;

import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

public class MyTreeSet {
    //特点 不重复 无索引 可排序
    //默认按照从小到大排序
    //TreeSet集合底层基于红黑树的数据结构，增删改查性能都很好


    public static void main(String[] args) {
        //使用TreeSet存储整数并排序
        TreeSet<Integer> ts = new TreeSet<>();
        //添加元素
        ts.add(2);
        ts.add(5);
        ts.add(1);
        ts.add(3);
        ts.add(4);
        ts.add(1);
        System.out.println(ts);
        //遍历集合 三种
        // Iterator<Integer> it = ts.iterator();
        // while(it.hasNext()){
        //     int num = it.next();
        //     System.out.println(num);
        // }
        //增强for
        // for (Integer tsnum : ts) {
        //     System.out.println(tsnum);
        // }
        //lambda表达式
        //ts.forEach(num -> System.out.println(num));

        //如果TreeSet中存的不是整数呢
        //TreeSet集合默认的规则
        //  对于数值类型，Integer，Double 默认按照从小到大的顺序排序
        //  对于字符、字符串类型：按照字符在ASCII码表中的数字升序进行排序，如果字符数量比较多，是一位一位比较着排的
        //  例如 ab和aba 前两位都相同，第三位前者没有，后者有，那么认为后者比较大
        Student s1 = new Student("zhangsan", 23);
        Student s2 = new Student("lisi", 24);
        Student s3 = new Student("wangwu", 25);
        TreeSet<Student> newts = new TreeSet<>();
        newts.add(s1);
        newts.add(s2);
        newts.add(s3);
        System.out.println(newts); //直接运行会报错，因为传入自定义类的时候并没有指定比较规则
        //TreeSet的两种比较方式
        //方式一：默认排序/自然排序：JavaBean类实现Comparable接口，重写方法，指定比较规则
        //由于TreeSet底层是红黑树，所以不需要重写hashCode和equals方法
        //重写后的compareTo方法
        // @Override
        // public int compareTo(Student o) {
        //     //只看年龄，按照升序排列
        //     int result = this.getAge() - o.getAge();
        //     return result;
        // }
        //其中this表示当前要添加的元素，o表示已经在红黑树中存在的元素
        //如果返回值是负数，认为添加的元素是小的，存左边，如果是正数，认为添加的元素是大的，存右边
        //如果返回值是0，则认为添加的元素已经存在，舍弃

        //方式二：比较器排序，创建TreeSet对象的时候，传递比较器Comparator指定规则
        //使用的时候默认使用第一种方式，如果第一种方式满足不了，再使用第二种方式


        //需求，存入四个字符串"c" "ab" "df" "qwer" 按照长度排序，如果长度一样按首字母排序
        //创建集合
        // TreeSet<String> ts1 = new TreeSet<>(new Comparator<String>() {
        //     @Override
        //     public int compare(String o1, String o2){
        //         int result = o1.length() - o2.length();
        //         result = result==0 ? o1.compareTo(o2):result;
        //         return result;
        //     }
        // });
        //由于Comparator是一个函数式接口，所以可以改成lambda表达式
        TreeSet<String> ts1 = new TreeSet<>((o1, o2)->{
            int result = o1.length() - o2.length();
            result = result == 0 ? o1.compareTo(o2) : result;
            return result;
        });
        //添加元素
        ts1.add("c");
        ts1.add("ab");
        ts1.add("df");
        ts1.add("qwer");
        //打印集合
        System.out.println(ts1);

        //当方式一和方式二同时存在时，以方式二为准


    }
}
