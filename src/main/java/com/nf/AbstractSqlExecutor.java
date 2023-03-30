package com.nf;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 */
public abstract class AbstractSqlExecutor {
    protected DataSource ds ;

    public AbstractSqlExecutor() {
    }

    public AbstractSqlExecutor(DataSource ds) {
        this.ds = ds;
    }

    /**
     * 该方法用于获取数据库的连接
     * 该方法在需要被扩展时，可以被子类重载
     * @return 一个数据库连接 Connection
     */
    protected Connection prepareConnection() {
        try {
            return  this.ds.getConnection();
        } catch (SQLException e) {
            throw new DaoException("从dataSource创建Connection失败",e);
        }
    }
}
