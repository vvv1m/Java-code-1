package my_commonalgo.sort;

public class Test {
    public static void main(String[] args) {
        int[]arr = {2,3,4,5,1};
        selectSort(arr);
        int[]arr1 = {1,24,56,342,53,5,32,4,99,4,5,2,4,2,4,5,4,6,64,534,33,5};
        //insertSort(arr1);
        printArr(arr1);
        quickSort(arr1, 0, arr1.length - 1);
        printArr(arr1);
    }


    public static void selectSort(int[]arr){
        //选择排序，从第一个开始，将其与他后面的元素依次比较，选出最小的放在该位置。
        for(int i = 0; i < arr.length - 1; i++){
            for(int j = i + 1; j <arr.length; j++){
                if(arr[i] > arr[j]){
                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp; 
                }
            }
            printArr(arr);
        }
    }


    public static void insertSort(int[]arr){
        //插入排序将0-N的元素看做有序，将N+1到length的元素视作无序，遍历无序数据，将得到的元素插入有序数列如遇相同数据
        //插在后面即可
        int startindex = -1;
        for(int i = 0; i < arr.length - 1; i++){
            if(arr[i] > arr[i + 1]){
                startindex = i + 1;
                break;
            }
        }
        for(int i = startindex; i < arr.length; i++){
            int j = i;
            while(j > 0 && arr[j] < arr[j - 1]){
                int temp = arr[j];
                arr[j] = arr[j - 1];
                arr[j - 1] = temp;
                j--;
            }
        }
    }

    public static void quickSort(int[]arr, int i, int j){
        int start = i;
        int end = j;
        if(start > end){
            return;
        }
        //这句话一定要放在判断条件之后，不然无法正常运行
        int basenum = arr[i];
        while(start != end){
            while(true){
                if(end <= start || arr[end] < basenum){
                    break;
                }
                end--;
            }
            while(true){
                if(end <= start || arr[start] > basenum){
                    break;
                }
                start++; 
            }
            int temp = arr[start];
            arr[start] = arr[end];
            arr[end] = temp;
        }
        int temp = arr[i];
        arr[i] = arr[start];
        arr[start] = temp;
        quickSort(arr, i, start - 1);
        quickSort(arr, start + 1, j);
    }


    //打印方法
    public static void printArr(int[]arr){
        for(int i = 0; i < arr.length; i++){
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
}
