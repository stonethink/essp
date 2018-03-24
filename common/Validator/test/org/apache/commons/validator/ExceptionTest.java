/*
 * $Header: /home/cvsroot/essp/essp2/common/Validator/test/org/apache/commons/validator/ExceptionTest.java,v 1.1.1.1 2005/08/15 06:10:36 huaxiao Exp $
 * $Revision: 1.1.1.1 $
 * $Date: 2005/08/15 06:10:36 $
 *
 * ====================================================================
 * Copyright  2004 The Apache Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.commons.validator;

import java.io.IOException;

import org.xml.sax.SAXException;

/**                                                       
 * Performs Validation Test for exception handling.
 */
public class ExceptionTest extends TestCommon {

    /**
     * The key used to retrieve the set of validation 
     * rules from the xml file.
     */
    protected static String FORM_KEY = "exceptionForm";

    /**
     * The key used to retrieve the validator action.
     */
    protected static String ACTION = "raiseException";

    public ExceptionTest(String name) {
        super(name);
    }

    /**
     * Load <code>ValidatorResources</code> from 
     * validator-exception.xml.
     */
    protected void setUp() throws IOException, SAXException {
        loadResources("validator-exception.xml");
    }

    /**
     * Tests handling of checked exceptions - should become 
     * ValidatorExceptions.
     */
    public void testValidatorException() {
        // Create bean to run test on.
        ValueBean info = new ValueBean();
        info.setValue("VALIDATOR");

        // Construct validator based on the loaded resources 
        // and the form key
        Validator validator = new Validator(resources, FORM_KEY);
        // add the name bean to the validator as a resource 
        // for the validations to be performed on.
        validator.setParameter(Validator.BEAN_PARAM, info);

        // Get results of the validation which can throw ValidatorException
        try {
            validator.validate();
            fail("ValidatorException should occur here!");
        } catch (ValidatorException expected) {
            assertTrue("VALIDATOR-EXCEPTION".equals(expected.getMessage()));
        }
    }

    /**
     * Tests handling of runtime exceptions.
     */
    public void testRuntimeException() throws ValidatorException {
        // Create bean to run test on.
        ValueBean info = new ValueBean();
        info.setValue("RUNTIME");

        // Construct validator based on the loaded resources 
        // and the form key
        Validator validator = new Validator(resources, FORM_KEY);
        // add the name bean to the validator as a resource 
        // for the validations to be performed on.
        validator.setParameter(Validator.BEAN_PARAM, info);

        // Get results of the validation which can throw ValidatorException
        try {
            validator.validate();
            //fail("RuntimeException should occur here!");
        } catch (RuntimeException expected) {
            fail("RuntimeExceptions should be treated as validation failures in Validator 1.x.");
            // This will be true in Validator 2.0
            //assertTrue("RUNTIME-EXCEPTION".equals(expected.getMessage()));
        }
    }

    /**
     * Tests handling of checked exceptions - should become 
     * ValidatorExceptions.
     */
    public void testCheckedException() {
        // Create bean to run test on.
        ValueBean info = new ValueBean();
        info.setValue("CHECKED");

        // Construct validator based on the loaded resources 
        // and the form key
        Validator validator = new Validator(resources, FORM_KEY);
        // add the name bean to the validator as a resource 
        // for the validations to be performed on.
        validator.setParameter(Validator.BEAN_PARAM, info);

        // Get results of the validation which can throw ValidatorException
        
        // Tests Validator 1.x exception handling
        try {
            validator.validate();
        } catch (ValidatorException expected) {
            fail("Checked exceptions are not wrapped in ValidatorException in Validator 1.x.");
        } catch (Exception e) {
            assertTrue("CHECKED-EXCEPTION".equals(e.getMessage()));
        }
        
        // This will be true in Validator 2.0
//        try {
//            validator.validate();
//            fail("ValidatorException should occur here!");
//        } catch (ValidatorException expected) {
//            assertTrue("CHECKED-EXCEPTION".equals(expected.getMessage()));
//        }
    }
}
