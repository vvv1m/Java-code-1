package my_io.my_properties;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

public class Base {
    public static void main(String[] args) throws IOException {
        //配置文件的一种
        //文件的后缀名就是properties
        //里面所有的数据按照键值对存储
        //Java为了方便读取properties文件，特地在Map下增加一个类Properties
        //拥有Map的所有特点，也有一些特有方法，可以把集合中的数据，按照键值对的形式写到配置文件中
        //也可以把配置文件中的数据读取到集合中

        //基本方法
        // 1 创建对象
        Properties prop1 = new Properties();
        // 2 添加数据
        //细节：虽然可以向Properties中添加任意类型的数据，但一般只会向其中添加字符串类型的数据
        prop1.put("aaa", "111");
        prop1.put("bbb", "222");
        prop1.put("ccc", "333");
        prop1.put("ddd", "444");

        // 3 遍历集合
        // Set<Object> keys = prop1.keySet();
        // for (Object key : keys) {
        //     Object value = prop1.get(key);
        //     System.out.println(key + " " + value);
        // }

        //Properties跟IO流结合的操作
        //存入
        Properties prop2 = new Properties();
        prop2.put("aaa", "111");
        prop2.put("bbb", "222");
        prop2.put("ccc", "333");
        prop2.put("ddd", "444");
        //将集合以键值对的形式写到本地文件当中
        FileOutputStream fos1 = new FileOutputStream("my_io\\my_properties\\a.properties");
        prop2.store(fos1, "test");
        fos1.close();

        //读取
        Properties prop3 = new Properties();
        FileInputStream fis = new FileInputStream("my_io\\my_properties\\a.properties");
        prop3.load(fis);
        fis.close();
        System.out.println(prop3);
    }
}
