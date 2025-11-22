package my_io.char_buffered_stream;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Base {
    public static void main(String[] args) throws IOException{
        //字符缓冲流
        //底层自带8192的缓冲区，但是因为字符流本身自带缓冲区，所以提升不是很明显
        //构造方法，把基本流变成高级流
        //public BufferedReader(Reader r)
        //public BufferedWriter(Writer w)
        //特有方法
        //字符缓冲输入流
        //  public String readLine() 读取一行数据，如果没有数据可读则返回null
        //  细节 读到回车换行就结束，但不会读回车换行
        BufferedReader br = new BufferedReader(new FileReader("my_io\\a1.txt"));
        String line;
        while((line = br.readLine()) != null){
            System.out.println(line);
        }
        //字符缓冲输出流
        //  public void newLine() 跨平台的换行
        //缓冲流为什么能提高性能呢
        //缓冲流提供8192长度的缓冲区，字节缓冲流的缓冲区是byte类型的，长度是8k，而字符缓冲区是char类型的，长度是16k
    }
}
