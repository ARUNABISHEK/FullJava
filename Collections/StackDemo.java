package com.collections;

import java.util.Iterator;
import java.util.Stack;

public class StackDemo {

    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();

        stack.push(10);
        stack.push(20);
        stack.push(30);
        stack.push(40);
        stack.push(40);

        stack.pop();

        Iterator itr = stack.iterator();

        while(itr.hasNext()) {
            System.out.println(itr.next());
        }
        System.out.println("Peek : " + stack.peek());
        System.out.println("index of 30 is : " + stack.search(30));
    }

}
