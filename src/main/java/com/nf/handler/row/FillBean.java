package com.nf.handler.row;

import java.sql.ResultSet;

/**
 * 该类用于填充 bean
 */
public interface FillBean {

    /**
     * 给 bean 填充数据
     * @param rs 结果集
     * @param bean 泛型对象
     * @param <T> 接受泛型
     * @return 原本的 带参 Bean 对象
     */
    <T> T populateBean(ResultSet rs, T bean);
}
