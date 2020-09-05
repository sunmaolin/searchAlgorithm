package sml.tree.binarySortTree;

/**
 * 二叉排序树
 * 左子节点小于根节点，右子节点大于根节点，尽量避免相同值
 * 存在相同节点时，程序会挂，挂在删除节点存在俩子树那
 */
public class BinarySortTreeDemo {
    public static void main(String[] args) {
//        int[] arr = {7,3,10,12,5,1,9,0};
        int[] arr = {10,1};
        BinarySortTree binarySortTree = new BinarySortTree();
        //循环的节点添加到二叉排序树
        for (int i = 0; i < arr.length; i++) {
            binarySortTree.add(new Node(arr[i]));
        }
        //中序遍历二叉排序树
        binarySortTree.infixOrder();
        //测试删除叶子节点
//        binarySortTree.delNode(2);
        //测试删除节点右一个子树
//        binarySortTree.delNode(1);
        //测试删除节点有俩个子树
        binarySortTree.delNode(10);
        //中序遍历二叉排序树
        binarySortTree.infixOrder();
    }
}

//创建二叉排序树
class BinarySortTree{
    private Node root;
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
