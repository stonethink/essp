/*
 * Created on 2008-6-18
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package client.essp.timesheet.report.attendance;

import java.awt.Rectangle;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import javax.swing.JButton;
import com.wits.util.Parameter;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.timesheet.report.DtoAttendanceQuery;
import c2s.essp.timesheet.report.DtoSalaryWkHrQuery;
import c2s.essp.timesheet.report.DtoTsStatusQuery;
import client.essp.common.view.VWGeneralWorkArea;
import client.framework.model.VMComboBox;
import client.framework.view.common.comFORM;
import client.framework.view.vwcomp.VWJComboBox;
import client.framework.view.vwcomp.VWJDatePanel;
import client.framework.view.vwcomp.VWJLabel;

public class VwAttendanceUp extends VWGeneralWorkArea{
        static final String actionIdLoadSite="FTSLoadSiteInfo";   
        private Boolean isPMO = false;
        private VWJLabel begLab = new VWJLabel();
        private VWJLabel endLab = new VWJLabel();
        protected VWJDatePanel inputBegin = new VWJDatePanel();
        protected VWJDatePanel inputEnd = new VWJDatePanel();
        protected JButton queryButton = new JButton();
        protected VWJComboBox siteCombox = new VWJComboBox();
        protected VWJLabel siteLab = new VWJLabel();
        protected Vector siteItem = new Vector();
        protected DtoAttendanceQuery dtoQuery = new DtoAttendanceQuery();
        public VwAttendanceUp()  {
            try {
                jbInit();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    
        //��ʼ��
        public void jbInit(){
            this.setLayout(null);
            //��һ��1��
            begLab.setBounds(new Rectangle(20,25,80,20));
            begLab.setText("rsid.timesheet.begin");
            inputBegin.setBounds(new Rectangle(110,25,100,20));
            inputBegin.setCanSelect(true);
            
            endLab.setBounds(new Rectangle(20,60,80,20));
            endLab.setText("rsid.timesheet.end");
            inputEnd.setBounds(new Rectangle(110,60,100,20));
            inputEnd.setCanSelect(true);
          
            add(inputBegin);
            add(begLab);
            add(endLab);
            add(inputEnd);
            
            siteLab.setBounds(new Rectangle(250,25,80,20));
            siteLab.setText("rsid.timesheet.dept");
            siteCombox.setBounds(new Rectangle(330,25,80,20));
            
            add(siteLab);
            add(siteCombox);
         }
    
    
    
    //�õ���ԃ�l����DTO
    public DtoAttendanceQuery getDtoAttendanceQuery(){
        dtoQuery = new DtoAttendanceQuery();
        dtoQuery.setBegin((Date)inputBegin.getUICValue());
        dtoQuery.setEnd((Date)inputEnd.getUICValue());
        dtoQuery.setSite((String)siteCombox.getUICValue());
        return dtoQuery;
    }
    
    protected void resetUI() {
        initBeginAndEndDate();
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(actionIdLoadSite);
        ReturnInfo returnInfo = this.accessData(inputInfo);
        if (returnInfo.isError())
                return;
           siteItem = (Vector) returnInfo.getReturnObj(
                   DtoSalaryWkHrQuery.DTO_SITE_LIST);
           VMComboBox itemSite = new VMComboBox(siteItem);
           siteCombox.setModel(itemSite);
           isPMO = (Boolean)returnInfo.getReturnObj(DtoSalaryWkHrQuery.
                    DTO_IS_PMO);
           if(isPMO){
               siteCombox.setEnabled(true);
               siteLab.setEnabled(true);
           }else{
               siteCombox.setEnabled(false);
               siteLab.setEnabled(false);
           }
          super.resetUI();
    }

    
    /**
     * ���ù��
     * @param foucsOn
     */
    protected void foucsOnData(String foucsOn) {
      if(foucsOn.equals(DtoTsStatusQuery.DTO_BEGIN_DATE)) {
          comFORM.setFocus(inputBegin);
      } else if(foucsOn.equals(DtoTsStatusQuery.DTO_END_DATE)){
          comFORM.setFocus(inputEnd);
      }
    }
    
    
    /**
    * ��ʼ����ʼ���ںͽ������ڣ���ǰʱ����ϸ��µ��³�����ĩ
    *
    */
    private void initBeginAndEndDate(){
       Calendar cal = Calendar.getInstance();
       int month = cal.get(Calendar.MONTH);
       //�����ǰ�·�Ϊ1�£�������һ���12�µ��³�����ĩ
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
    
    //�����O��
    public void setParameter(Parameter param) {
        super.setParameter(param);
    }

}
