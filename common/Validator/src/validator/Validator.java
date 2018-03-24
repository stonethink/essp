package validator;

import java.util.ResourceBundle;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import org.xml.sax.SAXException;
import java.io.IOException;
import java.util.Locale;

public class Validator {
  private String configXml = "";
  private String configMsg = "";
  ResourceBundle apps;
  org.apache.commons.validator.ValidatorResources resources;
  private static Class resourceClass=null;

  private Log log = new Log();

  public static void setResourceInstance(Class cls) {
	 resourceClass=cls;
  }

  /**
   *
   * @param configXml String 定义检查规则
   * @param configMsg String 定义错误信息
   */
  public Validator(String configXml, String configMsg) throws SAXException,
      IOException {
    Class resClass=resourceClass;
    if(resClass==null) {
		resClass=Validator.class;
	}

    this.configXml = configXml;
    if(this.configXml!=null && !this.configXml.startsWith("/")) {
      this.configXml="/"+this.configXml;
    }
    this.configMsg = configMsg;
    if(this.configMsg!=null && this.configMsg.startsWith("/")) {
      this.configMsg=this.configMsg.substring(1);
    }

    if(this.configMsg!=null && this.configMsg.endsWith(".properties")) {
      this.configMsg=this.configMsg.substring(0,this.configMsg.length()-11);
    }

    java.io.InputStream in = null;
    java.net.URL url = resClass.getResource(this.configXml);
    if(url==null) {
      log.error("Can't read "+this.configXml+" , validator setup fail");
    } else {
      in = url.openStream();
      resources = new org.apache.commons.validator.ValidatorResources(in);
      apps =
          ResourceBundle.getBundle(this.configMsg,Locale.getDefault(),resClass.getClassLoader());
    }
  }

  /**
   * 对单个值作检查
   * @param value Object
   * @param formName String
   * @param fieldName String
   * @return ValidatorResult
   */
  public ValidatorResult validate(Object value,
                                  String formName,
                                  String fieldName) {
    ValidatorResult result = new ValidatorResult();
    try {
      org.apache.commons.validator.Validator validator = new org.apache.commons.
          validator.Validator(resources, formName);

      validator.setParameter(org.apache.commons.validator.Validator.
                             BEAN_PARAM, value);
      validator.setOnlyReturnErrors(true);
      org.apache.commons.validator.ValidatorResults results = validator.
          validate();
      org.apache.commons.validator.Form form = resources.getForm(java.util.
          Locale.getDefault(), formName);
      if (form == null) {
        log.info("Form '" + formName + "' not configed");
        return result;
      }

      String propertyName = fieldName;

      org.apache.commons.validator.Field field = form.getField(propertyName);
      if (field == null) {
        log.info("Field '" + propertyName + "' not configed in Form '" +
                 formName + "'");
        return result;
      }

      // Look up the formatted name of the field from the Field arg0
      String prettyFieldName = apps.getString(field.getArg(0).getKey());
      // Get the result of validating the property.
      org.apache.commons.validator.ValidatorResult validateResult = results.
          getValidatorResult(propertyName);

      java.util.Map actionMap = validateResult.getActionMap();

      if (actionMap == null) {
        log.info("actionMap is null");
        return result;
      }

      java.util.Iterator keys = actionMap.keySet().iterator();
      while (keys.hasNext()) {
        String actName = (String) keys.next();
        // Get the Action for that name.
        org.apache.commons.validator.ValidatorAction action = resources.
            getValidatorAction(actName);

        //If the result failed, format the Action's message against the formatted field name
        if (!validateResult.isValid(actName)) {
          if (action.getMsg() != null && action.getMsg().equals("") == false) {
            String message = apps.getString(action.getMsg());
            Object[] args2 = {
                prettyFieldName};
            //msgList.add(java.text.MessageFormat.format(message, args2));
            result.addMsg(propertyName,
                          java.text.MessageFormat.format(message, args2));
          }
          else {
            log.info("The message of Action '" + actName + "' is null");
          }
        }

      }
    }
    catch (org.apache.commons.validator.ValidatorException ex) {
      ex.printStackTrace();
    }

    return result;
  }

