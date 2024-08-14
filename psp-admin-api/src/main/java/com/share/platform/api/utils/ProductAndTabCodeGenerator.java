package com.share.platform.api.utils;

import java.util.concurrent.atomic.AtomicLong;

public class ProductAndTabCodeGenerator {
    private static final AtomicLong counter = new AtomicLong(0);

    public static synchronized String generateProductCode() {
        // 假设商品编码格式为 "PC00000001"
        long id = counter.incrementAndGet();
        String code = "PC" + String.format("%08d", id);
        return code;
    }

    public static synchronized String generateTabCode() {
        // 假设店铺编码格式为 "TB00000001"
        long id = counter.incrementAndGet();
        String code = "TB" + String.format("%08d", id);
        return code;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            System.out.println(generateProductCode());
        }
    }
}
