package my_commonalgo.search;

public class BinarySearch {
    public static void main(String[] args) {
        int[]arr = {1,2,3,4,5,6,7,8,9,10};
        System.out.println(function(arr, 11));
    }
    public static int function(int[]arr, int num){
        int min = 0;
        int max = arr.length - 1;
        while(true){
            if(min > max){
                System.out.println("不存在");
                return -1;
            }
            int mid = (min + max) / 2;
            if(arr[mid] == num){
                return mid;
            }else if(arr[mid] < num){
                min = mid + 1;
            }else if(arr[mid] > num){
                max = mid - 1;
            }
        }
    }
    public static int functionpro(int[]arr, int num){
        int min = 0;
        int max = arr.length - 1;
        while(true){
            if(min > max){
                System.out.println("不存在");
                return -1;
            }
            //对mid的计算方法进行优化，使其在面对比较均匀分布的数据时可以更有效率，也称作插值查找
            //还可以利用黄金分割点计算mid，这样就叫做斐波那契查找
            int mid = min + ((num - arr[min])/(arr[max] - arr[min])) * (max - min);
            if(arr[mid] == num){
                return mid;
            }else if(arr[mid] < num){
                min = mid + 1;
            }else if(arr[mid] > num){
                max = mid - 1;
            }
        }
    }
}
