package com.sentinel.example;

/**
 * com.sentinel.example.ExceptionUtils
 *
 * @author lipeng
 * @date 2019-08-19 11:33
 */
public class ExceptionUtils {

    public static String handleException(String name) {
        return "网络不稳定，请检查您的网络! hello " + name;
    }
}
