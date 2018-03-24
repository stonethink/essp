package validator;

import org.apache.commons.validator.util.ValidatorUtils;
import org.apache.commons.validator.Field;
import org.apache.commons.validator.Var;
import org.apache.commons.validator.GenericValidator;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author yery
 * @version 1.0
 */
public class RangeValidator {
  public static boolean validateRange(Object bean, Field field) {
    double dMax = -1;
    double dMin = -1;
    boolean isValid = true;

    String value = ValidatorUtils.getValueAsString(bean, field.getProperty());
    if (value == null) {
      return true;
    }

    double dValue = 0;
    try {
      dValue = Double.parseDouble(value);
    }
    catch (NumberFormatException ex2) {
      return true;
    }

    try {
      Var maxVar = field.getVar("max");
      String maxValue = maxVar.getValue();
      dMax = Double.parseDouble(maxValue);
    }
    catch (Exception ex) {

    }

    try {
      Var minVar = field.getVar("min");
      String mimValue = minVar.getValue();
      dMin = Double.parseDouble(mimValue);
    }
    catch (Exception ex1) {
    }

    if (dMax == -1) {
      /**
       * 不检查最大值
       */
      if (dMin == -1) {
        /**
         * 不检查最小值
         */
        isValid = true;
      }
      else {
        /**
         * 检查最小值
         */
        if (dValue < dMin) {
          isValid = false;
        }
        else {
          isValid = true;
        }
      }
    }
    else {
      /**
       * 检查最大值
       */
      if (dMin == -1) {
        /**
         * 不检查最小值
         */
        if (dValue > dMax) {
          isValid = false;
        }
        else {
          isValid = true;
        }

      }
      else {
        /**
         * 检查最小值
         */
        if (dValue > dMax || dValue < dMin) {
          isValid = false;
        }
        else {
          isValid = true;
        }

      }

    }
    return isValid;
  }
}
