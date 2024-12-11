package com.nn.utils;

import java.util.List;

public class ListUtils {
    private ListUtils() {
    }

    public static boolean isEmpty(final List<?> list) {
        return list == null || list.isEmpty();
    }

    public static boolean isNotEmpty(final List<?> list) {
        return !isEmpty(list);
    }

}
