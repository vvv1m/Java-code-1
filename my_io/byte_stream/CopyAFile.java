package my_io.byte_stream;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class CopyAFile {
    public static void main(String[] args) throws IOException{
        //本方法适用于较小的文件
        FileInputStream fis = new FileInputStream("my_io\\a.txt");
        FileOutputStream fos = new FileOutputStream("my_io\\copy.txt");
        //拷贝核心思想---边读边写
        int b;
        while((b = fis.read()) != -1){
            fos.write(b);
        }
        //释放规则：先开的流后关闭
        fos.close();
        fis.close();

        //上述方法由于一次只能读取一个字节，所以速度较慢
        //所以可以使用读取数组的read方法加快速度，可以创建1024*1024*5大小的数组---也就是5MB
        FileInputStream fis1 = new FileInputStream("E:\\瓦罗兰特视频\\wonderfulVideos14233965920915784386\\23d118c6-2ed4-4aa4-b840-72945df62f0c\\20250727-155541-Killjoy-10-kill-2bf6.mp4");
        FileOutputStream fos1 = new FileOutputStream("my_io\\copy.mp4");
        byte[] bytes = new byte[1024*1024*5];
        //一次读取多个数据，读多少跟数组长度有关
        //返回值len返回本次读取到了多少个字节数据
        int len;
        while((len = fis1.read(bytes)) != -1){
            fos1.write(bytes, 0, len); //0-len是防止读到最后剩下的数据装不满数组
        }
        //释放
        fos1.close();
        fis1.close();
    }
}
