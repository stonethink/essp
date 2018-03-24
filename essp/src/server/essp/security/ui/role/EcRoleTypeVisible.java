package server.essp.security.ui.role;

import org.extremecomponents.table.view.html.ColumnBuilder;
import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.core.TableModel;
import org.apache.commons.beanutils.BeanUtils;
import org.extremecomponents.table.cell.Cell;

public class EcRoleTypeVisible implements Cell{
           public String getExportDisplay(TableModel model, Column column) {
                return null;
           }

            public String getHtmlDisplay(TableModel model, Column column) {
                ColumnBuilder columnBuilder = new ColumnBuilder(column);

                columnBuilder.tdStart();

                try {
                    Object bean = model.getCurrentRowBean();
                    String visible = BeanUtils.getProperty(bean, "visible");

                    if (visible != null && visible.equals("true")) {
                        columnBuilder.getHtmlBuilder().img(model.getContext().getContextPath()+"/image/yes1.gif");
                    } else {
                        columnBuilder.getHtmlBuilder().img(model.getContext().getContextPath()+"/image/no1.gif");
                    }
                } catch (Exception e) {}

                columnBuilder.tdEnd();

                return columnBuilder.toString();
            }
}
