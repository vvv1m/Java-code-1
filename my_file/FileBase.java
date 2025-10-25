package my_file;

import java.io.File;

public class FileBase {
    public static void main(String[] args) {
        //File对象就表示一个路径，文件路径/文件夹路径，存在/不存在都可
        //1 public File(String pathname) 根据路径创建文件对象
        //2 public File(String parent,String child) 根据父路径字符串和子路径字符串创建文件对象
        //3 public File(File parent,String child) 根据父路径文件对象和子路径字符串创建文件对象

        //1 D:\Java\Javacode\my_file\FileBase.java
        String path1 = "D:\\Java\\Javacode\\my_file\\a.txt";
        File file1 = new File(path1);
        System.out.println(file1);
        //2
        //父路径 --- 父级路径 去掉自己，剩下来的路径 D:\\Java\\Javacode\\my_file
        //子路径 --- 子级路径 a.txt
        String parent = "D:\\Java\\Javacode\\my_file";
        String child = "a.txt";
        File file2 = new File(parent,child);
        System.out.println(file2);
        //3
        //略
    }
}
