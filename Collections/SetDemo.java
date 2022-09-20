package com.collections;

import java.util.HashSet;
import java.util.Set;

public class SetDemo {

    public static void main(String[] args) {

        Set<Integer> set = new HashSet<>();

        set.add(10);
        set.add(20);
        set.add(20);
        set.add(30);

        for(Integer i:set) {
            System.out.println(i);
        }
        System.out.println(set.size());
    }

}
