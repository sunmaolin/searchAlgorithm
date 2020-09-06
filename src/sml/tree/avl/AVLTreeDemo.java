package sml.tree.avl;

/**
 * AVLƽ������������Ƕ�˳���������һ������
 * ��ӵ�ʱ�������ת
 */
public class AVLTreeDemo {
    public static void main(String[] args) {
        int[] arr = {4,3,6,5,7,8};
        //����һ��AVLTree����
        AVLTree avlTree = new AVLTree();
        //��ӽڵ�
        for (int i = 0; i < arr.length; i++) {
            avlTree.add(new Node(arr[i]));
        }
        avlTree.infixOrder();
        System.out.println("��û����ƽ�⴦��֮ǰ");
        System.out.println("���ĸ߶�"+avlTree.getRoot().height());
        System.out.println("�������ĸ߶�"+avlTree.getRoot().leftHeight());
        System.out.println("�������ĸ߶�"+avlTree.getRoot().rightHeight());
        System.out.println("��ǰ�ĸ��ڵ�="+avlTree.getRoot().value);
        System.out.println("���ڵ�����ӽڵ�+"+avlTree.getRoot().left.value);


    }
}

//����AVLTree
class AVLTree{
    private Node root;

    public Node getRoot() {
        return root;
    }

    //����Ҫɾ���Ľڵ�
    public Node search(int value){
        if(root == null){
            return null;
        }else{
            return root.search(value);
        }
    }
    //���Ҹ��ڵ�
    public Node searchParent(int value){
        if(root == null){
            return null;
        }else{
            return root.searchParent(value);
        }
    }

    //��������������С�ڵ��ֵ��ͬʱ�����С�Ľڵ��ɾ��
    public int delRightTreeMin(Node node){
        Node target = node;
        //ѭ��������ڵ��ҵ���Сֵ
        while (target.left != null){
            target = target.left;
        }
        //ɾ����С�ڵ�
        delNode(target.value);
        return target.value;
    }

