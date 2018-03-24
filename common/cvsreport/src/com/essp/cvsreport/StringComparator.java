package com.essp.cvsreport;

import java.util.*;

public class StringComparator

    implements Comparator, java.io.Serializable {

  public int compare(Object o1, Object o2) {
    if( o1 == null ){
      if( o2 == null ){
        return 0;
      }else{
        return -1;
      }
    }else{
      if( o2 == null ){
        return 1;
      }
    }

    String s1 = (String) o1;
    String s2 = (String) o2;
    int n1 = s1.length(), n2 = s2.length();
    for (int i1 = 0, i2 = 0; i1 < n1 && i2 < n2; i1++, i2++) {
      char c1 = s1.charAt(i1);
      char c2 = s2.charAt(i2);
      if (c1 != c2) {
        c1 = Character.toUpperCase(c1);
        c2 = Character.toUpperCase(c2);
        if (c1 != c2) {
          c1 = Character.toLowerCase(c1);
          c2 = Character.toLowerCase(c2);
          if (c1 != c2) {
            return c1 - c2;
          }
        }
      }
    }
    return n1 - n2;
  }


}
