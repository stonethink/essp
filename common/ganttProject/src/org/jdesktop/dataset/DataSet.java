/*
 * $Id: DataSet.java,v 1.1 2006/10/10 03:50:53 mingxingzhang Exp $
 *
 * Copyright 2005 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 */

package org.jdesktop.dataset;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.jdesktop.dataset.event.TableChangeEvent;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 
 * @author rbair
 */
public class DataSet {
    private String name;
	private Map<String,DataTable> tables = new HashMap<String,DataTable>();
    private Map<String,DataRelation> relations = new HashMap<String,DataRelation>();
    private Map<String,DataValue> values = new HashMap<String,DataValue>();

    private final class NameChangeListener implements PropertyChangeListener {
        public void propertyChange(PropertyChangeEvent evt) {
            Object source = evt.getSource();
            if (source instanceof DataTable) {
                DataTable table = (DataTable)source;
                tables.remove(evt.getOldValue());
                tables.put((String)evt.getNewValue(), table);
            } else if (source instanceof DataRelation) {
                DataRelation relation = (DataRelation)source;
                relations.remove(evt.getOldValue());
                relations.put((String)evt.getNewValue(), relation);
            } else if (source instanceof DataValue) {
                DataValue value = (DataValue)source;
                values.remove(evt.getOldValue());
                values.put((String)evt.getNewValue(), value);
            }
        }
    }
    
    private NameChangeListener nameChangeListener = new NameChangeListener();
    
    public DataSet() {
    }

    public DataTable createTable() {
        DataTable table = new DataTable(this);
        table.addPropertyChangeListener("name",  nameChangeListener);
        tables.put(table.getName(), table);
        return table;
    }
    
    public DataRelationTable createRelationTable() {
        DataRelationTable table = new DataRelationTable(this);
        table.addPropertyChangeListener("name",  nameChangeListener);
        tables.put(table.getName(), table);
        return table;
    }
    
    public DataRelation createRelation() {
        DataRelation relation = new DataRelation(this);
        relation.addPropertyChangeListener("name",  nameChangeListener);
        relations.put(relation.getName(), relation);
        return relation;
    }
    
    public DataValue createValue() {
        DataValue value = new DataValue(this);
        value.addPropertyChangeListener("name", nameChangeListener);
        values.put(value.getName(), value);
        return value;
    }
        
    public void dropTable(DataTable table) {
        dropTable(table.getName());
    }
    
    public void dropTable(String tableName) {
        tables.remove(tableName).removePropertyChangeListener("name",  nameChangeListener);
    }
    
    public void dropRelationTable(DataRelationTable table) {
        dropTable(table.getName());
    }
    
    public void dropRelationTable(String tableName) {
        dropTable(tableName);
    }
    
    public void dropRelation(DataRelation relation) {
        dropRelation(relation.getName());
    }
    
    public void dropRelation(String relationName) {
        relations.remove(relationName).removePropertyChangeListener("name",  nameChangeListener);
    }
    
    public void dropValue(DataValue value) {
        dropValue(value.getName());
    }
    
    public void dropValue(String valueName) {
        values.remove(valueName).removePropertyChangeListener("name",  nameChangeListener);
    }
    
    protected boolean hasElement(String name) {
        boolean b = relations.containsKey(name);
        if (!b) {
            b = tables.containsKey(name);
        }
        if (!b) {
            b = values.containsKey(name);
        }
        return b;
    }
    
