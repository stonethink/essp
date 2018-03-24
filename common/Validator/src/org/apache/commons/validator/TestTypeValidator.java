/*
 * $Header: /home/cvsroot/essp/essp2/common/Validator/src/org/apache/commons/validator/TestTypeValidator.java,v 1.1.1.1 2005/08/15 06:10:36 huaxiao Exp $
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

import org.apache.commons.validator.util.ValidatorUtils;
                                                          
/**                                                       
 * Contains validation methods for different unit tests.
 */                                                       
public class TestTypeValidator {

   /**
    * Checks if the field can be successfully converted to a <code>byte</code>.
    *
    * @param value The value validation is being performed on.
    * @return boolean If the field can be successfully converted 
    * to a <code>byte</code> <code>true</code> is returned.  
    * Otherwise <code>false</code>.
    */
   public static Byte validateByte(Object bean, Field field) {
      String value = ValidatorUtils.getValueAsString(bean, field.getProperty());

      return GenericTypeValidator.formatByte(value);
   }

   /**
    * Checks if the field can be successfully converted to a <code>short</code>.
    *
    * @param value The value validation is being performed on.
    * @return boolean If the field can be successfully converted 
    * to a <code>short</code> <code>true</code> is returned.  
    * Otherwise <code>false</code>.
    */
   public static Short validateShort(Object bean, Field field) {
      String value = ValidatorUtils.getValueAsString(bean, field.getProperty());

      return GenericTypeValidator.formatShort(value);
   }

   /**
    * Checks if the field can be successfully converted to a <code>int</code>.
    *
    * @param value The value validation is being performed on.
    * @return boolean If the field can be successfully converted 
    * to a <code>int</code> <code>true</code> is returned.  
    * Otherwise <code>false</code>.
    */
   public static Integer validateInt(Object bean, Field field) {
      String value = ValidatorUtils.getValueAsString(bean, field.getProperty());

      return GenericTypeValidator.formatInt(value);
   }

   /**
    * Checks if the field can be successfully converted to a <code>long</code>.
    *
    * @param value The value validation is being performed on.
    * @return boolean If the field can be successfully converted 
    * to a <code>long</code> <code>true</code> is returned.  
    * Otherwise <code>false</code>.
    */
   public static Long validateLong(Object bean, Field field) {
      String value = ValidatorUtils.getValueAsString(bean, field.getProperty());

      return GenericTypeValidator.formatLong(value);
   }

   /**
    * Checks if the field can be successfully converted to a <code>float</code>.
    *
    * @param value The value validation is being performed on.
    * @return boolean If the field can be successfully converted 
    * to a <code>float</code> <code>true</code> is returned.  
    * Otherwise <code>false</code>.
    */
   public static Float validateFloat(Object bean, Field field) {
      String value = ValidatorUtils.getValueAsString(bean, field.getProperty());

      return GenericTypeValidator.formatFloat(value);
   }
   
   /**
    * Checks if the field can be successfully converted to a <code>double</code>.
    *
    * @param value The value validation is being performed on.
    * @return boolean If the field can be successfully converted 
    * to a <code>double</code> <code>true</code> is returned.  
    * Otherwise <code>false</code>.
    */
   public static Double validateDouble(Object bean, Field field) {
      String value = ValidatorUtils.getValueAsString(bean, field.getProperty());

      return GenericTypeValidator.formatDouble(value);
   }
      
}                                                         