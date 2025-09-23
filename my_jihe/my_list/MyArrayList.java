package my_jihe.my_list;

import java.util.Arrays;

//编写一个类的时候，如果不确定类型，可以定义为泛型类
public class MyArrayList<E> {
        //Collection 和 List 中方法 ArrayList都可以之间调用
        //底层
        //底层是数组结构，利用空参创造的结合，在底层创建一个默认长度为0的数组，数组名为elementData
        //同时java底层有成员变量size记录elementData中元素个数
        //添加第一个元素的时候，底层会创建一个新的长度为10的数组
        //size两层含义，元素个数与下次存入数据的位置
        //当数组存满的时候，数组会扩容1.5倍

        //如果一次添加多个数据，1.5倍放不下，则新数组的长度以实际为准
    Object[] obj = new Object[10];
    int size;
    public boolean add(E e){
        obj[size] = e;
        size++;
        return true;
    }
    public E get(int index){
        return (E)obj[index];
    }
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return Arrays.toString(obj);
    }
}
