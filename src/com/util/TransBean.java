package com.util;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;

public class TransBean {
    /**
     * 根据request传来的参数Map，和指定的实体Bean的属性进行比较，将二者名称相同的参数转换成属性类型的值，
     * 并调用相应的set方法给Bean的属性赋值，
     *
     * @param map=request.getParameterMap();
     * @param obj                            要填充的对象
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    public static void populate(Object obj, Map<String, String[]> map) {
        Class objClass = obj.getClass();
        Field[] fields = objClass.getDeclaredFields();
        try {
            for (Field field : fields) {
                String propName = field.getName();
                if (field.getType().isArray() || field.getType().getName().equals("java.util.List")) {
                    if (map.get(propName) != null || map.get(propName + "[]") != null) {//如果数组中有多个元素
                        //如果参数是数组，则其名称可能为"xxx[]"（ajax传来的json对象参数）,所以要将后面的“[]”去掉再和属性名进行比较
                        field.setAccessible(true);
                        field.set(obj, convertArray(
                                map.get(propName) == null ? map.get(propName + "[]") : map.get(propName),
                                field.getType().getComponentType()));
                        //将字符串数组转换成该属性的数组类型并赋值getComponentType():获得数组中元素的类型
                    }
                } else {
                    if (map.get(propName) != null && map.get(propName).length == 1 && map.get(propName)[0] != null) {//如果值数组只有一个元素并且不为空
                        field.setAccessible(true);//设置属性可访问
                        field.set(obj, convert(map.get(propName)[0], field.getType()));//将字符串转换成该属性的类型并赋值
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将list(Map) 转换为list（object）
     * 使用
     *
     * @param objClass
     * @param listMapData
     * @return
     */
    public static List populate(Class objClass, List<Map> listMapData) {
        if (listMapData == null || listMapData.size() == 0) return null;
        List list = new ArrayList();
        Field[] fields = objClass.getDeclaredFields();//获得该类型所有声明的属性
        try {
            for (Map map : listMapData) {
                Object obj = objClass.newInstance();//创建实体类对象
                for (Field field : fields) {//遍历所有属性
                    String propName = field.getName();//获得当前属性的名称
                    if (map.containsKey(propName)) {//如果map中包含该属性
                        field.setAccessible(true);//设置属性可访问
                        field.set(obj, convert(map.get(propName) == null ? "" : map.get(propName).toString(), field.getType()));
                        //给该属性设置值（这里将所有类型的值都转换成string，然后再转换成该属性对应的类型）
                    }
                }
                list.add(obj);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return list;
    }


    /**
     * 将传入的字符串参数转化成制定的数据类型,如果格式不匹配则返回null
     *
     * @param <T>
     * @param param 要转换的参数字符串
     * @param clas  转换的目标CLASS
     * @return
     */
    private static <T extends Serializable> T convert(String param, Class clas) {
        if (param == null || param == "" || clas == null) return null;
        String type = clas.getName();// 获得要转换的数据类型名称
        if (type.equals("java.lang.String"))
            return (T) param;
        try {// 根据不同类型的属性，返回不同的结果，如果出现异常，则返回null
            if (type.equals("java.util.Date")) {
                return (T) new Date(java.sql.Date.valueOf(param)
                        .getTime());
            }
            if (type.equals("java.sql.Date")) {
                return (T) java.sql.Date.valueOf(param);
            }
            if (type.equals("java.sql.Timestamp")) {
                return (T) java.sql.Timestamp.valueOf(param);
            }
            if (type.equals("java.lang.Char")) {
                return (T) Character.valueOf(param.charAt(0));
            }
            if (type.equals("java.lang.Integer") || type.equals("int")) {
                return (T) new Integer(param);
            }
            if (type.equals("java.lang.Double") || type.equals("double")) {
                return (T) new Double(param);
            }
            if (type.equals("java.lang.Float") || type.equals("float")) {
                return (T) new Float(param);
            }
            if (type.equals("java.lang.Byte") || type.equals("boolean")) {
                return (T) new Boolean(param);
            }
            if (type.equals("java.lang.Short") || type.equals("short")) {
                return (T) new Short(param);
            }
            if (type.equals("java.lang.Long") || type.equals("long")) {
                return (T) new Long(param);
            }
            if (type.equals("java.lang.Boolean") || type.equals("boolean")) {
                return (T) new Boolean(param);
            }
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 将传入的字符串数组参数转化成指定的数据类型的数组,如果格式不匹配则返回null
     *
     * @param param        要转换的参数字符串
     * @param elementClass 转换的目标CLASS，一定是数组中元素的Class，而不是数组的Class
     * @return
     */
    private static Object convertArray(String[] param, Class elementClass) {
        if (param == null || elementClass == null)
            return null;
        String type = elementClass.getName();// 获得要转换的数据类型名称

        Object array = Array.newInstance(elementClass, param.length);// 创建指定类型的数组对象
        if (type.equals("java.lang.String")) {
            return param;
        }
        if (type.equals(List.class.getName())) {
            List list = Arrays.asList(param);//如果属性是List集合，则将字符串数组转换为LIst集合
            return list;
        }


        try {// 根据不同类型的属性，返回不同的结果，如果出现异常，则返回null
            if (type.equals("java.util.Date") || type.equals("java.sql.Date")
                    || type.equals("java.sql.Timestamp")) {
                for (int i = 0; i < param.length; i++) {
                    Array.set(array, i, convert(param[i], elementClass));
                }
                return array;
            }
            if (type.equals("java.lang.Character")) {

                for (int i = 0; i < param.length; i++) {
                    Array.setChar(array, i, param[i].charAt(i));
                }
                return array;
            }
            if (type.equals("java.lang.Integer") || type.equals("int")) {
                for (int i = 0; i < param.length; i++) {
                    Array.setInt(array, i, Integer.parseInt(param[i]));
                }
                return array;
            }
            if (type.equals("java.lang.Double") || type.equals("double")) {
                for (int i = 0; i < param.length; i++) {
                    Array.setDouble(array, i, Double.parseDouble(param[i]));
                }
                return array;
            }
            if (type.equals("java.lang.Float") || type.equals("float")) {
                for (int i = 0; i < param.length; i++) {
                    Array.setFloat(array, i, Float.parseFloat(param[i]));
                }
                return array;
            }
            if (type.equals("java.lang.Byte") || type.equals("byte")) {
                for (int i = 0; i < param.length; i++) {
                    Array.setByte(array, i, Byte.parseByte(param[i]));
                }
                return array;
            }
            if (type.equals("java.lang.Short") || type.equals("short")) {
                for (int i = 0; i < param.length; i++) {
                    Array.setShort(array, i, Short.parseShort(param[i]));
                }
                return array;
            }
            if (type.equals("java.lang.Long") || type.equals("long")) {
                for (int i = 0; i < param.length; i++) {
                    Array.setLong(array, i, Long.parseLong(param[i]));
                }
                return array;
            }
            if (type.equals("java.lang.Boolean") || type.equals("boolean")) {
                for (int i = 0; i < param.length; i++) {
                    Array.setBoolean(array, i, Boolean.parseBoolean(param[i]));
                }
                return array;
            }

        } catch (Exception e) {
        }
        return null;
    }

}
