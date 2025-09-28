package my_jihe.Prac;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.StringJoiner;
import java.util.Map.Entry;

public class Prac1 {
    public static void main(String[] args) {
        //练习1 班级里有N个学生，学生属性：姓名 年龄 性别 实现随机点名器
        // ArrayList<String> list = new ArrayList<>();
        // Collections.addAll(list, "zhagnsan", "lisi", "wangwu", "hey", "heliuhun");
        // Collections.shuffle(list, new Random());
        // //System.out.println(list.get(0));
        // for (String person : list) {
        //     System.out.println(person);
        // }


        //练习2 班里有N个学生 要求70的概率随机到男生，30的概率随机到女生
        // ArrayList<Integer> list = new ArrayList<>();
        // Collections.addAll(list, 1,1,1,1,1,1,1,0,0,0);
        // Collections.shuffle(list);
        // ArrayList<String> boylist = new ArrayList<>();
        // ArrayList<String> girllist = new ArrayList<>();
        // Collections.addAll(boylist, "男一号", "男二号", "男三号", "男四号","男五号");
        // Collections.addAll(girllist, "女一号", "女二号", "女三号", "女四号", "女五号");
        // // for (Integer num : list) {
        // //     if(num == 1){
        // //         System.out.println(boylist.get(new Random().nextInt(boylist.size())));
        // //     }else{
        // //         System.out.println(girllist.get(new Random().nextInt(girllist.size())));
        // //     }
        // // }
        // for(int i = 0; i < 20; i++){
        //     Collections.shuffle(list);
        //     if(list.get(0) == 1){
        //         System.out.println(boylist.get(new Random().nextInt(boylist.size())));
        //     }else{
        //         System.out.println(girllist.get(new Random().nextInt(girllist.size())));
        //     }
        // }


        //练习3 班级里有五个学生，要求随机点名，被点到的同学不会再被点到，如果所有同学都点完了，要重新开启第二轮点名
        // ArrayList<String> list = new ArrayList<>();
        // ArrayList<String> list2 = new ArrayList<>();
        // Collections.addAll(list, "男一号", "男二号", "男三号", "男四号","男五号",
        //             "女一号", "女二号", "女三号", "女四号", "女五号");
        // Random r = new Random();
    
        // for(int i = 0; i < list.size(); ){
        //     int index = r.nextInt(list.size());
        //     String name = list.remove(index);
        //     System.out.println(name);
        //     list2.add(name);
        // }
        // list.addAll(list2);
        // list2.clear();


        //练习4 定义一个Map集合，键表示省份，值表示市，但市有多个，添加完毕后遍历结果要按一定格式
        HashMap<String, ArrayList<String>> hm = new HashMap<>();
        ArrayList<String> city1 = new ArrayList<>();
        Collections.addAll(city1, "南京","扬州","苏州","无锡","常州");
        hm.put("江苏", city1);
        ArrayList<String> city2 = new ArrayList<>();
        Collections.addAll(city2, "武汉", "孝感", "十堰", "宜昌", "鄂州");
        hm.put("湖北", city2);
        Set<Map.Entry<String, ArrayList<String> > > entries = hm.entrySet();
        for (Entry<String,ArrayList<String>> entry : entries) {
            String key = entry.getKey();
            ArrayList<String> value = entry.getValue();
            //利用StringJoiner确定格式
            StringJoiner sj = new StringJoiner(", ", "", "");
            for (String i : value) {
                sj.add(i);
            }
            System.out.println(key+" = "+sj);
        }
    }
}
