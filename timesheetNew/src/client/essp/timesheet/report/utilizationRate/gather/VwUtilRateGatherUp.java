package client.essp.timesheet.report.utilizationRate.gather;

import c2s.dto.ReturnInfo;
import com.wits.util.Parameter;
import client.framework.view.common.comFORM;
import client.framework.view.vwcomp.VWJDatePanel;
import client.framework.view.vwcomp.VWJLabel;
import client.framework.view.vwcomp.VWJComboBox;
import client.framework.model.VMComboBox;
import java.awt.Rectangle;
import c2s.dto.InputInfo;
import client.essp.common.view.VWGeneralWorkArea;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import client.essp.timesheet.report.utilizationRate.detail.VwUtilRateDown;
import c2s.essp.timesheet.report.DtoUtilRateQuery;
import c2s.essp.timesheet.report.DtoUtilRateKey;
import javax.swing.JButton;

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
public class VwUtilRateGatherUp extends VWGeneralWorkArea{
   static final String actionIdLoadDept="FTSLoadDeptInfo";
   DtoUtilRateQuery dtoQuery = new DtoUtilRateQuery();
   VWJLabel deptLab = new VWJLabel();
   VWJComboBox deptComBox = new VWJComboBox();
   VWJLabel begLab = new VWJLabel();
   VWJLabel endLab = new VWJLabel();
   VWJDatePanel inputBegin = new VWJDatePanel();
   VWJDatePanel inputEnd = new VWJDatePanel();
   VwUtilRateDown vwDown = new VwUtilRateDown();
   Vector DeptItem = new Vector();
   JButton queryButton = new JButton();
   public VwUtilRateGatherUp()  {
       try {
           jbInit();
       } catch (Exception ex) {
       }
   }

   //初始化
   public void jbInit() throws Exception {
       this.setLayout(null);
       begLab.setBounds(new Rectangle(20,25,80,20));
       begLab.setText("rsid.timesheet.begin");
       inputBegin.setBounds(new Rectangle(110,25,100,20));
       inputBegin.setCanSelect(true);
       
       endLab.setBounds(new Rectangle(20,60,80,20));
       endLab.setText("rsid.timesheet.end");
       inputEnd.setBounds(new Rectangle(110,60,100,20));
       inputEnd.setCanSelect(true);
       
       deptLab.setBounds(new Rectangle(300,25,80,20));
       deptLab.setText("rsid.timesheet.dept");
       deptComBox.setBounds(new Rectangle(380,25,200,20));

       this.add(deptComBox);
       this.add(deptLab);
       this.add(begLab);
       this.add(inputBegin);
       this.add(endLab);
       this.add(inputEnd);
    }

   /**
   * 刷新界面
   */
  protected void resetUI() {
      InputInfo inputInfo = new InputInfo();
      inputInfo.setActionId(actionIdLoadDept);
      ReturnInfo returnInfo = this.accessData(inputInfo);
      if (returnInfo.isError())
          return;
      DeptItem = (Vector) returnInfo.getReturnObj(
              DtoUtilRateKey.DTO_DEPT_LIST);

      VMComboBox itemDept = new VMComboBox(DeptItem);
      deptComBox.setModel(itemDept);
      initBeginAndEndDate();
  }

  /**
   * 初始化开始日期和结束日期（当前时间的上个月的月初和月末
   *
   */
  private void initBeginAndEndDate(){
      Calendar cal = Calendar.getInstance();
      int month = cal.get(Calendar.MONTH);
      //如果当前月份为1月，则获得上一年的12月的月初与月末
      if(month==0){
          int year = cal.get(Calendar.YEAR)-1;
          month=11;
          cal.set(year,month,1);
          Date beg =cal.getTime();
          inputBegin.setUICValue(beg);
          int date = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
          cal.set(year,month,date);
          Date end = cal.getTime();
          inputEnd.setUICValue(end);
      }else{
         int year = cal.get(Calendar.YEAR);
         cal.set(year,month-1,1);
         Date beg =cal.getTime();
         inputBegin.setUICValue(beg);
         int date = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
         cal.set(year,month-1,date);
         Date end = cal.getTime();
         inputEnd.setUICValue(end);
      }
  }

  //參數設置
   public void setParameter(Parameter param) {
       super.setParameter(param);
   }

  //得到查\uFFFD\uFFFD條件的DTO
  public DtoUtilRateQuery getDtoUtilRateQuery(){
      dtoQuery = new DtoUtilRateQuery();
      dtoQuery.setAcntId((String)deptComBox.getUICValue());
      dtoQuery.setBegin((Date)inputBegin.getUICValue());
      dtoQuery.setEnd((Date)inputEnd.getUICValue());
      return dtoQuery;
  }

  /**
   * 设置光标
   * @param foucsOn
   */
  protected void foucsOnData(String foucsOn) {
    if(foucsOn.equals(DtoUtilRateKey.DTO_BEGIN_DATE)) {
        comFORM.setFocus(inputBegin);
    } else if(foucsOn.equals(DtoUtilRateKey.DTO_END_DATE)){
        comFORM.setFocus(inputEnd);
    }else if(DtoUtilRateKey.DTO_ACCOUNT_ID.equals(foucsOn)){
        comFORM.setFocus(deptComBox);
    }
  }
  
    public void refreshWorkArea() {
        super.refreshWorkArea();
       
    }


}
