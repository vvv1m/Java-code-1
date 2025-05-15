import java.util.Scanner;
public class test {
    public static void main (String[] args){
        Scanner scanner = new Scanner(System.in); // 创建Scanner对象
        ////浮点数运算
        // double a = 1.0;
        // double b = 3.0;
        // double c = -4.0;
        // double r1 = 0;
        // double r2 = 0;
        // r1 = (-b + Math.sqrt(b*b - 4*a*c))/(2*a);
        // r2 = (-b - Math.sqrt(b*b - 4*a*c))/(2*a);
        // System.out.println(r1);
        // System.out.println(r2);

        ////字符串的不可变特性
        // String s = "hello";
        // String t = s;
        // s = "world";
        // System.out.println(t);

        ////将一组int值视为字符的Unicode编码，然后拼成一个字符串
        // int a = 72;
        // int b = 105;
        // int c = 65281;
        // //char强转后才是Unicode码
        // //"" + a + b + c仅输出一串数字
        // String s = "" + (char)a + (char)b + (char)c;
        // System.out.println(s);

        ////输入与格式化输出的练习
        // System.out.println("请输入上次考试成绩");
        // int a = scanner.nextInt();
        // System.out.println("请输入本次考试成绩");
        // int b = scanner.nextInt();
        // double c = (b-a)*1.0/(a*1.0) * 100;
        // System.out.printf("成绩提高百分比为%.2f%%\n", c);

        System.out.println("石头剪刀布:1石头2剪刀3布4退出");
        while(true){
            System.out.println("第一位选手出：");
            int firstpl = scanner.nextInt();
            System.out.println("第二位选手出：");
            

        }

        scanner.close();
    }
}
