package client.framework.view.vwcomp;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import org.apache.log4j.Category;
import java.awt.event.*;

public class VWJTableEditor extends AbstractCellEditor implements TableCellEditor {
    static Category log = Category.getInstance("client");

    protected int clickCountToStart = 2;
    java.util.HashMap compBuffer = new java.util.HashMap();
    Component comp = null;

    public VWJTableEditor(Component comp) {
        this.comp = comp;
    }

    /**
     * Returns a reference to the editor component.
     *
     * @return the editor <code>Component</code>
     */
    public Component getComponent() {
        return comp;
    }

    //Modifying
    /**
     * Specifies the number of clicks needed to start editing.
     *
     * @param count  an int specifying the number of clicks needed to start editing
     * @see #getClickCountToStart
     */
    public void setClickCountToStart(int count) {
        clickCountToStart = count;
    }

    /**
     * Returns the number of clicks needed to start editing.
     * @returns the number of clicks needed to start editing
     */
    public int getClickCountToStart() {
        return clickCountToStart;
    }

//  Override the implementations of the superclass, forwarding all methods
//  from the CellEditor interface to our delegate.


    /**
     * Forwards the message from the <code>CellEditor</code> to
     * the <code>delegate</code>.
     * @see EditorDelegate#getCellEditorValue
     */
    public Object getCellEditorValue() {
        //System.out.println("invoke editorComponent.getUICValue()=" + ((IVWComponent)(config.getComponent())).getUICValue());
        //System.out.println("从编辑器取值:"+((IVWComponent)comp).getUICValue().toString());
        return ((IVWComponent) comp).getUICValue();
    }

    /**
     * Forwards the message from the <code>CellEditor</code> to
     * the <code>delegate</code>.
     * @see EditorDelegate#isCellEditable(EventObject)
     */
    public boolean isCellEditable(EventObject anEvent) {
        if (anEvent instanceof MouseEvent) {
            return ((MouseEvent) anEvent).getClickCount() >= clickCountToStart;
        }
        return true;
    }

    /**
     * Forwards the message from the <code>CellEditor</code> to
     * the <code>delegate</code>.
     * @see EditorDelegate#shouldSelectCell(EventObject)
     */
    public boolean shouldSelectCell(EventObject anEvent) {
        return true;
    }

    /**
     * Forwards the message from the <code>CellEditor</code> to
     * the <code>delegate</code>.
     * @see EditorDelegate#stopCellEditing
     */
    public boolean stopCellEditing() {
        IVWComponent editorComponent = (IVWComponent) comp;
        if (editorComponent instanceof VWJDate &&
            ((VWJDate) editorComponent).isEditing()) {
            return false;
        }
        if (editorComponent instanceof IVWJIssue &&
            ((IVWJIssue) editorComponent).isEditing()) {
            return false;
        }


        ((Component) editorComponent).transferFocus();
        fireEditingStopped();
        return true;
    }

    /**
     * Forwards the message from the <code>CellEditor</code> to
     * the <code>delegate</code>.
     * @see EditorDelegate#cancelCellEditing
     */
    public void cancelCellEditing() {
        IVWComponent editorComponent = (IVWComponent) comp;
        if (editorComponent instanceof VWJDate &&
            ((VWJDate) editorComponent).isEditing()) {
            return;
        }
        if (editorComponent instanceof IVWJIssue &&
            ((IVWJIssue) editorComponent).isEditing()) {
            return;
        }

        ((Component) editorComponent).transferFocus();
        fireEditingCanceled();
    }

    public Component getComponent(int row, int column) {
        return (Component) compBuffer.get(row + "@" + column);
    }

    //Implementing the CellEditor Interface

    /** Implements the <code>TableCellEditor</code> interface. */
    public Component getTableCellEditorComponent(JTable table, Object value2,
                                                 boolean isSelected,
                                                 int row, int column) {
        Object value = value2;
        IVWComponent editorComponent = null;
        if (value == null) {
            value = "";
        }
        //System.out.println("init editor value : ["+value.toString()+"]");
        editorComponent = ((IVWComponent) comp).duplicate();
        comp = (Component) editorComponent;

        editorComponent.setErrorField(false);
        editorComponent.setToolTipText(value.toString());
        if (editorComponent.getValidatorResult() != null) {
            editorComponent.getValidatorResult().clear();
        }

        //System.out.println("向编辑器设值:"+value.toString());

        editorComponent.setUICValue(value);

//        TableModel model=table.getModel();
//        if(model instanceof VMTable2) {
//            VMTable2 vmModel=(VMTable2)model;
//            ValidatorResult result=vmModel.getValidatorResult(row,column);
//            if(result!=null && result.isValid()==false) {
//                editorComponent.setErrorField(true);
//                ((JComponent)editorComponent).setToolTipText(result.getAllMsg()[0]);
//            }
//        }

        //if focus lost, stop edit
        ((Component) editorComponent).addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {


                if (comp instanceof VWJDate && ((VWJDate) comp).isEditing()) {
                    return ;
                }
                if (comp instanceof IVWJIssue && ((IVWJIssue) comp).isEditing()) {
                    return;
                }


                fireEditingStopped();
            }
        });

        return (Component) editorComponent;
    }


}
