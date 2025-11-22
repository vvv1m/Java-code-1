package my_io.print_stream;

public class MyPrintWriter {
    public static void main(String[] args) {
        //字符打印流
        //构造方法
        //大体和字节打印流相同
        //public PrintWriter(Writer/File/String) 关联字节输出流/文件/文件路径
        //public PrintWriter(String filename, Charset charset) 指定字符编码
        //public PrintWriter(Writer w, boolean autoFlush) 自动刷新
        //public PrintWriter(OutputStream out, boolean autoFlush,String encoding) 指定字符编码且自动刷新

        //字符流底层有缓冲区，想要自动刷新需要手动开启

        //成员方法
        //public void write(...) 常规方法，规则 与其他write一样
        //public void println(Xxx xx) 特有方法，打印任意数据，自动刷新，自动换行
        //public void print(Xxx xx) 特有方法，打印任意数据，不换行
        //public void printf(String format, Object...args) 特有方法，带有占位符的打印语句，不换行
        
    }
}
