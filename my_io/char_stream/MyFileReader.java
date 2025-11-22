package my_io.char_stream;

import java.io.FileReader;
import java.io.IOException;

public class MyFileReader {
    public static void main(String[] args) throws IOException{
        //三步走 创建对象 读取数据 释放资源
        //构造方法
        //public FileReader(File file)
        //public FileReader(String pathname) 底层还是会new一个File对象
        //细节一：如果读取的文件不存在则直接报错

        //读取方法
        //public int read() 一个一个读，读到末尾返回-1
        //public int read(char[] buffer) 读取多个，读到末尾返回-1
        //细节，默认按照字节的方法读取，一个一个字节读，读到中文再读多个

        //释放资源
        //public int close() 关流
        FileReader fr = new FileReader("my_io\\a1.txt");
        int ch = 0;
        while((ch = fr.read()) != -1){
            System.out.print((char)ch);//如果不加强转，会发现打印出的都是数字
        }
        //这里引出read方法的细节
        //读取之后，方法底层会进行解码并转成十进制
        //最终把这个十进制作为返回值，该数据也表示在字符集上的数字
        //如果想看到中文汉字，就要再把这些十进制数进行强转
        //注意，read使用系统默认的编码方式，为GBK
        fr.close();
        //有参的read方法
        //细节：将读取数据，解码，强转三步合并，把强转之后的字符放到数组当中
        FileReader fr1 = new FileReader("my_io\\a1.txt");
        char[] chars = new char[2];//数组大小表示一次读几个
        int len = 0;//表示当前读了几个
        while((len = fr1.read(chars)) != -1){
            //将数据转成字符串再输出，0，len表示读了多少个就转多少个
            System.out.print(new String(chars,0,len));
        }
        fr1.close();

    }
}
