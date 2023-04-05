package com.nf.handler;

import com.nf.ResultSetHandler;
import com.nf.handler.row.RowProcessorRealize;

public abstract class AbstractOneResultSetHandler<T> implements ResultSetHandler<T> {
    //添加依赖对象 RowProcessor
    protected RowProcessor processor;
    //默认依赖
    protected static final RowProcessor DEFAULTPROCESSOR = new RowProcessorRealize();

    /**
     * 无参构造函数
     */
    public AbstractOneResultSetHandler() {
        //设置默认依赖
        this(DEFAULTPROCESSOR);
    }

    /**
     * 用于设置依赖
     * @param processor 实现依赖对象
     */
    public AbstractOneResultSetHandler(RowProcessor processor) {
        this.processor = processor;
    }

}
