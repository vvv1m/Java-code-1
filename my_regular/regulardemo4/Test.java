package my_regular.regulardemo4;

public class Test {
    public static void main(String[] args) {
        //判断一串字符的开始和结束是否一致（只考虑一个字符）
        //例：a123a, b456b, 12541, %abc%, a123b
        //  \\组号 表示将分组x中的内容再用一次
        String regex1 = "(.).+\\1";
        //考虑多个字符
        //例：abc123abc
        String regex2 = "(.+).+\\1";
        System.out.println("abc123abc".matches(regex2));
        //判断一个字符开始部分和结束部分是否一致，开始部分内部的每个字符也需要一致
        //例：aaa123aaa
        String regex3 = "((.)\\2*).+\\1";
        System.out.println("aaa123aaa".matches(regex3));
        //口吃替换 将 我要学学学编编编程程程程 替换成我要学编程
        String str = "我要学学学编编编程程程程";
        String result = str.replaceAll("(.)\\1+", "$1");
        System.out.println(result);
    }
}
