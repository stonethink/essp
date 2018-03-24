package com.wits.excel.exporter.writer;

import com.wits.excel.IDataWriter;

public abstract class AbstractDataWriter implements IDataWriter {
    protected String format;
    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
