package org.yjcycc.lottery.utils;

import java.util.List;

public class StringUtils extends org.apache.commons.lang.StringUtils {

    /**
     * list转数组
     * @param list
     * @return
     */
    public static String[] listToArray(List<String> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        String[] targetArr = new String[list.size()];
        list.toArray(targetArr);
        return targetArr;
    }

    /**
     * list转字符串
     * @param list
     * @return
     */
    public static String listToString(List<String> list, String separator) {
        return join(listToArray(list), separator);
    }

    public static String[] stringToArray(String str, String separator) {
        if (isEmpty(str)) {
            return null;
        }
        return str.split(separator);
    }

}
