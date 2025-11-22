package my_io.char_stream;

public class MyCharStream {
    public static void main(String[] args) {
        //字符流解决的就是第一种乱码原因---读取数据时未读完整个汉字
        //底层就是字节流，在其基础上添加字节流的概念
        //特点
        //  输入流：一次读取一个字节，遇到中文时一次读取多个字节，具体读几个跟编码方式有关
        //  输出流：底层会把数据按照指定的编码方式进行编码，变成字节再写到文件中
        //使用场景：对于纯文本文件进行读写操作
        
        //字符输入流---Reader---FileReader
        //字符输出流---Writer---FileWriter
    }
}
