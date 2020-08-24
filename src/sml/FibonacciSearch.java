package sml;

import java.util.Arrays;

/**
 * 斐波那契（黄金分割法） 也是二分查找的优化
 * 斐波那契数列 {1，1，2，3，5，8，13} 1+1=2 1+2=3 2+3=5这样的一个数列，前后比值无限接近于黄金分割。
 * 上面的函数就是 f(k)=f(k-1)+f(k-2) => f(k)-1=(f(k-1)-1)+(f(k-2)-1)+1
 * 就是分割成俩段长度，中间的值当作mid
 */
public class FibonacciSearch {
    private static final int MAX_SIZE = 20;

    /**
     * 创建一个斐波那契数组
     * @return
     */
    private static int[] fib(){
        int[] f = new int[MAX_SIZE];
        f[0] = 1;
        f[1] = 1;
        for (int i = 2; i < MAX_SIZE; i++) {
            f[i] = f[i-1] + f[i-2];
        }
        return f;
    }

    public static int fibonacciSearch(int[] array,int findValue){
        int low = 0;
        int k = 0;//表示斐波那契分割数值的下标
        int high = array.length-1;
        int mid = 0;//中值下标
        int[] f = fib();
        //获取到斐波那契分割数值的下标
        while(high > f[k] - 1){//为什么为f[k]-1看上面推导
            k++;
        }
        int[] temp = Arrays.copyOf(array,f[k]);//制造成一个斐波那契的长度，不够补0
        //将补的0换成最大的数
        for (int i = high+1; i < f[k]; i++) {
            temp[i] = array[high];
        }
        while(low < high){//满足这个条件就可以找
            mid = low + f[k-2] -1;//这里我觉得k-1或者-2都行
            if(findValue < array[mid]){
                high = mid - 1;
                k--;
            }else if(findValue > array[mid]){
                low = mid + 1;
                k-=2;
            }else{
                //需要确定返回哪个下标
                if(mid <= high){
                    return mid;
                }else{
                    return high;
                }
            }
        }
        return -1;
    }
}
