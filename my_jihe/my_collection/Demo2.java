package my_jihe.my_collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Consumer;

public class Demo2 {
    public static void main(String[] args) {
        //Collection遍历方式
        //迭代器遍历
        //增强for遍历
        //Lambda表达式遍历
        //普通for不再普适，因为Set系列没有索引

        //迭代器遍历
        //1 创建数组
        Collection<String> coll = new ArrayList<>();
        coll.add("aaa");
        coll.add("bbb");
        coll.add("ccc");
        coll.add("ddd");
        //2 创建迭代器
        Iterator<String> it = coll.iterator();
        //3 利用循环遍历数组
        while(it.hasNext()){
            //next做了获取元素和移动指针两件事
            String str = it.next();
            //System.out.println(str);
            if("bbb".equals(str)){
                it.remove();//迭代器的删除方法
            }
        }
        //迭代器细节：1 迭代器指向没有元素的位置还调用next会报错 NoSuchElementException
        //  2 迭代器遍历完毕，指针不会复位
        //  3 循环中只能用一次next方法
        //  4 迭代器遍历时，不能用集合的方法增加或删除，如果实在要删除，使用迭代器提供的remove方法
        //      如果要添加，暂时没有办法

        ////--------------------------------------------------------------////
        //增强for循环遍历
        //底层就是迭代器
        //所有的单列集合和数组才能用增强for循环
        //格式 for(元素数据类型 变量名:数组或集合)
        for(String s:coll){
            //注：s是一个第三方变量，在循环的过程中依次表示集合的每个数据
            System.out.println(s);
        }
        //快捷方式：coll.for
        //细节1：修改增强for中第三方的变量，不会修改集合中的数据

        ////--------------------------------------------------------------////
        //Lambda表达式遍历
        //先利用匿名内部类遍历
        //forEach的底层原理：方法底层会遍历集合，依次得到每一个元素，把得到的元素传递给accept方法
        coll.forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                // s表示集合中的每个数据
                System.out.println(s);
            }
        });
        //写成Lambda表达式的形式
        coll.forEach((String s)->{
            System.out.println(s);
        });
        //更省略的Lambda表达式写法
        coll.forEach(s -> System.out.println(s));
    }
}
