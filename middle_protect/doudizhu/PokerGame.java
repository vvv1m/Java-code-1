package middle_protect.doudizhu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.TreeSet;

public class PokerGame {
    // //准备牌，由于只要准备一次牌，就可以玩无数次，所以使用静态代码块即可
    // //♦ ♣ ♥ ♠
    // //3 4 5 6 7 8 9 10 J Q K A 2
    // static ArrayList<String> poker = new ArrayList<>();
    // static {
    //     String[] color = {"D", "C", "H", "S"};//D方块 C梅花 H红桃 S黑桃
    //     String[] number = {"3", "4", "5", "6", "7","8", "9", "10", "J", "Q", "K", "A","2"};
        
    //     for (String c : color) {
    //         for (String n : number) {
    //             poker.add(c+n);
    //         }
    //     }
    //     poker.add("RedJoker");
    //     poker.add("BlackJoker");
    //     //System.out.println(poker);
    // }
    // PokerGame(){
    //     //洗牌
    //     Collections.shuffle(poker);
    //     //System.out.println(poker);
    //     //创建四个集合存储三家手牌和底牌
    //     ArrayList<String> lord = new ArrayList<>();
    //     ArrayList<String> p1 = new ArrayList<>();
    //     ArrayList<String> p2 = new ArrayList<>();
    //     ArrayList<String> p3 = new ArrayList<>();
    //     //发牌
    //     for(int i = 0; i < poker.size(); i++){
    //         if(i <= 2){
    //             lord.add(poker.get(i));
    //         }else{
    //             if(i %3 == 0){
    //                 p1.add(poker.get(i));
    //             }else if(i % 3 == 1){
    //                 p2.add(poker.get(i));
    //             }else{
    //                 p3.add(poker.get(i));
    //             }
    //         }
    //     }
    //     lookPoker("load", load);
    //     lookPoker("player1", p1);
    //     lookPoker("player2", p2);
    //     lookPoker("player3", p3);
    // }
    // public static void lookPoker(String name, ArrayList<String> poker){
    //     System.out.println(name + ": ");
    //     for (String apoker : poker) {
    //         System.out.print(apoker + " ");
    //     }
    //     System.out.println();
    // }

    //接下来需要理牌
    //以序号排序
    //采用编程中的对应思想，如果原始的数据非常复杂，我们可以先动手排序让每一个数据跟唯一的序号产生对应关系
    //这里的序号就是数字，数字的规律很简单，后续的所有操作就以序号为准

    //所以上述代码需要重写编写
    //仅仅要求牌跟序号产生对应关系，不需要排序
    // static HashMap<Integer, String> pokers = new HashMap<>();
    // static ArrayList<Integer> list = new ArrayList<>();
    // //初始化牌盒
    // static{
    //     String[] color = {"D", "C", "H", "S"};//D方块 C梅花 H红桃 S黑桃
    //     String[] number = {"3", "4", "5", "6", "7","8", "9", "10", "J", "Q", "K", "A","2"};
    //     int serialnumber = 1;
    //     for (String n : number) {
    //         for (String c : color) {
    //             pokers.put(serialnumber, n+c);
    //             list.add(serialnumber);
    //             serialnumber++;
    //         }
    //     }
    //     pokers.put(serialnumber, "blackJoker");
    //     list.add(serialnumber);
    //     serialnumber++;
    //     pokers.put(serialnumber, "redJoker");
    //     list.add(serialnumber);
    //     System.out.println(list);
    // }
    // public PokerGame(){
    //     //洗牌
    //     Collections.shuffle(list);
    //     //System.out.println(poker);
    //     //创建四个集合存储三家手牌和底牌
    //     TreeSet<Integer> lord = new TreeSet<>((o1,o2)->o2-o1);
    //     TreeSet<Integer> p1 = new TreeSet<>((o1,o2)->o2-o1);
    //     TreeSet<Integer> p2 = new TreeSet<>((o1,o2)->o2-o1);
    //     TreeSet<Integer> p3 = new TreeSet<>((o1,o2)->o2-o1);
    //     //发牌
    //     for(int i = 0; i < list.size(); i++){
    //         if(i <= 2){
    //             lord.add(list.get(i));
    //         }else{
    //             if(i %3 == 0){
    //                 p1.add(list.get(i));
    //             }else if(i % 3 == 1){
    //                 p2.add(list.get(i));
    //             }else{
    //                 p3.add(list.get(i));
    //             }
    //         }
    //     }
    //     lookPoker("load", load);
    //     lookPoker("player1", p1);
    //     lookPoker("player2", p2);
    //     lookPoker("player3", p3);
    // }
    // public static void lookPoker(String name, TreeSet<Integer> poker){
    //     System.out.println(name + ": ");
    //     for (Integer apoker : poker) {
    //         System.out.print(pokers.get(apoker) + " ");
    //     }
    //     System.out.println();
    // }


    //理牌的第二种方式：给每一张牌计算价值，那么再对上面代码进行重写
    static HashMap<String, Integer> cost = new HashMap<>();
    static ArrayList<String> list = new ArrayList<>();
    //初始化牌盒
    static{
        String[] color = {"D", "C", "H", "S"};//D方块 C梅花 H红桃 S黑桃
        String[] number = {"3", "4", "5", "6", "7","8", "9", "10", "J", "Q", "K", "A","2"};
        for (String n : number) {
            for (String c : color) {
                list.add(c+n);
            }
        }
        list.add(" blackJoker");
        list.add(" redJoker");
        System.out.println(list);

        //指定牌的价值
        //牌上的数字到Map集合中判断是否存在
        //存在就获取价值 不存在本身即价值， 这样做使Map中存储的数据变少，查询效率更高
        cost.put("J", 11);
        cost.put("Q", 12);
        cost.put("K", 13);
        cost.put("A", 14);
        cost.put("2", 15);
        cost.put("blackJoker", 99);
        cost.put("redJoker", 100);
    }
    public PokerGame(){
        //洗牌
        Collections.shuffle(list);
        //System.out.println(poker);
        //创建四个集合存储三家手牌和底牌
        ArrayList<String> lord = new ArrayList<>();
        ArrayList<String> p1 = new ArrayList<>();
        ArrayList<String> p2 = new ArrayList<>();
        ArrayList<String> p3 = new ArrayList<>();

        //发牌
        for(int i = 0; i < list.size(); i++){
            if(i <= 2){
                lord.add(list.get(i));
            }else{
                if(i %3 == 0){
                    p1.add(list.get(i));
                }else if(i % 3 == 1){
                    p2.add(list.get(i));
                }else{
                    p3.add(list.get(i));
                }
            }
        }
        //排序
        sortByCost(lord);
        sortByCost(p1);
        sortByCost(p2);
        sortByCost(p3);

        lookPoker("load", lord);
        lookPoker("player1", p1);
        lookPoker("player2", p2);
        lookPoker("player3", p3);

    }
    public void sortByCost(ArrayList<String> list){
        Collections.sort(list, (o1,o2)->{
            String color1 = o1.substring(0,1);
            String color2 = o2.substring(0,1);
            int result = getValue(o2) - getValue(o1);
            result = result == 0 ? color2.compareTo(color1) : result;
            return result;
        });
    }
    public int getValue(String poker){
        String num = poker.substring(1);
        if(cost.containsKey(num)){
            return cost.get(num);
        }else{
            return Integer.parseInt(num);
        }
    }
    public static void lookPoker(String name, ArrayList<String> poker){
        System.out.println(name + ": ");
        for (String apoker : poker) {
            System.out.print(apoker + " ");
        }
        System.out.println();
    }

}
