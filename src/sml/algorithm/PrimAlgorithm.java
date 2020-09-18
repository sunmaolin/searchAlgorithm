package sml.algorithm;

import java.util.Arrays;

public class PrimAlgorithm {
    public static void main(String[] args) {
        //测试看看图是否创建ok
        char[] data = {'A','B','C','D','E','F','G'};
        int verxs = data.length;
        //邻接矩阵的关系使用二维数组描述一下,10000表示不连通
        int[][] weight = {
                {10000,5,7,10000,10000,10000,2},
                {5,10000,10000,9,10000,10000,3},
                {7,10000,10000,10000,8,10000,10000},
                {10000,9,10000,10000,10000,4,10000},
                {10000,10000,8,10000,10000,5,4},
                {10000,10000,10000,4,5,10000,6},
                {2,3,10000,4,6,10000,2}
        };
        //创建MGraph的对象
        MGraph mGraph = new MGraph(verxs);
        //创建最小生成树对象
        MinTree minTree = new MinTree();
        minTree.createGraph(mGraph,verxs,data,weight);
        minTree.showGraph(mGraph);
        minTree.prim(mGraph,1);
    }
}

//创建最小生成树
class MinTree {
    /**
     * 创建图的邻接矩阵
     * @param mGraph 图对象
     * @param verxs 图对应的顶点个数
     * @param data 图的各个顶点的值
     * @param weight 图的邻接矩阵
     */
    public void createGraph(MGraph mGraph,int verxs,char[] data,int[][] weight){
        int i,j;
        for (i = 0; i < verxs; i++) {//顶点
            mGraph.data[i] = data[i];
            for (j = 0; j < verxs; j++) {
                mGraph.weight[i][j] = weight[i][j];
            }
        }
    }

    /**
     * 打印出图对应的邻接矩阵
     */
    public void showGraph(MGraph mGraph){
        for(int[] link : mGraph.weight){
            System.out.println(Arrays.toString(link));
        }
    }

    /**
     * 编写prim算法，得到最小生成树
     * @param graph
     * @param v 表示从图的第几个节点开始生成'A'->0 'B'->0，不管从哪开始，最后结果都是25
     */
    public void prim(MGraph graph,int v){
        //表示节点是否已访问，默认0
        int[] visited = new int[graph.verxs];
        //把当前节点标记为已访问
        visited[v] = 1;
        //h1 h2记录俩个顶点的下标
        int h1 = -1;
        int h2 = -1;
        int minWeight = 10000;//先初始化成一个较大数
        //prim算法生成后会生成n-1条边，n代表节点数
        for (int k = 1; k < graph.verxs; k++) {
            //这个是确定每一次生成子图和哪个节点的距离最近
            for (int i = 0; i < graph.verxs; i++) {// i节点表示被访问过的节点
                for (int j = 0; j < graph.verxs; j++) {//j节点表示还没有访问过的节点
                    if(visited[i] == 1 && visited[j] ==0 && graph.weight[i][j] < minWeight){
                        //寻找已经访问过的节点和未访问过的节点间的权值最小的边
                        minWeight = graph.weight[i][j];
                        h1 = i;
                        h2 = j;
                    }
                }
            }
            visited[h2] = 1;
            System.out.println(graph.data[h1]+"->"+graph.data[h2]+"  权值:"+graph.weight[h1][h2]);
            minWeight = 10000;//重新设置成最大值
        }
    }
}

class MGraph {
    int verxs;//表示图的节点个数
    char[] data;//存放节点数据
    int[][] weight;//存放边，就是我们的邻接矩阵

    public MGraph(int verxs) {
        this.verxs = verxs;
        data = new char[verxs];
        weight = new int[verxs][verxs];
    }
}
