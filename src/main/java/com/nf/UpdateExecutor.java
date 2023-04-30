package com.nf;

import com.nf.handler.BeanHandler;
import com.nf.handler.BeanListHandler;
import com.nf.util.AccessorParametersUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.List;

/**
 * 封装 SqlExecutor
 * 增加功能
 */
public class UpdateExecutor extends SqlExecutor implements Update{
    //生成 sql 语句依赖对象
    protected GenerateSQL generateSQL;
    //默认生成 sql 语句依赖
    protected static final GenerateSQL DEFAULT_GENERATE_SQL = new GenerateSQLRealize();

    /**
     * 默认构造器
     */
    public UpdateExecutor() {
        this(null,DEFAULT_GENERATE_SQL);
    }

    /**
     * 设置 sql 语句生成依赖
     * @param generateSQL 依赖对象
     */
    public UpdateExecutor(GenerateSQL generateSQL) {
        this.generateSQL = generateSQL;
    }

    /**
     * 创建对象
     *
     * @param ds DataSource对象
     */
    public UpdateExecutor(final DataSource ds) {
        this(ds,DEFAULT_GENERATE_SQL);
    }

    /**
     * 生成对象
     * @param ds 连接池
     * @param generateSQL 依赖对象
     */
    public UpdateExecutor(final DataSource ds, final GenerateSQL generateSQL){
        this.ds = ds;
        this.generateSQL = generateSQL;
    }

    /**
     * 通过对象可以自动插入一行对应的数据
     *
     * @param t   对象
     * @param <T> 可以接收泛型对象
     * @return 一个被修改的行数
     */
    public <T> int insertObject(T t) {
        //获取连接
        Connection conn = this.prepareConnection();
        //获取插入语句
        String sql = this.generateSQL.generateInsert(t);
        //调用自身 insertObject 方法
        return this.insertObject(conn, true, sql, t);
    }

    /**
     * 通过对象可以自动插入一行对应的数据
     *
     * @param sql sql语句
     * @param t   对象
     * @param <T> 可以接收泛型对象
     * @return 一个被修改的行数
     */
    public <T> int insertObject(final String sql, T t) {
        //获取连接
        Connection conn = this.prepareConnection();
        //调用自身 insertObject 方法
        return this.insertObject(conn, true, sql, t);
    }

    /**
     * 通过对象可以自动插入一行对应的数据
     *
     * @param conn 数据库连接
     * @param t    对象
     * @param <T>  可以接收泛型对象
     * @return 一个被修改的行数
     */
    public <T> int insertObject(final Connection conn, T t) {
        //获取插入语句
        String sql = this.generateSQL.generateInsert(t);
        //调用自身 insertObject 方法
        return this.insertObject(conn, false, sql, t);
    }

    /**
     * 通过对象可以自动插入一行对应的数据
     *
     * @param conn 数据库连接
     * @param sql  sql语句
     * @param t    对象
     * @param <T>  可以接收泛型对象
     * @return 一个被修改的行数
     */
    public <T> int insertObject(final Connection conn, final String sql, T t) {
        //调用自身 insertObject 方法
        return this.insertObject(conn, false, sql, t);
    }

    /**
     * 通过对象可以自动插入一行对应的数据
     *
     * @param conn      数据库连接
     * @param closeConn 是否关闭数据库连接
     * @param sql       sql语句
     * @param t         对象
     * @param <T>       可以接收泛型对象
     * @return 一个被修改的行数
     */
    private <T> int insertObject(final Connection conn, final boolean closeConn, final String sql, T t) {
        //获取 Object 数组，用于数据库连接参数
        Object[] objects = AccessorParametersUtils.generateObjects(t);
        //调用自身的 .update() 方法 , 进行更新操作
        return this.update(conn, closeConn, sql, objects);
    }

    /**
     * 根据类对象信息生成一个数据库 delete 删除语句，
     * 只能根据主键进行删除
     * 如果有主键的注解(@PrimaryKey)则按注解的字段生成 sql 删除语句
     * 如果没有默认 按照 id 字段进行删除
     * 如果没有指定字段，也没有 id 字段，则无法正常使用该方法
     * @param clz 类对象
     * @param primaryKey 主键，删除对象的凭证(依据)
     * @return 数据库被影响的行数
     */
    public int delete(final Class<?> clz, Object primaryKey) {
        //获取连接
        Connection conn = this.prepareConnection();
        //生成 sql 删除语句
        String sql = this.generateSQL.generateDelete(clz);
        //调用自身 delete 方法
        return this.delete(conn, true, sql, primaryKey);
    }

    /**
     * 根据类对象信息生成一个数据库 delete 删除语句，
     * 只能根据主键进行删除
     * 如果有主键的注解(@PrimaryKey)则按注解的字段生成 sql 删除语句
     * 如果没有默认 按照 id 字段进行删除
     * 如果没有指定字段，也没有 id 字段，则无法正常使用该方法
     * @param conn 数据库连接
     * @param clz 类对象
     * @param primaryKey 主键，删除对象的凭证(依据)
     * @return 数据库被影响的行数
     */
    public int delete(final Connection conn, final Class<?> clz, Object primaryKey) {
        //生成 sql 删除语句
        String sql = this.generateSQL.generateDelete(clz);
        //调用自身 delete 方法
        return this.delete(conn, false, sql, primaryKey);
    }

    /**
     * 调用器，用于更改调用名称
     */
    private int delete(final Connection conn, final boolean closeConn, final String sql, Object primaryKey) {
        //调用自身的 update 方法
        return this.update(conn, closeConn, sql, primaryKey);
    }
}
