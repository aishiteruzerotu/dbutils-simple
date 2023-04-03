package com.nf.handler.row.util;

import com.nf.ReflexException;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
/**
 * 该类用于bean的数据填充
 */
public class BeanUtils {
    /**
     * 给 bean 填充数据
     * @param rs 结果集
     * @param bean 泛型对象
     * @param <T> 接受泛型
     * @return 原本的 带参 Bean 对象
     */
    public static  <T> T populateBean(ResultSet rs,T bean) {
        //声明类对象
        Class<?> clz = bean.getClass();
        try {
            //获取源数据
            ResultSetMetaData metaData = rs.getMetaData();
            //获取列的长度
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                //获取列名
                String columnName = metaData.getColumnName(i);
                //获取对应的字段
                Field field = clz.getDeclaredField(columnName);
                //设置访问属性为真
                field.setAccessible(true);
                //为对象赋值
                field.set(bean, rs.getObject(columnName));
                //设置访问属性为假
                field.setAccessible(false);
            }
        }catch (Exception e){
            //抛出异常
            throw new ReflexException("data filling failed...",e);
        }
        //返回对象 t
        return bean;
    }
}
