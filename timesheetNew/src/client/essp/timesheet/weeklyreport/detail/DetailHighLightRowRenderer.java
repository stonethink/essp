package client.essp.timesheet.weeklyreport.detail;

import java.awt.Color;
import java.awt.Component;
import java.util.Date;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import c2s.essp.common.calendar.WorkCalendar;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheetDetail;
import client.essp.timesheet.weeklyreport.common.VWJTwoNumReal;
import client.framework.model.VMColumnConfig;
import client.framework.model.VMTable2;
import client.framework.view.vwcomp.NodeViewManager;
import com.wits.util.comDate;
import javax.swing.UIManager;
import javax.swing.BorderFactory;

/**
 *
 * <p>Title: DetailHighLightRowRenderer</p>
 *
 * <p>Description: 工时单明细Table工时列单元格Render</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class DetailHighLightRowRenderer extends DefaultTableCellRenderer {
    private final static Color highColor = Color.cyan;
    private final static Color highSelectColor =  new Color(180, 200, 255);
    private VWJTwoNumReal workHourComp;
    public DetailHighLightRowRenderer(VWJTwoNumReal workHourComp) {
        this.workHourComp = workHourComp;
    }

    public Component getTableCellRendererComponent(JTable table,
            Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {

        VWJTwoNumReal comp = (VWJTwoNumReal) workHourComp.duplicate();
        VMTable2 model = (VMTable2) table.getModel();
        VMColumnConfig config = (VMColumnConfig) model.getColumnConfigs().get(
                column);
        String dataName = config.getDataName();
        Date hourDate = comDate.toDate(dataName);

        if (row % 2 == 0) {
            comp.setBackground(NodeViewManager.TableLineOddBack); //设置偶数行底色
        } else if (row % 2 == 1) {
            comp.setBackground(NodeViewManager.TableLineEvenBack); //设置奇数行底色
        }
        
        Object dataBean = model.getRow(row);
        if(dataBean instanceof DtoTimeSheetDetail) {
        	DtoTimeSheetDetail dto = (DtoTimeSheetDetail) dataBean;
	        Date start = dto.getActPlanStart();
	        Date finish = dto.getActPlanFinish();
	        if (isInPlan(start, finish, hourDate)) {
	            comp.setBackground(isSelected ? highSelectColor : highColor);
	        } else if(isSelected){
	            comp.setBackground(NodeViewManager.TableSelectBack);
	            comp.setForeground(comp.getSelectedTextColor());
	        }
        }
        
        if(hasFocus) {
//            comp.setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));
            comp.setBorder(BorderFactory.createEtchedBorder(1));
        } else {
            comp.setBorder(noFocusBorder);
        }
        comp.setFont(table.getFont());
        comp.setUICValue(value);
        comp.setHorizontalAlignment(SwingConstants.RIGHT);
        return comp;
    }
    
    /**
     * 判断hourDate是否在start和finish范围内
     * 		精确到“日”
     * @param start
     * @param finish
     * @param hourDate
     * @return
     */
    private boolean isInPlan(Date start, Date finish, Date hourDate) {
    	if(start == null || finish == null || hourDate == null) {
    		return false;
    	}
    	Date s = WorkCalendar.resetBeginDate(start);
    	Date f = WorkCalendar.resetEndDate(finish);
    	return !hourDate.before(s) && !hourDate.after(f);
    }
}
