package com.nf.handler.row;

import com.nf.handler.row.create.CreateRealize;

/**
 * 该类用于管理生成对象的类
 * 其子类继承其依赖的生成对象
 */
public class AbstractProcessorRealize {
    //生成依赖
    protected CreateRow createRow;
    //默认依赖
    protected static CreateRow DEFAULT_CREATE_ROW = new CreateRealize();

    /**
     * 默认构造器
     */
    public AbstractProcessorRealize() {
        this(DEFAULT_CREATE_ROW);
    }

    /**
     * 设置依赖构造器
     * @param createRow 依赖
     */
    public AbstractProcessorRealize(CreateRow createRow) {
        this.createRow = createRow;
    }
}
