package validator;

import junit.framework.*;
import test.ValidateBean;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author yery
 * @version 1.0
 */

public class ValidatorTest
    extends TestCase {
  private Validator validator = null;

  protected void setUp() throws Exception {
    super.setUp();
    validator = new Validator("/test/validator-example.xml",
                              "test/applicationResources");
  }

  protected void tearDown() throws Exception {
    validator = null;
    super.tearDown();
  }

  public void testValidate() {
    ValidateBean bean = new ValidateBean();
    bean.setFirstName("John");
    bean.setLastName("Tester");
    bean.setStreet1("1 Test Street");
    bean.setCity("Testville");
    bean.setState("TE");
    bean.setPostalCode("12345");
    bean.setAge("905");

    ValidatorResult actualReturn = validator.validate(bean);

    System.out.println("validate result:" + actualReturn.isValid());
    System.out.println(actualReturn.isValid("age")?"":actualReturn.getMsg("age")[0]);

    System.out.println("bean.isAgeError()="+!actualReturn.isValid("age"));
  }

}
