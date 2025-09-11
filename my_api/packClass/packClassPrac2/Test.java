package my_api.packClass.packClassPrac2;

public class Test {
    public static void main(String[] args) {
        //自己实现十进制转二进制
        System.out.println(toBinaryString(6));

    }
    public static String toBinaryString(int num){
        StringBuilder  sb = new StringBuilder();
        while(num != 0){
            //获取余数
            int reminder = num % 2;
            //num减半
            System.out.println(reminder);
            sb.insert(0, reminder);
            num = num / 2;
        } 
        return sb.toString();
    }
}
