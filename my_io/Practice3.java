package my_io;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;

public class Practice3 {
    public static void main(String[] args) throws IOException{
        //排序文件中的数据

        //细节
        //  1 文件结尾不要换行，会增加\r\n
        //  2 bom头，如果文件含有bom头，读取的时候可能会读到，从而影响排序
        //      如果是本地新建的文件，可以修改编码方式，消去bom头
        //      如果在编译器中新建，默认是没bom头的




        //1 读取数据




        FileReader fr = new FileReader("my_io\\a.txt");
        int len = 0;
        //利用StringBuilder获取所有数据的字符串
        StringBuilder sb = new StringBuilder();
        while((len = fr.read()) != -1){
            sb.append((char)len);
        }
        fr.close();
        // System.out.println(sb);
        // //转化成String进行拆分
        // String str = sb.toString();
        // String[] arrstr = str.split("-");
        // System.out.println(Arrays.toString(arrstr));
        // //存入列表中进行排序
        // ArrayList<Integer> list = new ArrayList<>();
        // for (String s : arrstr) {
        //     list.add(Integer.parseInt(s));
        // }
        // list.sort((o1,o2) ->  o1-o2);
        // System.out.println(list);
        // //接下来准备将数据写入文件
        // FileWriter fw = new FileWriter("my_io\\a.txt");
        // for(int i = 0; i < list.size(); i++){
        //     if(i == list.size() - 1){
        //         fw.write(list.get(i) + "");
        //     }else{
        //         fw.write(list.get(i) + "-");
        //     }
        // }
        // fw.close();


        //另一种更妙的写法
        //不过这么写还是太麻烦了
        // Arrays.stream(sb.toString()
        //     .split("-"))
        //     .map(new Function<String,Integer>() {

        //         @Override
        //         public Integer apply(String s) {
        //  
        //             return Integer.parseInt(s);
        //         }
                
        //     });
        //使用方法引用
        Integer[] arr = Arrays.stream(sb.toString()
            .split("-"))
            .map(Integer::parseInt)
            .sorted()
            .toArray(Integer[]::new);
        System.out.println(Arrays.toString(arr));

        //写入
        String s = Arrays.toString(arr).replace(", ", "-");
        String res = s.substring(1, s.length()-1);
        System.out.println(res);
        FileWriter fw = new FileWriter("my_io\\a.txt");
        fw.write(res);
        fw.close();
        //优雅！！
    }
}
