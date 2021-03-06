package sml.tree.huffmanTree.huffmanCode;

import java.util.*;

/**
 * 赫夫曼编码
 */
public class HuffmanCode {
    public static void main(String[] args) {
        //这里不只可以压缩字符串也可以压缩文件，通过流读取
        /**
         * 思路：通过文件流进行读取，文件流写出，中间加上个高级流对(obj)象流
         * 写出后在将赫夫曼编码后的字节数组写入压缩文件，方便以后我们恢复
         * 文件进行使用。
         *
         * 解压时通过对象写入的顺序，类似于压栈，然后在取出俩个对象
         *
         * 注意事项：
         * 1.如果文件本身就是经过压缩处理的，那么使用赫夫曼在压缩效率不会右明显变化
         * 2.赫夫曼编码是按字节来处理的，因此可以处理所有的文件
         * 3.如果一个文件中重复的数据不多，压缩效果也不会很明显
         */

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

        System.out.println("测试赫夫曼解码后的数据：");
        byte[] bytes = decode(huffmanCodes,huffmanCodeBytes);
        System.out.println(new String(bytes));


    }

    //完成数据的解压
    //1.将HuffmanCodeBytes [-88,-65,-56,-65,-56,-65...]
    //  重写先转成 赫夫曼编码对应的二进制的字符串"1010100010111..."
    //  对照赫夫曼编码=> i like like....

    /**
     * 编写一个方法，完成对压缩数据的解码
     * @param huffmanCodes  赫夫曼编码表map
     * @param huffmanBytes 赫夫曼编码得到的字节数组
     * @return  就是原来字符串对应数字
     */
    private static byte[] decode(Map<Byte,String> huffmanCodes,byte[] huffmanBytes){
        //1.显得到huffmanBytes 对应的二进制的字符串。形式: 1010100010111...
        StringBuilder stringBuilder = new StringBuilder();
        //将byte数组转成二进制的字符串
        for (int i = 0; i < huffmanBytes.length; i++) {
            //判断是不是最后一个字节
            boolean flag = (i == huffmanBytes.length - 1);
            stringBuilder.append(byteToBitString(!flag,huffmanBytes[i]));
        }
        //把字符串按照的赫夫曼编码进行解码
        //把赫夫曼编码表进行调换，因为反向查询 a->100  100->a
        Map<String,Byte> map = new HashMap<>();
        for (Map.Entry<Byte,String> huffmanCode : huffmanCodes.entrySet()){
            map.put(huffmanCode.getValue(),huffmanCode.getKey());
        }
        //创建集合，存放byte
        List<Byte> list = new ArrayList<>();
        for (int i = 0; i < stringBuilder.length();) {
            int count = i; // 小的计数器
            Byte b = null;

            while(true){
                //取出一个'1' '0'
                String key = stringBuilder.substring(i,count);
                b = map.get(key);
                if(b == null){
                    count++;
                    continue;
                }
                break;
            }

            list.add(b);
            i = count;//因为下次循环要减一
        }
        //将list中的Byte转换成字符
        byte b[] = new byte[list.size()];
        for (int i = 0; i < b.length; i++) {
            b[i] = list.get(i);
        }
        return b;
    }

    /**
     * 将一个byte转成二进制的字符串
     * @param b
     * @param flag 表示是否需要补高位
     * @return 是该byte对应的二进制的字符串，注意是按补码返回
     */
    private static String byteToBitString(boolean flag,byte b){
        //使用一个变量保存b
        int temp = b;//将b转成int
        //如果是正数我们还存在补高位
        if(flag){
            temp |= 256;//按位或
        }
        String str = Integer.toBinaryString(temp);//返回的是补码
//        System.out.println(str);
        if(flag){
            return str.substring(str.length()-8);
        }else{
            return str;
        }
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
