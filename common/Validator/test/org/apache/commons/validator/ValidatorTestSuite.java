/*
 * $Header: /home/cvsroot/essp/essp2/common/Validator/test/org/apache/commons/validator/ValidatorTestSuite.java,v 1.1.1.1 2005/08/15 06:10:36 huaxiao Exp $
 * $Revision: 1.1.1.1 $
 * $Date: 2005/08/15 06:10:36 $
 *
 * ====================================================================
 * Copyright 2000-2004 The Apache Software Foundation
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

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.commons.validator.util.FlagsTest;

/**
 * Test suite for <code>org.apache.commons.validator</code>
 * package.
 */
public class ValidatorTestSuite extends TestCase {

    public ValidatorTestSuite(String name) {
        super(name);
    }

    public static Test suite() {
       TestSuite suite = new TestSuite();

       suite.addTest(RequiredNameTest.suite());
       suite.addTest(RequiredIfTest.suite());
       suite.addTest(MultipleTests.suite());
       suite.addTestSuite(MultipleConfigFilesTest.class);
       suite.addTest(ByteTest.suite());
       suite.addTest(ShortTest.suite());
       suite.addTest(IntegerTest.suite());
       suite.addTest(LongTest.suite());
       suite.addTest(FloatTest.suite());
       suite.addTest(DoubleTest.suite());
       suite.addTest(TypeTest.suite());
       suite.addTest(EmailTest.suite());
       suite.addTestSuite(CreditCardValidatorTest.class);
       suite.addTest(ValidatorTest.suite());
       suite.addTest(LocaleTest.suite());
       suite.addTestSuite(FieldTest.class);
       suite.addTestSuite(FlagsTest.class);
       suite.addTestSuite(ExceptionTest.class);
       suite.addTest(UrlTest.suite());
       
       return suite;
    }

    public static void main(String args[]) {
        junit.textui.TestRunner.run(suite());
    }

}
