package com.nf.annotate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 该注解用于设置实体类字段与数据库表中列名不同时，增加备注名
 * 如果实体类字段有该注解，则按该注解的值生成sql语句
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ColumnName {
    String value();
}
