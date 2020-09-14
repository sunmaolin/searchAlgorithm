package sml.algorithm;

/**
 * 分治算法
 * 经典案例：汉诺塔
 * 思想：拆成小的细节，然后最后进行合并.
 * 这个问题是无论多少个都当成俩个去处理，俩个的逻辑很简单，那么如果是三个的时候，
 * 其实就是调用一下方法，在执行一遍，本来是A-》C，如果是三个，我们需要把前俩个移动到
 * B先，也就是把B当作C柱。那么就是调用方法的时候柱子顺序的问题了。
 */
public class Hanoitower {
    public static void main(String[] args) {
        //测试三个，从A移到C
        hanoiTower(3,'A','B','C');
    }

    //汉诺塔的移动的方法
    //使用分治算法
    public static void hanoiTower(int num,char a,char b,char c){
        //如果只有一个盘
        if(num == 1){
            System.out.println("第1个盘从 " + a + "->" + c);
        }else{
            hanoiTower(num-1,a,c,b);
            System.out.println("第"+num+"个盘从 " + a + "->" + c);
            hanoiTower(num-1,b,a,c);
        }
    }
}
