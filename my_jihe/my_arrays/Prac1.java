package my_jihe.my_arrays;

public class Prac1 {
    public static void main(String[] args) {
        //爬楼梯
        System.out.println(function(20));
    }
    public static int function(int num){
        if(num == 1){
            return 1;
        }else if(num == 2){
            return 2;
        }else if(num == 3){
            return 4;
        }else{
            return function(num - 1) + function(num - 2) + function(num - 3);
        }
    }
}

