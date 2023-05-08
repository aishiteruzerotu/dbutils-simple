package com.nf;

import javax.sql.DataSource;
import java.sql.*;

/**
 * �����������ݿ����
 * �����ݿ����������
 */
public class SqlExecutor extends AbstractSqlExecutor {

    /**
     * Ĭ�Ϲ�����
     */
    public SqlExecutor() {
        super();
    }

    /**
     * ��������
     *
     * @param ds DataSource����
     */
    public SqlExecutor(final DataSource ds) {
        super(ds);
    }

    /**
     * �����ݿ���и��²�����������һ�����޸ĵ�����
     *
     * @param sql    sql�������
     * @param params ���ʲ���
     * @return ��Ӱ�������
     */
    public int update(final String sql, final Object... params) {
        //����һ�����ݿ����� Connection ���󣬵���������Ϊ�丳ֵ
        Connection conn = this.prepareConnection();
        //��������� .update() ����
        return this.update(conn, true, sql, params);
    }

    /**
     * �����ݿ���и��²�����������һ�����޸ĵ�����
     *
     * @param conn   ���ݿ�����
     * @param sql    sql�������
     * @param params ���ʲ���
     * @return ��Ӱ�������
     */
    public int update(final Connection conn, final String sql, final Object... params) {
        //��������� .update() ����
        return this.update(conn, false, sql, params);
    }

    /**
     * �����ݿ���и��²�����������һ�����޸ĵ�����
     *
     * @param conn      ���ݿ�����
     * @param closeConn �Ƿ�ر����ݿ����ӣ����� true ֵ�ر����ݿ����ӣ�false �򲻹ر�
     * @param sql       sql�������
     * @param params    ���ʲ���
     * @return ��Ӱ�������
     */
    protected int update(final Connection conn, final boolean closeConn, final String sql, final Object... params) {
        try {
            //�����쳣
            return this.update0(conn, closeConn, sql, params);
        } catch (SQLException e) {
            //�׳�����ʧ���쳣
            throw new DaoException("update defeated...", e);
        }
    }

    /**
     * �����ݿ���и��²�����������һ�����޸ĵ�����
     *
     * @param conn      ���ݿ�����
     * @param closeConn �Ƿ�ر����ݿ����ӣ����� true ֵ�ر����ݿ����ӣ�false �򲻹ر�
     * @param sql       sql�������
     * @param params    ���ʲ���
     * @return ��Ӱ�������
     */
    protected int update0(final Connection conn, final boolean closeConn, final String sql, final Object... params) throws SQLException {
        //������ݿ������Ƿ�Ϊ��
        checkConnection(conn);
        //���sql����Ƿ�Ϊ��
        checkSql(conn, closeConn, sql);

        //����PreparedStatement���� statement Ϊ��
        PreparedStatement statement = null;
        //�������޸ĵ����� rows
        int rows = 0;
        try {
            //�����ݿ������л�ȡ PreparedStatement ���� ����ֵ�� statement
            statement = conn.prepareStatement(sql);
            //��ִ���� statement �������
            statement = this.fillStatement(statement, params);
            //ʹ��statementִ������.executeUpdate() ���·�������������ֵ ��ֵ�� rows
            rows = statement.executeUpdate();
        } catch (SQLException e) {
            //�׳�����ʧ���쳣
            throw new DaoException("update defeated...", e);
        } finally {
            //�ر�ִ���� statement
            close(statement);
            //�ж��Ƿ���Ҫ�ر�����
            if (closeConn) {
                //Ϊ��ر�����
                close(conn);
            }
        }
        //���ر��޸ĵ����� rows
        return rows;
    }

    /**
     * ������ִ�в�ѯ����������һ�� �û�����Ķ���
     *
     * @param sql    sql��ѯ���
     * @param params ���ʲ���
     * @return һ������
     */
    public <T> T query(final String sql, final ResultSetHandler<T> rsh, final Object... params) {
        //���� prepareConnection ��ȡ���ݿ����Ӷ���
        Connection conn = this.prepareConnection();
        //��������� query ����
        return this.query(conn, true, sql, rsh, params);
    }

    /**
     * ������ִ�в�ѯ����������һ�� �û�����Ķ���
     *
     * @param sql    sql��ѯ���
     * @param params ���ʲ���
     * @return һ������
     */
    public <T> T query(final Connection conn, final String sql, final ResultSetHandler<T> rsh, final Object... params) {
        //��������� query ����
        return this.query(conn, false, sql, rsh, params);
    }

    /**
     * ������ִ�в�ѯ����������һ�� �û�����Ķ���
     *
     * @param conn      ���ݿ�����
     * @param closeConn �Ƿ�ر����ݿ����ӣ����� true ֵ�ر����ݿ����ӣ�false �򲻹ر�
     * @param sql       sql��ѯ���
     * @param params    ���ʲ���
     * @return һ������
     */
    protected <T> T query(final Connection conn, final boolean closeConn, final String sql, final ResultSetHandler<T> rsh, final Object... params) {
        try {
            //�����쳣
            return this.query0(conn, closeConn, sql, rsh, params);
        } catch (SQLException e) {
            //�׳���ѯʧ���쳣
            throw new DaoException("query defeated...", e);
        }
    }

