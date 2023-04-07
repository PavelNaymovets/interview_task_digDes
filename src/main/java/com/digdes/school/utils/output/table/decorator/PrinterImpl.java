package com.digdes.school.utils.output.table.decorator;

public class PrinterImpl implements Printer{
    @Override
    public void print(String value, Integer elementLength, Boolean isModified) {
        System.out.print(value);
    }
}
