package com.digdes.school.utils.output.table.decorator;

public class HorizontalBorder {

    public void print(Integer allElementsLength) {
        System.out.print("-".repeat(allElementsLength - 5)); //Убираю 4 элемента так как элемент '-' общий для 4 столбцов. Убираю 1, чтобы сделать перенос строки.
        System.out.print("-\n");
    }
}
