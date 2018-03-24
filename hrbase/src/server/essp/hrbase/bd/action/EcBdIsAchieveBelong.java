/*
 * Created on 2008-4-18
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.hrbase.bd.action;

import org.apache.commons.beanutils.BeanUtils;
import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.cell.Cell;
import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.table.view.html.ColumnBuilder;

public class EcBdIsAchieveBelong implements Cell{

    public String getExportDisplay(TableModel model, Column column) {
        return null;
    }

    public String getHtmlDisplay(TableModel model, Column column) {
        ColumnBuilder columnBuilder = new ColumnBuilder(column);
        
        columnBuilder.tdStart();
        
        try {
            Object bean = model.getCurrentRowBean();
            String status = BeanUtils.getProperty(bean, "achieveBelong");
            
            if (status != null && status.equals("true")) {
                columnBuilder.getHtmlBuilder().img(model.getContext().getContextPath()+"/image/yes1.gif");
            } else {
                columnBuilder.getHtmlBuilder().img(model.getContext().getContextPath()+"/image/no1.gif");
            }
        } catch (Exception e) {}
        
        columnBuilder.tdEnd();
        
        return columnBuilder.toString();
    }
}


