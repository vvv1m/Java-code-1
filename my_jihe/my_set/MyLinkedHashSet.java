package my_jihe.my_set;

import java.util.LinkedHashSet;

public class MyLinkedHashSet {
    //直接使用Collection里的API
    //底层原理
    //有序，不重复，无索引，有序指的是存入数据和取出数据的顺序是一致的
    //有序原理：底层数据结构仍然是哈希表，只是每个元素又额外多了一个双链表的机制记录存储的数据
    //当添加了两个元素后，第一个元素会记录第二个元素的地址值，第二个元素也会记录第一个元素的地址值，形成双向链表
    //再添加一个元素后，又会互相记录对方的地址值
    //最后遍历的时候，遍历的是双向链表
    public static void main(String[] args) {
        Student s1 = new Student("zhangsan", 23);
        Student s2 = new Student("lisi", 24);
        Student s3 = new Student("wangwu", 25);
        Student s4 = new Student("zhangsan", 23);
        LinkedHashSet<Student> lhs = new LinkedHashSet<>();
        lhs.add(s1);
        lhs.add(s2);
        lhs.add(s3);
        lhs.add(s4);
        System.out.println(lhs);
    }
}
