package my_api.BigIntAndBigDec;

import java.math.BigInteger;
import java.math.BigDecimal;
import java.util.Random;

public class Test {
    public static void main(String[] args) {
        //1 获取一个随机的大整数
        BigInteger bd1 = new BigInteger(4, new Random()); //范围---[0-2的num次方-1]
        System.out.println(bd1);
        //2 获取指定的大整数
        BigInteger bd2 = new BigInteger("999999999999999999999999999999999999");
        System.out.println(bd2);
        //3 获取指定进制的大整数
        //val对应的数字必须和进制吻合
        BigInteger bd3 = new BigInteger("5000", 10); //这个进制指的是你输入的是什么进制的数，而不是输出，输出默认十进制
        System.out.println(bd3);

        //4 静态方法获取BigInteger的对象，内部有优化
        //细节：表示的范围较小，在long的取值范围内，如果有超出long的范围就不行了
        //      在内部对常用的数字：-16到16进行了优化
        //      提前把-16到16先创建好BigInteger的对象，如果多次获取不会创建新的
        BigInteger bd4 = BigInteger.valueOf(100);
        System.out.println(bd4);

        //直接传入小数的值，仍会得到不准确的值
        BigDecimal bd5 =new BigDecimal(0.1);
        System.out.println(bd5);

        //以字符串形式传入，就会精确得多
        BigDecimal bd6 = new BigDecimal("0.1");
        System.out.println(bd6);

        //细节：
        //表示的数字没有超出double的范围，则使用静态方法valueOf
        //超出double范围，推荐使用构造方法
        //如果传递0到10的整数，包括0和10，方法就会返回已经创建好的对象，不会重新new

    }
}
