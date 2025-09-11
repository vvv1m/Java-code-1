package my_regular.regulardemo2;

public class Test {
    public static void main(String[] args) {
        //在利用正则表达式对某个数据进行验证时
        //拿一个正确的数据从左到右写
        //手机号 13536236536
        //利用正则验证的时候使用matches验证
        String regex1 = "1[3-9]\\d{9}";
        System.out.println("14789655552".matches(regex1));
        System.out.println("952366521252".matches(regex1));


        //座机号码
        //需要把正确的数据分为三个部分
        //一：区号
        //二： - ?表示次数一次或零次
        //三：号码
        String regex2 = "0\\d{2,3}-?[1-9]\\d{4,9}";
        System.out.println("020-2324242".matches(regex2));
        System.out.println("023--425126552".matches(regex2));


        //邮箱号码
        //依旧是三部分
        //第一部分：@的左边 \\w 代表 a-z A-Z 0-9 _  \\w+表示任意的字母数字下划线至少出现一次
        //第二部分：@
        //第三部分依旧弄成三小段
        //第一段为.的左边：任意的字母或数字，但是不能出现下划线，2-6次  [\\w&&[^_]]{2-6}
        //第二段为.：.不能直接出现在正则表达式中，所以要利用转义字符转义，所以加上\\在字符串中体现出\，在正则时就可以转义.
        //第三段为.的右边：
        String regex3 = "\\w+@[\\w&&[^_]]{2,6}(\\.[a-zA-Z]{2,3}){1,2}";
        System.out.println("wangyulm0517@qq.com".matches(regex3));

        //实际开发中很少自己写，一般都百度一个类似的，然后改成公司要求

        String regex4 = "thunderx?:\\/\\/[a-zA-Z\\d]+=";//右键选择正则大全即可选择对应格式（插件any_rule）
        String regex5 = "(?:[01]\\d|2[0-3]):[0-5]\\d:[0-5]\\d";
        System.out.println("20:52:30".matches(regex5));
    }
}
