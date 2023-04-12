package com.nf;

import com.nf.util.AccessorParametersUtils;
import com.nf.util.ResourceCleanerUtils;

import javax.sql.DataSource;
import java.sql.*;

/**
 * 父类 AbstractSqlExecutor 数据库操作工具
 */
public abstract class AbstractSqlExecutor {
    protected DataSource ds ;

    /**
     * 默认构造器
     */
    public AbstractSqlExecutor() {
        this.ds = null;
    }

    /**
     * 创建对象
     * @param ds DataSource对象
     */
    public AbstractSqlExecutor(DataSource ds) {
        this.ds = ds;
    }

    /**
     * 获取 DataSource 对象
     * @return DataSource 对象
     */
    public DataSource getDs() {
        return ds;
    }

    /**
     * 设置 DataSource 对象
     * @param ds DataSource 对象
     */
    public void setDs(DataSource ds) {
        this.ds = ds;
    }

    /**
     * 该方法用于获取数据库的连接
     * 该方法在需要被扩展时，可以被子类重载
     * @return 一个数据库连接 Connection 对象
     */
    protected Connection prepareConnection() {
        //判断DataSource对象是否为空
        if (this.ds==null){
            //为空抛出异常
            throw new DaoException("DataSource is null...");
        }
        try {
            //返回一个数据库连接对象
            return  this.ds.getConnection();
        } catch (SQLException e) {
            //抛出获取连接失败异常
            throw new DaoException("Connection creation failed...",e);
        }
    }

    /**
     * 给statement赋值，填充访问参数
     * @param statement 执行器
     * @param params 访问参数
     * @return 返回对象可以不必承接，该对象为原本的对象，所以不需要写新的声明，只需要执行原有的对象即可
     * @throws SQLException
     */
    protected PreparedStatement fillStatement(PreparedStatement statement, Object... params) throws SQLException {
        //调用工具类的 fillStatement 方法 给statement赋值
        return AccessorParametersUtils.fillStatement(statement,params);
    }


    /**
     * 判断 ResultSetHandler 对象是否为空
     * @param conn 连接对象
     * @param closeConn 是否关闭 连接对象
     * @param clz 类 对象
     * @param <T>
     */
    protected <T> void checkClass(Connection conn, boolean closeConn, Class<? extends T> clz) {
        //判断 类 对象是否为空
        if (clz ==null){
            //判断是否需要关闭连接
            if (closeConn){
                //为真关闭连接
                ResourceCleanerUtils.closeQuietly(conn);
            }
            //为空抛出异常
            throw new DaoException("ResultSetHandler is null...");
        }
    }


    /**
     * 判断 ResultSetHandler 对象是否为空
     * @param conn 连接对象
     * @param closeConn 是否关闭 连接对象
     * @param rsh ResultSetHandler 对象
     * @param <T>
     */
    protected <T> void checkResultSetHandler(Connection conn, boolean closeConn, ResultSetHandler<T> rsh) {
        //判断ResultSetHandler对象是否为空
        if (rsh ==null){
            //判断是否需要关闭连接
            if (closeConn){
                //为真关闭连接
                ResourceCleanerUtils.closeQuietly(conn);
            }
            //为空抛出异常
            throw new DaoException("ResultSetHandler is null...");
        }
    }

    /**
     * 判断 ResultSetHandler 对象是否为空
     * @param conn 连接对象
     * @param closeConn 是否关闭 连接对象
     * @param sql 数据操作字符串
     */
    protected void checkSql(Connection conn, boolean closeConn, String sql) {
        //判断sql语句是否为空
        if (sql ==null|| sql.trim().length()==0){
            //判断是否需要关闭连接
            if (closeConn){
                //为真关闭连接
                ResourceCleanerUtils.closeQuietly(conn);
            }
            //为空抛出异常
            throw new DaoException("Connection is null...");
        }
    }

    /**
     * 判断 ResultSetHandler 对象是否为空
     * @param conn 连接对象
     */
    protected void checkConnection(Connection conn) {
        //判断数据库连接是否为空
        if (conn ==null){
            //为空抛出异常
            throw new DaoException("Connection is null...");
        }
    }

    /**
     * 清理(关闭)结果集
     * @param rs 结果集
     * @throws SQLException
     */
    protected void close(ResultSet rs) throws SQLException {
        //调用工具类 ResourceCleanerUtils 的 close 方法
        ResourceCleanerUtils.close(rs);
    }

    /**
     * 安静的关闭连接
     * @param conn 连接
     * @throws SQLException
     */
    protected void close(Connection conn) throws SQLException {
        //调用工具类 ResourceCleanerUtils 的 close 方法
        ResourceCleanerUtils.close(conn);
    }

    /**
     * 安静的关闭statement
     * @param statement 执行器
     * @throws SQLException
     */
    protected void close(Statement statement) throws SQLException {
        //调用工具类 ResourceCleanerUtils 的 close 方法
        ResourceCleanerUtils.close(statement);
    }
}
