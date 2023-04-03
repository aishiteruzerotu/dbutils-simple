package com.nf.handler;

import com.nf.ResultSetHandler;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * 该类实现了 ResultSetHandler<T> 接口
 * 该类返回的对象是一个 实体类
 */
public class BeanHandler<T> implements ResultSetHandler<T> {
    private Class<? extends T> clz;

    public BeanHandler(Class<? extends T> clz) {
        this.clz = clz;
    }

    @Override
    public T handler(ResultSet rs) throws SQLException {
        T t = null;
        try {
            t = clz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        if (rs.next()) {
            try {
                bat(clz, rs, metaData, columnCount, t);
            } catch (NoSuchFieldException |IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return t;
    }
    public <T> void bat(Class<? extends T> clz, ResultSet rs, ResultSetMetaData metaData, int columnCount, T t) throws SQLException, NoSuchFieldException, IllegalAccessException {
        for (int i = 1; i <= columnCount; i++) {
            String columnName = metaData.getColumnName(i);
            Object columnValue = rs.getObject(columnName);
            Field field = clz.getDeclaredField(columnName);
            field.setAccessible(true);
            field.set(t, columnValue);
            field.setAccessible(false);
        }
    }
}
