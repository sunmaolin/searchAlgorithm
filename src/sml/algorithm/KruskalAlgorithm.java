package sml.algorithm;

import java.util.Arrays;

/**
 * 克鲁斯卡尔算法
 * 原理：求最小生成树
 */
public class KruskalAlgorithm {

    private int edgeNum;//边的个数
    private char[] vertexs;//顶点数组
    private int[][] matrix;//邻接矩阵
    //使用INF来表示俩个顶点不能联通
    private static final int INF = Integer.MAX_VALUE;

    public static void main(String[] args) {
        char[] vertexs = {'A','B','C','D','E','F','G'};
        int[][] matrix = {
                {0,12,INF,INF,INF,16,14},
                {12,0,10,INF,INF,7,INF},
                {INF,10,0,3,5,6,INF},
                {INF,INF,3,0,4,INF,INF},
                {INF,INF,5,4,0,2,8},
                {16,7,6,INF,2,0,9},
                {14,INF,INF,INF,8,9,0}
        };
        //创建克鲁斯卡尔对象的实例
        KruskalAlgorithm kruskalAlgorithm = new KruskalAlgorithm(vertexs,matrix);
        kruskalAlgorithm.print();

//        EData[] eData = kruskalAlgorithm.getEdges();
//        //未排序
//        System.out.println(Arrays.toString(eData));
//        //排序后
//        kruskalAlgorithm.sortEdge(eData);
//        System.out.println(Arrays.toString(eData));

        kruskalAlgorithm.kruskal();
    }

    //构造器
    public KruskalAlgorithm(char[] vertexs, int[][] matrix) {
        //初始化顶点数和边的个数
        int vlen = vertexs.length;
        //初始化顶点，复制拷贝的方式，也可以this.xx=xx;
        this.vertexs = new char[vlen];
        for (int i = 0; i < vlen; i++) {
            this.vertexs[i] = vertexs[i];
        }
        //初始化边
        this.matrix = new int[vlen][vlen];
        for (int i = 0; i < vlen; i++) {
            for (int j = 0; j < vlen; j++) {
                this.matrix[i][j] = matrix[i][j];
            }
        }
        //统计边
        for (int i = 0; i < vlen; i++) {
            for (int j = i+1; j < vlen; j++) {
                //代表边可以联通，有效
                if(matrix[i][j] != INF){
                    edgeNum++;
                }
            }
        }
    }

    public void kruskal(){
        int index = 0;//表示最后结果数组的索引
        int[] ends = new int[edgeNum];//用于保存已有最小生成树中的每个顶点在最小生成树中的终点
        //创建结果数组，保存最后的最小生成树
        EData[] rets = new EData[edgeNum];
        //获取图中所有边的集合
        EData[] edges = getEdges();
        //按照边的权值大小进行排序
        sortEdge(edges);
        //遍历edges数组,将边添加到最小生成树中时，判断准备加入的边是否形成回路，若回路，不可
        for (int i = 0; i < edges.length; i++) {
            //获取到第i条边的第一个顶点（起点）
            int p1 = getPosition(edges[i].start);
            //获取到第i条边的第二个顶点
            int p2 = getPosition(edges[i].end);
            //获取p1这个顶点它在已有的最小生成树中的终点是哪一个
            int m = getEnd(ends,p1);
            //获取p2这个顶点它在已有的最小生成树中的终点是哪一个
            int n = getEnd(ends,p2);
            //判断是否构成回路
            if(m != n){
                //[0,0,0,0,0]没加入之前，也就是0的时候，终点就是本身
                ends[m] = n;//设置m在已有最小生成树中的终点
                rets[index++] = edges[i];//边加入
            }
        }

        //统计并打印最小生成树
        System.out.println("最小生成树为："+ Arrays.toString(rets));
        for (int i = 0; i < index; i++) {
            System.out.println(rets[i]);
        }

    }

    //打印邻接矩阵
    public void print(){
        System.out.println("邻接矩阵为：\n");
        for (int i = 0; i < vertexs.length; i++) {
            for (int j = 0; j < vertexs.length; j++) {
                System.out.printf("%12d",matrix[i][j]);
            }
            System.out.println();
        }
    }

    //对边进行排序处理，冒泡
    private void sortEdge(EData[] edges){
        for (int i = 0; i < edges.length - 1; i++) {
            for (int j = 0; j < edges.length - 1 - i; j++) {
                if(edges[j].weight > edges[j+1].weight){
                    EData temp = edges[j];
                    edges[j] = edges[j+1];
                    edges[j+1] = temp;
                }
            }
        }
    }

    /**
     *
     * @param ch 顶点的值，比如'A','B'
     * @return 返回ch顶点对应的下标，如果找不到，返回-1
     */
    private int getPosition(char ch){
        for (int i = 0; i < vertexs.length; i++) {
            if(vertexs[i] == ch){
                return i;
            }
        }
        return -1;
    }

    /**
     * 获取图中的边，放到EData[]中，后面需要遍历该数组
     * 是通过matrix 邻接矩阵来获取
     * EData[] 形式 [['A','B',2],['B','F',7]]
     * @return
     */
    private EData[] getEdges(){
        int index = 0;
        EData[] eData = new EData[edgeNum];
        for (int i = 0; i < vertexs.length; i++) {
            //+1是因为自己跟自己没有必要
            for (int j = i+1; j < vertexs.length; j++) {
                if(matrix[i][j] != INF){
                    eData[index++] = new EData(vertexs[i],vertexs[j],matrix[i][j]);
                }
            }
        }
        return eData;
    }

    /**
     * 获取下标为i顶点的终点，用于后面判断俩个顶点的终点是否相同
     * @param ends 记录了各个顶点对应的终点是哪个，在遍历过程中，逐步形成的
     * @param i 表示传入的顶点对应的下标
     * @return 返回的就是下标为i的顶点对应的终点下标
     */
    private int getEnd(int[] ends,int i){
        while (ends[i] != 0){
            i = ends[i];
        }
        return i;
    }

}

//创建一个类EData,他的对象实例就表示一条边
class EData {
    char start;//边的一个点
    char end;//边的另一个点
    int weight;//边的权值

    public EData(char start, char end, int weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "EData{"  + start + "->" + end + "=" + weight +
                '}';
    }
}


