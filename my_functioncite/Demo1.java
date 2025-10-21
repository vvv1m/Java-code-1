package my_functioncite;

import java.util.Arrays;

public class Demo1 {
    public static void main(String[] args) {
        //需求 创建数组 倒序排序
        Integer[] arr = {2,4,6,11,4,67,3};
        //Lambda表达式简化形式
        // Arrays.sort(arr, (o1,o2)->o2-o1);
        //方法引用
        // 1 引用处必须是函数式接口
        // 2 被引用的方法必须是已经存在的
        // 3 被引用方法的形参和返回值需要跟抽象方法保持一致
        // 4 被引用方法的功能要满足当前的需求

        //表示引用Demo1中的sub方法
        //把这个方法当做抽象方法的方法体
        //::就是方法引用符，是方法引用中独特的符号
        Arrays.sort(arr, Demo1::sub);
        System.out.println(Arrays.toString(arr));
    }
    //可以是Java写好的或者是第三方工具类
    public static int sub(int num1, int num2){
        return num2 - num1;
    }
}
