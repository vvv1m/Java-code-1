package my_io.turn_stream;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

public class Base {
    public static void main(String[] args) throws IOException{
        //转换流是字符流的高级流，包装了基础流
        //是字符流和字节流之间的桥梁
        //图示
        //  数据源   字节流  转换流   内存   转换流  字节流   目的地
        //图中的输入字节流经过转换流的包装后就拥有了字符流的特性---读取数据不会乱码，可以一次读取多个字节
        //输出流则是要将字符流转换为字节流
        //作用 1 指定字符集读写
        //      2 字节流想要使用字符流中的方法

        // //练习1 利用转换流按照指定字符编码读取(被淘汰，需要掌握替代方案)
        // //  1 创建对象并指定字符编码
        // InputStreamReader isr = new InputStreamReader(new FileInputStream("my_io\\a1.txt"),"UTF-8");
        // //  2 读取数据
        // int ch = 0;
        // while((ch = isr.read()) != -1){
        //     System.out.print((char)ch);
        // }
        // isr.close();
        //替代方案 FileReader 的新构造方法JDK11之后,第二个参数是字符编码号，利用forName可以简单实用
        FileReader fr = new FileReader("my_io\\a1.txt", Charset.forName("UTF-8"));
        int ch = 0;
        while((ch = fr.read()) != -1){
            System.out.print((char)ch);
        }
        fr.close();


        // //2 利用转换流按字符编码写出 同样也有替代方案
        // OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream("my_io\\b.txt"),"UTF-8");
        // osw.write("你好，世界!");
        // osw.close();
        // 替代方案
        FileWriter fw = new FileWriter("my_io\\b.txt", Charset.forName("UTF-8"));
        fw.write("你好，世界！！");
        fw.close();

        // 3 使用字节流一次读取一行数据
        // 这是一个字节输入流，不能读中文
        FileInputStream fis = new FileInputStream("");
        //利用转换流转换成字符流，就可以读中文了，但是每次只能读取一个中文
        InputStreamReader isr = new InputStreamReader(fis);
        //再把字符流转换成字符缓冲流，就可以读一行数据了
        BufferedReader br = new BufferedReader(isr);
        //br.readLine();
        br.close();
    }
}
