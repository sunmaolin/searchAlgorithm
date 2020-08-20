package sml;

/**
 * 顺序查找（线性查找），逐一比对
 */
public class SeqSearch {
    public static int seqSearch(int[] array,int value){
        //逐一查找，发现相同的就返回下标，这里查找的是第一个相同的值
        for (int i = 0; i < array.length; i++) {
            if(array[i] == value){
                return i;
            }
        }
        return -1;
    }
}
