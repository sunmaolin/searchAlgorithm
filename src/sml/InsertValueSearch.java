package sml;

/**
 * 插值查找算法（二分查找的优化）
 * 适合条件，数据量比较大，值分布均匀，查找较快，值分布不均匀的情况下使用二分
 */
public class InsertValueSearch {
    //原先二分查找是的中值是(left+right)/2
    //进行优化，其实就是一个百分比 如 {1，3，5，7，9} 查找5 就是 (5-1)/(9-1)=1/2 也就是数组的一半位置
    //算法就是 (findValue-arr[left])(arr[right]-arr[left]) 这样就是一个中值下标所占的百分比
    //上面的百分比  * (right-left) 就是第几个数，当然我们只能最后需要加上最左边的下标 +left
    //=> left+(findValue-arr[left])(arr[right]-arr[left]) * (right-left)
    public static int insertValueSearch(int[] array,int left,int right,int findValue){
        if(left>right || findValue < array[left] || findValue > array[right]){
            return -1;
        }
        int mid = left + (findValue-array[left])/(array[right]-array[left])*(right-left);

        if(findValue > array[mid]){
            return insertValueSearch(array,mid+1,right,findValue);
        }else if (findValue < array[mid]){
            return insertValueSearch(array,left,mid-1,findValue);
        }else{
            return mid;
        }

    }
}
