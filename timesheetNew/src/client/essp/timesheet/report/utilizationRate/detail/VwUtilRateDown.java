package client.essp.timesheet.report.utilizationRate.detail;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import c2s.dto.DtoBase;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.timesheet.report.DtoUtilRateKey;
import c2s.essp.timesheet.report.DtoUtilRateQuery;
import client.essp.common.TableUitl.TableColumnChooseDisplay;
import client.essp.common.view.VWTableWorkArea;
import client.essp.timesheet.common.LevelNodeViewManager;
import client.framework.model.VMColumnConfig;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.VWJReal;
import client.framework.view.vwcomp.VWJText;

import com.wits.util.Parameter;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author tbh
 * @version 1.0
 */
public class VwUtilRateDown extends VWTableWorkArea{
    public static final String actionIdQuery = "FTSPersonnelUseQuery";
   
    DtoUtilRateQuery dtoQuery = new DtoUtilRateQuery();
    private int type = 0;
    public List deptList = new ArrayList();
    private Vector userList = new Vector();

    public VwUtilRateDown() {
        try {
            jbInit();
            addUICEvent();
        } catch (Exception ex) {
        }
    }

    //初始化
    public void jbInit() throws Exception {
        Object[][] configs = null;
        VWJText text = new VWJText();
        VWJReal real = new VWJReal();
        real.setMaxInputDecimalDigit(2);
        configs = new Object[][] { {"rsid.timesheet.dept", "acntId",VMColumnConfig.UNEDITABLE, text}, 
                  {"rsid.timesheet.employeeId", "loginId",VMColumnConfig.UNEDITABLE, text},
                  {"rsid.timesheet.employeeName", "loginName", VMColumnConfig.UNEDITABLE, text},
                  {"rsid.timesheet.date", "dateStr", VMColumnConfig.UNEDITABLE, text},
                  {"rsid.timesheet.standardHours","standardHours", VMColumnConfig.UNEDITABLE, real},
                  {"rsid.timesheet.actHours","actualHours", VMColumnConfig.UNEDITABLE, real},
                  {"rsid.timesheet.validHours","validHours", VMColumnConfig.UNEDITABLE, real},
                  {"rsid.timesheet.utilRate","utilRate", VMColumnConfig.UNEDITABLE, real},
        };
        super.jbInit(configs, DtoBase.class, new LevelNodeViewManager());
        //设置初始列宽
        JTableHeader header = this.getTable().getTableHeader();
       // 可排序
        this.getTable().setSortable(true);
        TableColumnModel tcModel = header.getColumnModel();
        tcModel.getColumn(0).setPreferredWidth(100);
        tcModel.getColumn(1).setPreferredWidth(80);
        tcModel.getColumn(2).setPreferredWidth(80);
        tcModel.getColumn(3).setPreferredWidth(80);
        tcModel.getColumn(4).setPreferredWidth(50);
        tcModel.getColumn(5).setPreferredWidth(50);
        tcModel.getColumn(6).setPreferredWidth(50);
        tcModel.getColumn(7).setPreferredWidth(50);
    }

    //參數傳遞
    public void setParameter(Parameter param) {
     dtoQuery = (DtoUtilRateQuery) param.get(DtoUtilRateKey.DTO_UNTIL_RATE_QUERY);
     type = (Integer)param.get(DtoUtilRateKey.DTO_UTIL_RATE_TYPE);
     userList = (Vector)param.get(DtoUtilRateKey.DTO_TYPE_USER_LIST);
     super.setParameter(param);
   }


   protected void resetUI() {
       if (validate(dtoQuery)) {
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdQuery);
        inputInfo.setInputObj(DtoUtilRateKey.DTO_UNTIL_RATE_QUERY, dtoQuery);
        inputInfo.setInputObj(DtoUtilRateKey.DTO_UTIL_RATE_TYPE, type);
        inputInfo.setInputObj(DtoUtilRateKey.DTO_TYPE_USER_LIST, userList);
        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == false) {
            deptList = (List) returnInfo.getReturnObj(DtoUtilRateKey.DTO_DEPT_LIST);
            this.getModel().setRows(deptList);
            if (deptList != null && deptList.size() > 0) {
                getTable().setSelectRow(0);
            }
        }
       }
   }
   
   /**
    * 校验输入条件是否正确
    * @param dtoQuery
    * @return Boolean
    */
   private Boolean validate(DtoUtilRateQuery dtoQuery){
       if(dtoQuery.getAcntId()== null){
           comMSG.dispErrorDialog("rsid.common.select.dept");
           return false;
       }
       if (dtoQuery.getBegin() == null && dtoQuery.getEnd() == null) {
           comMSG.dispErrorDialog("rsid.common.fill.begEnd");
           return false;
       }
       if(dtoQuery.getBegin()==null && dtoQuery.getEnd() != null){
           comMSG.dispErrorDialog("rsid.common.fill.begin");
           return false;
       }
       if(dtoQuery.getEnd()==null && dtoQuery.getBegin()!=null){
           comMSG.dispErrorDialog("rsid.common.fill.end");
           return false;
       }
       if (dtoQuery.getBegin().after(dtoQuery.getEnd())) {
           comMSG.dispErrorDialog("rsid.common.beginlessEnd");
           return false;
       }
       return true;
   }
   
   public void addUICEvent() {
//   Display
     TableColumnChooseDisplay chooseDisplay =
             new TableColumnChooseDisplay(this.getTable(), this);
     JButton button = chooseDisplay.getDisplayButton();
     this.getButtonPanel().addButton(button);
   }
}
