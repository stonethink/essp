/*
 * $Id: HierarchicalDataMetaData.java,v 1.1 2006/10/10 03:51:00 mingxingzhang Exp $
 *
 * Copyright 2004 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 */

package org.jdesktop.swing.data;

import java.util.HashMap;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.jdesktop.swing.table.TabularDataMetaData;

import org.jdesktop.swing.data.Converters;
import org.jdesktop.swing.data.Converter;

/**
 * @author Ramesh Gupta
 */
public class HierarchicalDataMetaData extends TabularDataMetaData {
    private		Element		columns = null;
    private static HashMap	typesMap;

    // Merge with realizer.attr.Decoder.typesMap()
    static {
        typesMap = new HashMap();
        typesMap.put("boolean", java.lang.Boolean.class);
        typesMap.put("date", java.util.Date.class);
        typesMap.put("double", java.lang.Double.class);
        typesMap.put("float", java.lang.Float.class);
        typesMap.put("integer", java.lang.Integer.class);
        typesMap.put("string", java.lang.String.class);
    }

    public HierarchicalDataMetaData(Element metaDataElement) {
        super((metaDataElement == null) ?
            0 : Integer.parseInt(metaDataElement.getAttribute("columnCount")));
    	columns = metaDataElement;
        init();
    }

    protected void init() {
        if (columns != null) {
            NodeList list = ( (Element) columns).getChildNodes();
            int i = 0, k = 0, max = list.getLength();
            Node node;
            Element elem;
            while (i < max) {
                node = list.item(i++);
                if (node instanceof Element) {
                    elem = (Element) node;
                    if (elem.getLocalName().equals("columnMetaData")) {
                        setColumnName(++k, elem.getAttribute("name"));
                        String	type = elem.getAttribute("type");
                        if (type.length() > 0) {
                            Class klass = decodeType(type);
                            if (klass != null) {
                                setColumnClass(k, klass);
                                if (klass != String.class) {
                                    Converter converter =
                                        Converters.get(klass);
                                    if (converter == null) {
                                        System.err.println(
                                            "warning: couldn't find converter for " +
                                            klass.getName() +
                                            ". Reseting class of column " +
                                            k + "to String.class");
                                        setColumnClass(k, String.class);
                                    }
                                    else {
                                        setColumnConverter(k, converter); //stash it
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Merge with realizer.attr.Decoder.decodeType()
    private Class decodeType(String value) {
        Class klass = (Class)typesMap.get(value);
        if (klass == null) {
            try {
                klass = Class.forName(value);
            } catch (ClassNotFoundException e) {
                System.out.println("Could not convert type: " + value + " to a java type or class");
            }
        }
        return klass;
    }

}
