package com.collections;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class QueueDemo {

    public static void main(String[] args) {

//        Queue<Integer> queue = new PriorityQueue<Integer>();
        Queue<Integer> queue = new LinkedList<>();

        queue.add(5);
        queue.add(10);
        queue.add(20);
        queue.add(30);

        queue.remove();

        for(Integer i:queue) {
            System.out.println(i);
        }

        System.out.println(queue.peek());
        //System.out.println(queue.element());
    }

}
