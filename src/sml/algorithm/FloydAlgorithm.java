package sml.algorithm;

import java.util.Arrays;

/**
 * 弗洛伊德算法
 * 经典案例：最短路径，求的是各个顶点到各个顶点的最短路径
 */
public class FloydAlgorithm {
    public static void main(String[] args) {
        char[] vertex = {'A','B','C','D','E','F','G'};
        int[][] matrix = new int[vertex.length][vertex.length];
        final int N = 65535;//表示不可链接
        matrix[0] = new int[]{0,5,7,N,N,N,2};
        matrix[1] = new int[]{5,0,N,9,N,N,3};
        matrix[2] = new int[]{7,N,0,N,8,N,N};
        matrix[3] = new int[]{N,9,N,0,N,4,N};
        matrix[4] = new int[]{N,N,8,N,0,5,4};
        matrix[5] = new int[]{N,N,N,4,5,0,6};
        matrix[6] = new int[]{2,3,N,N,4,6,0};
        Graphs graphs = new Graphs(vertex.length,matrix,vertex);
        graphs.floyd();
        graphs.show();
    }
}

//创建图
class Graphs {
    private char[] vertex;//存放顶点的数组
    private int[][] dis;//保存从各个顶点出发到其他顶点的距离，最后的结果也是保留在该数组
    private int[][] pre;//保存到达目标顶点的前驱顶点

    /**
     * 构造器
     * @param length 大小
     * @param matrix 邻接矩阵
     * @param vertex 顶点数组
     */
    public Graphs(int length,int[][] matrix,char[] vertex){
         this.vertex = vertex;
         this.dis = matrix;
         this.pre = new int[length][length];
         //pre数组初始化
        for (int i = 0; i < pre.length; i++) {
            Arrays.fill(pre[i],i);
        }
    }

    //显示pre数组和dis数组
    public void show(){
        System.out.println("dis数组（距离）");
        for (int i = 0; i < vertex.length; i++) {
            System.out.println(Arrays.toString(dis[i]));
        }
        System.out.println("pre数组（前驱顶点）");
        for (int i = 0; i < vertex.length; i++) {
            System.out.println(Arrays.toString(pre[i]));
        }
    }

    //弗洛伊德算法
    public void floyd(){
        int len = 0;//变量保存距离
        //对中间顶点的遍历，k就是中间顶点的下标
        for (int k = 0; k < dis.length; k++) {
            //从i顶点开始出发
            for (int i = 0; i < dis.length; i++) {
                //到达的终点
                for (int j = 0; j < dis.length; j++) {
                    //求出从i顶点出发经过k到达j顶点的距离
                    len = dis[i][k] + dis[k][j];
                    if(len < dis[i][j]){
                        dis[i][j] = len;
                        pre[i][j] = pre[k][j];
                    }
                }
            }
        }
    }


}
