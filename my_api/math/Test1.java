package my_api.math;

public class Test1 {
    public static void main(String[] args) {
        int a = 997;
        System.out.println(isPrime(a));
    }


    //判断一个数是否是质数
    public static boolean isPrime(int a){
        for(int i = 2; i < Math.sqrt(a); i++){
            if(a % i == 0){
                return false;
            }
        }
        return true;
    }
}
