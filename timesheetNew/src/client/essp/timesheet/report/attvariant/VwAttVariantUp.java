/*
 * Created on 2008-1-29
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package client.essp.timesheet.report.attvariant;

import java.awt.Rectangle;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import javax.swing.JButton;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.timesheet.report.DtoAttVariantQuery;
import client.essp.common.humanAllocate.VWJHrAllocateButton;
import client.essp.common.view.VWGeneralWorkArea;
import client.framework.model.VMComboBox;
import client.framework.view.common.comFORM;
import client.framework.view.vwcomp.VWJButton;
import client.framework.view.vwcomp.VWJComboBox;
import client.framework.view.vwcomp.VWJDatePanel;
import client.framework.view.vwcomp.VWJLabel;
import client.framework.view.vwcomp.VWJRadioButton;

import com.wits.util.Parameter;
/**
 * VwAttVariantUp
 * @author TBH
 */
public class VwAttVariantUp extends VWGeneralWorkArea{
    static final String actionIdLoadSite="FTSLoadSiteInfo";

    private VWJLabel begLab = new VWJLabel();
    private VWJLabel endLab = new VWJLabel();
    private VWJDatePanel inputBegin = new VWJDatePanel();
    private VWJDatePanel inputEnd = new VWJDatePanel();
    private Vector siteItem = new Vector();
    private VWJLabel lblEmployee = new VWJLabel();
    private VWJHrAllocateButton txtEmployee = new VWJHrAllocateButton();
    private VWJLabel lblSite = new VWJLabel();
    private VWJComboBox combSite = new VWJComboBox();
    private Boolean isPMO = false;
    private DtoAttVariantQuery dtoQuery = new DtoAttVariantQuery();
    JButton queryButton = new VWJButton();
    VWJRadioButton radioBtnAll = new VWJRadioButton();
    VWJRadioButton radioBtnVariant = new VWJRadioButton();
    public VwAttVariantUp()  {
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

        lblEmployee.setText("rsid.timesheet.employee");
        lblEmployee.setBounds(new Rectangle(300, 25, 100, 20));
        txtEmployee.setBounds(new Rectangle(400, 25, 150, 20));
        
        lblSite.setText("rsid.timesheet.site");
        lblSite.setBounds(new Rectangle(300, 60, 70, 20));
        combSite.setBounds(new Rectangle(400, 60, 100, 20));
        
        radioBtnAll.setBounds(new Rectangle(20, 90, 90, 20));
        radioBtnAll.setText("rsid.timesheet.all");
        radioBtnAll.setSelected(true);
        
        radioBtnVariant.setBounds(new Rectangle(110, 90, 100, 20));
        radioBtnVariant.setText("rsid.timesheet.variant");
        radioBtnVariant.setSelected(false);
        
        add(begLab);
        add(inputBegin);
        add(endLab);
        add(inputEnd);
        add(lblEmployee);
        add(txtEmployee);
        add(lblSite);
        add(combSite);
        add(radioBtnAll);
        add(radioBtnVariant);
        setUIName();
     }

     private void setUIName(){
         inputBegin.setName("beginDate");
         inputEnd.setName("endDate");
         txtEmployee.setName("empId");
         combSite.setName("site");
     }
     
     /**
      * 设置光标
      * @param foucsOn
      */
     protected void foucsOnData(String foucsOn) {
       if(foucsOn.equals(DtoAttVariantQuery.DTO_BEGIN_DATE)) {
           comFORM.setFocus(inputBegin);
       } else if(foucsOn.equals(DtoAttVariantQuery.DTO_END_)){
           comFORM.setFocus(inputEnd);
       }
     }
     
    /**
     * 刷新界面
     */
   protected void resetUI() {     
       InputInfo inputInfo = new InputInfo();
       inputInfo.setActionId(actionIdLoadSite);
       ReturnInfo returnInfo = this.accessData(inputInfo);
       if (returnInfo.isError())
               return;
          siteItem = (Vector) returnInfo.getReturnObj(
                   DtoAttVariantQuery.DTO_SITE_LIST);
          VMComboBox itemSite = new VMComboBox(siteItem);
          combSite.setModel(itemSite);
          isPMO = (Boolean)returnInfo.getReturnObj(DtoAttVariantQuery.
                   DTO_IS_PMO);
          if(isPMO){
               combSite.setEnabled(true);
               lblSite.setEnabled(true);
          }else{
               combSite.setEnabled(false);
               lblSite.setEnabled(false);
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
           Date beginDate =cal.getTime();
           int date = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
           cal.set(year,month,date);
           Date endDate = cal.getTime();
           inputBegin.setUICValue(beginDate);
           inputEnd.setUICValue(endDate);
       }else{
          int year = cal.get(Calendar.YEAR);
          cal.set(year,month-1,1);
          Date beginDate =cal.getTime();
          int date = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
          cal.set(year,month-1,date);
          Date endDate = cal.getTime();
          inputBegin.setUICValue(beginDate);
          inputEnd.setUICValue(endDate);
       }
   }

   //參數設置
    public void setParameter(Parameter param) {
        super.setParameter(param);
    }

   //得到查询條件的DTO
   public DtoAttVariantQuery getDtoAttVariant(){
       dtoQuery = new DtoAttVariantQuery();
       dtoQuery.setBeginDate((Date)inputBegin.getUICValue());
       dtoQuery.setEndDate((Date)inputEnd.getUICValue());
       dtoQuery.setEmpId((String)txtEmployee.getUICValue());
       dtoQuery.setSite((String)combSite.getUICValue());
       dtoQuery.setSelectAll((Boolean)radioBtnAll.isSelected());
       dtoQuery.setSelectVariant((Boolean)radioBtnVariant.isSelected());
       return dtoQuery;
   }

     public void refreshWorkArea() {
         super.refreshWorkArea();
     }

 }
