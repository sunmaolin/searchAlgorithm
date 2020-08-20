package sml;

/**
 * 顺序查找（线性查找），逐一比对
 */
public class SeqSearch {
    public static void main(String[] args) {
        int[] array = {1,4,56,21,41,-25};
        int index = seqSearch(array,21);
        if(index == -1){
            System.out.println("没有找到！");
        }else{
            System.out.println("下标为："+index);
        }
    }

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
