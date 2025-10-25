package my_exception;

public class UsualFunction {
    public static void main(String[] args) {
        //throwable类中定义的常用方法
        //public String getMessage() 返回此 throwable 的详细消息字符串
        //public String toString() 返回此 throwable 的描述
        //public void printStackTrace() 把异常信息打印在控制台
        //上述三者仅仅是打印信息，不会停止程序运行
        // int[] arr = {1,2,3};
        // try{
        //     System.out.println(arr[10]);
        // }catch(ArrayIndexOutOfBoundsException e){
        //     String message = e.getMessage();
        //     System.out.println("getMessage:"+message);//getMessage:Index 10 out of bounds for length 3
        //     System.out.println("toString:"+e.toString());//toString:java.lang.ArrayIndexOutOfBoundsException: Index 10 out of bounds for length 3
        //     e.printStackTrace(); // 常用，信息多，使用红色字体打印
        // }
        // System.out.println("111");
        //正常的输出语句
        //System.out.println(123);
        //错误的输出语句，用来打印错误信息,输出内容一般来说是红色的
        System.err.println(123);
    }
}
