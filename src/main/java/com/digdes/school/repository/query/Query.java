package com.digdes.school.repository.query;

import com.digdes.school.entity.Table;
import com.digdes.school.exception.QueryValidationException;
import com.digdes.school.utils.check.ConditionChecker;
import com.digdes.school.utils.check.ConditionCheckerImpl;
import com.digdes.school.utils.converter.ValueConverter;
import com.digdes.school.utils.converter.ValueConverterImpl;

import java.util.*;

public class Query {
    protected final List<Map<String, Object>> data;
    protected final ConditionChecker conditionChecker;
    protected final ValueConverter valueConverter;
    protected final List<Integer> indexModifiedRows;

    public Query() {
        data = Table.getTable().getData();
        conditionChecker = new ConditionCheckerImpl();
        valueConverter = new ValueConverterImpl();
        indexModifiedRows = new ArrayList<>();
    }

    protected void executeWithOrWithoutOr(String selector, List<String> params, List<String> whereParams, List<String> paramsBeforeOr, List<String> paramsAfterOr, Collection<Integer> indexModifiedRows, List<Integer> requestConditionsCount) {
        boolean withoutOr = requestConditionsCount.size() == 1; // Размер = 1 - запрос без or. Размер = 2 - запрос с or.

        if (withoutOr) {
            int conditionsCountWithoutOr = requestConditionsCount.get(0);
            findRowWithMatchingConditions(selector, params, whereParams, indexModifiedRows, conditionsCountWithoutOr);
        } else {
            int conditionsCountBeforeOr = requestConditionsCount.get(0);
            int conditionsCountAfterOr = requestConditionsCount.get(1);
            Set<Integer> uniqueIndexModifiedRows = new TreeSet<>(); //Строки с подходящими условиями не могут повторятся, чтобы не выводить 2 раза на печать одно и тоже.

            findRowWithMatchingConditions(selector, params, paramsBeforeOr, uniqueIndexModifiedRows, conditionsCountBeforeOr);
            findRowWithMatchingConditions(selector, params, paramsAfterOr, uniqueIndexModifiedRows, conditionsCountAfterOr);

            indexModifiedRows.addAll(uniqueIndexModifiedRows);
        }
    }

    protected void findRowWithMatchingConditions(String selector, List<String> params, List<String> whereParams, Collection<Integer> indexModifiedRows, int conditionsCount) {
        for (int rowNumber = 0; rowNumber < data.size(); rowNumber++) {
            Map<String, Object> row = data.get(rowNumber);
            int correspondConditions = 0;

            for (int paramIndex = 1; paramIndex < whereParams.size(); paramIndex = paramIndex + 3) { //Попадаем в знак '='
                String paramBeforeOperator = whereParams.get(paramIndex - 1);
                String operator = whereParams.get(paramIndex);
                String paramAfterOperator = whereParams.get(paramIndex + 1);
                Object tableColumnValue = row.get(paramBeforeOperator);
                Object convertedParamAfterOperator = valueConverter.convert(paramBeforeOperator, paramAfterOperator);

                if (conditionChecker.check(tableColumnValue, operator, convertedParamAfterOperator, paramBeforeOperator)) {
                    correspondConditions++;
                }

            }

            if (correspondConditions == conditionsCount) {
                indexModifiedRows.add(rowNumber);

                if (selector.equals("update")) {
                    putParamsInRow(params, row);
                }
            }
        }
    }

    protected void putParamsInRow(List<String> params, Map<String, Object> row) {
        for (int i = 1; i < params.size(); i = i + 3) { //Попадаем в знак '='
            String paramBeforeEqual = params.get(i - 1);
            String operator = params.get(i);
            String paramAfterEqual = params.get(i + 1);

            if (paramAfterEqual.equals("null")) {
                row.remove(paramBeforeEqual);
            } else {
                Object convertedParamAfterEqual = valueConverter.convert(paramBeforeEqual, paramAfterEqual);

                if (!operator.equals("=")) {
                    throw new QueryValidationException("Error query. Operator before 'where' have to be '='");
                }

                if (convertedParamAfterEqual.equals("null")) {
                    row.remove(paramBeforeEqual);
                }

                row.put(paramBeforeEqual, convertedParamAfterEqual);
            }
        }
    }

    protected Map<String, Object> prepareOperationResult(String selector) {
        Map<String, Object> operationResult = new HashMap<>();
        List<Integer> modifiedRows = new ArrayList<>(indexModifiedRows);

        operationResult.put("selector", selector);
        operationResult.put("modifiedRows", modifiedRows);

        return operationResult;
    }

    protected void clearParams(List<String> params, List<String> whereParams, List<String> paramsBeforeOr, List<String> paramsAfterOr, List<Integer> requestConditionsCount) {
        params.clear();
        whereParams.clear();
        paramsBeforeOr.clear();
        paramsAfterOr.clear();
        requestConditionsCount.clear();
        indexModifiedRows.clear();
    }
}