  /**
   * 对 bean 中所有的属性做检查
   * @param bean Object
   * @return ValidatorResult
   */
  public ValidatorResult validate(Object bean) {
    ValidatorResult result = new ValidatorResult();
    if (bean == null) {
      return result;
    }
    String beanName = bean.getClass().getName();
    int dotIndex = beanName.lastIndexOf(".");
    if (dotIndex >= 0) {
      beanName = beanName.substring(dotIndex + 1);
    }

    org.apache.commons.validator.Validator validator = new org.apache.commons.
        validator.Validator(resources, beanName);
    try {
      validator.setParameter(org.apache.commons.validator.Validator.
                             BEAN_PARAM, bean);
      validator.setOnlyReturnErrors(true);

      org.apache.commons.validator.Form form = resources.getForm(java.util.
          Locale.getDefault(), beanName);

      if (form == null) {
        log.info("Form '" + beanName + "' not configed");
        return result;
      }

      org.apache.commons.validator.ValidatorResults results = validator.
          validate();

      if (results.getPropertyNames() == null) {
        log.info("Form '" + beanName + "' no error");
        return result;
      }

      java.util.Iterator propertyNames = results.getPropertyNames().iterator();

      while (propertyNames.hasNext()) {
        String propertyName = (String) propertyNames.next();

        org.apache.commons.validator.Field field = form.getField(propertyName);

        // Look up the formatted name of the field from the Field arg0
        String prettyFieldName = apps.getString(field.getArg(0).getKey());
        // Get the result of validating the property.
        org.apache.commons.validator.ValidatorResult validateResult = results.
            getValidatorResult(propertyName);

        java.util.Map actionMap = validateResult.getActionMap();

        if (actionMap == null) {
          log.info("actionMap is null");
          continue;
        }

        java.util.Iterator keys = actionMap.keySet().iterator();
        while (keys.hasNext()) {
          String actName = (String) keys.next();
          // Get the Action for that name.
          org.apache.commons.validator.ValidatorAction action = resources.
              getValidatorAction(actName);

          //If the result failed, format the Action's message against the formatted field name
          if (!validateResult.isValid(actName)) {
            if (action.getMsg() == null || action.getMsg().equals("")) {
              log.info("The message of Action '" + actName + "' is null");
            }
            else {
              String message = apps.getString(action.getMsg());
              Object[] args2 = {
                  prettyFieldName};
              //msgList.add(java.text.MessageFormat.format(message, args2));
              result.addMsg(propertyName,
                            java.text.MessageFormat.format(message, args2));
            }

          }

        }

      }
    }
    catch (org.apache.commons.validator.ValidatorException ex) {
      ex.printStackTrace();
    }

    return result;
  }

  /**
   * validate要求:
   * 1) configXml中定义的Form名称与bean class的名称相同;
   * 2) configXml中定义的Field名称与bean class中的属性名称相同;
   * 3) 为了方便错误处理，bean class中要定义与属性匹配的setError和isError方法。
   *    比如，对于属性name，除了有setName()和getName()，还应该有setNameError()和isNameError()方法
   *
   *
   * @param bean Object
   * @param propertyName String
   * @return ValidatorResult
   */
  public ValidatorResult validate(Object bean, String propertyName) {
    ValidatorResult result = new ValidatorResult();

    if (propertyName == null || bean == null) {
      return result;
    }
    else {
      String beanName = bean.getClass().getName();

      int dotIndex = beanName.lastIndexOf(".");
      if (dotIndex >= 0) {
        beanName = beanName.substring(dotIndex + 1);
      }
      System.out.println("beanName=" + beanName + ",propertyName=" +
                         propertyName);

      String getMethodName = "get" + propertyName.substring(0, 1).toUpperCase() +
          propertyName.substring(1);
      String setErrorMethodName = "set" +
          propertyName.substring(0, 1).toUpperCase() +
          propertyName.substring(1) + "Error";
      Class[] types = new Class[1];
      types[0] = boolean.class;

      org.apache.commons.validator.Validator validator = new org.apache.commons.
          validator.Validator(resources, beanName);

      try {
        validator.setParameter(org.apache.commons.validator.Validator.
                               BEAN_PARAM, bean);
        validator.setOnlyReturnErrors(true);
        org.apache.commons.validator.Form form = resources.getForm(java.util.
            Locale.getDefault(), beanName);
        if (form == null) {
          log.info("Form '" + beanName + "' not configed");
          return result;
        }
        org.apache.commons.validator.Field field = form.getField(propertyName);
        if (field == null) {
          log.info("Field '" + propertyName + "' not configed in Form '" +
                   beanName + "'");
          return result;
        }

        org.apache.commons.validator.ValidatorResults results = validator.
            validate();

        // Look up the formatted name of the field from the Field arg0
        String prettyFieldName = apps.getString(field.getArg(0).getKey());
        // Get the result of validating the property.
        org.apache.commons.validator.ValidatorResult validateResult = results.
            getValidatorResult(propertyName);

        if (validateResult == null) {
          return result;
        }

        java.util.Map actionMap = validateResult.getActionMap();

        if (actionMap == null) {
          log.info("actionMap is null");
          return result;
        }

        java.util.Iterator keys = actionMap.keySet().iterator();
        int i = 0;

        while (keys.hasNext()) {
          String actName = (String) keys.next();
          // Get the Action for that name.
          org.apache.commons.validator.ValidatorAction action = resources.
              getValidatorAction(actName);

          //If the result failed, format the Action's message against the formatted field name
          if (!validateResult.isValid(actName)) {
            if (action.getMsg() != null && action.getMsg().equals("") == false) {
              String message = apps.getString(action.getMsg());
              Object[] args2 = {
                  prettyFieldName};
              result.addMsg(propertyName,
                            java.text.MessageFormat.format(message, args2));
            }
            else {
              log.info("The message of Action '" + actName + "' is null");
            }
          }
          i++;
        }

      }
      catch (org.apache.commons.validator.ValidatorException ex) {
        ex.printStackTrace();

      }
    }

    return result;
  }

  class Log {
    private String host = "[Validator] ";
    public void info(String msg) {
      System.out.println(host + msg);
    }

    public void error(String msg) {
      System.out.println(host + msg);
    }

    public void debug(String msg) {
      System.out.println(host + msg);
    }

  }
}
