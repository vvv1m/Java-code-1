package my_api.packClass.packClassPrac1;

import java.util.ArrayList;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        int sum = 0;
        while(sum < 200){
            String str = sc.nextLine();
            int num = Integer.parseInt(str);
            if(num < 0 || num > 100){
                System.out.println("数据错误");
                num = 0;
            }
            //触发自动装箱
            list.add(num);
            sum = sum + num;
            System.out.println("本次添加数字" + num + "，和为" + sum);

        }
    }
}
