package server.essp.projectpre.ui.parameter;

import org.apache.commons.beanutils.BeanUtils;
import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.cell.*;
import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.table.view.html.ColumnBuilder;

/**
 * 此类用于AreaCode List中显示Status栏位时EC标签的实现
 * @author Robin
 *
 */
public class EcProjectTypeStatus implements Cell{

	    public String getExportDisplay(TableModel model, Column column) {
	        return null;
	    }

	    public String getHtmlDisplay(TableModel model, Column column) {
	        ColumnBuilder columnBuilder = new ColumnBuilder(column);
	        
	        columnBuilder.tdStart();
	        
	        try {
	            Object bean = model.getCurrentRowBean();
	            String status = BeanUtils.getProperty(bean, "status");
	            
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



