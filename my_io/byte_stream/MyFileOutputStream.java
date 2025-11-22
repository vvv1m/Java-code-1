package my_io.byte_stream;

import java.io.FileOutputStream;
import java.io.IOException;

public class MyFileOutputStream {
    public static void main(String[] args) throws IOException{
        //以下是FileOutputStream 可以将程序中的数据写入文件
        //步骤 1 创建字节流输出对象 2 写数据 3 释放资源
        //演示 写入一串文字到本地文件中 非中文
        //创建对象，要指定文件路径
        //要在方法定义除抛出FileNotFoundException
        FileOutputStream fos = new FileOutputStream("my_io\\a.txt");
        //写入数据w
        //以下两个方法也要抛出异常，所以直接抛出IOException即可
        fos.write(97);
        //释放资源
        fos.close();

        //FileOutputStream原理
        //创建对象时在程序和文件之间产生一个数据传输的通道，再通过write方法通过通道传输数据
        //最后close方法将通道摧毁
        //创建对象细节
        //  细节1 参数是字符串表示的路径或者file对象都是可以的 因为即使传入字符串
        //      方法底层也会帮我们new一个file对象，并调用本类的其他构造
        //  细节2 如果文件不存在会创建一个新的文件，但是要保证父级路径是存在的
        //  细节3 如果文件已经存在，则会清空文件
        //写入数据细节
        //  细节1 write方法的参数是整数，但是实际上写到文件中的是整数所对应的ASCII码表上的内容
        //释放资源细节 每次使用完流都要释放资源，会解除对文件的占用

        //写数据的三种方式
        //void write(int b) 一次写一个字节数据

        //void write(byte[] b) 一次写一个字节数组数据

        //void write(byte[] b, int off, int len) 一次写一个字节数组数据
        //参数off --- 起始索引 参数len --- 个数
        FileOutputStream fos1 = new FileOutputStream("my_io\\b.txt");
        byte[] b = {97, 98, 99, 100, 101};
        fos1.write(b);
        fos1.write(b, 1, 2);

        fos1.close();


        //写出数据的两个问题
        // 1 如何换行写
        //      写出一个换行符 --- windows系统中为 \r\n --- Linux系统中为 \n --- Mac系统中为 \r
        //  细节 在windows中，Java对回车换行进行了优化，写\r或\n也可以实现换行，Java底层会补全 但建议不要省略
        // 2 如何续写（不清空原来的数据）
        //      在创建对象的方法中还有第二个参数，布尔类型，表示是否开启续写，默认为false 设置为true即可续写
        FileOutputStream fos2 = new FileOutputStream("my_io\\b.txt", true);
        String str = "hello world";
        byte[] bytes = str.getBytes();
        fos2.write(bytes);
        fos2.write("\r\n".getBytes());
        fos2.write("666".getBytes());
        fos2.close();
    }
}
