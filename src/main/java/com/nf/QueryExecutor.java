package com.nf;

import com.nf.handler.BeanHandler;
import com.nf.handler.BeanListHandler;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.List;

public class QueryExecutor extends SqlExecutor implements Query{
    //生成 sql 语句依赖对象
    protected GenerateSQL generateSQL;
    //默认生成 sql 语句依赖
    protected static final GenerateSQL DEFAULT_GENERATE_SQL = new GenerateSQLRealize();

    /**
     * 默认构造器
     */
    public QueryExecutor() {
        this(null,DEFAULT_GENERATE_SQL);
    }

    /**
     * 设置 sql 语句生成依赖
     * @param generateSQL 依赖对象
     */
    public QueryExecutor(GenerateSQL generateSQL) {
        this.generateSQL = generateSQL;
    }

    /**
     * 创建对象
     *
     * @param ds DataSource对象
     */
    public QueryExecutor(final DataSource ds) {
        this(ds,DEFAULT_GENERATE_SQL);
    }

    /**
     * 生成对象
     * @param ds 连接池
     * @param generateSQL 依赖对象
     */
    public QueryExecutor(final DataSource ds, final GenerateSQL generateSQL){
        this.ds = ds;
        this.generateSQL = generateSQL;
    }

    /**
     * 通过类型对象，返回对应的实体类对象
     *
     * @param clz    类对象
     * @param params 参数列表
     * @param <T>    可以接收泛型对象
     * @return 实体类对象
     */
    public <T> T queryBean(final Class<? extends T> clz, final Object... params) {
        //获取连接
        Connection conn = this.prepareConnection();
        //获取查询字段
        String sql = this.generateSQL.generateSelect(clz);
        //调用自身 queryBean 方法
        return this.queryBean(conn, true, sql, clz, params);
    }

    /**
     * 通过类型对象，返回对应的实体类对象
     *
     * @param sql    sql语句
     * @param clz    类对象
     * @param params 参数列表
     * @param <T>    可以接收泛型对象
     * @return 实体类对象
     */
    public <T> T queryBean(final String sql, final Class<? extends T> clz, final Object... params) {
        //获取连接
        Connection conn = this.prepareConnection();
        //调用自身 queryBean 方法
        return this.queryBean(conn, true, sql, clz, params);
    }

    /**
     * 通过类型对象，返回对应的实体类对象
     *
     * @param conn   数据库连接
     * @param clz    类对象
     * @param params 参数列表
     * @param <T>    可以接收泛型对象
     * @return 实体类对象
     */
    public <T> T queryBean(final Connection conn, final Class<? extends T> clz, final Object... params) {
        //获取查询字段
        String sql = this.generateSQL.generateSelect(clz);
        //调用自身 queryBean 方法
        return this.queryBean(conn, false, sql, clz, params);
    }

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
    public <T> T queryBean(final Connection conn, final String sql, final Class<? extends T> clz, final Object... params) {
        //调用自身 queryBean 方法
        return this.queryBean(conn, false, sql, clz, params);
    }

    /**
     * 通过类型对象，返回对应的实体类对象
     *
     * @param conn      数据库连接
     * @param closeConn 是否关闭数据库连接
     * @param sql       sql语句
     * @param clz       类对象
     * @param params    参数列表
     * @param <T>       可以接收泛型对象
     * @return 实体类对象
     */
    private <T> T queryBean(final Connection conn, final boolean closeConn, final String sql, final Class<? extends T> clz, final Object... params) {
        //声明 ResultSetHandler 对象
        ResultSetHandler<T> rsh = new BeanHandler<>(clz);
        //调用自身 query 方法
        return this.query(conn, closeConn, sql, rsh, params);
    }

    /**
     * 通过类型对象，返回对应的实体类列表对象
     *
     * @param clz    类对象
     * @param params 参数列表
     * @param <T>    可以接收泛型对象
     * @return @{List<T>} 实体类列表对象
     */
    public <T> List<T> queryBeanList(Class<? extends T> clz, final Object... params) {
        //获取连接
        Connection conn = this.prepareConnection();
        //获取查询字段
        String sql = this.generateSQL.generateSelect(clz);
        //调用自身 queryBeanList 方法
        return this.queryBeanList(conn, true, sql, clz, params);
    }

    /**
     * 通过类型对象，返回对应的实体类列表对象
     *
     * @param sql    sql语句
     * @param clz    类对象
     * @param params 参数列表
     * @param <T>    可以接收泛型对象
     * @return @{List<T>} 实体类列表对象
     */
    public <T> List<T> queryBeanList(final String sql, Class<? extends T> clz, final Object... params) {
        //获取连接
        Connection conn = this.prepareConnection();
        //调用自身 queryBeanList 方法
        return this.queryBeanList(conn, true, sql, clz, params);
    }

    /**
     * 通过类型对象，返回对应的实体类列表对象
     *
     * @param conn   数据库连接
     * @param clz    类对象
     * @param params 参数列表
     * @param <T>    可以接收泛型对象
     * @return @{List<T>} 实体类列表对象
     */
    public <T> List<T> queryBeanList(final Connection conn, Class<? extends T> clz, final Object... params) {
        //获取查询字段
        String sql = this.generateSQL.generateSelect(clz);
        //调用自身 queryBeanList 方法
        return this.queryBeanList(conn, false, sql, clz, params);
    }

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
    public <T> List<T> queryBeanList(final Connection conn, final String sql, Class<? extends T> clz, final Object... params) {
        //调用自身 queryBeanList 方法
        return this.queryBeanList(conn, false, sql, clz, params);
    }

    /**
     * 通过类型对象，返回对应的实体类列表对象
     *
     * @param conn      数据库连接
     * @param closeConn 是否关闭数据库连接
     * @param sql       sql语句
     * @param clz       类对象
     * @param params    参数列表
     * @param <T>       可以接收泛型对象
     * @return @{List<T>} 实体类列表对象
     */
    private <T> List<T> queryBeanList(final Connection conn, final boolean closeConn, final String sql, Class<? extends T> clz, final Object... params) {
        //声明 ResultSetHandler 子类 BeanListHandler 对象
        BeanListHandler<List<T>> blh = new BeanListHandler(clz);
        //调用自身 query 方法
        return (List<T>) this.query(conn, closeConn, sql, blh, params);
    }

}
