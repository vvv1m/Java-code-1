package my_io.byte_stream;

import java.io.IOException;

public class BaseIO {
    public static void main(String[] args) throws IOException{
        // IO流 --- 存储和读取数据的解决方案
        // IO流中谁在读，谁在写，以谁为参照物看读写的方向呢？ 程序/内存
        // IO流按照流的方向分类 --- 输入流 读取 --- 输出流 写出
        // 按照操作文件的类型来分 --- 字节流 操纵所有类型的文件 --- 字符流 只能操作纯文本文件
        // 纯文本文件 --- 能用windows自带的记事本打开并能看懂的文件，如txt文件、md文件、xml文件、lrc文件等


        //IO流体系 --- 字节流
        //              ---InputStream
        //                  --- FileInputStream 操作本地文件的字节输入流
        //              ---OutputStream
        //                  --- FileOutputStream 
        //        --- 字符流
        //              ---Reader
        //              ---Writer

        



    }
}
