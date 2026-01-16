package my_reflect;

public class GetConstructors {
    public static void main(String[] args) {
        //Class类中用于获取构造方法的方法
        //Constructor<?>[] getConstructors() 返回所有公共构造方法对象的数组
        //Constructor<?>[] getDeclaredConstructors() 返回所有构造方法对象的数组
        //Constructor<T> getConstructor(Class<?>...parameterTypes) 返回单个公共构造方法对象
        //  参数的含义是要与想获取的构造方法的参数对应
        //  比如想获取空参构造，就空着不填
        //  想获取带有String参数的构造方法，就填入String.class，想获取带有int参数的构造方法，就填入int.class
        //  以此类推
        //Constructor<T> getDeclaredConstructor(Class<?>...parameterTypes) 返回单个构造方法对象



        //Constructor类中用于创建对象的方法
        //T newInstance(Object...initargs) 根据指定的构造方法创建对象
        //setAccessible(boolean flag) 设置为true，表示取消访问检查
    }
}
