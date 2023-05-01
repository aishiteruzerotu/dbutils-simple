package com.nf;

import java.sql.Connection;

public interface Update {

    /**
     * 通过对象可以自动插入一行对应的数据
     *
     * @param t   对象
     * @param <T> 可以接收泛型对象
     * @return 一个被修改的行数
     */
    <T> int insertObject(T t);

    /**
     * 通过对象可以自动插入一行对应的数据
     *
     * @param sql sql语句
     * @param t   对象
     * @param <T> 可以接收泛型对象
     * @return 一个被修改的行数
     */
    <T> int insertObject(final String sql, T t);

    /**
     * 通过对象可以自动插入一行对应的数据
     *
     * @param conn 数据库连接
     * @param t    对象
     * @param <T>  可以接收泛型对象
     * @return 一个被修改的行数
     */
    <T> int insertObject(final Connection conn, T t);

    /**
     * 通过对象可以自动插入一行对应的数据
     *
     * @param conn 数据库连接
     * @param sql  sql语句
     * @param t    对象
     * @param <T>  可以接收泛型对象
     * @return 一个被修改的行数
     */
    <T> int insertObject(final Connection conn, final String sql, T t);

    /**
     * 根据类对象信息生成一个数据库 delete 删除语句，
     * 只能根据主键进行删除
     * 如果有主键的注解(@PrimaryKey)则按注解的字段生成 sql 删除语句
     * 如果没有默认 按照 id 字段进行删除
     * 如果没有指定字段，也没有 id 字段，则无法正常使用该方法
     *
     * @param clz        类对象
     * @param primaryKey 主键，删除对象的凭证(依据)
     * @return 数据库被影响的行数
     */
    int delete(final Class<?> clz, Object primaryKey);

    /**
     * 根据类对象信息生成一个数据库 delete 删除语句，
     * 只能根据主键进行删除
     * 如果有主键的注解(@PrimaryKey)则按注解的字段生成 sql 删除语句
     * 如果没有默认 按照 id 字段进行删除
     * 如果没有指定字段，也没有 id 字段，则无法正常使用该方法
     *
     * @param conn       数据库连接
     * @param clz        类对象
     * @param primaryKey 主键，删除对象的凭证(依据)
     * @return 数据库被影响的行数
     */
    int delete(final Connection conn, final Class<?> clz, Object primaryKey);

    /**
     * 对数据库进行更新操作，并返回一个被修改的行数
     *
     * @param sql    sql更新语句
     * @param params 访问参数
     * @return 被影响的行数
     */
    int update(final String sql, final Object... params);

    /**
     * 对数据库进行更新操作，并返回一个被修改的行数
     *
     * @param conn   数据库连接
     * @param sql    sql更新语句
     * @param params 访问参数
     * @return 被影响的行数
     */
    int update(final Connection conn, final String sql, final Object... params);
}
