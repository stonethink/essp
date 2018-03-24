package validator;

import org.apache.commons.validator.util.ValidatorUtils;
import org.apache.commons.validator.Field;
import org.apache.commons.validator.Var;
import org.apache.commons.validator.GenericValidator;
import java.util.Map;
import java.util.Collection;

public class ValueValidator {
    public static boolean validateMask(Object value, Field field) {
        String strValue =convertToString(value);
        Var maskVar = field.getVar("mask");
        String mask = maskVar.getValue();
        return GenericValidator.matchRegexp(strValue, mask);
    }

    public static boolean validateRequired(Object value, Field field) {
       String strValue =convertToString(value);
       return !GenericValidator.isBlankOrNull(strValue);
    }

    public static String convertToString(Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof String[]) {
            return ( (String[]) value).length > 0 ? value.toString() : "";

        } else if (value instanceof Collection) {
            return ( (Collection) value).isEmpty() ? "" : value.toString();

        } else {
            return value.toString();
        }
    }
}
