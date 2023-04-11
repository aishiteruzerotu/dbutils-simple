package com.nf.util;

import com.nf.ReflexException;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;

/**
 * 该类用于处理bean对象的属性
 */
public class JavaBeanUtils {
    private JavaBeanUtils(){}

    /**
     * 给一个bean对象的类对象数据
     * @param clz 类对象
     * @return 返回该对象的属性数组
     */
    public static PropertyDescriptor[] getPropertyDescriptors(Class<?> clz) {
        //声明 BeanInfo 序列化 bean
        BeanInfo beanInfo = null;
        try {
            //通过 Introspector 的 getBeanInfo 方法回去 BeanInfo 对象
            //该方法用于解析 bean 对象，该对象需要包含其字段的 getter 或 setter方法
            //否则不会有该字段的 PropertyDescriptor 属性信息
            beanInfo = Introspector.getBeanInfo(clz);
        } catch (IntrospectionException e) {
            //抛出创建 BeanInfo 异常
            throw new ReflexException("Create getter and setter failed...",e);
        }
        //返回属性数组
        return beanInfo.getPropertyDescriptors();
    }

    /**
     * 获取字段信息
     * @param clz 类对象
     * @param name 字段名称
     * @return 字段信息
     */
    public static Field getDeclaredField(Class<?>clz, String name){
        try {
            //获取字段信息
            return clz.getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            //抛出无法获取字段信息异常
            throw new ReflexException("Unable to obtain field...",e);
        }
    }
}
