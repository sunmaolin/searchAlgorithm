package sml.algorithm;

/**
 * 暴力匹配算法实现
 */
public class ViolenceMatch {
    public static void main(String[] args) {

        String str1 = "你好啊孙茂林";
        String str2 = "孙茂林";
        System.out.println(violenceMatch(str1, str2));

    }

    //暴力匹配算法实现
    public static int violenceMatch(String str1,String str2){
        char[] s1 = str1.toCharArray();
        char[] s2 = str2.toCharArray();

        int s1Len = s1.length;
        int s2Len = s2.length;

        int i = 0;//索引指向s1
        int j = 0;//索引指向s2

        while(i < s1Len && j < s2Len){//保证匹配时不越界
            if(s1[i] == s2[j]){//匹配成功
                i++;
                j++;
            }else{
                i = i - ( j - 1 );
                j = 0;
            }
        }

        //判断是否匹配成功
        if(j == s2Len){
            return i-j;
        }else{
            return -1;
        }
    }
}
