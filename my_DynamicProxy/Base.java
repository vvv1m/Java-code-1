package my_DynamicProxy;

public class Base {
    public static void main(String[] args) {
        //直接修改文件中的代码 --- 称作侵入式修改
        //通过动态代理，可以无侵入式的增加代码的功能

        //为什么需要代理 --- 对象认为身上干的事太多，可以通过代理来转移部分职责
        //代理长什么样子 --- 代理中的方法一般是对象想代理的方法+方法的扩展

        //通过接口来传递需要被代理的方法，对象和代理都要实现这个接口

        //利用java.lang.reflect.Proxy类，创建代理对象
        //public static Object newProxyInstance(ClassLoader loader, Class<?>[] interface, InvocationHandler h)
        //参数一 --- 用于指定用哪个类加载器，去加载生成的代理类，即将字节码文件加载到内存中
        //参数二 --- 指定接口，这些接口用于指定生成的代理长什么样，即有哪些方法
        //参数三 --- 用来指定生成的代理对象要干什么事情
    }
}
