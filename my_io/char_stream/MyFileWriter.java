package my_io.char_stream;

import java.io.FileWriter;
import java.io.IOException;

public class MyFileWriter {
    public static void main(String[] args) throws IOException{
        //构造方法
        //public FileWriter(File file)
        //public FileWriter(String pathname)
        //public FileWriter(File file, boolean append) 布尔类型决定是否可以续写
        //public FileWriter(String pathname, boolean append)
        //写出方法
        //public write(int c) 写出一个字符
        //public write(String str) 写出一个字符串
        //public write(String str, int off, int len) 写出一个字符串的一部分
        //public write(char[] cbuf) 写出一个字符数组
        //public write(char[] cbuf, int off, int len) 写出一个字符数组的一部分

        //细节
        //  创建对象
        //      如果文件不存在会创建一个新的文件，不过要保证父级路径是存在的
        //      如果文件已经存在，会清空文件，如果不想清空可以打开续写开关
        //  写数据
        //      如果write方法的参数是整数，但实际上写到本地文件中的是在字符集上对应的字符
        FileWriter fw = new FileWriter("my_io\\b1.txt");
        fw.write("你好，世界");
        fw.close();
        
    }
}
