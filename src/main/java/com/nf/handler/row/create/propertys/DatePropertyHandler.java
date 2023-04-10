package com.nf.handler.row.create.propertys;

import com.nf.handler.row.create.PropertyHandler;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 实现接口 PropertyHandler
 * 用于处理 Date 日期类型的数据
 */
public class DatePropertyHandler implements PropertyHandler {
    /**
     * 判断是否能处理该数据
     * @param clz setter 方法的参数的类对象
     * @param value 输入的值
     * @return 返回真表示该数据是
     */
    @Override
    public boolean mate(Class<?> clz,Object value) {
        if (value instanceof Date) {
            final String targetType = clz.getName();
            if ("java.sql.Date".equals(targetType)) {
                return true;
            }
            if ("java.sql.Time".equals(targetType)) {
                return true;
            }
            if ("java.sql.Timestamp".equals(targetType)
                    && !Timestamp.class.isInstance(value)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 处理 Date 日期类型的数据
     * @param clz setter 方法的参数的类对象
     * @param value 输入的值
     * @return 处理后的 Date 日期对象
     */
    @Override
    public Object apply(Class<?> clz,Object value) {
        final String targetType = clz.getName();
        final Date dateValue = (Date) value;
        final long time = dateValue.getTime();

        if ("java.sql.Date".equals(targetType)) {
            return new java.sql.Date(time);
        }
        if ("java.sql.Time".equals(targetType)) {
            return new java.sql.Time(time);
        }
        if ("java.sql.Timestamp".equals(targetType)) {
            return new Timestamp(time);
        }
        return value;
    }
}
