package com.digdes.school.execution;

import com.digdes.school.exception.QueryValidationException;
import com.digdes.school.service.TableService;
import com.digdes.school.service.TableServiceImpl;
import com.digdes.school.utils.validator.QueryValidator;
import com.digdes.school.utils.validator.QueryValidatorImpl;

import java.util.List;
import java.util.Map;

public class QueryExecutorImpl implements QueryExecutor {
    private QueryValidator validator;
    private TableService tableService;
    private List<String> query;
    private Map<String, Object> modifiedRows;

    public QueryExecutorImpl() {
        validator = new QueryValidatorImpl();
        tableService = new TableServiceImpl();
    }

    @Override
    public Map<String, Object> execute(String queryMessage) {
        String[] message = queryMessage.replaceAll("[^a-zA-ZА-Яа-я\\d %><=.!]", "")
                .trim()
                .toLowerCase()
                .split(" ");

        try {
            query = validator.validateSpelling(message);
            modifiedRows = tableService.executeQuery(query);
        } catch (Exception e) {
            System.err.println(
                    e.getMessage() + "\n" +
                    "Valid request example: SELECT id WHERE age >= 30 and lastName like %wordPart%\n" +
                    "Possible problems: spelling, no blanks    ^  ^                    ^\n"
            );
            tableService.clearAllParams(); //Чистим параметры для новой попытки
        }

        return modifiedRows;
    }
}
