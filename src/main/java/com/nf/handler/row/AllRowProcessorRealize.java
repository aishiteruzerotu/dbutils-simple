package com.nf.handler.row;

import com.nf.handler.AllRowProcessor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 该类实现了 AllRowProcessor 接口的方法
 */
public class AllRowProcessorRealize extends AbstractProcessorRealize implements AllRowProcessor {

    /**
     * 返回一个 @{List<Object[]>} 数组对象
     * @param rs 结果集
     * @return @{List<Object[]>}
     * @throws SQLException
     */
    @Override
    public List<Object[]> toArrayList(ResultSet rs) throws SQLException {
        //声明 List<Object[]> 列表对象
        List<Object[]> result = new ArrayList<>();
        //循环取值
        while (rs.next()){
            //增加数据
            // 依赖 的 .createObjects 方法生成添加数据
            result.add(this.createRow.createObjects(rs));
        }
        //返回 List<Object[]> 列表对象
        return result;
    }

    /**
     * 返回一个 泛型对象 列表
     * 一般为实体类的数组集合
     * @param rs 结果集
     * @param <T> 泛型对象
     * @return @{List<T>} 列表
     * @return 一个实体类对象，包含其数据
     * @throws SQLException
     */
    @Override
    public <T> List<T> toBeanList(ResultSet rs, Class<? extends T> type) throws SQLException {
        //声明 List<T> 列表对象
        List<T> result = new ArrayList<>();
        while (rs.next()){
            //增加数据
            // 依赖 的 .createBean 方法生成添加数据
            result.add(this.createRow.createBean(rs,type));
        }
        //返回 List<T> 列表对象
        return result;
    }

    /**
     * 返回一个由列名为 K(String) 值，数据为 V(Object) 值的  @{List<Map<String,Object>>} 的 list对象
     * @param rs 结果集
     * @return  @{List<Map<String,Object>>} list对象
     * @throws SQLException
     */
    @Override
    public List<Map<String, Object>> toMapList(ResultSet rs) throws SQLException {
        //声明 List<Map<String, Object>> 列表对象
        List<Map<String, Object>> result = new ArrayList<>();
        //循环取值
        while (rs.next()){
            //增加数据
            // 依赖 的 .createMap 方法生成添加数据
            result.add(this.createRow.createMap(rs));
        }
        //返回 List<Map<String, Object>> 列表对象
        return result;
    }
}
