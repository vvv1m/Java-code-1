import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;
import java.beans.*;
public class test {
    public static void main (String[] args) throws Exception{
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

        ////一个简单的石头剪刀布游戏
        // System.out.println("石头剪刀布:1石头2剪刀3布 任一方输入4退出");
        // while(true){
        //     System.out.println("第一位选手出：");
        //     int firstpl = scanner.nextInt();
        //     System.out.println("第二位选手出：");
        //     int secondpl = scanner.nextInt();
        //     if(firstpl == 4 || secondpl == 4){
        //         break;
        //     }
        //     if(firstpl == secondpl) System.out.println("平局");
        //     if(firstpl == 1){
        //         if(secondpl == 2) System.out.println("选手1胜出");
        //         if(secondpl == 3) System.out.println("选手2胜出");
        //     }
        //     if(firstpl == 2){
        //         if(secondpl == 3) System.out.println("选手1胜出");
        //         if(secondpl == 1) System.out.println("选手2胜出");
        //     }
        //     if(firstpl == 3){
        //         if(secondpl == 1) System.out.println("选手1胜出");
        //         if(secondpl == 2) System.out.println("选手2胜出");
        //     }
        // }

        ////将数组从大到小排序
        // int ns[] = {28, 12, 89, 73, 65, 18, 96, 50, 8, 36};
        // System.out.println(Arrays.toString(ns)); // 打印数组
        // for(int i = 0; i < ns.length; i++){
        //     ns[i] = -ns[i];
        // }
        // Arrays.sort(ns);
        // for(int i = 0; i < ns.length; i++){
        //     ns[i] = -ns[i];
        // }
        // System.out.println(Arrays.toString(ns));

        // String s = "a + b";
        // System.out.println(s.length());
        // String s = scanner.nextLine();
        // System.out.println(s);

        ////命令行参数的使用，存在问题
        // for(String arg : args){
        //     if("-version".equals(arg)){
        //         System.out.println("v 1.0");
        //         break;
        //     }
        // }

        ////类的实例化的示例
        // City bj = new City();
        // bj.name = "Beijing";
        // bj.latitude = "39.903";
        // bj.longitude = "116.401";
        // System.out.println(bj.name);
        // System.out.println("Location:" + bj.latitude+","+bj.longitude);
        
        ////类的继承例子
        // Person p = new Person("小明", 12);
        // Student s = new Student("小红", 20, 99);
        // Student ps = new PrimaryStudent("小军", 9, 100, 5);
        // System.out.println(ps.getScore());

        ////判断回文数
        // String num;
        // num = scanner.nextLine();
        // String renum = new StringBuilder(num).reverse().toString();
        // if(num.equals(renum)){
        //     System.out.println("Ture");
        // } else{
        //     System.out.println("False");
        // }

        //读取一个浮点数
        // System.out.println("输入一个浮点数");
        // float a = scanner.nextFloat();
        // System.out.println(a);
        // int i = 100;
        // //Integer n1 = new Integer(i);通过new符创造会有编译警告
        // Integer n2 = Integer.valueOf(i); //静态方法valueOf(int)创建
        // Integer n3 = Integer.valueOf("100");//静态方法valueOf(String)创建
        // //Byte.value();
        // int x2 = Integer.parseInt("100", 16);
        // System.out.println(Integer.toString(100, 36));
        // System.out.println(Integer.toHexString(100));
        // System.out.println(Integer.toOctalString(100));
        // System.out.println(Integer.toBinaryString(100));
        // BeanInfo info = Introspector.getBeanInfo(Person.class);
        // for(PropertyDescriptor pd:info.getPropertyDescriptors()){
        //     System.out.println(pd.getName());
        //     System.out.println(" " + pd.getReadMethod());
        //     System.out.println(" " + pd.getWriteMethod());
        // }

        // String s = Weekday.SUN.name(); // 获取名字
        // int n = Weekday.MON.ordinal(); // 获取定义常量的顺序 1
        // System.out.println(s);
        // System.out.println(n);
        // Weekday day = Weekday.SUN;
        // if(day.dayvalue == 0 || day.dayvalue == 1){
        //     System.out.println("Work in home");
        // } else{
        //     System.out.println("Work in office");
        // }
        ArrayList<String> list = new ArrayList<>(); //ArrayList的创建方法
        list.add("abc");
        list.add("bcd"); //元素添加方法
        System.out.print("[");
        for(int i = 0; i < list.size(); i++){
            if(i == list.size() - 1){
                System.out.print(list.get(i));
            }else{
                System.out.print(list.get(i) + ",");
            }
        }
        System.out.print("]");
        ArrayList<Integer> list_num = new ArrayList<>(); //ArrayList中只能存引用类型，使用Integer包装类型
        ArrayList<Character> list_char = new ArrayList<>();

        scanner.close();
    }
}
enum Weekday{ // 本质上是class，所以可以定制方法
    SUN(0), MON(1), TUE(2), WED(3), THU(4), FRI(5), SAT(6);
    int dayvalue;
    private Weekday(int dayvalue){
        this.dayvalue = dayvalue;
    }
}
class City{
    public String name;
    public String latitude;
    public String longitude;
}
class Person{
    protected String name;
    protected int age;

    public Person(String name, int age){
        this.name = name;
        this.age = age;
    }
    public String getName(){ return name; }
    public void setName(String name){ this.name = name;}

    public int getAge(){ return age; }
    public void setAge(int age){ this.age = age;}
}
class Student extends Person{
    protected int score;
    public Student(String name, int age, int score){
        super(name, age);
        this.score = score;
    }
    public int getScore(){ return this.score;}
}
class PrimaryStudent extends Student{ //类的继承练习
    protected int grade;
    public PrimaryStudent(String name, int age, int score, int grade){
        super(name, age, score);
        this.grade = grade;
    }
}