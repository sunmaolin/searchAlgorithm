package sml;

import java.util.ArrayList;
import java.util.List;

/**
 * 二分查找（折半查找）
 * 数组必须是有序的
 */
public class BinarySerach {
    /**
     * 只能查找出第一个值（普通版）
     * @param array 需要查找的数组
     * @param left 左边下标
     * @param right 右边下标
     * @param findValue 查找的值
     * @return
     */
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

    /**
     * 上一个方法的优化，查找所有相同值的下标
     * @param array
     * @param left
     * @param right
     * @param findValue
     * @return
     */
    public static List<Integer> binarySearch2(int[] array,int left,int right,int findValue){
        if(left > right){
            return new ArrayList<>();
        }
        int mid = (left + right)/2;
        if(findValue > array[mid]){
            return binarySearch2(array,mid+1,right,findValue);
        }else if (findValue < array[mid]){
            return binarySearch2(array,left,mid-1,findValue);
        }else{
            List<Integer> indexList = new ArrayList<>();
            indexList.add(mid);//先将第一次查找的数放进数组中
            //因为是有序数组，往左边，右边查相同的数，添加进下标集合
            int temp = mid + 1;
            while(true){
                if(temp > right || array[temp] != findValue){
                    break;
                }
                indexList.add(temp++);
            }
            temp = mid - 1;
            while (true){
                if(temp < left || array[temp] != findValue){
                    break;
                }
                indexList.add(temp--);
            }
            return indexList;
        }
    }
}
