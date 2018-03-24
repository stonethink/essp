package server.essp.hrbase.privilege.action;

import org.apache.commons.beanutils.BeanUtils;
import java.util.List;
import org.extremecomponents.table.cell.Cell;
import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.view.html.ColumnBuilder;
import c2s.essp.hrbase.maintenance.DtoHrbSitePrivilege;


public class SelectedRoles implements Cell{
            public static final String Selected="selectd";
            public String getExportDisplay(TableModel model, Column column) {
                return null;
            }
            public String getHtmlDisplay(TableModel model, Column column) {
                ColumnBuilder columnBuilder = new ColumnBuilder(column);
                columnBuilder.tdStart();
                try {
                    Object bean = model.getCurrentRowBean();
                    String siteName = BeanUtils.getProperty(bean, "belongSite");
                    List selectdSite = (List)model.getContext().getSessionAttribute(Selected);
                    boolean exist = false;
                    for (int i = 0; i < selectdSite.size(); i++) {
                        DtoHrbSitePrivilege role = (DtoHrbSitePrivilege)selectdSite.get(i);
                        if (siteName.equals(role.getBelongSite())) {
                            exist = true;
                            break;
                        }
                    }
                    if (exist) {
                        columnBuilder.getHtmlBuilder().input("hidden").name("chkbx_" + siteName).
                                value("selected").xclose();
                        columnBuilder.getHtmlBuilder().input("checkbox").name(siteName);
                        columnBuilder.getHtmlBuilder().onclick("setAreaState(this)");
                        columnBuilder.getHtmlBuilder().checked();
                        columnBuilder.getHtmlBuilder().xclose();
                    } else {
                        columnBuilder.getHtmlBuilder().input("hidden").name("chkbx_" +
                                siteName).value("unselected").xclose();
                        columnBuilder.getHtmlBuilder().input("checkbox").name(siteName);
                        columnBuilder.getHtmlBuilder().onclick("setAreaState(this)");
                        columnBuilder.getHtmlBuilder().xclose();
                    }

                } catch (Exception e) {}

                columnBuilder.tdEnd();

                return columnBuilder.toString();
            }
        }
