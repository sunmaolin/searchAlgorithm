package sml;

/**
 * 二分查找（折半查找）
 * 数组必须是有序的
 */
public class BinarySerach {
    public static int binarySearch(int[] array,int left,int right,int findValue){
        //没有找到的情况就是
        if(left > right){
            return -1;
        }

        int midIndex = (left+right)/2;
        if(findValue > array[midIndex]){
            return binarySearch(array,midIndex+1,right,findValue);
        }else if(findValue < array[midIndex]){
            return binarySearch(array,left,midIndex-1,findValue);
        }else{
            return midIndex;
        }
    }
}
