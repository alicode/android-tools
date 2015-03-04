package com.madding.android.dl.util;

/**
 * @author madding.lip
 */
public class StringUtil {

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    public static boolean isBlank(String str) {
        int len;
        if ((str == null) || ((len = str.length()) == 0)) return true;
        for (int i = 0; i < len; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isEmpty(String str) {
        return (str == null) || ("".equals(str));
    }
}
