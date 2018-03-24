package validator;

import org.apache.commons.validator.util.ValidatorUtils;
import org.apache.commons.validator.Field;
import org.apache.commons.validator.Var;
import org.apache.commons.validator.GenericValidator;

public class MaskValidator {
  public static boolean validateMask(Object bean, Field field) {
    String value = ValidatorUtils.getValueAsString(bean, field.getProperty());
    if(value==null) {
      return true;
    }
    Var maskVar=field.getVar("mask");
    String mask=maskVar.getValue();
    if(mask==null) {
      System.out.println("[MaskValidator] Warning:mask of '"+field.getProperty()+"' is null");
      return true;
    }
    return GenericValidator.matchRegexp(value,mask);
  }
}
