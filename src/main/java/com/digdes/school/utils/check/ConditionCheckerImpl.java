package com.digdes.school.utils.check;

import com.digdes.school.exception.QueryValidationException;

public class ConditionCheckerImpl implements ConditionChecker {
    @Override
    public boolean check(Object tableColumnValue, String operator, Object paramCondition, String paramBeforeOperator) {
        switch (operator) {
            case "!=":
                return switch (paramBeforeOperator) {
                    case "id", "age" -> ((Long) tableColumnValue) != ((Long) paramCondition);
                    case "cost" -> ((Double) tableColumnValue) != ((Double) paramCondition);
                    case "lastname" -> !tableColumnValue.equals(paramCondition);
                    case "active" -> (Boolean) tableColumnValue != (Boolean) paramCondition;
                    default -> throw new QueryValidationException("Query error. Incompatible types");
                };
            case ">":
                return switch (paramBeforeOperator) {
                    case "id", "age" -> (Long) tableColumnValue > (Long) paramCondition;
                    case "cost" -> (Double) tableColumnValue > (Double) paramCondition;
                    default -> throw new QueryValidationException("Query error. Incompatible types");
                };
            case "<":
                return switch (paramBeforeOperator) {
                    case "id", "age" -> (Long) tableColumnValue < (Long) paramCondition;
                    case "cost" -> (Double) tableColumnValue < (Double) paramCondition;
                    default -> throw new QueryValidationException("Query error. Incompatible types");
                };
            case "=":
                return switch (paramBeforeOperator) {
                    case "id", "age" -> ((Long) tableColumnValue).equals((Long) paramCondition);
                    case "cost" -> ((Double) tableColumnValue).equals((Double) paramCondition);
                    case "lastname" -> tableColumnValue.equals(paramCondition);
                    case "active" -> (Boolean) tableColumnValue == (Boolean) paramCondition;
                    default -> throw new QueryValidationException("Query error. Incompatible types");
                };
            case ">=":
                return switch (paramBeforeOperator) {
                    case "id", "age" -> (Long) tableColumnValue >= (Long) paramCondition;
                    case "cost" -> (Double) tableColumnValue >= (Double) paramCondition;
                    default -> throw new QueryValidationException("Query error. Incompatible types");
                };
            case "<=":
                return switch (paramBeforeOperator) {
                    case "id", "age" -> (Long) tableColumnValue <= (Long) paramCondition;
                    case "cost" -> (Double) tableColumnValue <= (Double) paramCondition;
                    default -> throw new QueryValidationException("Query error. Incompatible types");
                };
            case "like":
                if (paramBeforeOperator.equals("lastname")) {
                    String paramBefore = tableColumnValue.toString();
                    String paramAfter = paramCondition.toString();

                    if (paramAfter.startsWith("%") && paramAfter.endsWith("%")) {
                        paramAfter = paramAfter.substring(1, paramAfter.length() - 1);

                        return paramBefore.contains(paramAfter);
                    } else if (paramAfter.startsWith("%")) {
                        paramAfter = paramAfter.substring(1);

                        return paramBefore.endsWith(paramAfter);
                    } else if (paramAfter.endsWith("%")) {
                        paramAfter = paramAfter.substring(0, paramAfter.length() - 1);

                        return paramBefore.startsWith(paramAfter);
                    } else if (!paramAfter.contains("%")) {

                        return paramBefore.equals(paramAfter);
                    }
                } else {
                    throw new QueryValidationException("Query error. Incompatible types");
                }
                break;
            default:
                throw new QueryValidationException("Query error. Error operator");
        }

        return false;
    }
}
