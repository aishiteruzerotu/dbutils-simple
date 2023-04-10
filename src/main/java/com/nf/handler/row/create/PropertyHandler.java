package com.nf.handler.row.create;

/**
 * 该接口用于查看数据是否需要被处理
 */
public interface PropertyHandler {
    /**
     * 判断该数据是否能被处理，能被处理则返回 ture
     * @param clz setter 方法的参数的类对象
     * @param value 输入的值
     * @return 布尔值
     */
    boolean mate(Class<?> clz,Object value);

    /**
     * 如果该数据能被处理，则调用该方法进行处理数据
     * @param clz setter 方法的参数的类对象
     * @param value 输入的值
     * @return 布尔值
     */
    Object apply(Class<?> clz,Object value);
}
