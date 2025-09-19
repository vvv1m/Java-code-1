package my_commonalgo.search;

import java.util.ArrayList;

public class CommonSearch {
    public static void main(String[] args) {
        //基本查找：遍历查找
        //练习需求：查找元素，返回索引，考虑重复
        //返回多个元素，放到数组中返回
        int[] arr = {1,2,3,4,5,6,3,4,5,6,7,8};
        ArrayList<Integer> list = function(arr, 6);
        System.out.println(list);

    }
    public static ArrayList function(int[] arr, int num){
        ArrayList<Integer> list = new ArrayList<>();
        for(int i = 0; i < arr.length; i++){
            if(arr[i] == num){
                list.add(i);
            }
        }
        return list;
    }
}
