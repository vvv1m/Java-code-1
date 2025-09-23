package my_jihe.my_list;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Demo2 {
    public static void main(String[] args) {
        //关于List的遍历，前三种与Collection并无不同，第四种是用普通for循环进行遍历
        //5 列表迭代器
        List<String> list = new ArrayList<>();
        list.add("aaa");
        list.add("bbb");
        list.add("ccc");
        ListIterator<String> it = list.listIterator();
        while(it.hasNext()){
            String str = it.next();
            System.out.println(str);
            if("bbb".equals(str)){
                //list.add();不能使用集合的方式添加修改
                it.add("qqq");
            }
        }
        System.out.println(list);
    }
}
