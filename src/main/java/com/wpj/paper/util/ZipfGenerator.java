package com.wpj.paper.util;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

public class ZipfGenerator implements Serializable {
    private static final Random random = new Random(0);
    private final NavigableMap<Double, Integer> map;
    private static final double Constant = 1.0;

    private static final ArrayList<ArrayList<Object>> arr = new ArrayList<>();

    public ZipfGenerator(int R, double F) {
        // create the TreeMap
        map = computeMap(R, F);
    }

    //size为rank个数，skew为数据倾斜程度, 取值为0表示数据无倾斜，取值越大倾斜程度越高
    private static NavigableMap<Double, Integer> computeMap(
            int size, double skew) {
        NavigableMap<Double, Integer> map = new TreeMap<>();
        //总频率
        double div = 0;
        //对每个rank，计算对应的词频，计算总词频
        for (int i = 1; i <= size; i++) {
            //the frequency in position i
            div += (Constant / Math.pow(i, skew));
        }
        //计算每个rank对应的y值，所以靠前rank的y值区间远比后面rank的y值区间大
        double sum = 0;
        double psum = 0;
        for (int i = 1; i <= size; i++) {
            double p = (Constant / Math.pow(i, skew)) / div;

//            if(i < 10 || i > 999990){
//                ArrayList<Object> point = new ArrayList<>();
//                point.add(i);
//                point.add(p);
//                arr.add(point);
//            }else if(random.nextDouble() < 0.001){
//                ArrayList<Object> point = new ArrayList<>();
//                point.add(i);
//                point.add(p);
//                arr.add(point);
//            }

            sum += p;
            map.put(sum, i - 1);

            if (i <= size * 0.2) {
                psum += p;
            }
        }
        System.out.println("20%的数据集中率" + psum / sum);
        return map;
    }

    public String print() {
        return JSON.toJSONString(arr);
    }

    public int next() {         // [0,1]
        double value = random.nextDouble();
        //找最近y值对应的rank
        return map.ceilingEntry(value).getValue() + 1;
    }

    public static void main(String[] args) {
        ZipfGenerator zipf = new ZipfGenerator(100*10000, 1.7);
        System.out.println(zipf.print());
    }
}
