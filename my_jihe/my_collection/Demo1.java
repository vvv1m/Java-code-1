package my_jihe.my_collection;

import java.util.ArrayList;
import java.util.Collection;

public class Demo1 {
    public static void main(String[] args) {

        //Collection是接口，所以用他的实现类ArrayList做演示
        Collection<String> coll = new ArrayList<>();

        //1 添加元素 public boolean add(E e)
        //细节1：如果往List系列中添加数据，永远返回true，因为List系列允许元素重复
        //      如果往Set系列中添加，当该元素不存在，返回true，如果存在返回false
        coll.add("aaa");
        coll.add("jjk");
        coll.add("vfaf");
        System.out.println(coll);

        //2 清空 public void clear()
        //coll.clear();
        System.out.println(coll);

        //3 删除 public boolean remove(E e)
        //细节1 通过对象删除而不是索引，因为set系列没有索引，Collection定义共性方法
        //细节2 方法返回boolean，删除成功返回true，失败返回false（删除元素不存在）
        coll.remove("aaa");
        System.out.println(coll);

        //4 判断元素是否包含 public boolean contains(Object o)
        //细节 底层依赖equals方法判断元素是否存在
        //所以如果集合中存储自定义对象，也想通过contains方法判断包含，那么要在自定义的javabean中重写equals
        //比如定义Student，就要在Student中重写
        boolean result = coll.contains("jjk");
        System.out.println(result);
        
        //5 判断集合是否为空
        boolean result1 = coll.isEmpty();
        System.out.println(result1);

        //6 获取集合的长度public int size()
        
    }
}
