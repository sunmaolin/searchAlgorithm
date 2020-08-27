package sml;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class SearchTest {
    public static void main(String[] args) {
        int[] array = {0,0,0,0,1,2,3,4,5,6,7};
        int index;
        //测试线性查找
//        index = SeqSearch.seqSearch(array,0);
        //测试基础二分查找
//        index = BinarySerach.binarySearch(array,0,array.length-1,-1);
        //测试优化二分查找，查找所有的相同值的下标
//        List<Integer> indexList = BinarySerach.binarySearch2(array,0,array.length-1,0);
//        System.out.println(indexList.size());
        //测试插值查找算法
//        index = InsertValueSearch.insertValueSearch(array,0,array.length-1,1);
        //测试斐波那契查找
        index = FibonacciSearch.fibonacciSearch(array,4);
        System.out.println(index);

        String a = "0.8";
        System.out.println(Float.parseFloat(a));
    }
}