    public List<DataRow> getRows(String path) {
        if (path == null || path.trim().equals("")) {
            return Collections.EMPTY_LIST;
        }
        
        path = path.trim();
        
        //first, split on "."
        String[] steps = path.split("\\.");
        
        //each step is either a specific name ("myTable"), or is a composite
        //of a name and an index ("myTable[mySelector]")
        
        //maintain a collection of results
        List<DataRow> workingSet = null;
        
        for (String step : steps) {
            String name = null;
            String selectorName = null;
            if (step.contains("[")) {
                name = step.substring(0, step.indexOf('['));
                selectorName = step.substring(step.indexOf('[')+1,  step.indexOf(']'));
            }
            
            if (workingSet == null) {
                //get all of the results from the named object (better be a
                //table, not a relation!)
                DataTable table = tables.get(name);
                if (table == null) {
                    assert false;
                }
                workingSet = table.getRows();
                if (selectorName != null) {
//                    workingSet = filterRows(workingSet, selectors.get(selectorName));
                }
            } else {
                //better be a relation...
                DataRelation relation = relations.get(name);
                if (relation == null) {
                    assert false;
                }
                workingSet = relation.getRows((DataRow[])workingSet.toArray(new DataRow[workingSet.size()]));
                if (selectorName != null) {
//                    workingSet = filterRows(workingSet, selectors.get(selectorName));
                }
            }
        }
        return Collections.unmodifiableList(workingSet);
    }
    
    public List<DataRow> filterRows(List<DataRow> rows, DataSelector ds) {
        List<Integer> indices = ds.getRowIndices();
        List<DataRow> results = new ArrayList<DataRow>(indices.size());
        for (int index : indices) {
            results.add(rows.get(index));
        }
        return results;
    }
    
    public List<DataColumn> getColumns(String path) {
        //path will either include a single table name, or a single
        //relation name, followed by a single column name
        String[] parts = path.split("\\.");
        assert parts.length == 1 || parts.length == 2;
        
        DataTable table = tables.get(parts[0]);
        if (table == null) {
            DataRelation relation = relations.get(parts[0]);
            if (relation == null) {
                return new ArrayList<DataColumn>();
            } else {
                table = relation.getChildColumn().getTable();
            }
        }
        
        if (parts.length == 1) {
            return table.getColumns();
        } else {
            List<DataColumn> results = new ArrayList<DataColumn>();
            results.add(table.getColumn(parts[1]));
            return Collections.unmodifiableList(results);
        }
    }
    
    public DataTable getTable(String tableName) {
        return tables.get(tableName);
    }
    
    public DataRelationTable getRelationTable(String name) {
        return (DataRelationTable)tables.get(name);
    }
    
    public void load() {
        for (DataTable table : tables.values()) {
            if (!(table instanceof DataRelationTable)) {
                table.load();
            }
        }
    }

