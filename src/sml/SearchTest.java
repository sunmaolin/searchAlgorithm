package sml;

public class SearchTest {
    public static void main(String[] args) {
        int[] array = {0,1,2,3,4,5,6,7};
        int index;
        //测试线性查找
//        index = SeqSearch.seqSearch(array,0);
        //测试基础二分查找
        index = BinarySerach.binarySearch(array,0,array.length-1,-1);
        System.out.println(index);
    }
}
