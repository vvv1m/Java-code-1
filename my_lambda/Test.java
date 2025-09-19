package my_lambda;

import java.util.Arrays;
import java.util.Comparator;

public class Test {
    public static void main(String[] args) {
        //lambda用于简化匿名内部类的书写
        //函数式编程：一种思想，忽略面向对象的复杂语法，强调做什么，而不是谁去做，lambda表达式就是其一种体现
        //格式()->{}
        //其中()对应方法的形参，->是固定格式， {}是方法体
        //注：Lambda表达式只能简化函数式接口的匿名内部类的写法
        //函数式接口：有且只有一个抽象方法的接口，接口上方可以加@FunctionalInterface注解
        method(()->{
            System.out.println("游泳");
        });
        //Lambda的书写仍然有可以省略的地方 省略核心：可推导，可省略
        //省略规则：
        //1 参数类型可以省略不写
        //2 如果只有一个参数，参数类型可以省略，同时（）也可以省略
        //3 如果Lambda表达式的方法体只有一行，大括号，分号，return可以省略不写，需要同时省略
        //例如：
        Integer[] arr = {1,3,6,8,42,9,13,566};
        Arrays.sort(arr, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2){
                return o2 - o1;
            }
        });
        System.out.println(Arrays.toString(arr));
        //使用完整Lambda
        Arrays.sort(arr,(Integer o1, Integer o2)->{
            return o1 - o2;
        });
        System.out.println(Arrays.toString(arr));
        //使用省略版Lambda
        Arrays.sort(arr, (o1, o2)->o2 - o1);
        System.out.println(Arrays.toString(arr));


    }
    public static void method(Swim s){
        s.swim();
    }
    
}
interface Swim{
    public abstract void swim();
} 
