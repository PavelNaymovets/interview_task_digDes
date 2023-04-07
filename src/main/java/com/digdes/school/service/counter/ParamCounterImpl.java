package com.digdes.school.service.counter;

import com.digdes.school.entity.Table;
import com.digdes.school.exception.QueryValidationException;
import com.digdes.school.utils.validator.QueryValidator;
import com.digdes.school.utils.validator.QueryValidatorImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ParamCounterImpl implements ParamCounter {
    private final QueryValidator validator;

    public ParamCounterImpl() {
        validator = new QueryValidatorImpl();
    }

    @Override
    public void countConditionsWithOrKeyWord(List<String> whereParams, int indexWordOrInWhereParams, List<Integer> requestConditionsCount) {
        int operatorIndex = 1; //Попадаю в оператор после WHERE
        int operatorsCountBeforeOr = 0;
        int operatorsCountAfterOr = 0;

        int countedAndKeyWord = (int) whereParams.stream().filter(word -> word.equals("and")).count();
        indexWordOrInWhereParams = indexWordOrInWhereParams - countedAndKeyWord; //нужно уменьшить индекс or на количество вхождений and.

        whereParams.removeIf(param -> param.equals("or") || param.equals("and"));

        for (int i = operatorIndex; i < whereParams.size(); i = i + 3) { //Операторы бинарные. Повторяются через 3 единицы.
            validator.validateOperatorsAndParams(whereParams, i);

            if (i < indexWordOrInWhereParams) {
                operatorsCountBeforeOr++;
            } else {
                operatorsCountAfterOr++;
            }
        }

        requestConditionsCount.add(operatorsCountBeforeOr);
        requestConditionsCount.add(operatorsCountAfterOr);
    }

    @Override
    public void countConditionsWithAndKeyWordComaDelimiter(List<String> whereParams, List<Integer> requestConditionsCount) {
        int operatorIndex = 1;
        int conditionsCount = 0;

        whereParams.removeIf(param -> param.equals("or") || param.equals("and"));

        for (int i = operatorIndex; i < whereParams.size(); i = i + 3) { //Операторы бинарные. Повторяются через 3 единицы.
            validator.validateOperatorsAndParams(whereParams, i);
            conditionsCount++;
        }

        requestConditionsCount.add(conditionsCount);
    }

    @Override
    public void checkParamsQuantityBeforeWhere(List<String> params) {
        Map<String, Integer> row = new HashMap<>();
        List<String> schema = Table.getTable().getSchema();

        for (int i = 0; i < params.size(); i = i + 3) { //Операторы бинарные. Повторяются через 3 единицы.
            String param = params.get(i);

            if (schema.contains(param)) {
                if (!row.containsKey(param)) {
                    row.put(param, 1);
                } else {
                    row.put(param, row.get(param) + 1);
                }
            } else {
                throw new QueryValidationException("Error query. No correct param: " + param);
            }
        }

        for (String param : row.keySet()) {
            if (row.get(param) > 1) {
                throw new QueryValidationException("Error query. No correct params quantity: " + param);
            }
        }

        row.clear();
    }
}
