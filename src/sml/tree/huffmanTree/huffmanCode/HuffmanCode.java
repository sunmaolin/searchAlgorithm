package sml.tree.huffmanTree.huffmanCode;

import java.util.*;

/**
 * 赫夫曼编码
 */
public class HuffmanCode {
    public static void main(String[] args) {
        String content = "i like like like java do you like a java";
        byte[] contentBytes = content.getBytes();
        System.out.println("编码之前的长度："+contentBytes.length);

        List<Node> nodes = getNodes(contentBytes);
        System.out.println("生成的node集合："+nodes);

        Node root = createHuffmanTree(nodes);
        System.out.println("生成的赫夫曼树进行遍历：");
        root.preOrder();

        System.out.println("测试生成的哈夫曼编码：");
        getCodes(nodes.get(0),"",builder);
        System.out.println(huffmanCodes.toString());

        System.out.println("测试哈夫曼编码后的数据:");
        byte[] huffmanCodeBytes = zip(contentBytes,huffmanCodes);
        System.out.println(Arrays.toString(huffmanCodeBytes));


    }

    /**
     * 编写一个方法，将字符串对应的byte[]数组，通过生成的赫夫曼编码表，返回一个处理压缩后的byte数组（赫夫曼编码数据）
     * @param bytes 需要处理的子串串
     * @param huffmanCodes 赫夫曼编码表
     * @return 处理后的数据
     * 返回的是byte[] huffmanCodeBytes 既8位对应一个byte
     */
    private static byte[] zip(byte[] bytes,Map<Byte,String> huffmanCodes){
        //先利用赫夫曼编码表 将 bytes 转成 赫夫曼编码对应的字符串
        StringBuilder builder = new StringBuilder();
        //遍历bytes数组
        for (byte b : bytes){
            builder.append(huffmanCodes.get(b));
        }
        //将"1010100010111111110..."转成byte[]

        //统计byte[]长度
        //一句话 int len = (builde.length()+7)/8;
        int len  ;
        if(builder.length() % 8 == 0){
            len = builder.length()/8;
        }else{
            len = builder.length()/8+1;
        }

        //创建存储压缩后的byte数组
        byte[] huffmanCodeBytes = new byte[len];
        int index = 0;//记录第几个byte
        for (int i=0 ; i<builder.length() ; i+=8){//每8位对应一个byte
            String strByte;
            if(i+8 > builder.length()){//不够8位
                strByte = builder.substring(i);
            }else{
                strByte = builder.substring(i,i+8);
            }
            //将strByte转成一个byte，放入到huffmanCodeBytes
            //这里的第二个参数是strByte是几进制
            huffmanCodeBytes[index] = (byte) Integer.parseInt(strByte,2);
            index++;
        }

        return huffmanCodeBytes;

    }

    //生成赫夫曼树对应的赫夫曼编码
    //1.将赫夫曼编码表存放在Map<Byte,String>集合中
    //形式 =01 a=100 d=11000   右边为到权值的路径，左为0 右为1
    static Map<Byte,String> huffmanCodes = new HashMap<>();
    // ascii Byte拆装箱   32=01  97=100 100=11000
    //生成赫夫曼编码表示，需要去拼接路径
    static StringBuilder builder = new StringBuilder();

    /**
     * 功能:将传入的node节点的所有叶子节点的赫夫曼编码得到，并放入huffmanCodes
     * @param node 传入节点
     * @param code 路径：左子节点是0 右为1
     * @param stringBuilder
     */
    private static void getCodes(Node node,String code,StringBuilder stringBuilder){
        StringBuilder stringBuilder1 = new StringBuilder(stringBuilder);
        stringBuilder1.append(code);
        if(node != null){
            if(node.right != null && node.left != null){//非叶子节点
                getCodes(node.left,"0",stringBuilder1);
                getCodes(node.right,"1",stringBuilder1);
            }else{
                huffmanCodes.put(node.data,stringBuilder1.toString());
            }
        }
    }



    //接受byte数组，生成节点返回
    private static List<Node> getNodes(byte[] bytes){
        //1先创建一个ArrayList
        ArrayList<Node> nodes = new ArrayList<>();
        //遍历btes，统计每个byte出现的次数->map[key,value]
        Map<Byte,Integer> counts = new HashMap<>();
        for (byte b : bytes){
            Integer count = counts.get(b);
            if(count == null){
                counts.put(b,1);
            }else{
                counts.put(b,count+1);
            }
         }

        //把每一个键值对转换成一个Node对象，并加入到nodes集合
        for (Map.Entry<Byte,Integer> entry:counts.entrySet()){
            nodes.add(new Node(entry.getKey(),entry.getValue()));
        }

        return nodes;
    }

    //通过list创建一个赫夫曼树
    public static Node createHuffmanTree(List<Node> nodes){
        while(nodes.size() > 1){
            Collections.sort(nodes);
            Node left = nodes.get(0);
            Node right = nodes.get(1);
            Node parent = new Node(left.weight+right.weight);
            parent.left = left;
            parent.right = right;
            nodes.remove(left);
            nodes.remove(right);
            nodes.add(parent);
        }
        return nodes.get(0);
    }
}

//创建Node，带数据和权值
class Node implements Comparable<Node>{
    Byte data;//存放数据
    int weight;//权值，表示字符出现的次数
    Node left;
    Node right;

    public Node(Byte data, int weight) {
        this.data = data;
        this.weight = weight;
    }

    public Node(int weight) {
        this.weight = weight;
    }

    @Override
    public int compareTo(Node o) {
        //从小到大排序
        return this.weight - o.weight;
    }

    @Override
    public String toString() {
        return "Node{" +
                "data=" + data +
                ", weight=" + weight +
                '}';
    }

    //前序遍历
    public void preOrder(){
        System.out.println(this);
        if(this.left != null){
            this.left.preOrder();
        }
        if(this.right != null){
            this.right.preOrder();
        }
    }
}
