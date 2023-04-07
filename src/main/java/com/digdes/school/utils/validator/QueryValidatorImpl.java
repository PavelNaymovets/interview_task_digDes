package com.digdes.school.utils.validator;

import com.digdes.school.entity.Table;
import com.digdes.school.exception.QueryValidationException;

import java.util.ArrayList;
import java.util.List;

public class QueryValidatorImpl implements QueryValidator {
    private final List<String> validatedQuery;
    private final List<String> tableSchema;
    private final List<String> selectors;
    private final List<String> keyWords;
    private final List<String> operators;

    public QueryValidatorImpl() {
        validatedQuery = new ArrayList<>();
        tableSchema = Table.getTable().getSchema();
        selectors = List.of("select", "update", "delete", "insert");
        keyWords = List.of("values", "where");
        operators = List.of(">", "<", "=", ">=", "<=", "!=","like", "and", "or");
    }

    @Override
    public List<String> validateSpelling(String[] queryMessage) throws Exception {
        checkFirstKeyWord(queryMessage);
        checkOtherWord(queryMessage);
        removeExtraBlank();

        List<String> query = new ArrayList<>(validatedQuery);
        validatedQuery.clear();

        return query;
    }

    @Override
    public void validateOperatorsAndParams(List<String> whereParams, int i) {
        String queryOperator = whereParams.get(i);
        String param = whereParams.get(i - 1);

        if (!operators.contains(queryOperator)) {
            throw new QueryValidationException("Error query. No correct operator: " + queryOperator);
        }

        if (!tableSchema.contains(param)) {
            throw new QueryValidationException("Error query. No correct param: " + param);
        }
    }

    private void checkFirstKeyWord(String[] queryMessage) {
        String firstQueryWord = queryMessage[0];

        if (selectors.contains(firstQueryWord)) {
            validatedQuery.add(firstQueryWord);
        } else {
            throw new QueryValidationException("Error query. No correct selector: " + firstQueryWord);
        }
    }

    private void checkOtherWord(String[] queryMessage) {
        List<String> tableSchemaAndKeyWords = new ArrayList<>();
        tableSchemaAndKeyWords.addAll(tableSchema);
        tableSchemaAndKeyWords.addAll(keyWords);

        for (int i = 1; i < queryMessage.length; i++) { // i = 1, так как селектор я уже проверил.
            String queryWord = queryMessage[i];
            String operator = queryMessage[i - 1];

            for (String param : tableSchemaAndKeyWords) {
                /*
                  Если слово из запроса содержит параметр или ключевое слово, но не равно ему
                  (например: values_fdslkjg, id_sgfd) и если слева от проверяемого слова из запроса
                  не оператор (<, >, => и пр.), значит мы попали, при обходе, в ключевое слово или параметр и ошибка
                  в них.

                  Если же слева от проверяемого слова - оператор, значит мы попали, при обходе, в значение параметра и
                  ошибка в нем(например: id = michail), ведь id не может иметь строковое значение. Эта ошибка проверяется
                  при конвертации строкового значения из запроса в нужный тип данных для проверки условия или вставки в
                  таблицу.
                 */
                if (queryWord.contains(param) && !queryWord.equalsIgnoreCase(param) && !operators.contains(operator)
                ) {
                    throw new QueryValidationException("Error query. No correct param or key word: " + queryWord);
                }
            }

            validatedQuery.add(queryWord);
        }
    }

    private void removeExtraBlank() {
        validatedQuery.removeIf(String::isBlank);
    }

}
