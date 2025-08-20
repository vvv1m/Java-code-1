package my_interface.interface_demo1;
import java.util.Scanner;
public class Test {
    public static void main(String[] args) {
        Frog f = new Frog("小青", 2);
        f.eat();
        f.swim();
        Scanner sc = new Scanner(System.in);
        sc.next();
    }
}
