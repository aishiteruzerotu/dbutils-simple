package com.nf.handler.row.create.propertys;

import com.nf.Convert;
import com.nf.Reflection;
import com.nf.ReflexException;
import com.nf.handler.row.create.PropertyHandler;

public class SimpleTypePropertyHandler implements PropertyHandler {
    @Override
    public boolean mate(Class<?> clz, Object value) {
        return Reflection.isSimpleProperty(clz);
    }

    @Override
    public Object apply(Class<?> clz, Object value) {
        try {
            return Convert.toSimpleTypeValue(clz, value);
        } catch (Exception e) {
            throw new ReflexException("Data conversion exception," + value +
                    " no convert " + clz, e);
        }
    }
}
