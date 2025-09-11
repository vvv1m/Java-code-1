package my_regular.regulardemo1;

public class Test {
    public static void main(String[] args) {
        String qq = "1234567890";
        System.out.println(checkQQ(qq));
    }
    public static boolean checkQQ(String qq){
        return qq.matches("[1-9]\\d{5,19}");
    }
}
//在API帮助文档中搜索Pattern就可以看到转义字符的帮助文档