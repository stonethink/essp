package server.essp.security.ui.role;

import org.apache.commons.beanutils.BeanUtils;
import java.util.List;
import org.extremecomponents.table.cell.Cell;
import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.view.html.ColumnBuilder;
import c2s.essp.common.user.DtoRole;


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
                    String roleId = BeanUtils.getProperty(bean, "roleId");
                    List selectdRole = (List)model.getContext().getSessionAttribute(Selected);
                    boolean exist = false;
                    for (int i = 0; i < selectdRole.size(); i++) {
                        DtoRole role = (DtoRole) selectdRole.get(i);
                        if (roleId.equals(role.getRoleId())) {
                            exist = true;
                            break;
                        }
                    }
                    if (exist) {
                        columnBuilder.getHtmlBuilder().input("hidden").name("chkbx_" + roleId).
                                value("selected").xclose();
                        columnBuilder.getHtmlBuilder().input("checkbox").name(roleId);
                        columnBuilder.getHtmlBuilder().onclick("setAreaState(this)");
                        columnBuilder.getHtmlBuilder().checked();
                        columnBuilder.getHtmlBuilder().xclose();
                    } else {
                        columnBuilder.getHtmlBuilder().input("hidden").name("chkbx_" +
                                roleId).value("unselected").xclose();
                        columnBuilder.getHtmlBuilder().input("checkbox").name(roleId);
                        columnBuilder.getHtmlBuilder().onclick("setAreaState(this)");
                        columnBuilder.getHtmlBuilder().xclose();
                    }

                } catch (Exception e) {}

                columnBuilder.tdEnd();

                return columnBuilder.toString();
            }
        }
