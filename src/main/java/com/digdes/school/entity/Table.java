package com.digdes.school.entity;

import com.digdes.school.exception.TableNotCreatedException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Table {
    private static Table tableImpl;
    private String name;
    private List<String> schema;
    private List<Map<String, Object>> data;

    private Table(String name, List<String> schema) {
        this.name = name;
        this.schema = schema;
        this.data = new ArrayList<>();
    }

    public static void createTable(String name, List<String> schema) {
        if (tableImpl == null) {
            String nameLowerCase = name.toLowerCase();
            List<String> schemaLowerCase = schema.stream().map(String::toLowerCase).toList();

            tableImpl = new Table(nameLowerCase, schemaLowerCase);
        }
    }

    public static Table getTable() {
        if (tableImpl == null) {
            throw new TableNotCreatedException("Table is not created. Please create table.");
        }

        return tableImpl;
    }

    public String getName() {
        return name;
    }

    public List<String> getSchema() {
        return schema;
    }

    public List<Map<String, Object>> getData() {
        return data;
    }
}
