package my_api.JDK7time;
import java.text.SimpleDateFormat;
import java.util.Date;
public class Test {
    public static void main(String[] args) {
        //将时间段额表示格式改为 时：分：秒 星期
        Date d1 = new Date(0L);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss EE");
        String str = sdf.format(d1);
        System.out.println(str);
    }
}
