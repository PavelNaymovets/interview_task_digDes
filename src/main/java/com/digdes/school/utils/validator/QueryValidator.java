package com.digdes.school.utils.validator;

import java.util.List;

public interface QueryValidator {
    List<String> validateSpelling(String[] queryMessage) throws Exception;
    void validateOperatorsAndParams(List<String> whereParams, int i);
}
