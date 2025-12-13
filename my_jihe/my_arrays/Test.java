package my_jihe.my_arrays;
import java.util.Arrays;
import java.util.Comparator;
public class Test {
    public static void main(String[] args) {
        //toString将数组变成字符串
        int[]arr = {1,2,3,4,5,6,7,8,9,10};
        System.out.println(Arrays.toString(arr));
        //binarySearch 二分查找法查找元素
        //细节1：要求数组有序且为升序
        //细节2：如果查找元素存在返回索引，不存在则返回---插入点 - 1
        //这里的插入点指的是该数据如果插入数组中，应该在哪个位置
        //减一的目的是为了防止出现-0，导致和0区分不开
        System.out.println(Arrays.binarySearch(arr, 2));
        System.out.println(Arrays.binarySearch(arr,20));
        //copyOf:拷贝数组
        //参数分别是老数组，新数组的长度
        //方法底层会根据第二个参数创建新数组
        //长度小于老数组，部分拷贝，长度等于，完全拷贝，长度大于，补上默认初始值
        int[] newarr1 = Arrays.copyOf(arr, 2);
        System.out.println(Arrays.toString(newarr1));
        int[] newarr2 = Arrays.copyOf(arr, 20);
        System.out.println(Arrays.toString(newarr2));//默认初始值为0
        //copyOfRange：拷贝数组（指定范围）
        //细节：包头不包尾，包左不包右
        int[] newarr3 = Arrays.copyOfRange(arr, 0, 9);
        System.out.println(Arrays.toString(newarr3));
        //fill：填充
        Arrays.fill(newarr2, 100);
        System.out.println(Arrays.toString(newarr2));

        //sort(数组，排序规则) 按照指定的顺序排序
        //细节：只能给引用数据类型的数组进行排序
        //如果数据时基本数据类型的，需要变成对应的包装类
        Integer[] newarr4 = {2,4,25,90,7,4,7,4,68,8,131};
        //第二个参数是一个接口，所以我们在调用方法的时候，需要传递这个接口的实现类对象，作为排序的规则
        //但该类只需要使用一次，没有必要单独写一个类
        //所以可以使用匿名内部类

        //方法底层原理：
        //利用插入排序+二分查找，默认将0索引认为是无序的序列，1到最后认为是无序的序列
        //遍历无序得到A，将A插入有序序列，插入时利用二分查找确定A的插入点
        //用A与插入点元素比较，比较的规则就是compare方法体
        //如果compare返回的是负数，拿着A继续跟着前面的数据进行比较
        //如果返回的是正数，拿着A继续跟后面的数据进行比较
        //如果返回0，也拿着A跟后面的数据进行比较
        //直到确认A的最终位置
        Arrays.sort(newarr4, new Comparator<Integer>() {
            //compare方法的形式参数
            //o1：表示在无序序列中遍历得到的元素
            //o2：有序序列中的元素

            //返回值：
            //负数，当前插入的元素要放在前面
            //正数，当前插入的元素要放在后面
            //0，表示当前插入与现在的元素一样，也放在后面
            @Override
            public int compare(Integer o1, Integer o2){
                return o2 - o1;
            }
        });
        System.out.println(Arrays.toString(newarr4));
    }
}
