package my_lambda;
import java.util.*;
public class Prac {
    public static void main(String[] args) {
        String[] strarr = {"aed", "addwq", "ad", "a", "darfad"};
        Arrays.sort(strarr, (o1, o2)->o1.length() - o2.length());
        System.out.println(Arrays.toString(strarr));
    }
}
