package com.digdes.school.utils.output.table.decorator;

public class RightBorder implements Printer{
    private final Printer printer;

    public RightBorder(Printer printer) {
        this.printer = printer;
    }

    @Override
    public void print(String value, Integer elementLength, Boolean isModified) {
        int paddings = elementLength - value.length();
        int leftPadding = paddings / 2;
        int rightPadding = paddings - leftPadding;
        String modifiedValue;

        if (isModified == true) {
            modifiedValue = "|" + " ".repeat(leftPadding - 1) + value + " ".repeat(rightPadding - 1) + "|<-modified row\n"; //учет символа | слева и справа
        } else {
            modifiedValue = "|" + " ".repeat(leftPadding - 1) + value + " ".repeat(rightPadding - 1) + "|\n"; //учет символа | слева и справа
        }


        printer.print(modifiedValue, elementLength, isModified);
    }
}
