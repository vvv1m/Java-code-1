package my_stream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Prac1 {
    public static void main(String[] args) {


        //定义一个集合存储一些数，过滤奇数
        // ArrayList<Integer> list = new ArrayList<>();
        // Collections.addAll(list, 1,2,3,4,3,5,64,24,645,42,5431,3245,876567);
        // List<Integer> newlist = list.stream()
        //     .filter(num -> (num %2) == 0)
        //     .collect(Collectors.toList());
        // System.out.println(newlist);



        //创建ArrayList集合，并添加以下字符串，字符串中前面是姓名，后面是年龄
        //保留年龄大于等于24的人，并将结果收集到Map集合中，姓名为键，年龄为值
        // ArrayList<String> list = new ArrayList<>();
        // Collections.addAll(list, "zhangsan, 23", "lisi, 24", "wnagwu, 25");
        // Map<String, Integer> map = list.stream()
        //     .filter(s->Integer.parseInt(s.split(", ")[1]) >= 24)
        //     .collect(Collectors.toMap(
        //         s->s.split(", ")[0],
        //         s->Integer.parseInt(s.split(", ")[1])));
        // System.out.println(map);

        //有两个ArrayList集合，分别存储6名男演员和6名女演员的名字和年龄，中间用,隔开，完成如下操作
        //  男演员只要名字为三个字的前两个人
        //  女演员只要姓杨的，且不要第一个
        //  把过滤后的男演员姓名和女演员姓名合并到一起
        //  将上一步的演员姓名封装成Actor对象
        //  将所有演员对象都保存在List集合中

        ArrayList<String> actor = new ArrayList<>();
        Collections.addAll(actor, "蔡xk,25", "aaa,24", "bbb,23", "an,26", "mn,36", "ww,30");
        ArrayList<String> actress = new ArrayList<>();
        Collections.addAll(actress, "杨aa,22", "杨cc,18", "杨mm,20", "jjj,35", "awq,26", "aqq,24");
        
        Stream<String> stream = Stream.concat(
            actor.stream()
            .filter(s->s.split(",")[0].length()==3)
            .limit(2), 
            actress.stream()
            .filter(s->"杨".equals(s.substring(0, 1)))
            .skip(1));
        //现在获取到了合并的Stream流 泛型为String 想要用Actor封装，就要将其进行类型转换
        //其实也可以和上面的Stream流代码合并，但是那样可读性太差，就不做合并
        List<Actor> actorlist = stream.map(s-> new Actor(s.split(",")[0], 
                Integer.parseInt(s.split(",")[1])))
            .collect(Collectors.toList());

        System.out.println(actorlist);

    }
}
