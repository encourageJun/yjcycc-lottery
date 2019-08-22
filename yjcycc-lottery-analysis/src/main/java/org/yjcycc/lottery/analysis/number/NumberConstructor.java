package org.yjcycc.lottery.analysis.number;

import java.lang.reflect.Constructor;

public class NumberConstructor {

    public static Number getConstructor(String className) {
        try {
            Class cl = Class.forName(className);
            //获得指定对象的构造方法，参数值传入与构造方法参数对应的类型
            Constructor constructor = cl.getConstructor();
            //分为无参和有参，参数传入与构造方法参数对应的值，获得对象引用
            Number number = (Number) constructor.newInstance();
            return number;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Number getConstructor(String className, Integer singleCount) {
        try {
            Class cl = Class.forName(className);
            //获得指定对象的构造方法，参数值传入与构造方法参数对应的类型
            Constructor constructor = cl.getConstructor(Integer.class);
            //分为无参和有参，参数传入与构造方法参数对应的值，获得对象引用
            Number number = (Number) constructor.newInstance(singleCount);
            return number;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Number getConstructor(String className, String danNumber, String tuoNumber, Integer singleCount) {
        try {
            Class cl = Class.forName(className);
            //获得指定对象的构造方法，参数值传入与构造方法参数对应的类型
            Constructor constructor = cl.getConstructor(String.class, String.class, Integer.class);
            //分为无参和有参，参数传入与构造方法参数对应的值，获得对象引用
            Number number = (Number) constructor.newInstance(danNumber, tuoNumber, singleCount);
            return number;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
