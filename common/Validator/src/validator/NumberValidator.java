package validator;

import org.apache.commons.validator.Field;
import org.apache.commons.validator.util.ValidatorUtils;
import org.apache.commons.validator.GenericTypeValidator;

public class NumberValidator {
  public static boolean validateNonNegative(Object bean, Field field) {
    String value = ValidatorUtils.getValueAsString(bean, field.getProperty());
    if(value==null) {
      return true;
    }
    return GenericTypeValidator.formatDouble(value).doubleValue() >= 0;
  }

  public static boolean validatePositive(Object bean, Field field) {
    String value = ValidatorUtils.getValueAsString(bean, field.getProperty());
    return GenericTypeValidator.formatInt(value).intValue() > 0;
  }

}
