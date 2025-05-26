import java.util.ArrayList;
import java.util.Scanner;


public class bianyi2 {
    private static ArrayList<String> K = new ArrayList<String>() {{
        add("int"); add("void"); add("break"); add("float"); add("while");
        add("do"); add("struct"); add("const"); add("case"); add("for");
        add("return"); add("if"); add("default"); add("else");
    }};
    private static ArrayList<String> P = new ArrayList<String>() {{
        add("-"); add("/"); add("("); add(")"); add("=="); add("<=");
        add("<"); add("+"); add("*"); add(">"); add("="); add(",");
        add(";"); add("++"); add("{"); add("}");
    }};
    private static ArrayList<String> I = new ArrayList<>();
    private static ArrayList<String> C = new ArrayList<>();
    private static int judge_pend_I(String pend_s){ //判断是否在I中，在则返回位置
        for(int i = 0; i < I.size(); i++){
            if(pend_s.equals(I.get(i))){
                return i + 1;
            }
        }
        return 0;
    }
    private static int judge_pend_K(String pend_s){ //判断是否在K中，在则返回位置
        for(int i = 0; i < K.size(); i++){
            if(pend_s.equals(K.get(i))){
                return i + 1;
            }
        }
        return 0;
    }
    private static int judge_pend_C(String pend_s){ //判断是否在C中，在则返回位置
        for(int i = 0; i < C.size(); i++){
            if(pend_s.equals(C.get(i))){
                return i + 1;
            }
        }
        return 0;
    }
    private static int judge_pend_P(String pend_s){ //判断是否在P中，在则返回位置
        for(int i = 0; i < P.size(); i++){
            if(pend_s.equals(P.get(i))){
                return i + 1;
            }
        }
        return 0;
    }
     private static void print_all_token(String line) {
        int i = 0;
        while (i < line.length()) {
            // 跳过空格
            if (Character.isWhitespace(line.charAt(i))) {
                i++;
                continue;
            }
            // 尝试匹配双字符界符
            if (i + 1 < line.length()) {
                String two = line.substring(i, i + 2);
                int pIdx = judge_pend_P(two);
                if (pIdx > 0) {
                    System.out.print("(P " + pIdx + ")");
                    i += 2;
                    continue;
                }
            }
            // 尝试匹配单字符界符
            String one = line.substring(i, i + 1);
            int pIdx = judge_pend_P(one);
            if (pIdx > 0) {
                System.out.print("(P " + pIdx + ")");
                i++;
                continue;
            }
            // 识别标识符或常数
            if (Character.isLetter(line.charAt(i))) {
                int start = i;
                while (i < line.length() && (Character.isLetterOrDigit(line.charAt(i)))) i++;
                String word = line.substring(start, i);
                int kIdx = judge_pend_K(word);
                if (kIdx > 0) {
                    System.out.print("(K " + kIdx + ")");
                } else {
                    int idx = judge_pend_I(word);
                    if (idx == 0) { //如果不在则加入I表
                        I.add(word);
                        idx = I.size();
                    }
                    System.out.print("(I " + idx + ")");
                }
                continue;
            }
            if (Character.isDigit(line.charAt(i))) {
                int start = i;
                while (i < line.length() && Character.isDigit(line.charAt(i))) i++;
                String num = line.substring(start, i);
                int idx = judge_pend_C(num);
                if (idx == 0) { //如果不在则加入C表
                    C.add(num);
                    idx = C.size();
                }
                System.out.print("(C " + idx + ")");
                continue;
            }
            // 其他字符直接跳过
            i++;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String line = "";
        System.out.print("Token :");
        while(scanner.hasNextLine()){ //连续输入两次回车才退出
            line = scanner.nextLine();
            if (line.isEmpty()) {
                break;
            }
            print_all_token(line);
            System.out.println();
            System.out.print("I :");
            for (int i = 0; i < I.size(); i++) {
                System.out.print(I.get(i));
                if (i != I.size() - 1) System.out.print(" ");
            }
            System.out.println();
            System.out.print("C :");
            for (int i = 0; i < C.size(); i++) {
                System.out.print(C.get(i));
                if (i != C.size() - 1) System.out.print(" ");
            }
        }
        
        scanner.close();
    }
}
