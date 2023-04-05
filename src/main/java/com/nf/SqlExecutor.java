package com.nf;

import com.nf.handler.BeanHandler;
import com.nf.handler.BeanListHandler;
import com.nf.util.AccessorParametersUtils;
import com.nf.util.GenerateSQLUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

/**
 * 该类用于数据库操作
 * 是数据库操作工具类
 */
public class SqlExecutor extends AbstractSqlExecutor {

    /**
     * 默认构造器
     */
    public SqlExecutor() {
        super();
    }

    /**
     * 创建对象
     *
     * @param ds DataSource对象
     */
    public SqlExecutor(final DataSource ds) {
        super(ds);
    }

    /**
     * 对数据库进行更新操作，并返回一个被修改的行数
     *
     * @param sql    sql更新语句
     * @param params 访问参数
     * @return 被影响的行数
     */
    public int update(final String sql, final Object... params) {
        //声明一个数据库连接 Connection 对象，调用自身方法为其赋值
        Connection conn = this.prepareConnection();
        //调用自身的 .update() 方法
        return this.update(conn, true, sql, params);
    }

    /**
     * 对数据库进行更新操作，并返回一个被修改的行数
     *
     * @param conn   数据库连接
     * @param sql    sql更新语句
     * @param params 访问参数
     * @return 被影响的行数
     */
    public int update(final Connection conn, final String sql, final Object... params) {
        //调用自身的 .update() 方法
        return this.update(conn, false, sql, params);
    }

    /**
     * 对数据库进行更新操作，并返回一个被修改的行数
     *
     * @param conn      数据库连接
     * @param closeConn 是否关闭数据库连接，输入 true 值关闭数据库连接，false 则不关闭
     * @param sql       sql更新语句
     * @param params    访问参数
     * @return 被影响的行数
     */
    private int update(final Connection conn, final boolean closeConn, final String sql, final Object... params) {
        try {
            //捕获异常
            return this.update0(conn, closeConn, sql, params);
        } catch (SQLException e) {
            //抛出更新失败异常
            throw new DaoException("update defeated...", e);
        }
    }

    /**
     * 对数据库进行更新操作，并返回一个被修改的行数
     *
     * @param conn      数据库连接
     * @param closeConn 是否关闭数据库连接，输入 true 值关闭数据库连接，false 则不关闭
     * @param sql       sql更新语句
     * @param params    访问参数
     * @return 被影响的行数
     */
    private int update0(final Connection conn, final boolean closeConn, final String sql, final Object... params) throws SQLException {
        //检查数据库连接是否为空
        checkConnection(conn);
        //检查sql语句是否为空
        checkSql(conn, closeConn, sql);

        //声明PreparedStatement对象 statement 为空
        PreparedStatement statement = null;
        //声明被修改的行数 rows
        int rows = 0;
        try {
            //从数据库连接中获取 PreparedStatement 对象 并赋值给 statement
            statement = conn.prepareStatement(sql);
            //给执行器 statement 填入参数
            statement = this.fillStatement(statement, params);
            //使用statement执行器的.executeUpdate() 更新方法，并将返回值 赋值给 rows
            rows = statement.executeUpdate();
        } catch (SQLException e) {
            //抛出更新失败异常
            throw new DaoException("update defeated...", e);
        } finally {
            //关闭执行器 statement
            close(statement);
            //判断是否需要关闭连接
            if (closeConn) {
                //为真关闭连接
                close(conn);
            }
        }
        //返回被修改的行数 rows
        return rows;
    }

    /**
     * 对数据执行查询操作，返回一个 用户定义的对象
     *
     * @param sql    sql查询语句
     * @param params 访问参数
     * @return 一个对象
     */
    public <T> T query(final String sql, final ResultSetHandler<T> rsh, final Object... params) {
        //调用 prepareConnection 获取数据库连接对象
        Connection conn = this.prepareConnection();
        //调用自身的 query 方法
        return this.query(conn, true, sql, rsh, params);
    }

    /**
     * 对数据执行查询操作，返回一个 用户定义的对象
     *
     * @param sql    sql查询语句
     * @param params 访问参数
     * @return 一个对象
     */
    public <T> T query(final Connection conn, final String sql, final ResultSetHandler<T> rsh, final Object... params) {
        //调用自身的 query 方法
        return this.query(conn, false, sql, rsh, params);
    }

    /**
     * 对数据执行查询操作，返回一个 用户定义的对象
     *
     * @param conn      数据库连接
     * @param closeConn 是否关闭数据库连接，输入 true 值关闭数据库连接，false 则不关闭
     * @param sql       sql查询语句
     * @param params    访问参数
     * @return 一个对象
     */
    private <T> T query(final Connection conn, final boolean closeConn, final String sql, final ResultSetHandler<T> rsh, final Object... params) {
        try {
            //捕获异常
            return this.query0(conn, closeConn, sql, rsh, params);
        } catch (SQLException e) {
            //抛出查询失败异常
            throw new DaoException("query defeated...", e);
        }
    }

