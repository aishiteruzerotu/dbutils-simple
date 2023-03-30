package com.nf;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 该类用与清理(关闭)资源
 */
public class ResourceCleanerUtils {
    private ResourceCleanerUtils() {}

    /**
     * 关闭连接
     * @param conn 连接
     * @throws SQLException
     */
    public static void close(Connection conn) throws SQLException {
        if (conn!=null) {
            conn.close();
        }
    }

    /**
     * 关闭statement
     * @param statement 执行器
     * @throws SQLException
     */
    public static void close(Statement statement) throws SQLException {
        if (statement!=null) {
            statement.close();
        }
    }

    /**
     * 清理(关闭)结果集
     * @param rs 结果集
     * @throws SQLException
     */
    public static void close(ResultSet rs) throws SQLException {
        if (rs!=null) {
            rs.close();
        }
    }

    /**
     * 安静的关闭连接
     * @param conn 连接
     * @throws SQLException
     */
    public static void closeQuietly(Connection conn){
        try {
            close(conn);
        } catch (SQLException e) {
            //不抛出异常
        }
    }

    /**
     * 安静的关闭statement
     * @param statement 执行器
     * @throws SQLException
     */
    public static void closeQuietly(Statement statement) {
        try {
            close(statement);
        } catch (SQLException e) {
            //不抛出异常
        }
    }

    /**
     * 安静的清理(关闭)结果集
     * @param rs 结果集
     * @throws SQLException
     */
    public static void closeQuietly(ResultSet rs){
        try {
            close(rs);
        } catch (SQLException e) {
            //不抛出异常
        }
    }

    /**
     * 安静的关闭连接、statement、结果集
     * @param conn 数据库连接
     * @param statement 执行器
     */
    public static void closeQuietly(Connection conn , Statement statement){
        try {
            closeQuietly(statement);
        }finally {
            closeQuietly(conn);
        }
    }

    /**
     * 安静的关闭连接、statement、结果集
     * @param conn 数据库连接
     * @param statement 执行器
     * @param rs 结果集
     */
    public static void closeQuietly(Connection conn , Statement statement,ResultSet rs){
        try {
            closeQuietly(rs);
        }finally {
            try {
                closeQuietly(statement);
            }finally {
                closeQuietly(conn);
            }
        }
    }

}
