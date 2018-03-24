package validator;

import org.apache.commons.validator.util.ValidatorUtils;
import org.apache.commons.validator.Field;
import org.apache.commons.validator.Var;
import org.apache.commons.validator.GenericValidator;

public class DateValidator {
  public static boolean validateDate(Object bean, Field field) {
    String value = ValidatorUtils.getValueAsString(bean, field.getProperty());
    if(value==null) {
      return true;
    }
    Var patternVar=field.getVar("pattern");
    String pattern=patternVar.getValue();
    if(pattern==null) {
      System.out.println("[DateValidator] Warning:pattern of '"+field.getProperty()+"' is null");
      return true;
    }

    return GenericValidator.isDate(value,pattern,true);
  }

}
