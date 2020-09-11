package sml.graph;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * 图
 */
public class Graph {

    private ArrayList<String> vertexList;//存储顶点集合
    private int[][] edges;//存储图对应的邻接矩阵
    private int numOfEdges;//表示边的数目
    //定义一个数组boolean[] 记录某个节点是否被访问
    private boolean[] isVisited;

    public static void main(String[] args) {
        //测试图是否创建成功
        String[] verTexValue = {"A","B","C","D","E"};
        Graph graph = new Graph(verTexValue.length);
        //循环的添加顶点
        for (String value : verTexValue){
            graph.insertVertex(value);
        }
        //添加边
        //A-B A-C B-C B-D B-E
        graph.insertEdge(0,1,1);
        graph.insertEdge(0,2,1);
        graph.insertEdge(1,2,1);
        graph.insertEdge(1,3,1);
        graph.insertEdge(1,4,1);
        graph.showGraph();

        //测试深度优先遍历
//        graph.dfs();//这里重载遍历每一个节点，防止某节点谁也没联通
        //测试广度优先
        graph.bfs();
    }

    public Graph(int vertexLength) {
        //初始化矩阵和vertexList
        edges = new int[vertexLength][vertexLength];
        isVisited = new boolean[vertexLength];
        vertexList = new ArrayList<String>(vertexLength);
        numOfEdges = 0;//默认
    }

    /**
     * 得到第一个邻接节点的下标w
     * @param index
     * @return 如果存在就返回对应的下标，否则返回-1
     */
    public int getFirstNeighbor(int index){
        for (int i = 0; i < vertexList.size(); i++) {
            if(edges[index][i] > 0){
                return i;
            }
        }
        return -1;
    }

    /**
     * 根据前一个邻接节点的下标来获取下一个临界节点
     * @param v1
     * @param v2
     * @return
     */
    public int getNextNeighbor(int v1,int v2){
        for (int i = v2 + 1; i<vertexList.size(); i++){
            if(edges[v1][i] > 0){
                return i;
            }
        }
        return -1;
    }

    //深度优先遍历算法
    //i 第一次就是0
    private void dfs(boolean[] isVisited,int i){
        //首先访问该节点，输出
        System.out.print(getValueByIndex(i) + "->");
        //将该节点设置为已经访问过
        isVisited[i] = true;
        //查找节点i的第一个邻接点w
        int w = getFirstNeighbor(i);
        while(w != -1){
            if(!isVisited[w]){
                dfs(isVisited,w);
            }
            //如果w节点已经被访问过
            w = getNextNeighbor(i,w);
        }
    }

    public void dfs(){
        for (int i = 0; i < getNumOfVertex(); i++) {
            if(!isVisited[i]){
                dfs(isVisited,i);
            }
        }
    }

    //对一个节点进行广度优先遍历的方法
    private void bfs(boolean[] isVisited,int i){
        int u;//表示队列的头节点对应下标
        int w;//邻接点w
        //队列，记录节点访问的顺序
        LinkedList queue = new LinkedList();
        //访问节点输出节点信息
        System.out.print(getValueByIndex(i) + "=>");
        isVisited[i] = true;
        //将节点加入队列
        queue.addLast(i);
        while(!queue.isEmpty()){
            //取出队列的头节点下标
            u = (int)queue.removeFirst();
            //得到第一个邻接点的下标w
            w = getFirstNeighbor(u);
            while(w != -1){
                //是否访问过
                if(!isVisited[w]){
                    System.out.print(getValueByIndex(w)+"=>");
                    //标记以访问
                    isVisited[w] = true;
                    //入队列
                    queue.addLast(w);
                }
                w = getNextNeighbor(u,w);
            }
        }

    }

    //遍历所有的节点，都进行广度优先搜索
    public void bfs(){
        for (int i = 0; i < getNumOfVertex(); i++) {
            if(!isVisited[i]){
                bfs(isVisited,i);
            }
        }
    }

    //图中常用的方法
    //显示图对应的矩阵
    public void showGraph(){
        for (int i = 0; i < edges.length; i++) {
            for (int j = 0; j < edges[i].length; j++) {
                System.out.print(edges[i][j]+" ");
            }
            System.out.println();
        }
    }
    //返回节点的个数
    public int getNumOfVertex(){
        return vertexList.size();
    }
    //得到边的数目
    public int getNumOfEdges(){
        return numOfEdges;
    }
    //返回节点下标对应的数据
    public String getValueByIndex(int i){
        return vertexList.get(i);
    }
    //返回v1，v2的权值
    public int getWeight(int v1,int v2){
        return edges[v1][v1];
    }

    //插入节点
    public void insertVertex(String vertex){
        vertexList.add(vertex);
    }
    /**
     * 添加边
     * @param v1 表示点的下标
     * @param v2
     * @param weight 权值
     */
    public void insertEdge(int v1,int v2,int weight){
        edges[v1][v2] = weight;
        edges[v2][v1] = weight;
        numOfEdges++;
    }
}
