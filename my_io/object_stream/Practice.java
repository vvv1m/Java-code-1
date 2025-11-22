package my_io.object_stream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Practice {
    public static void main(String[] args) throws IOException{
        //1 序列化多个对象
        Student s1 = new Student("张三", 23, "M");
        Student s2 = new Student("lisi", 24, "M");
        Student s3 = new Student("wangwu", 25, "M");
        Student s4 = new Student("sangong", 18, "F");

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("my_io\\a.txt"));
        // oos.writeObject(s1);
        // oos.writeObject(s2);
        // oos.writeObject(s3);
        // oos.writeObject(s4);
        // oos.close();
        //那么存在一个问题，如果你不知道有多少个对象被序列化，那么读取的时候该如何做呢
        //一般做如下处理
        //我们会将所有对象放到一个ArrayList中
        //然后序列化这个list就可以了
        //这样我们在读取的时候也只要读一个list就好
        ArrayList<Student> list = new ArrayList<>();
        list.add(s1);
        list.add(s2);
        list.add(s3);
        list.add(s4);
        oos.writeObject(list);
        oos.close();
    }
}
