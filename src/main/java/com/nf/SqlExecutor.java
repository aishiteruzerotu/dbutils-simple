package com.nf;

import javax.sql.DataSource;
import java.sql.*;

/**
 * 该类用于数据库操作
 * 是数据库操作工具类
 */
public class SqlExecutor extends AbstractSqlExecutor{

    /**
     * 默认构造器
     */
    public SqlExecutor() {
        super();
    }

    /**
     * 创建对象
     * @param ds DataSource对象
     */
    public SqlExecutor(final DataSource ds) {
        super(ds);
    }

    /**
     * 对数据库进行更新操作，并返回一个被修改的行数
     * @param sql sql更新语句
     * @param params 访问参数
     * @return 被影响的行数
     */
    public int update(final String sql,final Object... params){
        //声明一个数据库连接 Connection 对象，调用自身方法为其赋值
        Connection conn = this.prepareConnection();
        //调用自身的 .update() 方法
        return this.update(conn,true,sql,params);
    }

    /**
     * 对数据库进行更新操作，并返回一个被修改的行数
     * @param conn 数据库连接
     * @param sql sql更新语句
     * @param params 访问参数
     * @return 被影响的行数
     */
    public int update(final Connection conn,final String sql,final Object... params){
        //调用自身的 .update() 方法
        return this.update(conn,false,sql,params);
    }

    /**
     * 对数据库进行更新操作，并返回一个被修改的行数
     * @param conn 数据库连接
     * @param closeConn 是否关闭数据库连接，输入 true 值关闭数据库连接，false 则不关闭
     * @param sql sql更新语句
     * @param params 访问参数
     * @return 被影响的行数
     */
    private int update(final Connection conn,final boolean closeConn,final String sql,final Object... params){
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
            statement = this.fillStatement(statement,params);
            //使用statement执行器的.executeUpdate() 更新方法，并将返回值 赋值给 rows
            rows = statement.executeUpdate();
        }catch (SQLException e){
            //抛出更新失败异常
            throw new DaoException("update defeated...",e);
        }finally {
            //关闭执行器 statement
            ResourceCleanerUtils.closeQuietly(statement);
            //判断是否需要关闭连接
            if (closeConn){
                //为真关闭连接
                ResourceCleanerUtils.closeQuietly(conn);
            }
        }
        //返回被修改的行数 rows
        return rows;
    }

    /**
     * 对数据库进行更新操作，并返回一个被修改的行数
     * @param conn 数据库连接
     * @param closeConn 是否关闭数据库连接，输入 true 值关闭数据库连接，false 则不关闭
     * @param sql sql更新语句
     * @param params 访问参数
     * @return 被影响的行数
     */
    private <T> T query(final Connection conn,final boolean closeConn,final String sql,final ResultSetHandler<T> rsh,final Object... params){
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
            statement = this.fillStatement(statement,params);
            //使用statement执行器的.executeQuery() 查询方法，得到查询结果集
            rs = statement.executeQuery();
            //使用 ResultSetHandler 对象的 handler 方法，得到一个返回对象
            result = rsh.handler(rs);
        }catch (SQLException e){
            //抛出更新失败异常
            throw new DaoException("query defeated...",e);
        }finally {
            try {
                //关闭结果集
                ResourceCleanerUtils.close(rs);
                //关闭执行器 statement
                ResourceCleanerUtils.closeQuietly(statement);
                //判断是否需要关闭连接
                if (closeConn){
                    //为真关闭连接
                    ResourceCleanerUtils.closeQuietly(conn);
                }
            }catch (SQLException e){
                //抛出资源关闭异常
                throw new DaoException("Resource shutdown failed...",e);
            }
        }
        //返回对象 result
        return result;
    }

    /**
     * 通过对象可以自动插入一行对应的数据
     * @param t 对象
     * @param <T> 可以接收泛型对象
     * @return 一个被修改的行数
     */
    public <T> int insertObject(T t){
        //获取连接
        Connection conn  = this.prepareConnection();
        //获取插入语句
        String sql = DbUtils.generateInsert(t);
        //调用自身 insertObject 方法
        return this.insertObject(conn,false,sql,t);
    }

    /**
     * 通过对象可以自动插入一行对应的数据
     * @param sql sql语句
     * @param t 对象
     * @param <T> 可以接收泛型对象
     * @return 一个被修改的行数
     */
    public <T> int insertObject(final String sql,T t){
        //获取连接
        Connection conn  = this.prepareConnection();
        //调用自身 insertObject 方法
        return this.insertObject(conn,false,sql,t);
    }

    /**
     * 通过对象可以自动插入一行对应的数据
     * @param conn 数据库连接
     * @param t 对象
     * @param <T> 可以接收泛型对象
     * @return 一个被修改的行数
     */
    public <T> int insertObject(final Connection conn,T t){
        //获取插入语句
        String sql = DbUtils.generateInsert(t);
        //调用自身 insertObject 方法
        return this.insertObject(conn,true,sql,t);
    }

    /**
     * 通过对象可以自动插入一行对应的数据
     * @param conn 数据库连接
     * @param sql sql语句
     * @param t 对象
     * @param <T> 可以接收泛型对象
     * @return 一个被修改的行数
     */
    public <T> int insertObject(final Connection conn,final String sql,T t){
        //调用自身 insertObject 方法
        return this.insertObject(conn,true,sql,t);
    }

    /**
     * 通过对象可以自动插入一行对应的数据
     * @param conn 数据库连接
     * @param closeConn 是否关闭数据库连接
     * @param sql sql语句
     * @param t 对象
     * @param <T> 可以接收泛型对象
     * @return 一个被修改的行数
     */
    private <T> int insertObject(final Connection conn,final boolean closeConn,final String sql,T t){
        //获取 Object 数组，用于数据库连接参数
        Object[] objects = DbUtils.getObjects(t);
        //调用自身的 .update() 方法 , 进行更新操作
        return this.update(conn,closeConn,sql,objects);
    }


}
