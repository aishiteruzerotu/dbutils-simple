package com.nf;

import com.nf.annotate.Auto;
import com.nf.annotate.ColumnName;
import com.nf.annotate.PrimaryKey;
import com.nf.annotate.TableName;
import com.nf.util.JavaBeanUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;

/**
 * 此类用于生成SQl语句，返回对象皆为字符串
 * 该类生成的 Sql 语句是根据类的字段与属性匹配的名称赋值的，
 * 如果没有相应的属性或是字段，则不会生成这个 操作的列名称
 * 是访问器相关的工具类
 */
public class GenerateSQLRealize implements GenerateSQL{
    public GenerateSQLRealize() {
    }

    /**
     * 给一个实体对象参数，返回该实体的SQL插入语句
     * @param t 实体对象
     * @return 返回一个SQL增加语句
     */
    public <T> String generateInsert(T t) {
        //声明类对象
        Class<?> clz = t.getClass();
        //声明 sql 对象
        StringBuffer sql = new StringBuffer();
        //添加增加格式头
        sql.append("insert into ");
        //添加表名称
        sql.append(this.getTableName(clz));
        //添加格式
        sql.append("(");
        //获取对象属性
        PropertyDescriptor[] pds = JavaBeanUtils.getPropertyDescriptors(t.getClass());
        //循环属性
        for (int i = 1; i < pds.length; i++) {
            //获取字信息
            Field field = JavaBeanUtils.getDeclaredField(clz,pds[i].getName());
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
            //添加列名称
            sql.append(this.getColumnName(field));
            //下面为序列化操作
            if (i != pds.length - 1) {
                sql.append( ",");
            }else{
                sql.append( ") values(");
            }
        }
        for (int i = 1; i < pds.length; i++) {
            //获取字段
            Field field = JavaBeanUtils.getDeclaredField(clz,pds[i].getName());
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
            //添加占位符
            sql.append( "?");
            //下面为序列化操作
            if (i != pds.length - 1) {
                sql.append( ",");
            }else  {
                sql.append( ")");
            }
        }
        //返回数据库增加语句
        return sql.toString();
    }

    /**
     * 给一个类对象 返回该对象的sql查询语句
     * @param clz 类对象
     * @return 数据库 查询语句
     */
    public String generateSelect(Class<?> clz){
        //声明 sql
        StringBuffer sql = new StringBuffer();
        //添加查询头
        sql.append("select ");
        //获取对象的字段
        Field[] fields = clz.getDeclaredFields();
        //根据获取到的字段进行循环
        for (int i = 0;i<fields.length;i++) {
            //循环添加查询列
            //声明字段
            Field field = fields[i];
            //添加查询列名称
            sql.append(this.getColumnName(field));
            //判断是否为最后一个字段
            if (i==fields.length-1)
                //是则跳出循环
                continue;
            //为查询列之间添加空格
            sql.append(",");
        }
        //添加指向对象
        sql.append(" from ");
        //添加查询的表名
        sql.append(this.getTableName(clz));
        //返回查询语句
        return sql.toString();
    }

    /**
     * 给一个类对象 返回该对象的sql删除语句
     * @param clz 类对象
     * @return 数据库 删除语句
     */
    public String generateDelete(Class<?> clz){
        //声明 sql
        StringBuffer sql = new StringBuffer();
        //添加删除头
        sql.append("delete from ");
        //添加表名称
        sql.append(this.getTableName(clz));
        //添加 where
        sql.append(" where ");
        //获取属性
        PropertyDescriptor[] pds = JavaBeanUtils.getPropertyDescriptors(clz);
        //声明 主键
        String isPrimaryKey = null;
        //循环字段
        for (int i = 0; i < pds.length; i++) {
            //获取字段信息
            Field field = JavaBeanUtils.getDeclaredField(clz,pds[i].getName());
            //判断获取的字段是否为空
            if (field==null) {
                //获取的字段为空跳出循环
                continue;
            }
            //判断该字段是否为设定主键
            if (field.isAnnotationPresent(PrimaryKey.class)) {
                //调用自身 getColumnName 获取列名
                isPrimaryKey = this.getColumnName(field);
            }
        }
        //判断是否有给 主键设置
        if (isPrimaryKey == null || isPrimaryKey.isEmpty()){
            //未设置，取 id为主键
            isPrimaryKey = "id";
        }
        //添加主键
        sql.append(isPrimaryKey);
        //添加条件
        sql.append("=?");
        //返回查询语句
        return sql.toString();
    }

    /**
     * 获取sql语句列名
     * @param field 字段信息
     * @return 列名称
     */
    protected String getColumnName(Field field){
        //判断是否有 @ColumnName 注解
        if (field.isAnnotationPresent(ColumnName.class)) {
            //有则返回注解名称
            return field.getAnnotation(ColumnName.class).value();
        }else {
            //没有则返回字段名称
            return field.getName();
        }
    }

    /**
     * 获取sql语句表名
     * @param clz bean类对象信息
     * @return 表名称
     */
    protected String getTableName(Class<?> clz){
        //判断该实体类是否有 @TableName 注解
        if (clz.isAnnotationPresent(TableName.class)){
            //有注解则按注解的值查询数据库
            return clz.getAnnotation(TableName.class).value();
        }else {
            //没有注解则按类名称
            return clz.getSimpleName();
        }
    }
}
