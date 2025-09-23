package my_jihe.my_set;

import java.util.HashSet;

public class MyHashSet1 {
    //并无什么新增方法
    //底层原理
    //底层采取哈希表存储数据
    //哈希表是一种对于增删改查数据性能都较好的结构
    //哈希表组成
    //  JDK8之前：数组+链表
    //  JDK8开始：数组+链表+红黑树
    //哈希表中有一个重要的值---哈希值 --- 对象的整数表现形式
    //  哈希值
    //      根据hashCode方法算出来的int类型的整数
    //      该方法定义在Object类中，所有的对象都可以调用，默认使用地址值进行计算
    //      一般情况下会重写hashCode方法，利用对象内部的属性值计算哈希值
    //  对象的哈希值的特点
    //      如果没有重写hashCode，不同对象计算出的哈希值是不同的
    //      如果重写了hashCode，不同对象属性值相同计算出的哈希值就是一样的
    //      小部分情况，不同的属性值或者不同的地址值计算出来的哈希值也有可能一样（哈希碰撞）

    public static void main(String[] args) {
        Student s1 = new Student("zhangsan", 25);
        Student s2 = new Student("zhangsan", 25);
        //如果没有重写hashCode
        //System.out.println(s1.hashCode());
        //System.out.println(s2.hashCode());
        //重写之后
        System.out.println(s1.hashCode());
        System.out.println(s2.hashCode());
        //哈希碰撞
        System.out.println("abc".hashCode()); //96354
        System.out.println("acD".hashCode()); //96354





        //1 创建一个默认长度为16 默认加载因子为0.75的数组，数组的名字叫table，此时什么都没存，默认长度为null
        HashSet<String> hm = new HashSet<>();
        //2 根据元素的哈希值跟数组的长度计算出应存入的位置
        //int index = (数组长度 - 1) & 哈希值;
        //3 判断当前元素位置是否是null 如果是则直接存入
        //4 如果位置不是null 表示有元素 则调用equals方法比较属性值
        //5 属性值一样，不存 属性值不一样，存入数组，形成链表
        //      在JDK8以前：新元素存入数组，老元素挂在新元素下面
        //      JDK8以后，新元素直接挂在老元素下面
        //      若当前的链表长度大于8而且数组长度大于等于64，当前的链表就会自动转成红黑树，从而提高查找效率
        //注意：1 转为红黑树的条件需要两个都满足
        //      2 如果集合中存储的是自定义对象，必须要重写hashCode和equals方法

        //知道底层后，就可以用来解释HashSet的三个问题
        //1 HashSet为什么存和取的顺序不一样
        //      跟利用哈希值存数据有关
        //2 HashSet为什么没有索引
        //      不够纯粹，底层有三种数据结构，不好规定索引
        //3 HashSet是用什么机制保证去重的
        //      hashCode和equals


    }


}
