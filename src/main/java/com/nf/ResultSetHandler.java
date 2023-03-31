package com.nf;

import java.sql.ResultSet;

public interface ResultSetHandler<T> {

    T handler(ResultSet rs);
}
