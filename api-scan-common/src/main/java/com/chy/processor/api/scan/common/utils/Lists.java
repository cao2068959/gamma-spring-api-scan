package com.chy.processor.api.scan.common.utils;


import java.util.ArrayList;
import java.util.List;

public class Lists {

    public static <T> List<T> of(T... element) {
        if (element == null || element.length == 0) {
            return new ArrayList<>(0);
        }
        ArrayList<T> result = new ArrayList<>(element.length);
        for (T t : element) {
            result.add(t);
        }
        return result;
    }


}
