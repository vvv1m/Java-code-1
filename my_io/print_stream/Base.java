package my_io.print_stream;

public class Base {
    public static void main(String[] args) {
        //打印流，高级流
        //不能读，只能写，包装了OutputStream和Writer
        //分为PrintStream --- 字节打印流
        //      PrintWriter --- 字符打印流
        //三个特点
        //  1 只能操纵目的地，不能操纵数据源
        //  2 有特有的写出方法，可以实现数据原样写出
        //  3 特有的写出方法可以实现自动刷新，自动换行，所以打印一次数据=写出+换行+刷新
    }
}
