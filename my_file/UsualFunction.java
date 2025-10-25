package my_file;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

public class UsualFunction {
    public static void main(String[] args) throws IOException{
        //常用方法之判断
        //public boolean exists() 测试此抽象路径名表示的文件或目录是否存在
        //public boolean isDirectory() 测试此抽象路径名表示的文件是否为目录
        //public boolean isFile() 测试此抽象路径名表示的文件是否为普通文件

        //常用方法之获取
        //public long length() 返回由此抽象路径名表示的文件的大小---字节数量
        //    细节1 该方法只能获得文件的大小，单位时字节
        //        如果单位是M，G要不断除以1024
        //    细节2 该方法无法获取文件夹的大小，如果要获取一个文件夹的大小，累加其中所有文件大小
        //public String getAbsolutePath() 返回绝对路径
        //public String getPath() 返回定义文件时使用的字符串
        //public String getName() 返回由此抽象路径名表示的文件或目录的名称，带后缀
        //public long lastModified() 返回文件最后一次被修改的时间，毫秒值

        //常用方法之创建
        //public boolean createNewFile() 当且仅当具有该名称的文件尚不存在时，创建一个新的空文件
        //细节1 如果当前路径表示的文件是存在的，则不创建，返回false
        //细节2 如果父级路径是不存在的，则抛出IOException，所以要在用到该方法的方法声明处抛出异常
        //细节3 该方法创建的一定是文件，如果路径不包含后缀名，就会创建一个没有后缀的文件
        //public boolean mkdir() 创建单级文件夹
        //细节1 windows中路径唯一，如果当前路径已经存在，则不能创建
        //细节2 只能创建单级文件夹
        //public boolean mkdirs() 创建多级文件夹
        //该方法也可以创建单级文件夹，是在底层调用mkdir方法

        //常用方法之删除
        //public boolean delete() 删除由此抽象路径名表示的文件或目录
        //注意 delete方法默认只能删除文件和空文件夹，直接删除，不走回收站

        //常用方法之获取并遍历
        //public String[] listFiles() 获取当前该路径下所有内容
        File f1 = new File("my_file\\aaa");
        f1.mkdirs();
        File[] files = f1.listFiles();
        for(File f:files){
            //f 表示aaa文件夹下的每一个文件/文件夹
            System.out.println(f);
        }
        //细节1 当调用者File表示的路径不存在时，返回null，当调用者File表示的是文件时也会返回null
        //    当调用者表示的路径是一个空文件夹时，返回一个长度为0的数组
        //    当调用者File表示的路径是一个有内容文件夹，将里面所有文件和文件夹的路径放到File数组中返回
        //    当File表示的路径是一个有隐藏内容的文件夹，返回的内容包括隐藏文件夹
        //    当File表示的路径是需要权限才能访问的文件夹，返回null

        //其他获取方法 了解即可
        //public static File[] listRoots() 列出可用的文件系统根---即所有盘符
        //public String[] list() 获取当前该路径下所有内容的名称数组，仅能获取名字
        //public String[] list(FilenameFilter filter) 获取当前该路径下经过过滤器过滤后的内容名称数组
        //accept中
        String[] arr2 = f1.list(new FilenameFilter() {

            @Override
            public boolean accept(File dir, String name) {
                
                return false;
            }
            
        });
        String[] arr3 = f1.list((dir,name)->name.endsWith(".txt"));

    }
}
