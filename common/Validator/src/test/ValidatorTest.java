package test;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.validator.Field;
import org.apache.commons.validator.Form;
import org.apache.commons.validator.Validator;
import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.ValidatorException;
import org.apache.commons.validator.ValidatorResources;
import org.apache.commons.validator.ValidatorResult;
import org.apache.commons.validator.ValidatorResults;
import org.xml.sax.SAXException;

public class ValidatorTest {

  /**
   * We need a resource bundle to get our field names and errors messages
   * from.  Note that this is not strictly required to make the Validator
   * work, but is a good coding practice.
   */
  private static ResourceBundle apps =
      ResourceBundle.getBundle(
      "test.applicationResources");

  /**
   * This is the main method that will be called to initialize the Validator, create some sample beans, and
   * run the Validator against them.
   */
  public static void main(String[] args) throws ValidatorException, IOException,
      SAXException {

    InputStream in = null;
    ValidatorResources resources = null;

    try {

      // Create a new instance of a ValidatorResource, then get a stream
      // handle on the XML file with the actions in it, and initialize the
      // resources from it.  This would normally be done by a servlet
      // run during JSP initialization or some other application-startup
      // routine.
      in = ValidateExample.class.getResourceAsStream("validator-example.xml");
      resources = new ValidatorResources(in);

    }
    finally {
      // Make sure we close the input stream.
      if (in != null) {
        in.close();
      }
    }

    // Create a test bean to validate against.
    ValidateBean bean = new ValidateBean();

    // Create a validator with the ValidateBean actions for the bean
    // we're interested in.
    Validator validator = new Validator(resources, "ValidateBean");

    // Tell the validator which bean to validate against.
    validator.setParameter(Validator.BEAN_PARAM, bean);

    ValidatorResults results = null;

    // Now only report failed fields
    validator.setOnlyReturnErrors(true);
    results=validator.validate();

    // Start by getting the form for the current locale and Bean.
    Form form = resources.getForm(Locale.getDefault(), "ValidateBean");

    Iterator propertyNames = results.getPropertyNames().iterator();
    while (propertyNames.hasNext()) {
      String propertyName = (String) propertyNames.next();

      // Get the Field associated with that property in the Form
      Field field = form.getField(propertyName);

      // Look up the formatted name of the field from the Field arg0
      String prettyFieldName = apps.getString(field.getArg(0).getKey());

      // Get the result of validating the property.
      ValidatorResult result = results.getValidatorResult(propertyName);

      // Get all the actions run against the property, and iterate over their names.
      Map actionMap = result.getActionMap();
      Iterator keys = actionMap.keySet().iterator();
      while (keys.hasNext()) {
        String actName = (String) keys.next();

        // Get the Action for that name.
        ValidatorAction action = resources.getValidatorAction(actName);

        //If the result failed, format the Action's message against the formatted field name
        if (!result.isValid(actName)) {
          boolean success = false;
          String message = apps.getString(action.getMsg());
          Object[] args2 = {
              prettyFieldName};
          System.out.println(
              "     Error message will be: "
              + MessageFormat.format(message, args2));

        }
      }
    }
  }

  /**
   * Dumps out the Bean in question and the results of validating it.
   */
  public static void printResults(
      ValidateBean bean,
      ValidatorResults results,
      ValidatorResources resources) {

    boolean success = true;

    // Start by getting the form for the current locale and Bean.
    Form form = resources.getForm(Locale.getDefault(), "ValidateBean");

    System.out.println("\n\nValidating:");
    System.out.println(bean);

    // Iterate over each of the properties of the Bean which had messages.
    Iterator propertyNames = results.getPropertyNames().iterator();
    while (propertyNames.hasNext()) {
      String propertyName = (String) propertyNames.next();

      // Get the Field associated with that property in the Form
      Field field = form.getField(propertyName);

      // Look up the formatted name of the field from the Field arg0
      String prettyFieldName = apps.getString(field.getArg(0).getKey());

      // Get the result of validating the property.
      ValidatorResult result = results.getValidatorResult(propertyName);

      // Get all the actions run against the property, and iterate over their names.
      Map actionMap = result.getActionMap();
      Iterator keys = actionMap.keySet().iterator();
      while (keys.hasNext()) {
        String actName = (String) keys.next();

        // Get the Action for that name.
        ValidatorAction action = resources.getValidatorAction(actName);

        // If the result is valid, print PASSED, otherwise print FAILED
        System.out.println(
            propertyName
            + "["
            + actName
            + "] ("
            + (result.isValid(actName) ? "PASSED" : "FAILED")
            + ")");

        //If the result failed, format the Action's message against the formatted field name
        if (!result.isValid(actName)) {
          success = false;
          String message = apps.getString(action.getMsg());
          Object[] args = {
              prettyFieldName};
          System.out.println(
              "     Error message will be: "
              + MessageFormat.format(message, args));

        }
      }
    }
    if (success) {
      System.out.println("FORM VALIDATION PASSED");
    }
    else {
      System.out.println("FORM VALIDATION FAILED");
    }

  }

}