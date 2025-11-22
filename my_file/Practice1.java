package my_file;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Practice1 {
    public static void main(String[] args) throws IOException{
        // 在当前目录下的aaa文件夹中创建一个名为a.txt的文件
        // File f1 = new File("my_file\\aaa\\a.txt");
        // boolean b = f1.createNewFile();
        // if(b){
        //     System.out.println("创建成功");
        // }else{
        //     System.out.println("创建失败");
        // }
        // 在电脑中寻找所有avi结尾的文件，并打印路径
        // File[] roots = File.listRoots();
        // for (File root : roots) {
        //     findAvi(root);
        // }
        // 统计一个文件夹中的各种文件的数量
        File f1 = new File("my_file\\aaa");
        HashMap<String, Integer> map = getCount(f1);
        map.forEach((String o1, Integer o2) -> System.out.println(o1 + ":" + o2));

    }
    public static void findAvi(File root){
        File[] files = root.listFiles();
        if(files==null){
            return;
        }
        for (File file : files) {
            if(file.isDirectory()){
                findAvi(file);
            }else if(file.getName().toLowerCase().endsWith(".avi")){
                System.out.println(file.getAbsolutePath());
            }
        }
    }
    //统计txt,doc,jpg种类文件的个数
    public static HashMap<String, Integer> getCount(File src){
        HashMap<String, Integer> map = new HashMap<>();
        File[] files = src.listFiles();
        for (File file : files) {
            if(file.isFile()){
                String str = file.toString().substring(file.toString().length()-4, file.toString().length());
                String check = str.substring(0,1);
                String end = str.substring(1,4);
                if(map.containsKey(end) && check.equals(".")){
                    int count = map.get(end);
                    count++;
                    map.put(end, count);
                }else if(!map.containsKey(end) && check.equals(".")){
                    map.put(end,1);
                }
            }else{
                HashMap<String, Integer> tempmap = getCount(file);
                tempmap.forEach((String o1, Integer o2) -> {
                    if(map.containsKey(o1)){
                        int count = o2 + map.get(o1);
                        map.put(o1, count);
                    }else{
                        map.put(o1, o2);
                    }
                });
            }

        }
        return map;
    }
}
