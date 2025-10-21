package my_exception;

public class Demo2 {
    public static void main(String[] args) {
        Student s = new Student();
        s.setAge(50);//抛出异常，调用处就知道赋值失败
        //此时有两种选择
        //1 自己悄悄处理
        //2 打印在控制台上
    }
}
