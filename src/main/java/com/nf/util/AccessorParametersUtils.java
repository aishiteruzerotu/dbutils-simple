package com.nf.util;

import com.nf.ReflexException;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

/**
 * 此类用于访问参数的设置，以及生成
 * 是访问器相关的工具类
 */
public class AccessorParametersUtils {
    private AccessorParametersUtils() {
    }

    /**
     * 给statement赋值，填充访问参数
     * @param statement 执行器
     * @param params 访问参数
     * @return 返回对象可以不必承接，该对象为原本的对象，所以不需要写新的声明，只需要执行原有的对象即可
     * @throws SQLException
     */
    public static PreparedStatement fillStatement(PreparedStatement statement, Object... params) throws SQLException {
        //判断params是否为空，如果为空则直接返回PreparedStatement对象
        if (params==null||params.length==0){
            return statement;
        }
        //循环给statement赋访问参数
        for (int i = 0; i < params.length; i++) {
            //判断当前参数 params[i] 是否为空
            if (params[i]!=null){
                //不为空则正常赋值
                statement.setObject(i+1,params[i]);
            }else {
                //为空则给该值赋空值
                statement.setNull(i+1, Types.VARCHAR);
            }
        }
        //返回PreparedStatement对象
        return statement;
    }

    /**
     * 通过对象生成 Object 数组，用于给数据库访问器带参
     * @param t 数据库操作实体类对象
     * @param <T> 可以收任意对象
     * @return Object 访问器数组
     */
    public static <T> Object[] generateObjects(T t){
        //判断实体类对象是否为空
        if (t==null){
            //实体类对象为空抛出异常
            throw new ReflexException("Entity Class is null...");
        }
        //声明 Object 数组为空
        Object[] objects = null;
        //获取 对象 的类对象
        Class<?> clz = t.getClass();
        //获取对象声明的字段数组
        Field[] fields = clz.getDeclaredFields();
        //创建 Object 对象
        objects = new Object[fields.length];
        //循环给 Object 对象赋值
        for (int i = 0; i < fields.length; i++) {
            //声明 Field 对象
            Field field = fields[i];
            //设置访问属性为真
            field.setAccessible(true);
            try {
                //给 objects 对象赋值
                objects[i] = field.get(t);
            } catch (IllegalAccessException e) {
                throw new ReflexException("Failed to obtain object field value...",e);
            }finally {
                //设置访问属性为假
                field.setAccessible(false);
            }
        }
        //返回 objects
        return objects;
    }

}
