/*
 * $Id: NameGenerator.java,v 1.1 2006/10/10 03:50:53 mingxingzhang Exp $
 *
 * Copyright 2005 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 */

package org.jdesktop.dataset;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Generates names. Give it a prefix, it adds a postfix. Ask it to clear out
 * a name when it is no longer used, so that it can reuse that name
 *
 * @author rbair
 */
class NameGenerator {
    private String prefix;
    private Map<Object,String> usedNames = new WeakHashMap<Object,String>();
    
    /** Creates a new instance of NameGenerator */
    public NameGenerator(String prefix) {
        this.prefix = prefix;
    }
    
    /**
     * Generates a new name based on the prefix. The object reference is used
     * to associate with the generated name. The object reference is kept in a
     * weak reference, so that when this object retains the last reference to
     * obj, the generated name can be removed automatically
     */
    public String generateName(Object obj) {
        for (int i=0; i<Integer.MAX_VALUE; i++) {
            String name = prefix + (i+1);
            if (!usedNames.values().contains(name)) {
                usedNames.put(obj, name);
                return name;
            }
        }
        assert false : "Apparently I ran out of available names, this should never happen";
        return null;
    }    
}
