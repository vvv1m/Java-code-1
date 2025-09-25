package my_jihe;

public class ChangeableNum {
    //定义方法可以灵活的求多个数的和
    public static void main(String[] args) {
        System.out.println(getSum(1,2,3,4,5,6,7,8,9,10));
    }
    public static int getSum(int...is){ // 可变参数
        int sum = 0;
        for (int i : is) {
            sum = sum + i;
        }
        return sum;
    }
    //可变参数底层就是一个数组
    //只不过不需要我们自己创建，Java会帮我们创建好
    //方法的形参中最多只能写一个可变参数
    //在方法的形参中，如果除了可变参数还有其他的形参，要把可变参数写在最后
}
