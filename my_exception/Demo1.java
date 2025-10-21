package my_exception;
public class Demo1 {
    public static void main(String[] args) {
        //异常作用1 查询bug的关键参考信息
        //异常作用2 异常可以作为方法内部的一种特殊返回值，以便通知调用者底层的执行情况


        
        Student[] arr = new Student[3];
        String name = arr[0].getName();
        System.out.println(name);
    }
}
