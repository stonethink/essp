/*
 * Created on 2008-6-27
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package client.essp.timesheet.report.busyRate;

import java.awt.Rectangle;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import javax.swing.JButton;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.timesheet.report.DtoBusyRateQuery;
import client.essp.common.view.VWGeneralWorkArea;
import client.framework.model.VMComboBox;
import client.framework.view.common.comFORM;
import client.framework.view.vwcomp.VWJComboBox;
import client.framework.view.vwcomp.VWJDatePanel;
import client.framework.view.vwcomp.VWJLabel;
import com.wits.util.Parameter;

public class VwBusyRateGatherUp extends VWGeneralWorkArea{
        static final String actionIdLoadDept="FTSStatusListDept";
        private DtoBusyRateQuery dtoQuery = new DtoBusyRateQuery();
        private VWJLabel deptLab = new VWJLabel();
        private VWJComboBox deptComBox = new VWJComboBox();
        private VWJLabel begLab = new VWJLabel();
        private VWJLabel endLab = new VWJLabel();
        private VWJDatePanel inputBegin = new VWJDatePanel();
        private VWJDatePanel inputEnd = new VWJDatePanel();
        public Vector DeptItem = new Vector();
        public JButton queryButton = null;
        public VwBusyRateGatherUp()  {
            try {
                jbInit();
            } catch (Exception ex) {
               
            }
        }
    
        //��ʼ��
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
        * ˢ�½���
        */
       protected void resetUI() {
           InputInfo inputInfo = new InputInfo();
           inputInfo.setActionId(actionIdLoadDept);
           ReturnInfo returnInfo = this.accessData(inputInfo);
           if (returnInfo.isError())
               return;
           DeptItem = (Vector) returnInfo.getReturnObj(
                   DtoBusyRateQuery.DTO_DEPT_LIST);
    
           VMComboBox itemDept = new VMComboBox(DeptItem);
           deptComBox.setModel(itemDept);
           initBeginAndEndDate();
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
    
       //�õ���\uFFFD\uFFFD�l����DTO
       public DtoBusyRateQuery getDtoBusyRateQuery(){
           dtoQuery = new DtoBusyRateQuery();
           dtoQuery.setAcntId((String)deptComBox.getUICValue());
           dtoQuery.setBegin((Date)inputBegin.getUICValue());
           dtoQuery.setEnd((Date)inputEnd.getUICValue());
           return dtoQuery;
       }
    
       /**
        * ���ù��
        * @param foucsOn
        */
       protected void foucsOnData(String foucsOn) {
         if(foucsOn.equals(DtoBusyRateQuery.DTO_BEGIN_DATE)) {
             comFORM.setFocus(inputBegin);
         } else if(foucsOn.equals(DtoBusyRateQuery.DTO_END_DATE)){
             comFORM.setFocus(inputEnd);
         }else if(DtoBusyRateQuery.DTO_ACCOUNT_ID.equals(foucsOn)){
             comFORM.setFocus(deptComBox);
         }
       }
       
         public void refreshWorkArea() {
             super.refreshWorkArea();
         }
 }
