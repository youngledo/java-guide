package com.youngledo.jvm.diagnostic;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * -Xms60m -Xmx60m -XX:SurvivorRatio=8
 */
public class Jstat {

    public static void main(String[] args) throws InterruptedException {
        ArrayList<Object> objects = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            // 100KB
            byte[] bytes = new byte[1024 * 100];
            objects.add(bytes);
            TimeUnit.MILLISECONDS.sleep(50);
        }
    }
}
