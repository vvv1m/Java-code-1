package my_io.print_stream;

import java.io.PrintStream;

public class UseOfPrintStream {
    public static void main(String[] args) {
        //打印流的应用场景
        //获取打印流的对象，此打印流在虚拟机中启动时，由虚拟机创建，默认指向控制台
        //特殊的打印流，系统中的标准输出流,是不能关闭的,在系统是唯一的
        //out是一个字节打印流对象
        PrintStream ps = System.out;
        ps.println(123);
        //平时我们使用链式编程
        System.out.println(123);
    }
    //
}
