package my_io.zip_stream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Base {
    public static void main(String[] args) throws IOException {
        //压缩流和解压缩流
        //1 创建file指向压缩包
        File src = new File("my_io\\example.zip");
        //2 创建file表示解压的目的地
        File dest = new File("my_io\\");
        //unzip(src,dest);

        File src1 = new File("my_io\\b.txt");
        File dest1 = new File("my_io\\");
        //zipOne(src1, dest1);

        //压缩文件夹流程
        //1 创建对象表示要压缩的文件夹
        File src2 = new File("my_io\\copy");
        //2 创建父级对象表示压缩包放在哪里（压缩包的父级路径）
        File destParent = src2.getParentFile();
        //3 创建File对象表示压缩位置
        File dest2 = new File(destParent, src2.getName() + ".zip");
        //4 创建压缩流指向目的地
        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(dest2));
        //5 递归将文件夹中的每个文件都变成ZipEntry对象并存储到压缩包中
        zipDir(src2, zos, src2.getName());
        //6 关流
        zos.close();


    }
    //定义一个方法用来解压
    public static void unzip(File src, File dest) throws IOException{
        //解压的本质：把压缩包里面的每一个文件或者文件夹读取出来，按照层级拷贝到目的地当中
        //创建一个解压缩流用来读取压缩包当中的数据
        ZipInputStream zis = new ZipInputStream(new FileInputStream(src), Charset.forName("GBK"));
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
                File file = new File(dest,entry.toString());
                FileOutputStream fos = new FileOutputStream(file);
                int b;
                while((b = zis.read()) != -1){
                    fos.write(b);
                }
                fos.close();
                //表示在压缩包中的一个文件处理完毕了
                zis.closeEntry();
            }
        }
        zis.close();
    }
    //压缩单个文件
    //压缩的本质：把每一个文件/文件夹看成zipEntry对象放到压缩包中
    public static void zipOne(File src, File dest) throws IOException{
        //1 创建压缩流关联压缩包
        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(new File(dest, "b.zip")));
        //创建ZipEntry对象，表示压缩包里的每一个文件和文件夹
        ZipEntry entry = new ZipEntry("b.txt");
        //把ZipEntry对象放到压缩包中
        zos.putNextEntry(entry);
        //把文件中的数据写到压缩包中
        FileInputStream fis = new FileInputStream(src);
        int b;
        while((b = fis.read()) != -1){
            zos.write(b);
        }
        fis.close();
        zos.closeEntry();
        zos.close();
    }
    //压缩文件夹
    //数据源，压缩流，压缩包内部的路径
    public static void zipDir(File src, ZipOutputStream zos, String name) throws IOException{
        File[] files = src.listFiles();
        for (File file : files) {
            if(file.isDirectory()){
                //如果是文件夹，递归
                zipDir(file, zos, name + "\\" + file.getName());
            }else{
                //如果是文件，转为ZipEntry对象并压缩进压缩包
                ZipEntry entry = new ZipEntry(name + "\\" + file.getName());
                zos.putNextEntry(entry);
                FileInputStream fis = new FileInputStream(file);
                int b;
                while((b = fis.read()) != -1){
                    zos.write(b);
                }
                fis.close();
                zos.closeEntry();
            }
        }
    }


}
