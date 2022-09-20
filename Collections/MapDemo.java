package com.collections;

import java.util.HashMap;
import java.util.Map;

public class MapDemo {

    public static void main(String[] args) {

        Map<Integer,String> map = new HashMap<Integer,String>();

        map.put(1,"Civil");
        map.put(2,"Cse");
        map.put(3,"Ece");
        map.put(4,"Mech");
        map.put(5,"eee");

        for(Map.Entry<Integer,String> entry : map.entrySet()) {
            System.out.println(entry.getKey() +" " + entry.getValue());
        }

        System.out.println("Keys : ");
        for(Integer key : map.keySet()) {
            System.out.println(key);
        }

        System.out.println("Values : ");
        for(String str : map.values()) {
            System.out.println(str);
        }
    }

}
