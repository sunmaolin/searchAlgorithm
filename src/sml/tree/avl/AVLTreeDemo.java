package sml.tree.avl;

/**
 * AVL平衡二叉树，算是对顺序二叉树的一个改造
 * 添加的时候进行旋转
 */
public class AVLTreeDemo {
    public static void main(String[] args) {
        int[] arr = {4,3,6,5,7,8};
        //创建一个AVLTree对象
        AVLTree avlTree = new AVLTree();
        //添加节点
        for (int i = 0; i < arr.length; i++) {
            avlTree.add(new Node(arr[i]));
        }
        avlTree.infixOrder();
        System.out.println("在没有做平衡处理之前");
        System.out.println("树的高度"+avlTree.getRoot().height());
        System.out.println("左子树的高度"+avlTree.getRoot().leftHeight());
        System.out.println("右子树的高度"+avlTree.getRoot().rightHeight());
        System.out.println("当前的根节点="+avlTree.getRoot().value);
        System.out.println("根节点的左子节点+"+avlTree.getRoot().left.value);


    }
}

//创建AVLTree
class AVLTree{
    private Node root;

    public Node getRoot() {
        return root;
    }

    //查找要删除的节点
    public Node search(int value){
        if(root == null){
            return null;
        }else{
            return root.search(value);
        }
    }
    //查找父节点
    public Node searchParent(int value){
        if(root == null){
            return null;
        }else{
            return root.searchParent(value);
        }
    }

    //返回右子树的最小节点的值，同时完成最小的节点的删除
    public int delRightTreeMin(Node node){
        Node target = node;
        //循环查找左节点找到最小值
        while (target.left != null){
            target = target.left;
        }
        //删除最小节点
        delNode(target.value);
        return target.value;
    }

    //删除节点
    public void delNode(int value){
        if(root == null){
            return;
        }else{
            //需求先去找到要删除的节点 targetNode
            Node targetNode = search(value);
            if(targetNode == null){
                return;
            }
            //去找到targetNode的父节点
            Node parentNode = searchParent(value);
            if(parentNode == null){
                root = null;
                return;
            }else if(parentNode == root){
                //TODO 单独处理
            }
            //如果要删除的节点是叶子节点
            if(targetNode.left == null && targetNode.right == null){
                //判断targetNode是父节点的左子节点还是右子节点
                if(parentNode.left != null && parentNode.left.value == value){
                    parentNode.left = null;
                }else if(parentNode.right != null && parentNode.right.value == value){
                    parentNode.right = null;
                }
                //删除节点有俩颗子树
            }else if(targetNode.left != null && targetNode.right != null){
                int rightTreeMinValue = delRightTreeMin(targetNode.right);
                targetNode.value = rightTreeMinValue;
                //删除节点只有一颗子树的节点
            }else{
                //如果要删除的节点有左子节点
                if(targetNode.left != null){
                    if(parentNode.left.value == value){
                        parentNode.left = targetNode.left;
                    }else{
                        parentNode.right = targetNode.left;
                    }
                    //如果要删除的节点有右子节点
                }else{
                    if(parentNode.left.value == value){
                        parentNode.left = targetNode.right;
                    }else{
                        parentNode.right = targetNode.right;
                    }
                }

            }

        }
    }

    //添加节点的方法
    public void add(Node node){
        if(root == null){
            root = node;
        }else{
            root.add(node);
        }
    }
    //中序遍历的方法
    public void infixOrder(){
        if(root != null){
            root.infixOrder();
        }else{
            System.out.println("树为空！");
        }
    }
}

//创建node节点
class Node{
    int value;
    Node left;
    Node right;

    public Node(int value) {
        this.value = value;
    }

    //返回左子树的高度
    public int leftHeight(){
        if(left == null){
            return 0;
        }
        return left.height();
    }

    //返回右子树的高度
    public int rightHeight(){
        if(right == null){
            return 0;
        }
        return right.height();
    }

    //返回当前节点的高度,以该节点为根节点的树的高度
    public int height(){
        return Math.max(left == null ? 0:left.height(),right == null ? 0:right.height())+1;
    }

    //左旋转方法
    private void leftRotate(){
        //以当前根节点的值，创建根节点的值
        Node newNode = new Node(value);
        //把新的节点的左子树设置成当前节点的左子树
        newNode.left = left;
        //把新的节点的右子树设置成当前节点的右子树的左子树
        newNode.right = right.left;
        //把当前节点的值替换成右子节点的值
        value = right.value;
        //把当前节点的右子树设置成右子树的右子树
        right = right.right;
        //把当前节点的左子树（左子节点）设置成新的节点
        left = newNode;
    }

    //右旋转方法
    private void rightRotate(){
        Node newNode = new Node(value);
        newNode.right = right;
        newNode.left = left.right;
        value = left.value;
        left = left.left;
        right = newNode;
    }

    //查找要删除的节点
    public Node search(int value){
        if(value == this.value){//找到该节点
            return this;
        }else if(value < this.value){
            //如果左子节点为空
            if(this.left == null){
                return null;
            }
            return this.left.search(value);
        }else{
            if(this.right == null){
                return null;
            }
            return this.right.search(value);
        }
    }

    //查找要删除节点的父节点
    public Node searchParent(int value){
        //如果当前节点就是要删除的节点的父节点，就返回
        if((this.left != null && this.left.value == value)
                || (this.right != null && this.right.value == value)){
            return this;
        }else{
            //如果查找的值小于当前节点的值，并且当前节点的左子节点不为空
            if(value < this.value && this.left != null){
                return this.left.searchParent(value);
            }else if(value >= this.value && this.right != null){
                return this.right.searchParent(value);
            }else{
                return null;//没有父节点
            }
        }

    }

    //添加节点的方法
    //递归的形式添加节点，注意需要满足二叉排序树的要求
    public void add(Node node){
        if(node == null){
            return;
        }
        //判断传入的节点的值，和当前子树的根节点的值的关系
        if(node.value < this.value){
            //如果当前节点左子节点为空
            if(this.left == null){
                this.left = node;
            }else{
                //递归的向左子树添加
                this.left.add(node);
            }
        }else {
            if(this.right == null){
                this.right = node;
            }else{
                this.right.add(node);
            }
        }
        //当添加完一个节点后，如果右子树的高度比左子树的高度大于1，左旋转
        if(rightHeight() - leftHeight() > 1){
            //如果他的右子树的左子树的高度大于他的右子树的右子树的高度
            if(right != null && right.leftHeight() > right.rightHeight()){
                //先对右子节点右旋转
                right.rightRotate();
                //然后在对当前节点进行左旋转
                leftRotate();
            }else{
                leftRotate();
            }
            return;
        }
        //当添加完一个节点后，如果（左子树的高度 - 右子树的高度）>1，右旋转
        if(leftHeight() - rightHeight() > 1){
            //如果他的左子树的右子树高度大于他的左子树的左子树高度
            if(left != null && left.rightHeight() > left.leftHeight()){
                //先对当前节点的左节点（左子树）->左旋转
                left.leftRotate();
                //在对当前节点进行右旋转
            }else{
                rightRotate();
            }
        }

    }

    //中序遍历
    public void infixOrder(){
        if(this.left != null){
            this.left.infixOrder();
        }
        System.out.println(this.value);
        if(this.right != null){
            this.right.infixOrder();
        }
    }
}
