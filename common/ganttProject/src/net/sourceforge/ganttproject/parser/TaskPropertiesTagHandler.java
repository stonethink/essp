/*
 * Created on Mar 10, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package net.sourceforge.ganttproject.parser;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import net.sourceforge.ganttproject.CustomPropertyDefinition;
import net.sourceforge.ganttproject.CustomPropertyManager;
import net.sourceforge.ganttproject.GanttCalendar;
import net.sourceforge.ganttproject.GanttTreeTable;
import net.sourceforge.ganttproject.Mediator;
import net.sourceforge.ganttproject.task.CustomColumn;

import org.w3c.util.DateParser;
import org.w3c.util.InvalidDateException;
import org.xml.sax.Attributes;

/**
 * @author bbaranne Mar 10, 2005
 */
public class TaskPropertiesTagHandler implements TagHandler, ParsingListener {
    private List columns = null;

    private GanttTreeTable treeTable = null;

    public TaskPropertiesTagHandler(GanttTreeTable ganttTreeTable) {
        treeTable = ganttTreeTable;
        columns = new ArrayList();
    }

    /**
     * @see net.sourceforge.ganttproject.parser.TagHandler#startElement(String,
     *      String, String, Attributes)
     */
    public void startElement(String namespaceURI, String sName, String qName,
            Attributes attrs) {
        if (qName.equals("taskproperty"))
            loadTaskProperty(attrs);
    }

    /**
     * @see net.sourceforge.ganttproject.parser.TagHandler#endElement(String,
     *      String, String)
     */
    public void endElement(String namespaceURI, String sName, String qName) {
        // System.out.println(Mediator.getCustomColumnsStorage().toString());
    }

    private void loadTaskProperty(Attributes atts) {
        String name = atts.getValue("name");
        String id = atts.getValue("id");
        String type = atts.getValue("valuetype");

        if (atts.getValue("type").equals("custom")) {
            CustomColumn cc;
            String valueStr = atts.getValue("defaultvalue");
            CustomPropertyDefinition stubDefinition = CustomPropertyManager.PropertyTypeEncoder.decodeTypeAndDefaultValue(type, valueStr); 
            cc = new CustomColumn(name, stubDefinition.getType(), stubDefinition.getDefaultValue());
            cc.setId(id);

            Mediator.getCustomColumnsManager().addNewCustomColumn(cc);
        }
        // // else if(atts.getValue("type").equals("default"))
        // // {
        // GanttTreeTable.DisplayedColumn dc = treeTable.new
        // DisplayedColumn(id);
        // System.out.println(dc);
        // // if(dc.isDisplayed())
        // columns.add(dc);
        // // }
    }

    /**
     * @see net.sourceforge.ganttproject.parser.ParsingListener#parsingStarted()
     */
    public void parsingStarted() {
        // nothing to do.

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sourceforge.ganttproject.parser.ParsingListener#parsingFinished()
     */
    public void parsingFinished() {
        // this.treeTable.setDisplayedColumns(columns);
    }
}
