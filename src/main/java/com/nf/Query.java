package com.nf;

import java.sql.Connection;
import java.util.List;

public interface Query {
    /**
     * 通过类型对象，返回对应的实体类对象
     *
     * @param clz    类对象
     * @param params 参数列表
     * @param <T>    可以接收泛型对象
     * @return 实体类对象
     */
    <T> T queryBean(final Class<? extends T> clz, final Object... params);

    /**
     * 通过类型对象，返回对应的实体类对象
     *
     * @param sql    sql语句
     * @param clz    类对象
     * @param params 参数列表
     * @param <T>    可以接收泛型对象
     * @return 实体类对象
     */
    <T> T queryBean(final String sql, final Class<? extends T> clz, final Object... params);

    /**
     * 通过类型对象，返回对应的实体类对象
     *
     * @param conn   数据库连接
     * @param clz    类对象
     * @param params 参数列表
     * @param <T>    可以接收泛型对象
     * @return 实体类对象
     */
    <T> T queryBean(final Connection conn, final Class<? extends T> clz, final Object... params);

    /**
     * 通过类型对象，返回对应的实体类对象
     *
     * @param conn   数据库连接
     * @param sql    sql语句
     * @param clz    类对象
     * @param params 参数列表
     * @param <T>    可以接收泛型对象
     * @return 实体类对象
     */
    <T> T queryBean(final Connection conn, final String sql, final Class<? extends T> clz, final Object... params);

    /**
     * 通过类型对象，返回对应的实体类列表对象
     *
     * @param clz    类对象
     * @param params 参数列表
     * @param <T>    可以接收泛型对象
     * @return @{List<T>} 实体类列表对象
     */
    <T> List<T> queryBeanList(Class<? extends T> clz, final Object... params);

    /**
     * 通过类型对象，返回对应的实体类列表对象
     *
     * @param sql    sql语句
     * @param clz    类对象
     * @param params 参数列表
     * @param <T>    可以接收泛型对象
     * @return @{List<T>} 实体类列表对象
     */
    <T> List<T> queryBeanList(final String sql, Class<? extends T> clz, final Object... params);

    /**
     * 通过类型对象，返回对应的实体类列表对象
     *
     * @param conn   数据库连接
     * @param clz    类对象
     * @param params 参数列表
     * @param <T>    可以接收泛型对象
     * @return @{List<T>} 实体类列表对象
     */
    <T> List<T> queryBeanList(final Connection conn, Class<? extends T> clz, final Object... params);

    /**
     * 通过类型对象，返回对应的实体类列表对象
     *
     * @param conn   数据库连接
     * @param sql    sql语句
     * @param clz    类对象
     * @param params 参数列表
     * @param <T>    可以接收泛型对象
     * @return @{List<T>} 实体类列表对象
     */
    <T> List<T> queryBeanList(final Connection conn, final String sql, Class<? extends T> clz, final Object... params);
}
