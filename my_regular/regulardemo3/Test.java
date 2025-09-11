package my_regular.regulardemo3;

public class Test {
    public static void main(String[] args) {
        //进行用户名和身份证号的校验
        //用户名要求：大小写字母，数字，下划线一共4-16位
        String regex1 = "\\w{4,16}";
        System.out.println("zhangsan".matches(regex1));
        //身份证号码的简单检验
        //18位，前17位是任意数字，第一位不能是0，最后一位可以是数字或大写X或小写x
        String regex2 = "[1-9]\\d{16}(\\d|X|x)";
        //或者使用另一种方法
        String regex3 = "[1-9]\\d{16}[\\dXx]";
        //忽略大小写的书写方式
        String regex4 = "(?i)abc"; //(?i)忽略其后面内容的大小写
        String regex5 = "a(?i)bc"; //这样就只忽略bc的大小写而不忽略a的
        String regex6 = "a((?i)b)c";//这样就可以只忽略b的大小写而不忽略a和c的
        //将其应用到身份证的检验上
        String regex7 = "[1-9]\\d{16}(\\d|(?i)x)";

        //身份证号码的严格校验
        //410801 1993 02 28 457x
        //身份证前六位表示省份，市区，派出所等信息  第一位不能是0，后面五位是任意数字 [1-9]\\d{5}
        //年的前半段：18,19,20  (18|19|20)
        //年的后半段：任意数字出现两次 \\d{2}
        //月份：01-09， 10 11 12 (0[1-9]|1[0-2])
        //日期： 01-31 (0[1-9]|[12]//d|3[01])
        //后面四位：任意数字出现三次，最后一位数字或大小写x \\d{3}[\\dXx]
        String regex8 = "[1-9]\\d{5}(18|19|20)\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])\\d{3}[\\dXx]";
        System.out.println("41080119930228457x".matches(regex8));


    }
}
