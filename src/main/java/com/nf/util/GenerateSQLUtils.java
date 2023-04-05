package com.nf.util;

import java.lang.reflect.Field;

/**
 * 此类用于生成SQl语句，返回对象皆为字符串
 * 是访问器相关的工具类
 */
public class GenerateSQLUtils {
    private GenerateSQLUtils() {
    }

    /**
     * 给一个实体对象参数，返回该实体的SQL插入语句
     * @param t 实体对象
     * @return 返回一个SQL增加语句
     */
    public static <T> String generateInsert(T t) {
        Class<?> clz = t.getClass();
        String sql = "insert into ";
        sql += clz.getSimpleName() + "(";
        Field[] fields = clz.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            sql += fields[i].getName();
            if (i != fields.length - 1) {
                sql += ",";
            }
            if (i == fields.length - 1) {
                sql += ") values(";
            }
        }
        for (int i = 0; i < fields.length; i++) {
            sql += "?";
            if (i != fields.length - 1) {
                sql += ",";
            }
            if (i == fields.length - 1) {
                sql += ")";
            }
        }
        return sql;
    }

    /**
     * 给一个类对象 返回该对象的sql查询语句
     * @param clz 类对象
     * @return 数据库 查询语句
     */
    public static String generateSelect(Class<?> clz){
        //声明 sql
        StringBuffer sql = new StringBuffer();
        //添加查询头
        sql.append("select ");
        //获取对象的字段
        Field[] fields = clz.getDeclaredFields();
        //根据获取到的字段进行循环
        for (int i = 0;i<fields.length;i++) {
            //循环添加查询列
            sql.append(fields[i].getName());
            //判断是否为最后一个字段
            if (i==fields.length-1)
                //是则跳出循环
                continue;
            //为查询列之间添加空格
            sql.append(",");
        }
        //添加指向对象
        sql.append(" from ");
        //添加查询的表名
        sql.append(clz.getSimpleName());
        //返回查询语句
        return sql.toString();
    }
}
