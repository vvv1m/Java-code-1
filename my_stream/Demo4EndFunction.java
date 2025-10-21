package my_stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Demo4EndFunction {
    public static void main(String[] args) {
        //void forEach(Consumer action) 遍历
        //long count() 统计
        //toArray() 收集流中的数据放到数组中
        //collect(Collector collector) 收集流中的数据，放到集合中
        ArrayList<String> list = new ArrayList<>();
        Collections.addAll(list, "aaa-m-12", "bbb-m-23", "ccc-m-24",
                 "ddd-m-35", "adc-m-19", "avv-f-12", "avbb-f-34","ac-f-23", "ad-f-72");

        //遍历略过不表
        //来看看统计
        long count = list.stream().count();
        System.out.println(count);

        //toArray 收集流中的数据 放到数组
        //如果使用最简单的空参构造，会返回一个Object类型的数组
        // Object[] arr1 = list.stream().toArray();
        //想传入其他类型的方法，就要使用带参方法
        //IntFunction的泛型：具体类型的数组
        //toArray方法的参数的作用：创建一个指定类型的数组
        //toArray方法的底层：会依次得到流里面的每个数据，并把数据放到数组当中
        //toArray方法的返回值：是一个装着流里面所有数据的数组

        // String[] arr = list.stream().toArray(new IntFunction<String[]>() {
        //     //apply的形参： 流中数据的个数，要跟数组长度保持一致
        //     //apply的返回值：具体类型的数组
        //     @Override
        //     public String[] apply(int value) {

        //         return new String[value];
        //     }
            
        // });
        // System.out.println(Arrays.toString(arr));

        //利用Lambda表达简化写法
        String[] arr2 = list.stream().toArray(value -> new String[value]);
        System.out.println(Arrays.toString(arr2));

        
        //收集流中的数据，放到List集合中
        //需求 收集所有男性
        List<String> newlist1 = list.stream()
            .filter(s->"m".equals(s.split("-")[1]))
            //Collectors是Stream流里的一个工具类，toList是一个静态方法 方法底层会创建一个ArrayList集合
            //这样就可以把流里面的数据都放到集合当中
            .collect(Collectors.toList());

        System.out.println(newlist1);
        //收集流中的数据，放到Set集合中
        Set<String> newlist2 = list.stream()
            .filter(s->"f".equals(s.split("-")[1]))
            .collect(Collectors.toSet());
        System.out.println(newlist2);
        //两种收集方式的区别在于 收集到Set中的数据会自动去重


        //收集到Map集合中
        //要想好谁作为键，谁作为值
        //键 姓名 值 年龄
        //注意点
        //  如果要将数据收集到Map中，那么键是不能重复的，否则会报错
        //  观察底层可以发现，toMap调用map.putIfAbsent(k,v)的方法把键跟值传递过去
        //  在这个方法中，会进行一个判断，根据键找值，如果值是null，就把数据添加到map中
        //      如果不是null就返回值
        //  返回后在下面又做了一个判断，如果返回值不是null，就会产生一个异常
        // Map<String, Integer> map = list.stream()
        //     .filter(s->"m".equals(s.split("-")[1])) 
        //     //toMap的两个参数分别是键的规则和值的规则
        //     //Function前一个泛型表示流里面数据的类型，第二个泛型表示Map集合中键的类型
        //     .collect(Collectors.toMap(new Function<String,String>() {

        //         @Override
        //         //apply形参依次表示流中每一个数据，方法体里写生成键的代码
        //         public String apply(String t) {
        //             return t.split("-")[0];
        //         }
        //     //第一个形参表示流中数据的类型 第二个形参表示Map中值的类型
        //     }, new Function<String,Integer>() {

        //         @Override
        //         //形参同上
        //         public Integer apply(String t) {
                    
        //             return Integer.parseInt(t.split("-")[2]);
        //         }
                
        //     }));
        // System.out.println(map);

        //对代码用Lambda表达式化简
        Map<String, Integer> map = list.stream()
            .filter(s->"m".equals(s.split("-")[1]))
            .collect(Collectors.toMap(
                s -> s.split("-")[0],
                s -> Integer.parseInt(s.split("-")[2])));
        System.out.println(map);
    }
}
