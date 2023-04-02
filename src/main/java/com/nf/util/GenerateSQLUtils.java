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
}
