package my_reflect;

public class FunctionToGetClass {
    public static void main(String[] args) {
        //获取要先获取class对象

        //获取class对象的三种方法：
        // 1 --- Class.forName("全类名");
        // 2 --- 类名.class
        // 3 --- 对象.getClass();

        //三种方式对应Java创建对象的三种阶段
        //源代码阶段：A.java ---> A.class 使用方式1获取class对象
        //加载阶段：将A.class放到内存中 使用方式2
        //运行阶段：A a = new A(); 使用方式3

    }
}
