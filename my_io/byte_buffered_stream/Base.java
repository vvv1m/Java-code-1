package my_io.byte_buffered_stream;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Base {
    public static void main(String[] args) throws IOException{
        //原理：自带了长度为8192的缓冲区提高性能
        //缓冲流只是在原有流的基础上做一个包装
        //本身不能读取和写入数据，需要关联基本流
        //构造方法
        //public BufferedInputStream(InputStream is)
        //public BufferedOutputStream(OutputStream os)

        //1 创建缓冲流对象
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream("my_io\\a.txt"));
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("my_io\\copy.txt"));
        //2 循环拷贝，一次读写多个字节的方法与FileInputStream相同，不再赘述
        int len = 0;
        while((len = bis.read()) != -1){
            bos.write(len);
        }
        //在底层帮我们关闭了基本流，所以不用再进行关闭
        bos.close();
        bis.close();

        //提高效率的原理
        //创建缓冲输入流对象的时候会创建一个缓冲区，创建缓冲输出流对象的时候同理，但这两个缓冲区不是同一个
        //内部图示
        //硬盘                      内存                          硬盘
        //源  基本流  缓冲输入流  缓冲区  缓冲区 缓冲输出流 基本流  目的地
        //读写的时候先从缓冲区中读取一个数据存到变量（这里假设b）中，再将b写到另一个缓冲区中
        //如果改用定义数组的方法，发生改变的额是中间倒手的容器，也就是变量b
    }
}