    /**
     * 对数据执行查询操作，返回一个 用户定义的对象
     *
     * @param conn      数据库连接
     * @param closeConn 是否关闭数据库连接，输入 true 值关闭数据库连接，false 则不关闭
     * @param sql       sql查询语句
     * @param params    访问参数
     * @return 一个对象
     */
    private <T> T query0(final Connection conn, final boolean closeConn, final String sql, final ResultSetHandler<T> rsh, final Object... params) throws SQLException {
        //检查数据库连接是否为空
        checkConnection(conn);
        //检查sql语句是否为空
        checkSql(conn, closeConn, sql);
        //检查ResultSetHandler对象是否为空
        checkResultSetHandler(conn, closeConn, rsh);

        //声明PreparedStatement对象 statement 为空
        PreparedStatement statement = null;
        //声明返回结果集为空
        ResultSet rs = null;
        //声明返回的对象为空
        T result = null;
        try {
            //从数据库连接中获取 PreparedStatement 对象 并赋值给 statement
            statement = conn.prepareStatement(sql);
            //给执行器 statement 填入参数
            statement = this.fillStatement(statement, params);
            //使用statement执行器的.executeQuery() 查询方法，得到查询结果集
            rs = statement.executeQuery();
            //使用 ResultSetHandler 对象的 handler 方法，得到一个返回对象
            result = rsh.handler(rs);
        } catch (SQLException e) {
            //抛出查询失败异常
            throw new DaoException("query defeated...", e);
        } finally {
            //关闭结果集
            close(rs);
            //关闭执行器 statement
            close(statement);
            //判断是否需要关闭连接
            if (closeConn) {
                //为真关闭连接
                close(conn);
            }

        }
        //返回对象 result
        return result;
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
        String sql = GenerateSQLUtils.generateInsert(t);
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
        String sql = GenerateSQLUtils.generateInsert(t);
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
     * @param clz       类对象
     * @param params    参数列表
     * @param <T>       可以接收泛型对象
     * @return 实体类对象
     */
    public <T> T queryBean(final Class<? extends T> clz, final Object... params){
        //获取连接
        Connection conn = this.prepareConnection();
        //获取查询字段
        String sql = GenerateSQLUtils.generateSelect(clz);
        //调用自身 queryBean 方法
        return this.queryBean(conn,true,sql,clz,params);
    }


    /**
     * 通过类型对象，返回对应的实体类对象
     * @param sql       sql语句
     * @param clz       类对象
     * @param params    参数列表
     * @param <T>       可以接收泛型对象
     * @return 实体类对象
     */
    public <T> T queryBean(final String sql, final Class<? extends T> clz, final Object... params){
        //获取连接
        Connection conn = this.prepareConnection();
        //调用自身 queryBean 方法
        return this.queryBean(conn,true,sql,clz,params);
    }

    /**
     * 通过类型对象，返回对应的实体类对象
     * @param conn      数据库连接
     * @param clz       类对象
     * @param params    参数列表
     * @param <T>       可以接收泛型对象
     * @return 实体类对象
     */
    public <T> T queryBean(final Connection conn,  final Class<? extends T> clz, final Object... params){
        //获取查询字段
        String sql = GenerateSQLUtils.generateSelect(clz);
        //调用自身 queryBean 方法
        return this.queryBean(conn,false,sql,clz,params);
    }


    /**
     * 通过类型对象，返回对应的实体类对象
     * @param conn      数据库连接
     * @param sql       sql语句
     * @param clz       类对象
     * @param params    参数列表
     * @param <T>       可以接收泛型对象
     * @return 实体类对象
     */
    public <T> T queryBean(final Connection conn, final String sql, final Class<? extends T> clz, final Object... params){
        //调用自身 queryBean 方法
        return this.queryBean(conn,false,sql,clz,params);
    }

    /**
     * 通过类型对象，返回对应的实体类对象
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
        return this.query(conn,closeConn, sql,rsh, params);
    }

    /**
     * 通过类型对象，返回对应的实体类列表对象
     * @param clz       类对象
     * @param params    参数列表
     * @param <T>       可以接收泛型对象
     * @return @{List<T>} 实体类列表对象
     */
    public <T> List<T> queryBeanList( Class<? extends T> clz, final Object... params){
        //获取连接
        Connection conn = this.prepareConnection();
        //获取查询字段
        String sql = GenerateSQLUtils.generateSelect(clz);
        //调用自身 queryBeanList 方法
        return this.queryBeanList(conn,true,sql,clz,params);
    }

    /**
     * 通过类型对象，返回对应的实体类列表对象
     * @param sql       sql语句
     * @param clz       类对象
     * @param params    参数列表
     * @param <T>       可以接收泛型对象
     * @return @{List<T>} 实体类列表对象
     */
    public <T> List<T> queryBeanList(final String sql, Class<? extends T> clz, final Object... params){
        //获取连接
        Connection conn = this.prepareConnection();
        //调用自身 queryBeanList 方法
        return this.queryBeanList(conn,true,sql,clz,params);
    }

    /**
     * 通过类型对象，返回对应的实体类列表对象
     * @param conn      数据库连接
     * @param clz       类对象
     * @param params    参数列表
     * @param <T>       可以接收泛型对象
     * @return @{List<T>} 实体类列表对象
     */
    public <T> List<T> queryBeanList(final Connection conn, Class<? extends T> clz, final Object... params){
        //获取查询字段
        String sql = GenerateSQLUtils.generateSelect(clz);
        //调用自身 queryBeanList 方法
        return this.queryBeanList(conn,false,sql,clz,params);
    }

    /**
     * 通过类型对象，返回对应的实体类列表对象
     * @param conn      数据库连接
     * @param sql       sql语句
     * @param clz       类对象
     * @param params    参数列表
     * @param <T>       可以接收泛型对象
     * @return @{List<T>} 实体类列表对象
     */
    public <T> List<T> queryBeanList(final Connection conn, final String sql, Class<? extends T> clz, final Object... params){
        //调用自身 queryBeanList 方法
        return this.queryBeanList(conn,false,sql,clz,params);
    }

    /**
     * 通过类型对象，返回对应的实体类列表对象
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
        return (List<T>) this.query(conn,closeConn, sql,blh, params);
    }
}
