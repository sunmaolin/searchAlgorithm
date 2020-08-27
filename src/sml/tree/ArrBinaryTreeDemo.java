package sml.tree;

public class ArrBinaryTreeDemo {
    public static void main(String[] args) {
        int[] arr = {1,2,3,4,5,6,7};
        ArrBinaryTree arrBinaryTree = new ArrBinaryTree(arr);
        arrBinaryTree.preOrder();
    }
}

//编写一个ArrayBinaryTree，实现顺序存储二叉树遍历（通常是完全二叉树）
class ArrBinaryTree{
    private int[] arr;//存储数据节点的数组

    public ArrBinaryTree(int[] arr) {
        this.arr = arr;
    }

    public void preOrder(){
        this.preOrder(0);
    }

    //编写一个方法，完成顺序存储二叉树的前序遍历
    //index 表示数组的下标
    public void preOrder(int index){
        //如果数组为空，或者arr.len = 0
        if(arr == null || arr.length == 0){
            System.out.println("数组为空");
        }
        //输出当前元素
        System.out.println(arr[index]);
        //向左递归遍历
        if(2*index+1<arr.length){
            preOrder(2 * index + 1);
        }
        //向右递归遍历
        if(2*index+2<arr.length){
            preOrder(2 * index + 2);
        }
    }

    public void infixOrder(int index){
        //如果数组为空，或者arr.len = 0
        if(arr == null || arr.length == 0){
            System.out.println("数组为空");
        }
        if(2*index+1<arr.length){
            infixOrder(2*index+1);
        }
        System.out.println(arr[index]);
        if(2*index+2<arr.length){
            infixOrder(2*index+2);
        }
    }

    public void postOrder(int index){
        //如果数组为空，或者arr.len = 0
        if(arr == null || arr.length == 0){
            System.out.println("数组为空");
        }
        if(2*index+2<arr.length){
            postOrder(2*index+2);
        }
        if(2*index+1<arr.length){
            postOrder(2*index+1);
        }
        System.out.println(arr[index]);
    }
}