    /**
     * ������ִ�в�ѯ����������һ�� �û�����Ķ���
     *
     * @param conn      ���ݿ�����
     * @param closeConn �Ƿ�ر����ݿ����ӣ����� true ֵ�ر����ݿ����ӣ�false �򲻹ر�
     * @param sql       sql��ѯ���
     * @param params    ���ʲ���
     * @return һ������
     */
    protected <T> T query0(final Connection conn, final boolean closeConn, final String sql, final ResultSetHandler<T> rsh, final Object... params) throws SQLException {
        //������ݿ������Ƿ�Ϊ��
        checkConnection(conn);
        //���sql����Ƿ�Ϊ��
        checkSql(conn, closeConn, sql);
        //���ResultSetHandler�����Ƿ�Ϊ��
        checkResultSetHandler(conn, closeConn, rsh);

        //����PreparedStatement���� statement Ϊ��
        PreparedStatement statement = null;
        //�������ؽ����Ϊ��
        ResultSet rs = null;
        //�������صĶ���Ϊ��
        T result = null;
        try {
            //�����ݿ������л�ȡ PreparedStatement ���� ����ֵ�� statement
            statement = conn.prepareStatement(sql);
            //��ִ���� statement �������
            statement = this.fillStatement(statement, params);
            //ʹ��statementִ������.executeQuery() ��ѯ�������õ���ѯ�����
            rs = statement.executeQuery();
            //ʹ�� ResultSetHandler ����� handler �������õ�һ�����ض���
            result = rsh.handler(rs);
        } catch (SQLException e) {
            //�׳���ѯʧ���쳣
            throw new DaoException("query defeated...", e);
        } finally {
            //�رս����
            close(rs);
            //�ر�ִ���� statement
            close(statement);
            //�ж��Ƿ���Ҫ�ر�����
            if (closeConn) {
                //Ϊ��ر�����
                close(conn);
            }

        }
        //���ض��� result
        return result;
    }

    /**
     * �����ݿ�����һ�����ݺ󣬷���һ����������ֵ
     * @param sql       sql��ѯ���
     * @param params    ���ʲ���
     * @return ��������ֵ
     */
    public <T> T insert(final String sql, final ResultSetHandler<T> rsh, final Object... params){
        //�������� insert ����
        return this.insert(this.prepareConnection(),true,sql,rsh,params);
    }

    /**
     * �����ݿ�����һ�����ݺ󣬷���һ����������ֵ
     * @param conn      ���ݿ�����
     * @param sql       sql��ѯ���
     * @param params    ���ʲ���
     * @return ��������ֵ
     */
    public <T> T insert(final Connection conn, final String sql, final ResultSetHandler<T> rsh, final Object... params){
        //�������� insert ����
        return this.insert(conn,false,sql,rsh,params);
    }

    /**
     * �����ݿ�����һ�����ݺ󣬷���һ����������ֵ
     * @param conn      ���ݿ�����
     * @param closeConn �Ƿ�ر����ݿ����ӣ����� true ֵ�ر����ݿ����ӣ�false �򲻹ر�
     * @param sql       sql��ѯ���
     * @param params    ���ʲ���
     * @return ��������ֵ
     */
    protected <T> T insert(final Connection conn, final boolean closeConn, final String sql, final ResultSetHandler<T> rsh, final Object... params){
        try {
            //�������� insert0 ����
            return this.insert0(conn,closeConn,sql,rsh,params);
        } catch (SQLException e) {
            throw new DaoException("insert defeated...", e);
        }
    }

    /**
     * �����ݿ�����һ�����ݺ󣬷���һ����������ֵ
     * @param conn      ���ݿ�����
     * @param closeConn �Ƿ�ر����ݿ����ӣ����� true ֵ�ر����ݿ����ӣ�false �򲻹ر�
     * @param sql       sql��ѯ���
     * @param params    ���ʲ���
     * @return ��������ֵ
     */
    protected <T> T insert0(final Connection conn, final boolean closeConn, final String sql, final ResultSetHandler<T> rsh, final Object... params) throws SQLException {
        //������ݿ������Ƿ�Ϊ��
        checkConnection(conn);
        //���sql����Ƿ�Ϊ��
        checkSql(conn, closeConn, sql);
        //���ResultSetHandler�����Ƿ�Ϊ��
        checkResultSetHandler(conn, closeConn, rsh);

        //����PreparedStatement���� statement Ϊ��
        PreparedStatement statement = null;
        //�������ؽ����Ϊ��
        ResultSet rs = null;
        //�������صĶ���Ϊ��
        T result = null;
        try {
            //�����ݿ������л�ȡ PreparedStatement ���� ����ֵ�� statement
            statement = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            //��ִ���� statement �������
            statement = this.fillStatement(statement, params);
            //�����ݿ���и��²���
            statement.executeUpdate();
            //ʹ��statementִ������.getGeneratedKeys() ��ѯ�������õ��������Ľ����
            rs = statement.getGeneratedKeys();
            //ʹ�� ResultSetHandler ����� handler �������õ�һ�����ض���
            result = rsh.handler(rs);
        } catch (SQLException e) {
            //�׳���������ʧ���쳣
            throw new DaoException("insert defeated...", e);
        } finally {
            //�رս����
            close(rs);
            //�ر�ִ���� statement
            close(statement);
            //�ж��Ƿ���Ҫ�ر�����
            if (closeConn) {
                //Ϊ��ر�����
                close(conn);
            }

        }
        //���ض��� result
        return result;
    }

}
