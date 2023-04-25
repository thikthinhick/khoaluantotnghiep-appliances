package com.vnu.demo.common;

import java.util.Random;

public class RandomData {
    public static int get(int high, int low) {
        return low + (int)(Math.random() * ((high - low) + 1));
    }
}
