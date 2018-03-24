package client.essp.timesheet.report.utilizationRate.gather;

import c2s.dto.DtoBase;

import javax.swing.JButton;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import client.framework.view.vwcomp.VWJText;
import client.essp.common.TableUitl.TableColumnChooseDisplay;
import client.framework.model.VMColumnConfig;
import c2s.dto.ReturnInfo;
import com.wits.util.Parameter;
import c2s.dto.InputInfo;
import c2s.essp.timesheet.report.DtoUtilRateQuery;
import client.framework.view.vwcomp.VWJReal;
import java.util.Vector;
import c2s.essp.timesheet.report.DtoUtilRateKey;
import client.essp.timesheet.common.LevelNodeViewManager;
import client.essp.common.view.VWTreeTableWorkArea;
import c2s.dto.ITreeNode;
import java.util.Map;

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
public class VwUtilRateGatherDown extends VWTreeTableWorkArea{
    static final String actionIdQuery = "FTSUtilRateGatherQuery";
    DtoUtilRateQuery dtoQuery = new DtoUtilRateQuery();
    VwUtilRateGatherUp vwUp = new VwUtilRateGatherUp();
    JButton exporterButton = new JButton();
    public Map deptList;
    private ITreeNode root;
    private Vector deptVector = new Vector();

    public VwUtilRateGatherDown() {
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
                  {"rsid.timesheet.deptName","acntName",VMColumnConfig.UNEDITABLE, text}
                  , {"rsid.timesheet.jan", "janUtilRate", VMColumnConfig.UNEDITABLE, real},
                  {"rsid.timesheet.feb", "febUtilRate", VMColumnConfig.UNEDITABLE, real},
                  {"rsid.timesheet.mar","marUtilRate", VMColumnConfig.UNEDITABLE, real},
                  {"rsid.timesheet.apr","aprUtilRate", VMColumnConfig.UNEDITABLE, real},
                  {"rsid.timesheet.may","mayUtilRate", VMColumnConfig.UNEDITABLE, real},
                  {"rsid.timesheet.jun","junUtilRate", VMColumnConfig.UNEDITABLE, real},
                  {"rsid.timesheet.jul","julUtilRate", VMColumnConfig.UNEDITABLE, real},
                  {"rsid.timesheet.aug", "augUtilRate", VMColumnConfig.UNEDITABLE, real},
                  {"rsid.timesheet.sep", "sepUtilRate", VMColumnConfig.UNEDITABLE, real},
                  {"rsid.timesheet.oct", "octUtilRate", VMColumnConfig.UNEDITABLE, real},
                  {"rsid.timesheet.nov", "novUtilRate", VMColumnConfig.UNEDITABLE, real},
                  {"rsid.timesheet.dec", "decUtilRate", VMColumnConfig.UNEDITABLE, real},
                  {"rsid.timesheet.standardHours", "standardHours", VMColumnConfig.UNEDITABLE, real},
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
     dtoQuery = (DtoUtilRateQuery) param.get(DtoUtilRateKey.DTO_UNTIL_RATE_QUERY);
     deptVector = (Vector)param.get(DtoUtilRateKey.DTO_DEPT_VECTOR);
     root = (ITreeNode)param.get(DtoUtilRateKey.DTO_DEPT_LIST);
     super.setParameter(param);
   }

   public void queryUtilRateGather(){
           getTreeTable().setRoot(null);
           InputInfo inputInfo = new InputInfo();
           inputInfo.setActionId(this.actionIdQuery);
           inputInfo.setInputObj(DtoUtilRateKey.DTO_UNTIL_RATE_QUERY, dtoQuery);
           inputInfo.setInputObj(DtoUtilRateKey.DTO_DEPT_VECTOR, deptVector);
           ReturnInfo returnInfo = accessData(inputInfo);
           if (returnInfo.isError() == false) {
               deptList = (Map) returnInfo.getReturnObj(DtoUtilRateKey.
                       DTO_DEPT_LIST);
           } else {
               deptList = null;
           }    
   }
   
   protected void resetUI() {
       getTreeTable().setRoot(root);
//       VWJSumLevelRender.setSumLevelRender(this);
   }
}
