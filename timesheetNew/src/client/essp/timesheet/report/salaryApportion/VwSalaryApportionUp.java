/*
 * Created on 2008-1-2
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package client.essp.timesheet.report.salaryApportion;

import java.awt.Rectangle;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import javax.swing.JButton;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.timesheet.report.DtoAttVariantQuery;
import c2s.essp.timesheet.report.DtoSalaryWkHrQuery;
import client.essp.common.view.VWGeneralWorkArea;
import client.framework.model.VMComboBox;
import client.framework.view.common.comFORM;
import client.framework.view.vwcomp.VWJComboBox;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.vwcomp.VWJDatePanel;
import client.framework.view.vwcomp.VWJLabel;
import client.framework.view.vwcomp.VWJRadioButton;
import com.wits.util.Parameter;
/**
 * <p>Title: </p>
 *
 * <p>Description:VwSalaryApportionUp </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author tubaohui
 * @version 1.0
 */
public class VwSalaryApportionUp extends VWGeneralWorkArea{
        static final String actionIdLoadSite="FTSLoadSite";
        private DtoSalaryWkHrQuery dtoQuery = new DtoSalaryWkHrQuery();
        private VWJLabel monthLab = new VWJLabel();
        private VWJDatePanel inputMonth = new VWJDatePanel();
        private VWJComboBox siteComb = new VWJComboBox();
        private  VWJLabel siteLable = new VWJLabel();
        private Boolean isPMO = false;
        private Vector siteItem = new Vector();
        VWJRadioButton isBalanceOneInput = new VWJRadioButton();
        VWJRadioButton isBalanceTwoInput = new VWJRadioButton();
        JButton queryButton = new JButton();
        public VwSalaryApportionUp()  {
            try {
                jbInit();
            } catch (Exception ex) {
              ex.printStackTrace();
            }
        }
    
        //初始化
        public void jbInit() throws Exception {
                this.setLayout(null);
                monthLab.setBounds(new Rectangle(20,25,80,20));
                monthLab.setText("rsid.timesheet.begin");
                inputMonth.setBounds(new Rectangle(110,25,100,20));
                inputMonth.setCanSelect(true);
                inputMonth.setDataType(VWJDate._DATA_PRO_YM);
                
                isBalanceOneInput.setSelected(false);
                isBalanceOneInput.setBounds(new Rectangle(240,25,180,20));
                isBalanceOneInput.setText("rsid.timesheet.timeBalance1");
                
                isBalanceTwoInput.setSelected(false);
                isBalanceTwoInput.setBounds(new Rectangle(420,25,180,20));
                isBalanceTwoInput.setText("rsid.timesheet.timeBalance2");
                
                siteLable.setBounds(new Rectangle(20,60,80,20));
                siteLable.setText("rsid.timesheet.site");
                siteComb.setBounds(new Rectangle(110, 60, 100, 20));
                
                add(monthLab);
                add(inputMonth);
                add(isBalanceOneInput);
                add(isBalanceTwoInput);
                add(siteComb);
                add(siteLable);
     }
    
        /**
        * 刷新界面
        */
       protected void resetUI() {
              InputInfo inputInfo = new InputInfo();
              inputInfo.setActionId(actionIdLoadSite);
              ReturnInfo returnInfo = this.accessData(inputInfo);
              if (returnInfo.isError())return;
              siteItem = (Vector) returnInfo.getReturnObj(
                       DtoAttVariantQuery.DTO_SITE_LIST);
              VMComboBox itemSite = new VMComboBox(siteItem);
              siteComb.setModel(itemSite);
              isPMO = (Boolean)returnInfo.getReturnObj(DtoAttVariantQuery.
                       DTO_IS_PMO);
              if(isPMO){
                  siteComb.setEnabled(true);
                  siteLable.setEnabled(true);
              }else{
                  siteComb.setEnabled(false);
                  siteLable.setEnabled(false);
              }
           initBeginAndEndDate();
       }

       /**
        * 初始化开始日期和结束日期（当前时间的上个月的月初和月末)
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
               Date date =cal.getTime();
               inputMonth.setUICValue(date);
           }else{
              int year = cal.get(Calendar.YEAR);
              cal.set(year,month-1,1);
              Date date =cal.getTime();
              inputMonth.setUICValue(date);
           }
       }
    
       //參數設置
        public void setParameter(Parameter param) {
            super.setParameter(param);
        }
    
       //得到查询條件的DTO
       public DtoSalaryWkHrQuery getDtoSalaryQuery(){
               dtoQuery = new DtoSalaryWkHrQuery();
               dtoQuery.setBeginDate((Date)inputMonth.getUICValue());
               dtoQuery.setSite((String)siteComb.getUICValue());
               dtoQuery.setIsBalanceOne(isBalanceOneInput.isSelected());
               dtoQuery.setIsBalanceTwo(isBalanceTwoInput.isSelected());
               return dtoQuery;
       }
    
       /**
        * 设置光标
        * @param foucsOn
        */
       protected void foucsOnData(String foucsOn) {
         if(foucsOn.equals(DtoSalaryWkHrQuery.DTO_BEGIN_DATE)) {
             comFORM.setFocus(inputMonth);
         } else if(foucsOn.equals(DtoSalaryWkHrQuery.DTO_BALANCE_ONT)){
             comFORM.setFocus(isBalanceOneInput);
         }
       }
       
         public void refreshWorkArea() {
             super.refreshWorkArea();
         }
    
     }
