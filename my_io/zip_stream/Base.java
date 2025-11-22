package my_io.zip_stream;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Base {
    public static void main(String[] args) throws IOException {
        //压缩流和解压缩流
        //1 创建file指向压缩包
        File src = new File("my_io\\example.zip");
        //2 创建file表示解压的目的地
        File dest = new File("my_io\\");
        unzip(src,dest);
    }
    //定义一个方法用来解压
    public static void unzip(File src, File dest) throws IOException{
        //解压的本质：把压缩包里面的每一个文件或者文件夹读取出来，按照层级拷贝到目的地当中
        //创建一个解压缩流用来读取压缩包当中的数据
        ZipInputStream zis = new ZipInputStream(new FileInputStream(src));
        //要先获取到压缩包中的每一个zipentry对象
        //entry表示在当前在压缩包中获取到的文件或是文件夹
        ZipEntry entry;
        while((entry = zis.getNextEntry()) != null){
            
            if(entry.isDirectory()){
                //如果当前路径是文件夹，需要在目的地创建一个同样的文件夹
                File file = new File(dest,entry.toString());
                file.mkdirs();
            }else{
                //如果是文件，需要读取到文件，并把它存放在目的地的文件夹中
                int b;
                while(())
            }
        }
    }
}
