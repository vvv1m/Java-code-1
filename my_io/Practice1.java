package my_io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Practice1 {
    public static void main(String[] args) throws IOException{
        //使用场景
        //字节流
        //  拷贝任意类型的文件
        //字符流
        //  读取纯文本文件的数据
        //  往纯文本文件中写出数据

        //练习一 拷贝一个文件夹 考虑子文件夹
        copydir("my_file\\aaa","my_io\\copy");

    }
    public static void copydir(String src, String obj) throws IOException{
        File srcfile = new File(src);
        File objfile = new File(obj);
        copydir(srcfile, objfile);
    }
    //作用---拷贝文件夹
    //参数一---数据源
    //参数二---目的地
    public static void copydir(File src, File obj) throws IOException{
        obj.mkdirs();
        File[] files = src.listFiles();
        for (File file : files) {
            if(file.isDirectory()){
                //如果是文件夹，创建新的目的路径，递归调用
                File dir = new File(obj, file.getName());
                copydir(file, dir);
            }else{
                //如果是文件，创建字节输入输出对象，准备拷贝
                FileInputStream fis = new FileInputStream(file);
                FileOutputStream fos = new FileOutputStream(new File(obj,file.getName()));
                //一次读5MB
                byte[] bytes = new byte[1024*1025*5];
                int len;
                while((len = fis.read(bytes)) != -1){
                    fos.write(bytes, 0, len);
                }
                fos.close();
                fis.close();
            }
        }
    }
}