    /**
     * Constructs a DataSet based on the given XML schema.
     */
    public static DataSet createFromSchema(String schema) {
        DataSet ds = new DataSet();
        //set up an XML parser to parse the schema
        try {
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document dom = db.parse(new ByteArrayInputStream(schema.getBytes()));
            
            //extract the dataset name
            XPath xpath = XPathFactory.newInstance().newXPath();
            ds.name = xpath.evaluate("/schema/@id", dom);
            
            //begin extracting the dataset. First, locate the element related
            //to this dataset (where the name attribute equals the id
            String expression = "/schema/element[@name='" + ds.name + "']";
            NodeList nodes = (NodeList)xpath.evaluate(expression, dom, XPathConstants.NODESET);
            if (nodes != null && nodes.getLength() > 0) {
                Node dataSetNode = nodes.item(0);
                //now that I have the node, extract the DataTables
                NodeList tableNodes = (NodeList)xpath.evaluate("complexType/choice/element", dataSetNode, XPathConstants.NODESET);
                for (int i=0; i<tableNodes.getLength(); i++) {
                    Node tableNode = tableNodes.item(i);
                    DataTable table = ds.createTable();
                    table.setName(xpath.evaluate("@name", tableNode));
                    //construct the table's data columns
                    NodeList columnNodes = (NodeList)xpath.evaluate("complexType/sequence/element", tableNode,  XPathConstants.NODESET);
                    for (int j=0; j<columnNodes.getLength(); j++) {
                        Node colNode = columnNodes.item(j);
                        DataColumn col = table.createColumn(xpath.evaluate("@name", colNode));
                        //set the required flag
                        String minOccurs = xpath.evaluate("@minOccurs", colNode);
                        if (minOccurs.equals("")) {
                            col.setRequired(true);
                        }
                        
                        String defaultValue = xpath.evaluate("@default", colNode);
                        String classType = xpath.evaluate("@type", colNode);
                        if (classType.equals("xs:string")) {
                            col.setType(String.class);
                            if (!defaultValue.equals("")) {
                                col.setDefaultValue(defaultValue);
                            }
                        } else if (classType.equals("xs:decimal")) {
                            col.setType(BigDecimal.class);
                            if (!defaultValue.equals("")) {
                                col.setDefaultValue(new BigDecimal(defaultValue));
                            }
                        } else if (classType.equals("xs:integer") || classType.equals("xs:int")) {
                            col.setType(Integer.class);
                            if (!defaultValue.equals("")) {
                                col.setDefaultValue(new Integer(defaultValue));
                            }
                        } else if (classType.equals("xs:boolean")) {
                            col.setType(Boolean.class);
                            if (!defaultValue.equals("")) {
                                col.setDefaultValue(Boolean.parseBoolean(defaultValue));
                            }
                        } else if (classType.equals("xs:date") || classType.equals("xs:time") || classType.equals("xs.dateTime")) {
                            col.setType(Date.class);
                            if (!defaultValue.equals("")) {
                                col.setDefaultValue(new Date(Date.parse(defaultValue)));
                            }
                        } else if (classType.equals("xs:unsignedByte")) {
                            col.setType(Byte.class);
                            if (!defaultValue.equals("")) {
                                col.setDefaultValue(new Byte(defaultValue));
                            }
                        } else {
                            System.err.println("unexpected classType: '"  + classType + "'");
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }
    
    /**
     * Construct and return a proper schema file describing the DataSet
     */
    public String getSchema() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("<?xml version=\"1.0\" standalone=\"yes\" ?>\n");
        buffer.append("<xs:schema id=\"DataSet2\" targetNamespace=\"http://www.tempuri.org/DataSet2.xsd\" xmlns:mstns=\"http://www.tempuri.org/DataSet2.xsd\" " +
                "xmlns=\"http://www.tempuri.org/DataSet2.xsd\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" " +
                "xmlns:msdata=\"urn:schemas-microsoft-com:xml-msdata\" attributeFormDefault=\"qualified\" elementFormDefault=\"qualified\">\n");
        buffer.append("\t<xs:element name=\"");
        buffer.append(name);
        buffer.append(" msdata:IsDataSet=\"true\">\n");
        buffer.append("\t\t<xs:complexType>\n");
        buffer.append("\t\t\t<xs:choice maxOccurs=\"unbounded\">\n");
        for (DataTable table : tables.values()) {
            buffer.append("\t\t\t\t<xs:element name=\"");
            buffer.append(table.getName());
            buffer.append("\">\n");
            buffer.append("\t\t\t\t\t<xs:complexType>\n");
            buffer.append("\t\t\t\t\t\t<xs:sequence>\n");
            for (DataColumn col : table.getColumns()) {
                buffer.append("\t\t\t\t\t\t\t<xs:element name=\"");
                buffer.append(col.getName());
                buffer.append("\" type=\"");
                if (col.getType() == String.class) {
                    buffer.append("xs:string");
                } else if (col.getType() == BigDecimal.class) {
                    buffer.append("xs:decimal");
                } else if (col.getType() == Integer.class) {
                    buffer.append("xs:integer");
                } else if (col.getType() == Boolean.class) {
                    buffer.append("xs:boolean");
                } else if (col.getType() == Date.class) {
                    buffer.append("xs:dateTime");
                } else if (col.getType() == Byte.class) {
                    buffer.append("xs:unsignedByte");
                }
                if (col.getDefaultValue() != null) {
                    buffer.append("\" default=\"");
                    buffer.append(col.getDefaultValue());
                }
                if (!col.isRequired()) {
                    buffer.append("\" minOccurs=\"0");
                }
                buffer.append("\" />\n");
            }
            buffer.append("\t\t\t\t\t\t</xs:sequence>\n");
            buffer.append("\t\t\t\t\t</xs:complexType>\n");
            buffer.append("\t\t\t\t</xs:element>\n");
        }
        buffer.append("\t\t\t</xs:choice>\n");
        buffer.append("\t\t</xs:complexType>\n");
        
        //TODO write the relations etc out
        
        //close the document down
        buffer.append("\t</xs:element>\n");
        buffer.append("</xs:schema>\n");
	
        return buffer.toString();
    }
    
    public static DataSet createFromSchema(File f) {
        String schema = "";
        try {
            FileInputStream fis = new FileInputStream(f);
            DataSet ds = createFromSchema(fis);
            fis.close();
            return ds;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static DataSet createFromSchema(InputStream is) {
        String schema = "";
        try {
            StringBuilder builder = new StringBuilder();
            byte[] bytes = new byte[4096];
            int length = -1;
            while ((length = is.read(bytes)) != -1) {
                builder.append(new String(bytes, 0, length));
            }
            schema = builder.toString();
            return createFromSchema(schema);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public void readXml(File f) {
        String xml = "";
        try {
            FileInputStream fis = new FileInputStream(f);
            readXml(fis);
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void readXml(InputStream is) {
        String xml = "";
        try {
            StringBuilder builder = new StringBuilder();
            byte[] bytes = new byte[4096];
            int length = -1;
            while ((length = is.read(bytes)) != -1) {
                builder.append(new String(bytes, 0, length));
            }
            xml = builder.toString();
            readXml(xml);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void readXml(String xml) {
        //TODO when parsing the xml, validate it against the xml schema
        
        try {
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document dom = db.parse(new ByteArrayInputStream(xml.getBytes()));

            //create the xpath
            XPath xpath = XPathFactory.newInstance().newXPath();
            
            //for each table, find all of its items
            for (DataTable table : tables.values()) {
                if (!(table instanceof DataRelationTable)) {
                    //clear out the table
                    table.clear();
                    //get the nodes
                    NodeList nodes = (NodeList)xpath.evaluate("/" + name + "/" + table.getName(), dom, XPathConstants.NODESET);
                    //for each node, iterate through the columns, loading their values
                    for (int i=0; i<nodes.getLength(); i++) {
                        //each rowNode node represents a row
                        Node rowNode = nodes.item(i);
                        DataRow row = table.appendRowNoEvent();
                        NodeList cols = rowNode.getChildNodes();
                        for (int j=0; j<cols.getLength(); j++) {
                            Node colNode = cols.item(j);
                            if (colNode.getNodeType() == Node.ELEMENT_NODE) {
                                //TODO this doesn't take into account type conversion...
                                //could use a default converter...
                            	System.out.println(colNode.getNodeName() + "=" + colNode.getTextContent());
                                row.setValue(colNode.getNodeName(), colNode.getTextContent());
                            }
                        }
                        row.setStatus(DataRow.DataRowStatus.UNCHANGED);
                    }
                    table.fireDataTableChanged(new TableChangeEvent(table));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public String writeXml() {
        StringBuilder builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\" ?>\n");
        builder.append("<");
        builder.append(name);
        builder.append(">\n");
        for (DataTable table : tables.values()) {
            if (!(table instanceof DataRelationTable)) {
                for (DataRow row : table.rows) {
                    builder.append("\t<");
                    builder.append(table.getName());
                    builder.append(">\n");

                    for (DataColumn col : table.columns.values()) {
                        builder.append("\t\t<");
                        builder.append(col.getName());
                        builder.append(">");
                        builder.append(row.getValue(col));
                        builder.append("</");
                        builder.append(col.getName());
                        builder.append(">\n");
                    }

                    builder.append("\t</");
                    builder.append(table.getName());
                    builder.append(">\n");
                }
            }
        }
        builder.append("</");
        builder.append(name);
        builder.append(">");
        
        return builder.toString();
    }
    
    public static void main(String[] args) {
        DataSet ds = createFromSchema(new File("/home/rb156199/dataset.xsd"));
        System.out.println("DataSet: " + ds.name);
        for (DataTable table : ds.tables.values()) {
            System.out.println("\tDataTable: " + table.getName());
            for (DataColumn col : table.getColumns()) {
                System.out.println("\t\tDataColumn: " + col.getName());
            }
        }
        
        System.out.println(ds.getSchema());
    }
}