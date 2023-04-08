package com.nf.handler.row.create;

import com.nf.ReflexException;
import com.nf.annotate.ColumnName;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Arrays;

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
        try {
            PropertyDescriptor[] pds = propertyDescriptor(bean.getClass());
            int[] columnToProperty = mapColumnToProperty(rs, pds,bean.getClass());
            //调用方法 populateBean
            return populateBean(rs,bean,pds,columnToProperty);
        }catch (Exception e){
            throw new ReflexException("data filling failed...",e);
        }
    }

    private static  <T> T populateBean(ResultSet rs,T bean,PropertyDescriptor[] pds,int[] columnToProperty) throws Exception {
        for (int i = 1; i < columnToProperty.length; i++) {
            if (columnToProperty[i]==-1){
                continue;
            }
            PropertyDescriptor pd = pds[columnToProperty[i]];
            Object value = rs.getObject(i);
            callSetter(bean,value,pd);
        }
        //返回对象 t
        return bean;
    }

    private static int[] mapColumnToProperty(ResultSet rs , PropertyDescriptor[] pds,Class<?> clz) throws Exception{

        ResultSetMetaData metaData = rs.getMetaData();
        int cols = metaData.getColumnCount();
        int[] columnToProperty = new int[cols+1];
        Arrays.fill(columnToProperty,-1);
        for (int col = 1; col <= cols; col++) {
            String columnName = metaData.getColumnLabel(col);
            if (columnName==null||columnName.isEmpty()){
                columnName = metaData.getColumnName(col);
            }
            if (columnName ==null){
                columnName = Integer.toString(col);
            }

            for (int i = 1; i < pds.length; i++) {
                PropertyDescriptor pd = pds[i];
                Field field = clz.getDeclaredField(pd.getName());
                ColumnName column;
                if (field!=null && field.isAnnotationPresent(ColumnName.class)){
                    column = field.getAnnotation(ColumnName.class);
                }else {
                    column =null;
                }
                String propertyColumnName;
                if (column!=null){
                    propertyColumnName = column.value();
                }else {
                    propertyColumnName = pd.getName();
                }
                if (columnName.equalsIgnoreCase(propertyColumnName)){
                    columnToProperty[col] = i;
                    break;
                }
            }
        }

        return columnToProperty;
    }

    private static <T> void callSetter(T bean, Object value, PropertyDescriptor pd){
        Method setter = pd.getWriteMethod();
        try {
            setter.invoke(bean,value);
        } catch (Exception e) {
            throw new ReflexException("data filling failed,setter in null...",e);
        }
    }
    private static PropertyDescriptor[] propertyDescriptor(Class<?> clz){
        BeanInfo beanInfo = null;
        try {
            beanInfo = Introspector.getBeanInfo(clz);
        } catch (IntrospectionException e) {
            throw new ReflexException("Create getter and setter failed...",e);
        }
        return beanInfo.getPropertyDescriptors();
    }
}
