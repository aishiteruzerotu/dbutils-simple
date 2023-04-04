package com.nf.handler;

import com.nf.ResultSetHandler;
import com.nf.handler.row.AllRowProcessorRealize;

public abstract class AbstractAllResultSetHandler<T> implements ResultSetHandler<T> {
    //添加依赖对象 AllRowProcessor
    protected AllRowProcessor processor;

    /**
     * 无参构造函数
     */
    public AbstractAllResultSetHandler() {
        //设置默认依赖
        this(new AllRowProcessorRealize());
    }

    /**
     * 用于设置依赖
     * @param processor 实现依赖对象
     */
    public AbstractAllResultSetHandler(AllRowProcessor processor) {
        this.processor = processor;
    }
}
