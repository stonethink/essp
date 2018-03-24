/*
 * $Header: /home/cvsroot/essp/essp2/common/Validator/src/org/apache/commons/validator/TestPair.java,v 1.1.1.1 2005/08/15 06:10:36 huaxiao Exp $
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
 /**
  * Groups tests and expected results.
  */
 package org.apache.commons.validator;
 class TestPair {
      public String item;
      public boolean valid;

      public TestPair(String item, boolean valid) {
         this.item = item;
         this.valid = valid;  //Weather the individual part of url is valid.
      }
   }
