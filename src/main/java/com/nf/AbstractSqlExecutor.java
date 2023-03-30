package com.nf;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
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
            throw new DaoException("Connection creation failed...",e);
        }
    }
}
