package com.nf.handler.row.create;

import com.nf.DaoException;
import com.nf.ReflexException;
import com.nf.annotate.ColumnName;
import com.nf.handler.row.FillBean;
import com.nf.util.ResultSetMetaUtils;

import javax.swing.*;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 该类用于bean的数据填充
 */
public class FillBeanRealize implements FillBean {

    //数据库查询数据与字段映射关系
    protected Map<String,String> propertyOverrides;
    //默认忽略值
    protected static final int  DEFAULT_NO_MAPPING_VALUE = -1;

    /**
     * 默认构造函数
     */
    public FillBeanRealize() {
        this(new HashMap<>());
    }

    /**
     * 设置数据库查询数据与字段映射关系
     * @param propertyOverrides
     */
    public FillBeanRealize(Map<String, String> propertyOverrides) {
        //判断映射关系是否为空
        if (propertyOverrides == null) {
            //为空抛出异常
            throw new DaoException("Field mapping relationship cannot be empty...");
        }
        this.propertyOverrides = propertyOverrides;
    }

    /**
     * 给 bean 填充数据
     * @param rs 结果集
     * @param bean 泛型对象
     * @param <T> 接受泛型
     * @return 原本的 带参 Bean 对象
     */
    public  <T> T populateBean(ResultSet rs,T bean) {
        try {
            //调用自身方法 propertyDescriptor 获取对象的属性
            PropertyDescriptor[] pds = this.propertyDescriptor(bean.getClass());
            //调用自身方法 mapColumnToProperty 获取 数据库表 与 bean 对象字段的映射关系
            int[] columnToProperty = this.mapColumnToProperty(rs.getMetaData(), pds,bean.getClass());
            //调用方法 populateBean 填充数据
            return populateBean(rs,bean,pds,columnToProperty);
        }catch (Exception e){
            //抛出无法填充数据异常
             throw new ReflexException("data filling failed...",e);
        }
    }

    /**
     * 给 bean 填充数据
     * @param rs 结果集
     * @param bean 泛型对象
     * @param pds bean 对象属性
     * @param columnToProperty 映射关系
     * @param <T> 泛型
     * @return 原本的 带参 Bean 对象
     * @throws Exception 数据库结果集取值异常
     */
    protected  <T> T populateBean(ResultSet rs,T bean,PropertyDescriptor[] pds,int[] columnToProperty) throws Exception {
        //循环映射关系
        //由于数据库数据是从1开始，所以从1开始循环，跳过无意义行
        for (int i = 1; i < columnToProperty.length; i++) {
            //判断该数据列是否有映射关系
            if (columnToProperty[i]==DEFAULT_NO_MAPPING_VALUE){
                //如果该数据为无映射关系则跳出此次循环
                continue;
            }

            //通过映射关系找到对应的属性
            PropertyDescriptor pd = pds[columnToProperty[i]];
            //判断属性是否为空
            if (pd==null){
                //为空跳出此次循环
                continue;
            }

            //获取数据
            Object value = rs.getObject(i);
            //判断返回值是否为空
            if (value==null){
                //如果数据为空，我们就什么事也不做
                //也就是说，我们不给这个对象的当前这个字段赋值
                continue;
            }
            //调用自身 callSetter 方法，给当前 bean 对象赋值
            this.callSetter(bean,value,pd);
        }
        //返回对象 t
        return bean;
    }

