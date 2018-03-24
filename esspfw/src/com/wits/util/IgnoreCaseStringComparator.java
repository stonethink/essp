package com.wits.util;

import java.util.*;

public class IgnoreCaseStringComparator implements Comparator {
    public IgnoreCaseStringComparator() {
    }

    /**
     * Compares its two arguments for order.
     *
     * @param o1 the first object to be compared.
     * @param o2 the second object to be compared.
     * @return a negative integer, zero, or a positive integer as the first
     *   argument is less than, equal to, or greater than the second.
     * @todo Implement this java.util.Comparator method
     */
    public int compare(Object o1, Object o2) {
        String s1 = (String) o1;
        String s2 = (String) o2;

        return s1.compareToIgnoreCase(s2);
    }

    public boolean equals(Object obj) {
        return this.equals(obj);
    }


}
