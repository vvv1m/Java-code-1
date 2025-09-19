package other;
public class Prac {
    public static void main(String[] args) {
        int num1 = 348;
        int num2 = 578;
        int result = Add.count(num1, num2);
        System.out.println(result);
    }
}
class Add{
    public static int count(int num1, int num2){
        return num1 + num2;
    }
    public static int count(int num1, int num2, int num3){
        return num1 + num2 + num3;
    }
    public static int count(int num1, int num2, int num3, int num4){
        return num1 + num2 + num3 + num4;
    }
}
