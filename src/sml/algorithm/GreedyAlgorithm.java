package sml.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * 贪心算法
 * 经典案例：覆盖全部城市选择的最少电台或者说最优解
 */
public class GreedyAlgorithm {
    public static void main(String[] args) {
        //创建广播电台
        HashMap<String, HashSet<String>> broadcases = new HashMap<>();
        //将各个电台放到broadcasts
        HashSet<String> hashSet1 = new HashSet<>();
        hashSet1.add("北京");
        hashSet1.add("上海");
        hashSet1.add("天津");

        HashSet<String> hashSet2 = new HashSet<>();
        hashSet2.add("广州");
        hashSet2.add("北京");
        hashSet2.add("深圳");

        HashSet<String> hashSet3 = new HashSet<>();
        hashSet3.add("成都");
        hashSet3.add("上海");
        hashSet3.add("杭州");

        HashSet<String> hashSet4 = new HashSet<>();
        hashSet4.add("上海");
        hashSet4.add("天津");

        HashSet<String> hashSet5 = new HashSet<>();
        hashSet5.add("杭州");
        hashSet5.add("大连");

        //加入到map
        broadcases.put("k1",hashSet1);
        broadcases.put("k2",hashSet2);
        broadcases.put("k3",hashSet3);
        broadcases.put("k4",hashSet4);
        broadcases.put("k5",hashSet5);

        //存放所有的地区
        HashSet<String> allAreas = new HashSet<>();
        allAreas.add("北京");
        allAreas.add("上海");
        allAreas.add("天津");
        allAreas.add("深圳");
        allAreas.add("成都");
        allAreas.add("杭州");
        allAreas.add("大连");
        allAreas.add("广州");

        //存放选择的电台
        ArrayList<String> selects = new ArrayList<>();

        //定义一个临时集合，保存在遍历过程中给，存放遍历过程中的电台覆盖的地区和当前还没有覆盖的地区的交集
        //这里不使用临时变量不是更好吗？虽然会改变原集合
//        HashSet<String> tempSet = new HashSet<>();

        //定义一个maxKey，保存在一次遍历过程中，能够覆盖最大未覆盖地区对应的电台key
        //如果maskKey不为空，则会加入到selects中
        String maxKey = null;

        while(allAreas.size() != 0){
            //每次循环需要置空
            maxKey=null;
            for(String key : broadcases.keySet()){
//                tempSet.clear();
                //当前的key能覆盖的地区
                HashSet<String> areas = broadcases.get(key);
//                tempSet.addAll(areas);
                //求出tempSet和allAreas集合的交集，不知道啥意思就看源码
                areas.retainAll(allAreas);
                //如果当前这个集合包含的未覆盖地区的数量，比maxKey指向的集合未覆盖的地区还多
                //就需要重置maxKey
                //每次都选最好的，就是贪心算法的特点
                if(areas.size()>0 && (maxKey == null || areas.size() > broadcases.get(maxKey).size())){
                    maxKey = key;
                }
            }
            //maxKey != null,就应该将maxKey加入到selects中
            if(maxKey != null){
                selects.add(maxKey);
                //将maxKey指向的电台覆盖的地区从allAreas中去掉
                allAreas.removeAll(broadcases.get(maxKey));
            }
        }


        System.out.println(selects.toString());

    }
}
