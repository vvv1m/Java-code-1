package other;
import java.util.Scanner;
public class bianyi {
    private static int num; // 当前分析的字符位置
    private static String obj_s; // 待分析的表达式

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
       while(scanner.hasNextLine()){
            obj_s = scanner.nextLine();
            if(obj_s.isEmpty()) break;
            num = 0;
            try{
                parse_E();
                if(num == obj_s.length()) System.out.println("True");
                else System.out.println("False");
            } catch(Exception e){
                System.out.println("False");
            }
       }
       scanner.close();
    }
    //E->TE'
    private static void parse_E(){
        parse_T();
        parse_E1();
    }
    //E'->+TE'|-TE'|空 
    private static void parse_E1(){
        if(num < obj_s.length()){
            while(num < obj_s.length() && obj_s.charAt(num) == ' '){
                num++;
            }
            if(num >= obj_s.length()){
                return;
            }
            char c = obj_s.charAt(num);
            if(c == '+' || c == '-'){
                num++;
                parse_T();
                parse_E1(); 
            }

        }
    }
    //T->FT'
    private static void parse_T(){
        parse_F();
        parse_T1();
    }
    //T'->*FT'|/FT'|空
    private static void parse_T1(){
        if(num < obj_s.length()){
            while(num < obj_s.length() && obj_s.charAt(num) == ' '){
                num++;
            }
            if(num >= obj_s.length()){
                return;
            }
            char c = obj_s.charAt(num);
            if(c == '*' || c == '/'){
                num++;
                parse_F();
                parse_T1();
            }
        }
    }
    //F->i|num|(E)
    private static void parse_F(){
        if(num >= obj_s.length()){
            throw new RuntimeException("error");
        }
        while(num < obj_s.length() && obj_s.charAt(num) == ' '){
            num++;
        }
        if(num >= obj_s.length()){
            return;
        }
        char c = obj_s.charAt(num);
        if(Character.isDigit(c)){
            while(num < obj_s.length() && Character.isDigit(obj_s.charAt(num))){
                num++;
            }
            if(num < obj_s.length() && obj_s.charAt(num) == '.'){
                num++;
                if(num < obj_s.length() && Character.isDigit(obj_s.charAt(num))){
                    while(num < obj_s.length() && Character.isDigit(obj_s.charAt(num))){
                        num++;
                    }
                } else {
                    throw new RuntimeException("error");
                }
            }
            //检测e+或e-
            if(num < obj_s.length() && (obj_s.charAt(num) == 'e' || obj_s.charAt(num) == 'E')){
                num++;
                if(num < obj_s.length() && (obj_s.charAt(num) == '+' || obj_s.charAt(num) == '-')){
                    num++;
                }
                if(num < obj_s.length() && Character.isDigit(obj_s.charAt(num))){
                    while(num < obj_s.length() && Character.isDigit(obj_s.charAt(num))){
                        num++;
                    }
                } else {
                    throw new RuntimeException("error");
                }
            }
        }
        else if(Character.isLetter(c)){
            while(num < obj_s.length() && Character.isLetter(obj_s.charAt(num))){
                num++;
            }
            //读取字母后的数字
            while(num < obj_s.length() && Character.isDigit(obj_s.charAt(num))){
                num++;
            }
        }
        else if(c == '('){
            num++;
            parse_E();
            if(num >= obj_s.length()||obj_s.charAt(num) !=')' ){
                throw new RuntimeException("error");
            }
            num++;
        }
        else{
            throw new RuntimeException("error");
        }
    }
}