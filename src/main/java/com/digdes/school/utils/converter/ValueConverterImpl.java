package com.digdes.school.utils.converter;

import com.digdes.school.exception.QueryValidationException;

public class ValueConverterImpl implements ValueConverter {

    @Override
    public Object convert(String paramBeforeOperator, String paramAfterOperator) {
        try {
            if (paramAfterOperator.equalsIgnoreCase("null")) {
                throw new QueryValidationException("Error query. Param can not be null: " + paramBeforeOperator);
            }

            if (paramBeforeOperator.equals("id") || paramBeforeOperator.equals("age")) {
                return Long.valueOf(paramAfterOperator);
            } else if (paramBeforeOperator.equalsIgnoreCase("lastName")) {
                return paramAfterOperator;
            } else if (paramBeforeOperator.equals("cost")) {
                return Double.valueOf(paramAfterOperator);
            } else if (paramBeforeOperator.equals("active")) {

                if (paramAfterOperator.equalsIgnoreCase("true") ||
                        paramAfterOperator.equalsIgnoreCase("false") ||
                        paramAfterOperator.equalsIgnoreCase("1") ||
                        paramAfterOperator.equalsIgnoreCase("0")
                ) {
                    return Boolean.valueOf(paramAfterOperator);
                } else {
                    throw new QueryValidationException("Error query. Param" + paramBeforeOperator + " can not have no boolean param:" + paramAfterOperator);
                }

            } else {
                throw new QueryValidationException("Error query. It can not have param:" + paramBeforeOperator);
            }
        } catch (NumberFormatException e) {
            throw new QueryValidationException("Error query. It can not have param:" + paramAfterOperator);
        }
    }
}
