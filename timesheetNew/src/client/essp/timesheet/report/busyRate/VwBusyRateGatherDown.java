/*
 * Created on 2008-6-27
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package client.essp.timesheet.report.busyRate;

import java.util.Map;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import c2s.dto.DtoBase;
import c2s.dto.ITreeNode;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.timesheet.report.DtoBusyRateQuery;
import client.essp.common.TableUitl.TableColumnChooseDisplay;
import client.essp.common.view.VWTreeTableWorkArea;
import client.essp.timesheet.common.LevelNodeViewManager;
import client.framework.model.VMColumnConfig;
import client.framework.view.vwcomp.VWJReal;
import client.framework.view.vwcomp.VWJText;
import com.wits.util.Parameter;

public class VwBusyRateGatherDown extends VWTreeTableWorkArea{
        static final String actionIdQuery = "FTSBusyRateGatherQuery";
        private DtoBusyRateQuery dtoQuery = new DtoBusyRateQuery();
        public Map deptList;
        private ITreeNode root;
        private Vector deptVector = new Vector();
    
        public VwBusyRateGatherDown() {
            try {
                jbInit();
            } catch (Exception ex) {
                log.error(ex);
            }
            addUICEvent();
        }
    
        //初始化
        public void jbInit() throws Exception {
            Object[][] configs = null;
            VWJText text = new VWJText();
            VWJReal real = new VWJReal();
            real.setMaxInputDecimalDigit(2);
            configs = new Object[][] { {"rsid.timesheet.deptCode", "acntId",
                      VMColumnConfig.EDITABLE, null},
                      {"rsid.timesheet.deptName","acntName",VMColumnConfig.UNEDITABLE, text},
                      {"rsid.timesheet.jan", "janBusyRate", VMColumnConfig.UNEDITABLE, real},
                      {"rsid.timesheet.feb", "febBusyRate", VMColumnConfig.UNEDITABLE, real},
                      {"rsid.timesheet.mar", "marBusyRate", VMColumnConfig.UNEDITABLE, real},
                      {"rsid.timesheet.apr", "aprBusyRate", VMColumnConfig.UNEDITABLE, real},
                      {"rsid.timesheet.may", "mayBusyRate", VMColumnConfig.UNEDITABLE, real},
                      {"rsid.timesheet.jun", "junBusyRate", VMColumnConfig.UNEDITABLE, real},
                      {"rsid.timesheet.jul", "julBusyRate", VMColumnConfig.UNEDITABLE, real},
                      {"rsid.timesheet.aug", "augBusyRate", VMColumnConfig.UNEDITABLE, real},
                      {"rsid.timesheet.sep", "sepBusyRate", VMColumnConfig.UNEDITABLE, real},
                      {"rsid.timesheet.oct", "octBusyRate", VMColumnConfig.UNEDITABLE, real},
                      {"rsid.timesheet.nov", "novBusyRate", VMColumnConfig.UNEDITABLE, real},
                      {"rsid.timesheet.dec", "decBusyRate", VMColumnConfig.UNEDITABLE, real},
                      {"rsid.timesheet.assignHours", "assginHours", VMColumnConfig.UNEDITABLE, real},
                      {"rsid.timesheet.validHours", "validHours", VMColumnConfig.UNEDITABLE, real},
                      {"rsid.timesheet.invalidHours", "invalidHours", VMColumnConfig.UNEDITABLE, real},
                      {"rsid.timesheet.total", "actualHours", VMColumnConfig.UNEDITABLE, real},
            };
            super.jbInit(configs, "acntId", DtoBase.class, new LevelNodeViewManager());
            //设置初始列宽
            JTableHeader header = this.getTreeTable().getTableHeader();
            TableColumnModel tcModel = header.getColumnModel();
            tcModel.getColumn(0).setPreferredWidth(60);
            tcModel.getColumn(1).setPreferredWidth(60);
            tcModel.getColumn(2).setPreferredWidth(40);
            tcModel.getColumn(3).setPreferredWidth(40);
            tcModel.getColumn(4).setPreferredWidth(40);
            tcModel.getColumn(5).setPreferredWidth(40);
            tcModel.getColumn(6).setPreferredWidth(40);
            tcModel.getColumn(7).setPreferredWidth(40);
            tcModel.getColumn(8).setPreferredWidth(40);
            tcModel.getColumn(9).setPreferredWidth(40);
            tcModel.getColumn(10).setPreferredWidth(40);
            tcModel.getColumn(11).setPreferredWidth(40);
            tcModel.getColumn(12).setPreferredWidth(50);
            tcModel.getColumn(13).setPreferredWidth(50);
            tcModel.getColumn(14).setPreferredWidth(50);
        }
        
        private void addUICEvent() {
    //      Display
             TableColumnChooseDisplay chooseDisplay =
                     new TableColumnChooseDisplay(this.getTreeTable(), this);
             JButton button = chooseDisplay.getDisplayButton();
             this.getButtonPanel().addButton(button);
        }
    
        
    
        //參數傳遞
        public void setParameter(Parameter param) {
             dtoQuery = (DtoBusyRateQuery) param.get(DtoBusyRateQuery.DTO_UNTIL_RATE_QUERY);
             deptVector = (Vector)param.get(DtoBusyRateQuery.DTO_DEPT_VECTOR);
             root = (ITreeNode)param.get(DtoBusyRateQuery.DTO_DEPT_LIST);
             super.setParameter(param);
       }
    
       public void queryUtilRateGather(){
               getTreeTable().setRoot(null);
               InputInfo inputInfo = new InputInfo();
               inputInfo.setActionId(this.actionIdQuery);
               inputInfo.setInputObj(DtoBusyRateQuery.DTO_UNTIL_RATE_QUERY, dtoQuery);
               inputInfo.setInputObj(DtoBusyRateQuery.DTO_DEPT_VECTOR, deptVector);
               ReturnInfo returnInfo = accessData(inputInfo);
               if (returnInfo.isError() == false) {
                   deptList = (Map) returnInfo.getReturnObj(DtoBusyRateQuery.
                           DTO_DEPT_LIST);
               } else {
                   deptList = null;
               }    
       }
       
       protected void resetUI() {
           getTreeTable().setRoot(root);
//           VWJSumLevelRender.setSumLevelRender(this);
       }
}
