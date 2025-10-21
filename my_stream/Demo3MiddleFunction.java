package my_stream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Function;
import java.util.stream.Stream;

public class Demo3MiddleFunction {
    public static void main(String[] args) {
        //流中的中间方法
        //Stream<T> filter(Predicate<? super T> predicate) 过滤
        //Stream<T> limit(long maxSize) 获取前几个元素
        //Stream<T> skip(long n) 跳过前几个元素
        //Stream<T> distinct() 元素去重 依赖hashCode和equals方法
        //static <T> Stream<T> concat(Stream a, Stream b) 合并a和b两个流为一个流 尽量维持数据一样，如果不一样，大流上的数据类型是他们的父类
        //Stream<R> map(Function<T,R> mapper) 转换流中的数据类型

        //注意 中间方法返回新的Stream流，原来的Stream流只能使用一次，所以建议使用链式编程
        //      修改Stream流中的数据，不会影响原来集合或者数组中的数据

        ArrayList<String> list = new ArrayList<>();
        Collections.addAll(list, "张aa-15", "张aa-14","张aa-13","张2-55", "aaaa-36", "张ss-24", "vvv-25", "adf-26", "hhh-34");
        //过滤
        // list.stream()
        //     .filter(name->name.startsWith("张"))
        //     .forEach(name->System.out.println(name));

        //获取/跳过前几个元素
        // list.stream()
        //     .limit(3).forEach(name->System.out.println(name));
        // list.stream()
        //     .skip(3).forEach(name->System.out.println(name));


        //去重，底层利用HashSet，依赖hashCode和equals方法
        ArrayList<String> list1 = new ArrayList<>();
        Collections.addAll(list1, "米娅", "hlh", "cgn");
        // list.stream().distinct().forEach(name->System.out.println(name));

        //合并ab两个流
        // Stream.concat(list.stream(), list1.stream()).forEach(name->System.out.println(name));

        //转换流中的数据类型
        //现在要只获取list里中的年龄
        //String -> int
        //第一个类型 流中原本的数据类型
        //第二个类型 要转成之后的类型
        //apply的形参，依次表示流里面的每个数据
        //返回值：表示转换之后的数据
        //当map方法执行完毕之后，流上的数据就变成了整数
        //所以在下面的forEach，s依次表示流里面的每一个数据，这个数据现在就是整数了
        // list.stream().map(new Function<String,Integer>() {

        //     @Override
        //     public Integer apply(String t) {
        //         String[] arr = t.split("-");
        //         String agestring = arr[1];
        //         int age = Integer.parseInt(agestring);
        //         return age;
        //     }
        // }).forEach(s->System.out.println(s));
        list.stream()
            .map(s->Integer.parseInt(s.split("-")[1]))
            .forEach(num->System.out.println(num));
    }
}
