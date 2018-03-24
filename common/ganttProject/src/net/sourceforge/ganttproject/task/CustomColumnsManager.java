package net.sourceforge.ganttproject.task;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sourceforge.ganttproject.GanttTreeTable;
import net.sourceforge.ganttproject.Mediator;
import net.sourceforge.ganttproject.gui.GanttDialogCustomColumn;

/**
 * This class has to be used to add or remove new custom columns. It will
 * perform the changes to the linked treetable.
 * 
 * @author bbaranne (Benoit Baranne) Mar 4, 2005
 */
public class CustomColumnsManager {
    /**
     * The associated GanttTreeTable.
     */
    private GanttTreeTable ganttTreeTable = null;

    private List myListeners;

    /**
     * Creates an instance of CustomColumnsManager for the given treetable.
     * 
     * @param treetable
     */
    public CustomColumnsManager(GanttTreeTable treetable) {
        ganttTreeTable = treetable;
        myListeners = new ArrayList();
    }
    
    public void setGanttTreeTable(GanttTreeTable treetable){
        ganttTreeTable = treetable;
    }

    /**
     * Add a new custom column to the treetable.
     */
    public void addNewCustomColumn(CustomColumn customColumn) {
        
        if (customColumn == null) {
            customColumn = new CustomColumn();
            GanttDialogCustomColumn d = new GanttDialogCustomColumn(Mediator
                    .getGanttProjectSingleton().getUIFacade(), customColumn);
            d.setVisible(true);
        }

        ganttTreeTable.addNewCustomColumn(customColumn);
        CustomColumEvent event = new CustomColumEvent(
                CustomColumEvent.EVENT_ADD, customColumn.getName());
        fireCustomColumnsChange(event);
    }

    /**
     * Delete the custom column whose name is given in parameter from the
     * treetable.
     * 
     * @param name
     *            Name of the column to delete.
     */
    public void deleteCustomColumn(String name) {
        // System.out.println("--- Delete --- (CustomColumnsManager)");
        ganttTreeTable.deleteCustomColumn(name);
        CustomColumEvent event = new CustomColumEvent(
                CustomColumEvent.EVENT_REMOVE, name);
        fireCustomColumnsChange(event);
    }

    public void changeCustomColumnName(String oldName, String newName) {
        ganttTreeTable.renameCustomcolumn(oldName, newName);
        Mediator.getCustomColumnsStorage().renameCustomColumn(oldName, newName);
    }

    public void changeCustomColumnDefaultValue(String colName,
            Object newDefaultValue) throws CustomColumnsException {
        // ganttTreeTable.changeDefaultValue(colName, newDefaultValue);
        Mediator.getCustomColumnsStorage().changeDefaultValue(colName,
                newDefaultValue);
    }

    public void addCustomColumnsListener(CustomColumsListener listener) {
        myListeners.add(listener);
    }

    private void fireCustomColumnsChange(CustomColumEvent event) {
        Iterator it = myListeners.iterator();
        while (it.hasNext()) {
            CustomColumsListener listener = (CustomColumsListener) it.next();
            listener.customColumsChange(event);
        }
    }

}
