package my_functioncite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Prac1 {
    public static void main(String[] args) {
        ArrayList<String> list1 = new ArrayList<>();
        Collections.addAll(list1, "aaa,15", "bbb,14", "ccc,14", "ddd,20", "eee,21");
        Student[] arr = list1.stream()
            .map(Student::new)  //利用类的构造方法引用将数据转变成自定义类型
            .toArray(Student[]::new);   //数组的类型需要与流中的类型保持一致
        System.out.println(Arrays.toString(arr));


        
    }
}
