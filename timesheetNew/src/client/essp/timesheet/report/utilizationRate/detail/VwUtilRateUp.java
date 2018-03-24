package client.essp.timesheet.report.utilizationRate.detail;

import client.essp.common.view.VWGeneralWorkArea;
import c2s.dto.ReturnInfo;
import com.wits.util.Parameter;

import client.framework.view.common.comFORM;
import client.framework.view.vwcomp.VWJCheckBox;
import client.framework.view.vwcomp.VWJLabel;
import client.framework.view.vwcomp.VWJComboBox;
import client.framework.model.VMComboBox;
import java.awt.Rectangle;
import c2s.dto.InputInfo;
import java.util.Vector;
import c2s.essp.timesheet.report.DtoUtilRateKey;
import c2s.essp.timesheet.report.DtoUtilRateQuery;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;

import client.essp.timesheet.period.VWTsPeriodComponent;

/**
 * <p>Title: </p>
 *
 * <p>Description: VwUtilRateUp</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author tbh
 * @version 1.0
 */
public class VwUtilRateUp extends VWGeneralWorkArea{
   static final String actionIdLoadUserAndDept="FTSLoadUsersAndDept";
   static final String actionIdLoadUser="FTSLoadUsers";
   DtoUtilRateQuery dtoQuery = new DtoUtilRateQuery();
   VWJLabel userLab = new VWJLabel();
   VWJComboBox userComBox = new VWJComboBox();
   VWJLabel deptLab = new VWJLabel();
   VWJComboBox deptComBox = new VWJComboBox();
   VWTsPeriodComponent vwTsPeriodComp = new VWTsPeriodComponent();
   VwUtilRateDown vwDown = new VwUtilRateDown();
   public Vector userItem = new Vector();
   JButton queryButton = new JButton();
   VWJCheckBox subCheckBox = new VWJCheckBox();
   VWJLabel subLab = new VWJLabel();

   public VwUtilRateUp()  {
       try {
           jbInit();
       } catch (Exception ex) {
           ex.printStackTrace();
       }
   }

   //初始化
   public void jbInit() throws Exception {
       this.setLayout(null);
       //第一行1列
       deptLab.setBounds(new Rectangle(10, 22, 80, 20));
       deptLab.setText("rsid.timesheet.dept");
       deptComBox.setBounds(new Rectangle(100, 22, 200, 20));

       subLab.setBounds(new Rectangle(310,22,80,20));
       subLab.setText("rsid.timesheet.includeSub");
       subCheckBox.setBounds(new Rectangle(390,22,25,20));
       subCheckBox.setEnabled(true);
       subCheckBox.setSelected(false);
       
       userLab.setBounds(new Rectangle(450, 22, 60, 20));
       userLab.setText("rsid.timesheet.employee");
       userComBox.setBounds(new Rectangle(510, 21, 130, 20));
       
       vwTsPeriodComp.setBounds(new Rectangle(10, 12, 710, 30));
       vwTsPeriodComp.setMultiFlag(true);
     
       Border period = BorderFactory.createTitledBorder("");
       JPanel panel = new JPanel();
       panel.setLayout(null);
       panel.setBounds(8, 67, 730,50);
       panel.setBorder(period);
       panel.add(vwTsPeriodComp);
       
       add(userLab);
       add(deptLab);
       add(subLab);
       add(subCheckBox);
       add(deptComBox);
       add(userComBox);
       add(panel);
    }


   /**
   * 刷新界面
   */
  protected void resetUI() {
      InputInfo inputInfo = new InputInfo();
      inputInfo.setActionId(actionIdLoadUserAndDept);
      ReturnInfo returnInfo = this.accessData(inputInfo);
      if (returnInfo.isError())
          return;
      userItem = (Vector) returnInfo.getReturnObj(
              DtoUtilRateKey.DTO_USER_LIST);
      Vector DeptItem = (Vector) returnInfo.getReturnObj(
              DtoUtilRateKey.DTO_DEPT_LIST);
      VMComboBox itemUsers;
      if(userItem == null){
          itemUsers = new VMComboBox(new Vector());
      }else{
          itemUsers = new VMComboBox(userItem);
      }
      VMComboBox itemDept = new VMComboBox(DeptItem);
      userComBox.setModel(itemUsers);
      deptComBox.setModel(itemDept);
       vwTsPeriodComp.refreshWorkArea();
//      Calendar cal = Calendar.getInstance();
//      Date sDate = WorkCalendar.getNextBEWeekDay(cal, -1)[0].getTime();
//      Date eDate = WorkCalendar.getNextBEWeekDay(cal, 0)[1].getTime();
  }

  //部門變化觸發員工集合變化
  public void changeDept(String acntId){
       InputInfo inputInfo = new InputInfo();
       inputInfo.setActionId(actionIdLoadUser);
       inputInfo.setInputObj(DtoUtilRateKey.DTO_ACCOUNT_ID,acntId);
       ReturnInfo returnInfo = this.accessData(inputInfo);
       if (returnInfo.isError())
           return;
       userItem = (Vector) returnInfo.getReturnObj(
               DtoUtilRateKey.DTO_USER_LIST);
       VMComboBox itemUsers = new VMComboBox(userItem);
       userComBox.setModel(itemUsers);

  }

  //參數設置
   public void setParameter(Parameter param) {
       super.setParameter(param);
   }

  //得到查詢條件的DTO
  public DtoUtilRateQuery getDtoUtilRateQuery(){
      dtoQuery = new DtoUtilRateQuery();
      dtoQuery.setAcntId((String)deptComBox.getUICValue());
      dtoQuery.setLoginId((String)userComBox.getUICValue());
      dtoQuery.setBegin(vwTsPeriodComp.vwTsPeriod.getBeginDate());
      dtoQuery.setEnd(vwTsPeriodComp.vwTsPeriod.getEndDate());
      dtoQuery.setIsSub(subCheckBox.isSelected());
      return dtoQuery;
  }

}