    /**
     * 获取映射关系数组
     * @param metaData 源数据
     * @param pds 属性数组
     * @param clz 类对象
     * @return 映射关系数组
     * @throws Exception 数据库列名获取异常，字段查询异常
     */
    protected int[] mapColumnToProperty(ResultSetMetaData metaData , PropertyDescriptor[] pds,Class<?> clz) throws Exception{
        //获取查询的行数
        int cols = metaData.getColumnCount();
        //声明映射关系数组 由于数据中的数据行是从 1 开始的，所以在Java的数组声明中需要 +1 ，方便后续的使用
        int[] columnToProperty = new int[cols+1];
        //赋默认映射值
        Arrays.fill(columnToProperty,DEFAULT_NO_MAPPING_VALUE);

        //循环数据行数
        for (int col = 1; col <= cols; col++) {
            //通过 工具类 ResultSetMetaUtils 的 getColumnName 方法获得行名称
            String columnName = ResultSetMetaUtils.getColumnName(metaData,col);
            //调用映射规则 getPropertyName 方法 获取数据库行的映射名称
            String propertyName = this.getPropertyName(columnName);

            //循环属性 属性有 class 这个默认字段，循环时跳过该字段
            for (int i = 1; i < pds.length; i++) {
                //获取属性
                PropertyDescriptor pd = pds[i];
                //获取字段
                Field field = clz.getDeclaredField(pd.getName());
                //声明字段注解
                ColumnName column;
                //判断字段是否为空，以及该字段是否有 ColumnName 注解
                if (field!=null && field.isAnnotationPresent(ColumnName.class)){
                    //为真获取注解
                    column = field.getAnnotation(ColumnName.class);
                }else {
                    //为假赋 null 值
                    column =null;
                }
                //声明字段的映射名称
                String propertyColumnName;
                //判断注解是否为空
                if (column!=null){
                    //注解不为空，按注解值赋值给 字段的映射名称
                    propertyColumnName = column.value();
                }else {
                    //注解为空则 按字段本身名称赋值给 字段的映射名称
                    propertyColumnName = pd.getName();
                }
                //不区分大小写判断 数据库行的映射名称 与 字段的映射名称 是否相同
                if (propertyName.equalsIgnoreCase(propertyColumnName)){
                    //相同记录映射关系
                    columnToProperty[col] = i;
                    //结束循环
                    break;
                }
            }
        }
        //返回映射关系数组
        return columnToProperty;
    }

    /**
     * 获取字段映射关系
     * @param columnName 数据库对象
     * @return 映射结果
     */
    protected String getPropertyName(String columnName){
        //使用三则运算，得到得到字段与数据库表列的映射关系
        //获取数据为空，则方法行名称，不为空，返回映射对象
        return this.propertyOverrides.get(columnName)==null?columnName:this.propertyOverrides.get(columnName);
    }

    /**
     * 执行赋值
     * @param bean 被赋值对象
     * @param value 值
     * @param pd 属性
     * @param <T> 泛型
     */
    protected <T> void callSetter(T bean, Object value, PropertyDescriptor pd){
        //获取 set 方法
        Method setter = pd.getWriteMethod();
        try {
            //判断set方法是否为空，以及set方法是否规范
            if (setter==null&&setter.getParameterCount()!=1){
                //执行方法
                setter.invoke(bean,value);
            }else {
                //抛出无 set 方法异常
                throw new ReflexException("setter in null...");
            }
        } catch (Exception e) {
            throw new ReflexException("data filling failed,setter in null...",e);
        }
    }

    /**
     * 获取bean对象属性
     * @param clz Bean 的类对象
     * @return bean 对象的属性数组
     */
    protected PropertyDescriptor[] propertyDescriptor(Class<?> clz){
        //声明 BeanInfo 序列化 bean
        BeanInfo beanInfo = null;
        try {
            //通过 Introspector 的 getBeanInfo 方法回去 BeanInfo 对象
            //该方法用于得到一个 有 bean 类对象字段组成， BeanInfo 对象
            //其 BeanInfo 对象，会自动生成 getter setter 访问器，以及一个public的无参数构造器
            //使得 Bean 类可以序列化
            beanInfo = Introspector.getBeanInfo(clz);
        } catch (IntrospectionException e) {
            //抛出创建 BeanInfo 异常
            throw new ReflexException("Create getter and setter failed...",e);
        }
        //返回属性数组
        return beanInfo.getPropertyDescriptors();
    }
}
