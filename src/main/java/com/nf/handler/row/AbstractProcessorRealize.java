package com.nf.handler.row;

import com.nf.handler.row.create.CreateRealize;

public class AbstractProcessorRealize {
    protected CreateRow createRow;

    protected static CreateRow DEFAULT_CREATE_ROW = new CreateRealize();

    public AbstractProcessorRealize() {
        this(DEFAULT_CREATE_ROW);
    }

    public AbstractProcessorRealize(CreateRow createRow) {
        this.createRow = createRow;
    }
}
