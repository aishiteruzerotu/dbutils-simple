package com.nf;

import java.sql.*;

/**
 * 该类用于数据库操作
 * 是数据库操作工具类
 */
public class SqlExecutor extends AbstractSqlExecutor{

    /**
     * 对数据库进行更新操作，并返回一个被修改的行数
     * @param sql sql更新语句
     * @param params 访问参数
     * @return 被影响的行数
     */
    public int update(final String sql,final Object... params){
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
        //判断数据库连接是否为空
        if (conn==null){
            //为空抛出异常
            throw new DaoException("Connection is null...");
        }
        //判断sql语句是否为空
        if (sql==null||sql.trim().length()==0){
            //判断是否需要关闭连接
            if (closeConn){
                //为真关闭连接
                ResourceCleanerUtils.closeQuietly(conn);
            }
            //为空抛出异常
            throw new DaoException("Connection is null...");
        }
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
            throw new DaoException("update be defeated...",e);
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
     * 给statement赋值，填充访问参数
     * @param statement 执行器
     * @param params 访问参数
     * @return 返回对象可以不必承接，该对象为原本的对象，所以不需要写新的声明，只需要执行原有的对象即可
     * @throws SQLException
     */
    private PreparedStatement fillStatement(PreparedStatement statement, Object... params) throws SQLException {
        //判断params是否为空，如果为空则直接返回PreparedStatement对象
        if (params==null||params.length==0){
            return statement;
        }
        //循环给statement赋访问参数
        for (int i = 0; i < params.length; i++) {
            //判断当前参数 params[i] 是否为空
            if (params[i]!=null){
                //不为空则正常赋值
                statement.setObject(i+1,params[i]);
            }else {
                //为空则给该值赋空值
                statement.setNull(i+1, Types.VARCHAR);
            }
        }
        //返回PreparedStatement对象
        return statement;
    }

}