    //ɾ���ڵ�
    public void delNode(int value){
        if(root == null){
            return;
        }else{
            //������ȥ�ҵ�Ҫɾ���Ľڵ� targetNode
            Node targetNode = search(value);
            if(targetNode == null){
                return;
            }
            //ȥ�ҵ�targetNode�ĸ��ڵ�
            Node parentNode = searchParent(value);
            if(parentNode == null){
                root = null;
                return;
            }else if(parentNode == root){
                //TODO ��������
            }
            //���Ҫɾ���Ľڵ���Ҷ�ӽڵ�
            if(targetNode.left == null && targetNode.right == null){
                //�ж�targetNode�Ǹ��ڵ�����ӽڵ㻹�����ӽڵ�
                if(parentNode.left != null && parentNode.left.value == value){
                    parentNode.left = null;
                }else if(parentNode.right != null && parentNode.right.value == value){
                    parentNode.right = null;
                }
                //ɾ���ڵ�����������
            }else if(targetNode.left != null && targetNode.right != null){
                int rightTreeMinValue = delRightTreeMin(targetNode.right);
                targetNode.value = rightTreeMinValue;
                //ɾ���ڵ�ֻ��һ�������Ľڵ�
            }else{
                //���Ҫɾ���Ľڵ������ӽڵ�
                if(targetNode.left != null){
                    if(parentNode.left.value == value){
                        parentNode.left = targetNode.left;
                    }else{
                        parentNode.right = targetNode.left;
                    }
                    //���Ҫɾ���Ľڵ������ӽڵ�
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

    //��ӽڵ�ķ���
    public void add(Node node){
        if(root == null){
            root = node;
        }else{
            root.add(node);
        }
    }
    //��������ķ���
    public void infixOrder(){
        if(root != null){
            root.infixOrder();
        }else{
            System.out.println("��Ϊ�գ�");
        }
    }
}

//����node�ڵ�
class Node{
    int value;
    Node left;
    Node right;

    public Node(int value) {
        this.value = value;
    }

    //�����������ĸ߶�
    public int leftHeight(){
        if(left == null){
            return 0;
        }
        return left.height();
    }

    //�����������ĸ߶�
    public int rightHeight(){
        if(right == null){
            return 0;
        }
        return right.height();
    }

    //���ص�ǰ�ڵ�ĸ߶�,�Ըýڵ�Ϊ���ڵ�����ĸ߶�
    public int height(){
        return Math.max(left == null ? 0:left.height(),right == null ? 0:right.height())+1;
    }

    //����ת����
    private void leftRotate(){
        //�Ե�ǰ���ڵ��ֵ���������ڵ��ֵ
        Node newNode = new Node(value);
        //���µĽڵ�����������óɵ�ǰ�ڵ��������
        newNode.left = left;
        //���µĽڵ�����������óɵ�ǰ�ڵ����������������
        newNode.right = right.left;
        //�ѵ�ǰ�ڵ��ֵ�滻�����ӽڵ��ֵ
        value = right.value;
        //�ѵ�ǰ�ڵ�����������ó���������������
        right = right.right;
        //�ѵ�ǰ�ڵ�������������ӽڵ㣩���ó��µĽڵ�
        left = newNode;
    }

    //����ת����
    private void rightRotate(){
        Node newNode = new Node(value);
        newNode.right = right;
        newNode.left = left.right;
        value = left.value;
        left = left.left;
        right = newNode;
    }

    //����Ҫɾ���Ľڵ�
    public Node search(int value){
        if(value == this.value){//�ҵ��ýڵ�
            return this;
        }else if(value < this.value){
            //������ӽڵ�Ϊ��
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

    //����Ҫɾ���ڵ�ĸ��ڵ�
    public Node searchParent(int value){
        //�����ǰ�ڵ����Ҫɾ���Ľڵ�ĸ��ڵ㣬�ͷ���
        if((this.left != null && this.left.value == value)
                || (this.right != null && this.right.value == value)){
            return this;
        }else{
            //������ҵ�ֵС�ڵ�ǰ�ڵ��ֵ�����ҵ�ǰ�ڵ�����ӽڵ㲻Ϊ��
            if(value < this.value && this.left != null){
                return this.left.searchParent(value);
            }else if(value >= this.value && this.right != null){
                return this.right.searchParent(value);
            }else{
                return null;//û�и��ڵ�
            }
        }

    }

    //��ӽڵ�ķ���
    //�ݹ����ʽ��ӽڵ㣬ע����Ҫ���������������Ҫ��
    public void add(Node node){
        if(node == null){
            return;
        }
        //�жϴ���Ľڵ��ֵ���͵�ǰ�����ĸ��ڵ��ֵ�Ĺ�ϵ
        if(node.value < this.value){
            //�����ǰ�ڵ����ӽڵ�Ϊ��
            if(this.left == null){
                this.left = node;
            }else{
                //�ݹ�������������
                this.left.add(node);
            }
        }else {
            if(this.right == null){
                this.right = node;
            }else{
                this.right.add(node);
            }
        }
        //�������һ���ڵ������������ĸ߶ȱ��������ĸ߶ȴ���1������ת
        if(rightHeight() - leftHeight() > 1){
            //����������������������ĸ߶ȴ����������������������ĸ߶�
            if(right != null && right.leftHeight() > right.rightHeight()){
                //�ȶ����ӽڵ�����ת
                right.rightRotate();
                //Ȼ���ڶԵ�ǰ�ڵ��������ת
                leftRotate();
            }else{
                leftRotate();
            }
            return;
        }
        //�������һ���ڵ��������������ĸ߶� - �������ĸ߶ȣ�>1������ת
        if(leftHeight() - rightHeight() > 1){
            //����������������������߶ȴ����������������������߶�
            if(left != null && left.rightHeight() > left.leftHeight()){
                //�ȶԵ�ǰ�ڵ����ڵ㣨��������->����ת
                left.leftRotate();
                //�ڶԵ�ǰ�ڵ��������ת
            }else{
                rightRotate();
            }
        }

    }

    //�������
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
