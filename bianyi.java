import java.util.Scanner;
public class bianyi{
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Hello world");
        System.out.println(10.0/3);
        final int num3 = 10; //不可被修改，要赋初值
        String s = in.nextLine();
        System.out.println(num3+s);
        in.close();
    }
}