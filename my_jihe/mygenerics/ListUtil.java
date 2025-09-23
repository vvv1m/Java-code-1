package my_jihe.mygenerics;

import java.util.ArrayList;

public class ListUtil {
    private ListUtil(){}
    //定义一个静态方法addAll，用来添加多个集合的元素
    public static<E> void addAll1(ArrayList<E> list, E e1, E e2, E e3, E e4){
        list.add(e1);
        list.add(e2);
        list.add(e3);
        list.add(e4);
    }
    //那么想传多个元素，自己也不知道数量 该怎么办
    public static<E> void addAll2(ArrayList<E> list, E...e){//E...e为可变参数，可以理解为数组
        for (E element : e) {
            list.add(element); //此时不管添加多少个参数都是可以的
        }
    }
}
