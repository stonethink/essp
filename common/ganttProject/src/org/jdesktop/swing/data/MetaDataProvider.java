/*
 * $Id: MetaDataProvider.java,v 1.1 2006/10/10 03:51:00 mingxingzhang Exp $
 *
 * Copyright 2004 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 */
package org.jdesktop.swing.data;

/**
 * Interface for marking objects which can return MetaData instances for
 * data objects.
 *
 * @author Jeanette Winzenburg
 * @author Amy Fowler
 * @version 1.0
 */
public interface MetaDataProvider {

    /**
     * Note: if the type for id is changed to Object type this will
     * have to change to returning Object[].
     * 
     * @return array containing the names of all data fields in this map
     */
    String[] getFieldNames();

    /**
    *
    * @return integer containing the number of contained MetaData
    */

    int getFieldCount();

    /**
     * Note: String will likely be converted to type Object for the ID
     * @param dataID String containing the id of the data object
     * @return MetaData object which describes properties, edit constraints
     *         and validation logic for a data object
     */
    MetaData getMetaData(String dataID);

    /**
     * convenience to return all MetaData.
     * 
     * @return
     */
    MetaData[] getMetaData();


}