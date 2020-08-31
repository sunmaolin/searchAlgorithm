package sml.tree.huffmanTree;

import java.util.ArrayList;
import java.util.Collections;

public class HuffmanTree {
    public static void main(String[] args) {
        int[] arr = {13,7,8,3,29,6,1};
        Node root = createHuffmanTree(arr);
        preOrder(root);
    }

    //编写一个前序遍历的方法
    public static void preOrder(Node root){
        if(root != null){
            root.preOrder();
        }
    }

    //创建赫夫曼树的方法
    public static Node createHuffmanTree(int[] arr){
        ArrayList<Node> nodes = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            nodes.add(new Node(arr[i]));
        }

        //我们处理的过程是一个新的二叉树
        while(nodes.size()>1){
            Collections.sort(nodes);//排序从小到大
            //第一次处理之前
//            System.out.println(nodes);

            //取出根节点最小的权值最小的俩颗二叉树
            //1）取出权值最小的节点（二叉树）
            Node left = nodes.get(0);
            //2)取出权值第二小的节点（二叉树）
            Node right = nodes.get(1);

            //3)创建一颗新的二叉树
            Node parent = new Node(left.value + right.value);
            parent.left = left;
            parent.right = right;

            //4)从ArrayList种删除处理过的二叉树
            nodes.remove(right);
            nodes.remove(left);

            //5)将parent加入到nodes
            nodes.add(parent);
            //第一次处理之后
//            System.out.println(nodes);
        }
        //返回赫夫曼树的root节点
        return nodes.get(0);
    }
}



//创建节点类
//为了让Node对象支持排序Collections集合排序
//让Node实现compareble接口
class Node implements Comparable<Node>{
    int value;//节点权值
    Node left;//指向左子节点
    Node right;//指向右子节点

    //写一个前序遍历
    public void preOrder(){
        System.out.println(this);
        if(this.left != null){
            this.left.preOrder();
        }
        if(this.right != null){
            this.right.preOrder();
        }
    }

    public Node(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }

    @Override
    public int compareTo(Node o) {
        //表示从小到大进行排序
        return this.value - o.value;
    }
}