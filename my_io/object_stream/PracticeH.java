package my_io.object_stream;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class PracticeH {
    public static void main(String[] args) throws IOException, ClassNotFoundException{
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("my_io\\a.txt"));
        // Student s1 = (Student)ois.readObject();
        // Student s2 = (Student)ois.readObject();
        // Student s3 = (Student)ois.readObject();
        // Student s4 = (Student)ois.readObject();
        // System.out.println(s1);
        // System.out.println(s2);
        // System.out.println(s3);
        // System.out.println(s4);
        // ois.close();
        ArrayList<Student> list = (ArrayList<Student>)ois.readObject();
        for (Student l : list) {
            System.out.println(l);
        }
        ois.close();
        
    }
}
