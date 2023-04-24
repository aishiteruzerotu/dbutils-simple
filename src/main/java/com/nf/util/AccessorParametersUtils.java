package com.nf.util;

import com.nf.ReflexException;
import com.nf.annotate.Auto;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

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
        //获取对象属性
        PropertyDescriptor[] pds = JavaBeanUtils.getPropertyDescriptors(t.getClass());
        //声明 objectList 列表
        List<Object> objectList = new ArrayList<>();
        //循环给 objectList 列表赋值
        for (int i = 0; i < pds.length; i++) {
            //声明 属性对象
            PropertyDescriptor pd = pds[i];
            //获取字段信息
            Field field = JavaBeanUtils.getDeclaredField(t.getClass(),pds[i].getName());
            //判断获取的字段是否为空
            if (field==null) {
                //获取的字段为空跳出循环
                continue;
            }
            //判断当前字段是否是自增长
            if (field.isAnnotationPresent(Auto.class)) {
                //是，结束此次循环
                continue;
            }
            //获取 getter 方法
            Method getter = pd.getReadMethod();
            //判断 getter 方法是否为空，且方法格式正确
            if(getter==null&&getter.getParameters().length!=0){
                continue;
            }
            try {
                //添加数据
                objectList.add(getter.invoke(t));
            } catch (Exception e) {
                //抛出无法执行 getter 方法异常
                throw new ReflexException("Failed to obtain object field value...",e);
            }
        }
        //返回一个 Object 数组
        return objectList.toArray();
    }
}
