package com.busbooking.controller;

public class Sleep {

    public static void sleep() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
