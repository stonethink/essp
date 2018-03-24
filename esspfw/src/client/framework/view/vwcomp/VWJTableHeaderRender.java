package client.framework.view.vwcomp;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

import client.framework.model.IColumnConfig;
import client.framework.model.VMColumnConfig;
import client.framework.model.VMTable2;

import java.util.List;

public class VWJTableHeaderRender extends DefaultTableCellRenderer {
    IColumnConfig columnConfig;

    public VWJTableHeaderRender() {
        this(null);
    }

    public VWJTableHeaderRender(IColumnConfig columnConfig) {
        this.columnConfig = columnConfig;
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        if (table != null) {
            JTableHeader header = table.getTableHeader();
            if (header != null) {
                setForeground(header.getForeground());
                setBackground(header.getBackground());
                setFont(header.getFont());
            }
            if(table.getModel() instanceof VMTable2) {
            	VMTable2 m = (VMTable2) table.getModel();
            	int msc = m.getSortingColumn();
            	int vsc = table.convertColumnIndexToView(msc);
            	boolean sa = m.isAscending();
            	if(column == vsc) {
            		this.setIcon(new VWBevelArrowIcon(sa));
            	} else {
            		this.setIcon(null);
            	}
            }
        }

        setText((value == null) ? "" : value.toString());
        setBorder(UIManager.getBorder("TableHeader.cellBorder"));
        setToolTipText(this.getText());

        //set Horizontal Alignment
        if (columnConfig != null) {
            VMColumnConfig config = null;
//            = (VMColumnConfig) columnConfig.getColumnMap().
//                                    get(this.getText());
            List colList = columnConfig.getColumnMap();
            for (int i = 0; i < colList.size(); i++) {
                VMColumnConfig configTemp = (VMColumnConfig) colList.get(i);
                if (this.getText().equals(configTemp.getName())) {
                    config = configTemp;
                    break;
                }
            }
            if (config != null) {
                Component comp = config.getComponent();
                if (comp != null && comp instanceof IVWComponent) {
                    setHorizontalAlignment(((IVWComponent) comp).
                                           getHorizontalAlignment());
                } else {
                    setHorizontalAlignment(SwingConstants.CENTER);
                }
            } else {
                setHorizontalAlignment(SwingConstants.CENTER);
            }
        } else {
            setHorizontalAlignment(SwingConstants.CENTER);
        }
        return this;
    }
}
