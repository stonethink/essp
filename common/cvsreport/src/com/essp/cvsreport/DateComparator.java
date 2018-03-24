package com.essp.cvsreport;

import java.util.*;
import java.util.Calendar;

public class DateComparator

    implements Comparator, java.io.Serializable {

  final static long P2_20 = (long) Math.pow(2, 20);
  final static long P2_19 = (long) Math.pow(2, 19);
  final static long P2_18 = (long) Math.pow(2, 18);
  final static long P2_17 = (long) Math.pow(2, 17);
  final static long P2_16 = (long) Math.pow(2, 16);
  final static long P2_15 = (long) Math.pow(2, 15);
  final static long P2_14 = (long) Math.pow(2, 15);

  public int compare(Object o1, Object o2) {
    if (o1 == null) {
      if (o2 == null) {
        return 0;
      } else {
        return -1;
      }
    } else {
      if (o2 == null) {
        return 1;
      }
    }

    Calendar s1 = (Calendar) o1;
    Calendar s2 = (Calendar) o2;

    long n = s1.getTimeInMillis() - s2.getTimeInMillis();
    if (n > 0) {
      return 1;
    } else if (n < 0) {
      return -1;
    } else {
      return 0;
    }
  }
}
