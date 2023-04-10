package com.nf.handler.row.create.propertys;

import com.nf.handler.row.create.PropertyHandler;

/**
 * 实现接口 PropertyHandler
 * 用于处理 Enum 枚举类型的数据
 */
public class EnumPropertyHandler implements PropertyHandler {
    /**
     * 判断是否能处理该数据
     * @param clz setter 方法的参数的类对象
     * @param value 输入的值
     * @return 返回真表示该数据是 Enum 数据类型
     */
    @Override
    public boolean mate(Class<?> clz, Object value) {
        return value instanceof String && clz.isEnum();
    }

    /**
     * 处理 Enum 枚举数据
     * @param clz setter 方法的参数的类对象
     * @param value 输入的值
     * @return Enum 枚举对象
     */
    @Override
    public Object apply(Class<?> clz, Object value) {
        return Enum.valueOf(clz.asSubclass(Enum.class),(String) value);
    }
}
