package com.nf.util;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * 该类用于源数据列的处理
 */
public class ResultSetMetaUtils {
    private ResultSetMetaUtils() {
    }

    /**
     * 获取列名
     * @param metaData 原数据
     * @param index 下标
     * @return 列名
     * @throws SQLException SQL
     */
    public static String getColumnName(ResultSetMetaData metaData,int index) throws SQLException {
        //获取别名 getColumnLabel
        //声明 columnName 接收 得到的别名
        String columnName = metaData.getColumnLabel(index);
        //判断 columnName 是否为空
        if (columnName==null||columnName.isEmpty()){
            //为空则 获取列名
            columnName = metaData.getColumnName(index);
        }
        //判断 columnName 是否为空
        if (columnName==null|| columnName.isEmpty()){
            //为空则获取改行的行号
            columnName=Integer.toString(index);
        }
        //返回列名
        return columnName;
    }
}
