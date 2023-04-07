package com.digdes.school.utils.check;

public interface ConditionChecker {
    boolean check(Object tableColumnValue, String operator, Object paramCondition, String paramBeforeOperator);
}
