package com.nf;

import javax.sql.DataSource;

public class AbstractSqlExecutor {
    protected DataSource ds ;

    public AbstractSqlExecutor() {
    }

    public AbstractSqlExecutor(DataSource ds) {
        this.ds = ds;
    }
}
