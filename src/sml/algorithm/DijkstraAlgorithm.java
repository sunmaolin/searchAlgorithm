package sml.algorithm;

import java.util.Arrays;

/**
 * 迪杰斯特拉算法
 * 经典案例：求最短路径
 */
public class DijkstraAlgorithm {
    public static void main(String[] args) {
        char[] vertex = {'A','B','C','D','E','F','G'};
        int[][] matrix = new int[vertex.length][vertex.length];
        final int N = 65535;//表示不可链接
        matrix[0] = new int[]{N,5,7,N,N,N,2};
        matrix[1] = new int[]{5,N,N,9,N,N,3};
        matrix[2] = new int[]{7,N,N,N,8,N,N};
        matrix[3] = new int[]{N,9,N,N,N,4,N};
        matrix[4] = new int[]{N,N,8,N,N,5,4};
        matrix[5] = new int[]{N,N,N,4,5,N,6};
        matrix[6] = new int[]{2,3,N,N,4,6,N};
        //创建Graph对象
        Graph graph = new Graph(vertex,matrix);
        graph.dsj(6);
        graph.showDijkstra();
    }
}

class Graph {
    private char[] vertex;//存放顶点数组
    private int[][] matrix;//邻接矩阵
    private VisitedVertex vv;//已经访问的顶点的集合

    public Graph(char[] vertex, int[][] matrix) {
        this.vertex = vertex;
        this.matrix = matrix;
    }

    /**
     * 展示图
     */
    public void showGraph() {
        for(int[] link:matrix){
            System.out.println(Arrays.toString(link));
        }
    }

    /**
     * 迪杰斯特拉算法实现
     * @param index 出发顶点的下标
     */
    public void dsj(int index){
        vv = new VisitedVertex(vertex.length,index);
        update(index);//更新index下标顶点到周围顶点的距离和前驱顶点
        for (int i = 1; i < vertex.length; i++) {
            index = vv.updateArr();//选择并返回新的访问顶点
            update(index);
        }
    }

    /**
     * 更新index下标顶点到周围顶点的距离和周围顶点的前驱顶点
     */
    private void update(int index){
        int len = 0;
        //根据遍历我们的邻接矩阵 matrix[index]行
        for (int i = 0; i < vertex.length; i++) {
            //出发顶点到index顶点的距离+从index顶点到i顶点的距离
            len = vv.getDis(index) + matrix[index][i];
            //如果i顶点没有被访问过，并且len小于出发顶点到j顶点的距离就需要更新
            if(!vv.in(i) && len < vv.getDis(i)){
                vv.updatePre(i,index);//更新i顶点的前驱为index节点
                vv.updateDis(i,len);//更新出发顶点到j顶点的距离
            }
        }
    }

    public void showDijkstra(){
        vv.show();
    }
}

//已访问顶点集合
class VisitedVertex {
    //记录各个顶点是否访问过 1：访问过 0未访问过，会动态更新
    public int[] already_arr;
    //每个下标对应的值为前一个顶点下标，会动态更新
    public int[] pre_visited;
    //记录出发顶点到其他所有顶点的距离，比如G为出发顶点，就会记录G到其他顶点的距离，会动态更新
    public int[] dis;

    /**
     * @param length 顶点的个数
     * @param index 出发顶点
     */
    public VisitedVertex(int length,int index){
        this.already_arr = new int[length];
        this.pre_visited = new int[length];
        this.dis = new int[length];
        //初始化dis数组
        Arrays.fill(dis,65535);
        //设置出发顶点的访问距离为0，自己访问自己的距离
        this.dis[index] = 0;
        this.already_arr[index] = 1;
    }

    /**
     * 判断index顶点是否被访问过
     * @param index
     * @return
     */
    public boolean in(int index){
        return already_arr[index] == 1;
    }

    /**
     * 更新出发节点到index节点的距离
     * @param index
     * @param len
     */
    public void updateDis(int index,int len){
        dis[index] = len;
    }

    /**
     * 更新pre顶点的前驱顶点为index的节点
     * @param pre
     * @param index
     */
    public void updatePre(int pre,int index){
        pre_visited[pre] = index;
    }

    /**
     * 返回出发顶点到index顶点的距离
     * @param index
     */
    public int getDis(int index){
        return dis[index];
    }

    /**
     * 继续选择并返回新的访问节点，比如这里的G完成后，就是A作为新的访问顶点，注意不是出发顶点
     */
    public int updateArr(){
        int min = 65535;
        int index = 0;
        for (int i = 0; i < already_arr.length; i++) {
            if(already_arr[i] == 0 && dis[i] < min){
                min = dis[i];
                index = i;
            }
        }
        //更新index顶点被访问过
        already_arr[index] = 1;
        return index;
    }

    /**
     * 将三个数组的情况输出
     */
    public void show(){
        System.out.println("=================");
        for (int i : already_arr) {
            System.out.print( i + " ");
        }
        System.out.println();
        for (int i : pre_visited) {
            System.out.print( i + " ");
        }
        System.out.println();
        for (int i : dis) {
            System.out.print( i + " ");
        }
    }
}
