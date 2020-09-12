package sml.algorithm;

/**
 * 二分查找，非递归
 */
public class BinarySearchNoRecursion {
    public static void main(String[] args) {
        int[] arr = {1,2,3,4,5,6,7};
//        int index = binarySearch(arr,1);
        int index = binarySearch(arr,8);
        System.out.println(index);
    }

    /**
     * 二分查找的实现
     * @param arr 查找的数组
     * @param target 查找的树
     * @return 找到的下标
     */
    public static int binarySearch(int[] arr,int target){
        int left = 0;
        int right = arr.length-1;
        while(left <= right){
            int mid = (left+right)/2;
            if(arr[mid] == target){
                return mid;
            }else if(arr[mid] < target){
                left = mid + 1;
            }else if(arr[mid] > target){
                right = mid - 1;
            }
        }
        return -1;
    }
}

