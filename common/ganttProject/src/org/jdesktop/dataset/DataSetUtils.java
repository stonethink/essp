/*
 * $Id: DataSetUtils.java,v 1.1 2006/10/10 03:50:53 mingxingzhang Exp $
 *
 * Copyright 2005 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 */

package org.jdesktop.dataset;

/**
 *
 * @author rbair
 */
class DataSetUtils {
    
    /** Creates a new instance of DataSetUtils */
    private DataSetUtils() {
    }
    
    /**
     * Checks to see if the given name is valid. If not, then return false.<br>
     * A valid name is one that follows the Java naming rules for
     * indentifiers, <b>except</b> that Java reserved words can
     * be used, and the name may begin with a number.
     */
    static boolean isValidName(String name) {
        return !name.matches(".*[\\s]");
    }
}
