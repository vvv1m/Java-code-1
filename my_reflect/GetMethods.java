package my_reflect;

public class GetMethods {
    public static void main(String[] args) {
        //Class类中用于获取成员方法的方法
        //Method[] getMethods() 返回所有公共成员方法对象的数组，包括继承的
        //Method[] getDeclaredMethods() 返回所有成员方法对象的数组，不包括继承的
        //Method getMethod(String name, Class<?>...parameterTypes) 返回单个公共
        //Method getDeclaredMethod(String name, Class<?>...parameterTypes) 返回单个成员

        //Method类中用于创建对象的方法
        //Object invoke(Object obj, Object...args) 运行方法
        //参数一 用obj对象调用该方法
        //参数二 调用的方法传递的参数(如果没有就不写)
        //返回值 (如果没有就不写)
 
    }
}
