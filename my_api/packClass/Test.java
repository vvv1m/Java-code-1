package my_api.packClass;
import java.util.*;
public class Test {
    public static void main(String[] args) {
        //基本数据类型对应的引用数据类型
        //包装类即是用一个对象将基本类包起来
        //JDK5的时候提出了一个机制：自动拆箱和自动装箱
        //自动装箱：把基本数据类型自动的变成对应的包装类
        //自动拆箱：把包装类自动的变成其对象的基本数据类型
        Integer i = 10; // 自动装箱

        //将字符串类型的整数转成int类型的整数
        //强类型语言：每种数据在java中都有各自的类型，计算的时候需要类型相同
        String str = "123";
        int i1 = Integer.parseInt(str);
        System.out.println(i1);
        //括号中的参数只能是数字，不能是其他
        //除了Character，其他的包装类都有parseXxx的方法
        //处理键盘输入时，可以应用parse系列方法
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        double v = Double.parseDouble(line);
        System.out.println(v);
    }
}
