package com.carrynong.springbootlibrary.utils;

public class Utils {
    public static boolean validatePageable(Integer page, Integer size) {
        return page == null || size == null || page < 0 || size < 0;
    }
}
