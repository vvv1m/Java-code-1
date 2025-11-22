package my_io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Practice2 {
    public static void main(String[] args) throws IOException{
        //加密一个文件
        reencrypt("my_io\\111111.jpg", "my_io\\copyjpg.jpg");
    }
    public static void encrypt(String src, String obj) throws IOException{
        File srcfile = new File(src);
        File objfile = new File(obj);
        encrypt(srcfile, objfile);
    }
    public static void reencrypt(String obj, String src) throws IOException{
        File srcfile = new File(src);
        File objfile = new File(obj);
        encrypt(srcfile, objfile);
    }

    //作用: 加密一个文件
    //参数1：要加密的文件的路径
    //参数2：加密后文件存储的路径
    public static void encrypt(File src, File obj) throws IOException{
        FileInputStream fis = new FileInputStream(src);
        FileOutputStream fos = new FileOutputStream(obj);
        int len;
        while((len = fis.read()) != -1){
            fos.write(len ^ 1000);
        }
        fos.close();

        fis.close();
    }
}
