package client.essp.timesheet.report.attvariant;

import java.awt.Color;
import java.awt.Component;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import c2s.essp.timesheet.report.DtoAttVariant;
import client.essp.common.view.VWTableWorkArea;
import client.framework.model.VMColumnConfig;
import client.framework.model.VMTable2;
import client.framework.view.vwcomp.IVWComponent;
import client.framework.view.vwcomp.NodeViewManager;
import client.framework.view.vwcomp.VWJTable;
import client.framework.view.vwcomp.VWJText;

public class VwVariantRender implements TableCellRenderer {
	
	private final static Color VARIANT_TRUE_COLOR = Color.PINK;
	private final static Color VARIANT_FALSE_COLOR = new Color(204, 255, 204);
    private static final DefaultTableCellRenderer defaultRender
            = new DefaultTableCellRenderer();
    private static final NodeViewManager nodeViewManager
            = new NodeViewManager();
    private Component comp = null;


    public VwVariantRender(Component comp) {
        this.comp = comp;
    }

    /**
     *  Returns the component used for drawing the cell.  This method is
     *  used to configure the renderer appropriately before drawing.
     * @param table JTable
     * @param value Object
     * @param isSelected boolean
     * @param hasFocus boolean
     * @param row int
     * @param column int
     * @return Component
     */
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {

        if( table.getModel() instanceof VMTable2 ){
            nodeViewManager.setNode( ((VMTable2)table.getModel()).getRow(row) );
        }

        if (comp == null || !(comp instanceof IVWComponent)) {
            comp = defaultRender.getTableCellRendererComponent(
                table, value, isSelected, hasFocus, row, column);
        } else {
            IVWComponent vwComp = (IVWComponent) comp;
            vwComp.setUICValue(value);
        }

        if (row % 2 == 0) {
            comp.setBackground(nodeViewManager.getOddBackground()); //设置奇数行底色
        } else if (row % 2 == 1) {
            comp.setBackground(nodeViewManager.getEvenBackground()); //设置偶数行底色
        }

        if (isSelected) {
            comp.setForeground(nodeViewManager.getSelectForeground());
            comp.setBackground(nodeViewManager.getSelectBackground());
        } else {
            comp.setForeground(nodeViewManager.getForeground());
        }

        if (comp instanceof JComponent) {
            ((JComponent) comp).setBorder(BorderFactory.createEmptyBorder());
        }
        comp.setFont(nodeViewManager.getTextFont());


        //当此行没有选中时重新按层级设置背景色
        if (isSelected == false) {
        	Object data = null;
        	if(table instanceof VWJTable) {
	            VWJTable vwjtable = (VWJTable) table;
	            data = ((VMTable2) vwjtable.getModel()).getRow(row);
        	}
            if (data instanceof DtoAttVariant) {
                if(((DtoAttVariant)data).isVariant()) {
                    comp.setBackground(VARIANT_TRUE_COLOR);
                } else {
                	comp.setBackground(VARIANT_FALSE_COLOR);
                }
            }
        }
        return comp;
    }

    /**
     * 设置VWTableWorkArea的所有单元格的Render为VWJSumLevelRender
     * @param tableArea VWTableWorkArea
     */
    public static void setSumLevelRender(VWTableWorkArea tableArea) {
        List cfgs = tableArea.getModel().getColumnConfigs();
        VWJTable table = tableArea.getTable();
        int ColumnCount = table.getColumnCount();
        TableColumnModel columnModel = table.getColumnModel();
        for (int i = 0; i < ColumnCount; i++) {
            TableColumn column = columnModel.getColumn(i);
            VMColumnConfig columnConfig = (VMColumnConfig) cfgs.get(i);
            Component comp = columnConfig.getComponent();
            if (comp != null &&
                comp instanceof client.framework.view.vwcomp.IVWComponent) {
                if (comp instanceof JComboBox) {
                    comp = new VWJText();
                }
                VwVariantRender render = new VwVariantRender(comp);
                column.setCellRenderer(render);
            }
        }
    }
}