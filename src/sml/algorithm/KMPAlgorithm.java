package sml.algorithm;

/**
 * KMP匹配算法
 * 重点前缀后缀：
 * ABCDAB
 * 前缀：A AB ABC ABCD ABCDA
 * 后缀：BCDAB CDAB DAB AB B
 */
public class KMPAlgorithm {
    public static void main(String[] args) {
        String str1 = "BBC ABCDAB ABCDABCDABDE";
        String str2 = "ABCDABD";
        int[] next = kmpNext(str2);
        int index = kmpSearch(str1,str2,next);
        System.out.println(index);
    }

    /**
     * kmp搜索算法
     * @param str1 源字符串
     * @param str2 子串
     * @param next 部分匹配表
     * @return
     */
    public static int kmpSearch(String str1,String str2,int[] next){
        for (int i = 0,j = 0; i < str1.length(); i++) {
            //kmp算法核心点
            while(j > 0 && str1.charAt(i) != str2.charAt(j)){
                j = next[j-1];
            }

            if(str1.charAt(i) == str2.charAt(j)){
                j++;
            }
            if(j == str2.length()){
                return i-(j-1);
            }
        }
        return -1;
    }

    /**
     * 获取到一个字符串（子串）部分匹配值表
     */
    public static int[]  kmpNext(String dest){
        //创建一个next数组，保存部分匹配值
        int[] next = new int[dest.length()];
        next[0] = 0;//如果字符串是长度为1，部分匹配值就是0
        for (int i = 1, j = 0; i < next.length; i++) {
            //当dest.charAt(i) != dest.charAt(j),我们需要从next[j-1]获取新的j
            //直到我们发现有dest.charAt(i) == dest.charAt(j)成立才退出
            //这是kmp算法的核心点
            while( j > 0 && dest.charAt(i) != dest.charAt(j)){
                //这里j-1，是因为前面已经匹配过的，无需在匹配，只需要往下走验证下一个字符是否匹配
                j = next[j-1];
            }
            //当这个条件满足时，部分匹配值就是要+1
           if(dest.charAt(i) == dest.charAt(j)){
               j++;
           }
           next[i] = j;
        }
        return next;
    }
}
