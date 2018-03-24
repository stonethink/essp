/*
 * $Id: DefaultMetaDataProvider.java,v 1.1 2006/10/10 03:51:00 mingxingzhang Exp $
 *
 * Copyright 2004 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 */
package org.jdesktop.swing.data;

import java.util.*;


/**
 * @author Jeanette Winzenburg
 */
public class DefaultMetaDataProvider implements MetaDataProvider {

  private List fieldNames;
  private Map metaData;

  public DefaultMetaDataProvider() {
    
  }
  public DefaultMetaDataProvider(MetaDataProvider provider) {
    this(provider.getMetaData());
  }

  public DefaultMetaDataProvider(MetaData[] metaDatas) {
    
    setMetaData(metaDatas);
}
//------------------------ metaDataProvider  
  public MetaData[] getMetaData() {
    MetaData[] metaData = new MetaData[getFieldCount()];
    for (int i = 0; i < metaData.length; i++) {
      metaData[i] = getMetaData(getFieldName(i));
    }
    return metaData;
  }

  public MetaData getMetaData(String dataID) {
    return (MetaData) getMetaDataMap().get(dataID);
  }

//-------------------------- convenience accessing
  public String[] getFieldNames() {
    return (String[]) getFieldNameList().toArray(new String[getFieldCount()]);
  }

  public int getFieldCount() {
    return getFieldNameList().size();
  }

  /**
   * PRE: 0 <= index < getFieldCount()
   * @param index
   * @return
   */
  public String getFieldName(int index) {
    return (String) getFieldNameList().get(index);
  }

  public int getFieldIndex(String fieldName) {
    for (int i = 0; i < getFieldCount(); i++) {
      if (fieldName.equals(getFieldName(i))) {
        return i;
      }
    }
    return -1;
  }

  public boolean hasField(String fieldName) {
    return getFieldNameList().contains(fieldName);
  }

//-------------------------- mutating (use on init mostly)
  public void setMetaData(MetaData[] metaData) {
    clear();
    for (int i = 0; i < metaData.length; i++) {
      addField(metaData[i]);
    }
  }

  public void setMetaData(List metaData) {
    clear();
    for (Iterator iter = metaData.iterator(); iter.hasNext(); ) {
      addField((MetaData) iter.next());
    }
  }

  public void addField(MetaData data) {
    getFieldNameList().add(data.getName());
    getMetaDataMap().put(data.getName(), data);
  }

  public void clear() {
    fieldNames = null;
    metaData = null;
  }

//-------------------------- helper
  private List getFieldNameList() {
    if (fieldNames == null) {
      fieldNames = new ArrayList();
    }
    return fieldNames;
  }

  private Map getMetaDataMap() {
    if (metaData == null) {
      metaData = new HashMap();
    }
    return metaData;
  }
}
