package my_reflect.GetConstructorsDemo;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
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

        Class clazz = Class.forName("my_reflect.GetConstructorsDemo.Student");
        Constructor cons = clazz.getDeclaredConstructor(String.class, int.class);
        //此时虽然取出了私有的构造方法，但是还不能使用
        //Student student = (Student)cons.newInstance("zhangsan", 23);

        //暴力反射：表示临时取消权限检验
        cons.setAccessible(true);
        Student student = (Student)cons.newInstance("zhangsan", 23);
        System.out.println(student);
    }
}
