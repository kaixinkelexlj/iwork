package com.fun.bitmap;

import java.util.BitSet;

import org.junit.Test;
import org.roaringbitmap.RoaringBitmap;

import com.kuaishou.dataarch.shaded.roaringbitmap.longlong.Roaring64NavigableMap;

/**
 * @author xulujun@kuaishou.com
 * Created on 2023-02-02
 */
public class BitmapTest {

    @Test
    public void test() throws Exception {
        BitSet bitSet = new BitSet();
        bitSet.set(0);
        bitSet.set(1);
        System.out.println(bitSet.get(1)); //true

        bitSet.clear(1); //删除
        System.out.println(bitSet.get(1)); //false
        System.out.println(bitSet.cardinality()); //2
        System.out.println(bitSet.size()); //64/8=8 字节

        RoaringBitmap roaringBitmap = new RoaringBitmap();
        for (int i = 1; i <= 4096; i++) {
            roaringBitmap.add(i);
        }
        roaringBitmap.add(4097); // BitmapContainer
        roaringBitmap.runOptimize(); // RunContainer

        Roaring64NavigableMap roaring64NavigableMap = new Roaring64NavigableMap();
        roaring64NavigableMap.add(1233453453345L);
        roaring64NavigableMap.runOptimize();
        System.out.println(roaring64NavigableMap.getLongCardinality());

    }

}
