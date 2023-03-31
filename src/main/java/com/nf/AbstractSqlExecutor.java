package com.nf;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
        return DbUtils.fillStatement(statement,params);
    }
}
