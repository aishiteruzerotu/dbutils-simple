package com.nf.handler;

import com.nf.ResultSetHandler;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BeanListHandler<T> implements ResultSetHandler<List<T>> {

    private Class<? extends T> clz;

    public BeanListHandler(Class<? extends T> clz) {
        this.clz = clz;
    }

    @Override
    public List<T> handler(ResultSet rs) throws SQLException {
        List<T> list = new ArrayList<>();

        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        while (rs.next()) {
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
            try {
                bat(clz, rs, metaData, columnCount, t);
            } catch (NoSuchFieldException |IllegalAccessException e) {
                e.printStackTrace();
            }
            list.add(t);
        }
        return list;

    }
    public static  <T> void bat(Class<? extends T> clz, ResultSet rs, ResultSetMetaData metaData, int columnCount, T t) throws SQLException, NoSuchFieldException, IllegalAccessException {
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

