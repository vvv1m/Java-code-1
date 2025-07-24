package my_static.a01demo02;

public class TestArrUtil {
    public static void main(String[] args){
        int[] arr1 = {1,2,3,4,5};
        double[] arr2 = {1.2, 1.3, 2.4, 2.6, 3.5};
        String s = ArrUtil.printArr(arr1);
        System.out.println(s);
        double aver = ArrUtil.getAverage(arr2);
        System.out.println(aver);
    }
}
