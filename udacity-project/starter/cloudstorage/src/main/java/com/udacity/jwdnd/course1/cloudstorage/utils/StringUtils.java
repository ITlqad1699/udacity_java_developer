package com.udacity.jwdnd.course1.cloudstorage.utils;

/**
 * The type String utils.
 */
public class StringUtils {
    /**
     * The constant EMPTY.
     */
    public static final String EMPTY = "";
    
    /**
     * Is empty boolean.
     *
     * @param s the s
     * @return the boolean
     */
    public static boolean isEmpty(String s) {
        return s.isEmpty();
    }
    
    /**
     * Has text boolean.
     *
     * @param s the s
     * @return the boolean
     */
    public static boolean hasText(String s) {
        return !s.isBlank();
    }
}
