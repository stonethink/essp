/*
 * $Header: /home/cvsroot/essp/essp2/common/Validator/test/org/apache/commons/validator/TestNumber.java,v 1.1.1.1 2005/08/15 06:10:36 huaxiao Exp $
 * $Revision: 1.1.1.1 $
 * $Date: 2005/08/15 06:10:36 $
 *
 * ====================================================================
 * Copyright 2001-2004 The Apache Software Foundation
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
 * Abstracts number unit tests methods.
 */
abstract public class TestNumber extends TestCommon {
    
    /**
     * The key used to retrieve the set of validation
     * rules from the xml file.
     */
    protected String FORM_KEY;
    
    /**
     * The key used to retrieve the validator action.
     */
    protected String ACTION;


    public TestNumber(String name) {
        super(name);
    }

    /**
     * Load <code>ValidatorResources</code> from 
     * validator-numeric.xml.
     */
    protected void setUp() throws IOException, SAXException {
        // Load resources
        loadResources("validator-numeric.xml");
    }

    protected void tearDown() {
    }

    /**
     * Tests the number validation.
     */
    public void testNumber() throws ValidatorException {
        // Create bean to run test on.
        ValueBean info = new ValueBean();
        info.setValue("0");
        if (log.isDebugEnabled()) {
            log.debug("testNumberFailure Action=" + ACTION + ", FORM_KEY=" + FORM_KEY);
        }
        valueTest(info, true);
    }

    /**
     * Tests the float validation failure.
     */
    public void testNumberFailure() throws ValidatorException {
        // Create bean to run test on.
        ValueBean info = new ValueBean();
        if (log.isDebugEnabled()) {
            log.debug("testNumberFailure Action=" + ACTION + ", FORM_KEY=" + FORM_KEY);
        }
        valueTest(info, false);
    }

    /**
     * Utlity class to run a test on a value.
     *
     * @param	info	Value to run test on.
     * @param	passed	Whether or not the test is expected to pass.
     */
    protected void valueTest(Object info, boolean passed) throws ValidatorException {
        // Construct validator based on the loaded resources
        // and the form key
        Validator validator = new Validator(resources, FORM_KEY);
        // add the name bean to the validator as a resource
        // for the validations to be performed on.
        validator.setParameter(Validator.BEAN_PARAM, info);

        // Get results of the validation.
        ValidatorResults results = null;

        // throws ValidatorException,
        // but we aren't catching for testing
        // since no validation methods we use
        // throw this
        results = validator.validate();

        assertNotNull("Results are null.", results);

        ValidatorResult result = results.getValidatorResult("value");

        assertNotNull(ACTION + " value ValidatorResult should not be null.", result);
        assertTrue(ACTION + " value ValidatorResult should contain the '" + ACTION + "' action.", result.containsAction(ACTION));
        assertTrue(ACTION + " value ValidatorResult for the '" + ACTION + "' action should have " + (passed ? "passed" : "failed") + ".", (passed ? result.isValid(ACTION) : !result.isValid(ACTION)));
    }


}
