package my_io.print_stream;

public class MyPrintStream {
    public static void main(String[] args) {
        //创建对象
        //public PrintStream(OutputStream/File/String) 关联字节输出流/文件/文件路径
        //public PrintStream(String filename, Charset charset) 指定字符编码
        //public PrintStream(OutputStream out, boolean autoFlush) 自动刷新
        //public PrintStream(OutputStream out, boolean autoFlush,String encoding) 指定字符编码且自动刷新

        //注意：字节流底层没有缓冲区，所以开不开自动刷新都一样

        //成员方法
        //public void write(int b) 常规方法，规则 与其他write一样
        //public void println(Xxx xx) 特有方法，打印任意数据，自动刷新，自动换行
        //public void print(Xxx xx) 特有方法，打印任意数据，不换行
        //public void printf(String format, Object...args) 特有方法，带有占位符的打印语句，不换行
        //占位符示例
        //ps.printf("%s 爱上了 %s", "阿珍", "阿强");
        //写到文件中的结果是：阿珍 爱上了 阿强

    }
}
