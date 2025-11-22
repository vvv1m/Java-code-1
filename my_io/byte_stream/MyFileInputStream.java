package my_io.byte_stream;

import java.io.FileInputStream;
import java.io.IOException;

public class MyFileInputStream {
    public static void main(String[] args) throws IOException{
        // 书写步骤
        // 1 创建字节输入流对象
        //      细节1 文件不存在直接报错
        //      Java为什么会这么设计 因为程序中最重要的是数据，文件不存在就没有数据，创建无意义
        // 2 读数据
        //      
        // 3 释放资源

        // 创建对象
        FileInputStream fis = new FileInputStream("my_io\\b.txt");
        // 读数据
        //read方法会一个个的读取数据，如果数据都读完了则会返回-1
        int b1 = fis.read();
        System.out.println(b1);
        // 释放
        fis.close();

        //FileInputStream循环读取
        FileInputStream fis1 = new FileInputStream("my_io\\a.txt");
        int b;
        while((b = fis1.read()) != -1){
            System.out.print((char)b);
        }
        fis1.close();
    }
}
