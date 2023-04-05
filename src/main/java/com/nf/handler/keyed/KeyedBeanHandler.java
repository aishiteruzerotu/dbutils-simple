package com.nf.handler.keyed;

import com.nf.handler.RowProcessor;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 该类继承了  AbstractKeyedHandler<Map<String>>
 * 该类返回的对象是一个 @{Map<Object,T>} Map的数据结果集
 * 该类的是由用户指定所需要的列名称或行数，取值给 K ，由该值给 Map对象做 Key
 * K 值所在的行，则会打包成一个 <T> 的对象，作为返回对象 Value
 * 由此，该返回的 Map 对象，一条数据包含了数据库查询的一条数据
 */
public class KeyedBeanHandler<T> extends AbstractKeyedHandler<T> {
    //返回类型
    private Class<? extends T> type;

    /**
     * 该类返回的是一个 @{Map<Object,T>} 的 Map 对象，泛型 <T> 是方法传入类对象的实例
     * @param type 类对象数据
     */
    public KeyedBeanHandler(Class<? extends T> type) {
        this(type,COLUMINDEXDEFAULTVALUE,COLUMNAMEDEFAULTVALUE,DEFAULTPROCESSOR);
    }

    /**
     * 该类返回的是一个 @{Map<Object,T>} 的 Map 对象，泛型 <T> 是方法传入类对象的实例
     * @param type 类对象数据
     * @param columIndex K 值取值的行数
     */
    public KeyedBeanHandler(Class<? extends T> type, Integer columIndex) {
        this(type,columIndex,COLUMNAMEDEFAULTVALUE,DEFAULTPROCESSOR);
    }

    /**
     * 该类返回的是一个 @{Map<Object,T>} 的 Map 对象，泛型 <T> 是方法传入类对象的实例
     * @param type 类对象数据
     * @param columName K 值取值的名称，有名称时，按名称取值，无名称时，按行数取值，行数默认值为 1
     */
    public KeyedBeanHandler(Class<? extends T> type, String columName) {
        this(type,COLUMINDEXDEFAULTVALUE,columName,DEFAULTPROCESSOR);
    }

    /**
     * 该类返回的是一个 @{Map<Object,T>} 的 Map 对象，泛型 <T> 是方法传入类对象的实例
     * @param type 类对象数据
     * @param columIndex K 值取值的行数
     * @param processor 实现类对象
     */
    public KeyedBeanHandler(Class<? extends T> type, Integer columIndex, RowProcessor processor) {
        this(type,columIndex,COLUMNAMEDEFAULTVALUE,processor);
    }

    /**
     * 该类返回的是一个 @{Map<Object,T>} 的 Map 对象，泛型 <T> 是方法传入类对象的实例
     * @param type 类对象数据
     * @param columName K 值取值的名称，有名称时，按名称取值，无名称时，按行数取值，行数默认值为 1
     * @param processor 实现类对象
     */
    public KeyedBeanHandler(Class<? extends T> type, String columName, RowProcessor processor) {
        this(type,COLUMINDEXDEFAULTVALUE,columName,processor);
    }

    /**
     * 该类返回的是一个 @{Map<Object,T>} 的 Map 对象，泛型 <T> 是方法传入类对象的实例
     * @param type 类对象数据
     * @param columIndex K 值取值的行数
     * @param columName K 值取值的名称，有名称时，按名称取值，无名称时，按行数取值，行数默认值为 1
     * @param processor 实现类对象
     */
    public KeyedBeanHandler(Class<? extends T> type,Integer columIndex, String columName, RowProcessor processor) {
        this.type = type;
        this.processor = processor;
        this.columIndex = columIndex;
        this.columName = columName;
    }

    /**
     * 该方法返回值是一个 Map<String, Object> 的 Map 对象
     * @param rs 数据库查询结果集
     * @return @{<T>} 的 泛型 对象
     * @throws SQLException
     */
    @Override
    protected T createValue(ResultSet rs) throws SQLException {
        //调用依赖的 toBean 方法
        return this.processor.toBean(rs,this.type);
    }
}
