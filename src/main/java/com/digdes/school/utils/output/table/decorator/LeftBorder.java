package com.digdes.school.utils.output.table.decorator;

public class LeftBorder implements Printer {
    private final Printer printer;

    public LeftBorder(Printer printer) {
        this.printer = printer;
    }

    @Override
    public void print(String value, Integer elementLength, Boolean isModified) {
        int paddings = elementLength - value.length();
        int leftPadding = paddings / 2;
        int rightPadding = paddings - leftPadding;

        String modifiedValue = "|" + " ".repeat(leftPadding - 1) + value + " ".repeat(rightPadding - 1); //учет символа | слева и справа

        printer.print(modifiedValue, elementLength, isModified);
    }
}
