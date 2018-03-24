/*
 * Created on Mar 14, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package net.sourceforge.ganttproject.parser;

import net.sourceforge.ganttproject.GanttTreeTable;

import org.xml.sax.Attributes;

/**
 * @author bbaranne Mar 14, 2005
 */
public class TaskDisplayColumnsTagHandler implements TagHandler,
        ParsingListener {

    private GanttTreeTable treeTable = null;

    private GanttTreeTable.DisplayedColumnsList displayColumns = null;

    public TaskDisplayColumnsTagHandler(GanttTreeTable ganttTreeTable) {
        this.treeTable = ganttTreeTable;
        // displayColumns = this.treeTable.getDisplayColumns();
        // if (displayColumns == null)
        displayColumns = treeTable.new DisplayedColumnsList();
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sourceforge.ganttproject.parser.TagHandler#startElement(java.lang.String,
     *      java.lang.String, java.lang.String, org.xml.sax.Attributes)
     */
    public void startElement(String namespaceURI, String sName, String qName,
            Attributes attrs) throws FileFormatException {
        if (qName.equals("displaycolumn"))
            loadTaskDisplay(attrs);

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sourceforge.ganttproject.parser.TagHandler#endElement(java.lang.String,
     *      java.lang.String, java.lang.String)
     */
    public void endElement(String namespaceURI, String sName, String qName) {
        // TODO Auto-generated method stub

    }

    /**
     * @see net.sourceforge.ganttproject.parser.ParsingListener#parsingStarted()
     */
    public void parsingStarted() {

    }

    /**
     * @see net.sourceforge.ganttproject.parser.ParsingListener#parsingFinished()
     */
    public void parsingFinished() {
        treeTable.setDisplayedColumns(displayColumns);
    }

    private void loadTaskDisplay(Attributes atts) {
        String id = atts.getValue("property-id");
        String orderStr = atts.getValue("order");
        String widthStr = atts.getValue("width");
        int order = Integer.parseInt(orderStr);

        GanttTreeTable.DisplayedColumn dc = treeTable.new DisplayedColumn(id);
        dc.setDisplayed(true);
        dc.setOrder(order);
        if (widthStr != null)
            dc.setWidth(Integer.parseInt(widthStr));
        displayColumns.add(dc);

    }

}
