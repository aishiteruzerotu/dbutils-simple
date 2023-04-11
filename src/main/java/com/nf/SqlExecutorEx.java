package com.nf;

import com.nf.handler.BeanHandler;
import com.nf.handler.BeanListHandler;
import com.nf.util.AccessorParametersUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.List;

/**
 * 封装 SqlExecutor
 * 增加功能
 */
public class SqlExecutorEx extends SqlExecutor{
    //生成 sql 语句依赖对象
    protected GenerateSQL generateSQL;
    //默认生成 sql 语句依赖
    protected static final GenerateSQL DEFAULT_GENERATE_SQL = new GenerateSQLRealize();

    /**
     * 默认构造器
     */
    public SqlExecutorEx() {
        this(null,DEFAULT_GENERATE_SQL);
    }

    /**
     * 创建对象
     *
     * @param ds DataSource对象
     */
    public SqlExecutorEx(final DataSource ds) {
        this(ds,DEFAULT_GENERATE_SQL);
    }

    /**
     * 生成对象
     * @param ds 连接池
     * @param generateSQL 依赖对象
     */
    public SqlExecutorEx(final DataSource ds,final GenerateSQL generateSQL){
        this.ds = ds;
        this.generateSQL = generateSQL;
    }

    /**
     * 通过对象可以自动插入一行对应的数据
     *
     * @param t   对象
     * @param <T> 可以接收泛型对象
     * @return 一个被修改的行数
     */
    public <T> int insertObject(T t) {
        //获取连接
        Connection conn = this.prepareConnection();
        //获取插入语句
        String sql = this.generateSQL.generateInsert(t);
        //调用自身 insertObject 方法
        return this.insertObject(conn, true, sql, t);
    }

    /**
     * 通过对象可以自动插入一行对应的数据
     *
     * @param sql sql语句
     * @param t   对象
     * @param <T> 可以接收泛型对象
     * @return 一个被修改的行数
     */
    public <T> int insertObject(final String sql, T t) {
        //获取连接
        Connection conn = this.prepareConnection();
        //调用自身 insertObject 方法
        return this.insertObject(conn, true, sql, t);
    }

    /**
     * 通过对象可以自动插入一行对应的数据
     *
     * @param conn 数据库连接
     * @param t    对象
     * @param <T>  可以接收泛型对象
     * @return 一个被修改的行数
     */
    public <T> int insertObject(final Connection conn, T t) {
        //获取插入语句
        String sql = this.generateSQL.generateInsert(t);
        //调用自身 insertObject 方法
        return this.insertObject(conn, false, sql, t);
    }

    /**
     * 通过对象可以自动插入一行对应的数据
     *
     * @param conn 数据库连接
     * @param sql  sql语句
     * @param t    对象
     * @param <T>  可以接收泛型对象
     * @return 一个被修改的行数
     */
    public <T> int insertObject(final Connection conn, final String sql, T t) {
        //调用自身 insertObject 方法
        return this.insertObject(conn, false, sql, t);
    }

    /**
     * 通过对象可以自动插入一行对应的数据
     *
     * @param conn      数据库连接
     * @param closeConn 是否关闭数据库连接
     * @param sql       sql语句
     * @param t         对象
     * @param <T>       可以接收泛型对象
     * @return 一个被修改的行数
     */
    private <T> int insertObject(final Connection conn, final boolean closeConn, final String sql, T t) {
        //获取 Object 数组，用于数据库连接参数
        Object[] objects = AccessorParametersUtils.generateObjects(t);
        //调用自身的 .update() 方法 , 进行更新操作
        return this.update(conn, closeConn, sql, objects);
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

    /**
     * 根据类对象信息生成一个数据库 delete 删除语句，
     * 只能根据主键进行删除
     * 如果有主键的注解(@PrimaryKey)则按注解的字段生成 sql 删除语句
     * 如果没有默认 按照 id 字段进行删除
     * 如果没有指定字段，也没有 id 字段，则无法正常使用该方法
     * @param clz 类对象
     * @param primaryKey 主键，删除对象的凭证(依据)
     * @return 数据库被影响的行数
     */
    public int delete(final Class<?> clz, Object primaryKey) {
        //获取连接
        Connection conn = this.prepareConnection();
        //生成 sql 删除语句
        String sql = this.generateSQL.generateDelete(clz);
        //调用自身 delete 方法
        return this.delete(conn, true, sql, primaryKey);
    }

    /**
     * 根据类对象信息生成一个数据库 delete 删除语句，
     * 只能根据主键进行删除
     * 如果有主键的注解(@PrimaryKey)则按注解的字段生成 sql 删除语句
     * 如果没有默认 按照 id 字段进行删除
     * 如果没有指定字段，也没有 id 字段，则无法正常使用该方法
     * @param conn 数据库连接
     * @param clz 类对象
     * @param primaryKey 主键，删除对象的凭证(依据)
     * @return 数据库被影响的行数
     */
    public int delete(final Connection conn, final Class<?> clz, Object primaryKey) {
        //生成 sql 删除语句
        String sql = this.generateSQL.generateDelete(clz);
        //调用自身 delete 方法
        return this.delete(conn, false, sql, primaryKey);
    }

    /**
     * 调用器，用于更改调用名称
     */
    private int delete(final Connection conn, final boolean closeConn, final String sql, Object primaryKey) {
        //调用自身的 update 方法
        return this.update(conn, closeConn, sql, primaryKey);
    }
}
