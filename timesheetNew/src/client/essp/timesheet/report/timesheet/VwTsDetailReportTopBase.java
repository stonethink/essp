package client.essp.timesheet.report.timesheet;

import java.awt.Rectangle;
import java.util.Calendar;

import c2s.essp.timesheet.report.DtoQueryCondition;
import client.essp.common.view.VWGeneralWorkArea;
import client.framework.view.common.comFORM;
import client.framework.view.vwcomp.*;

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
 * @author wenhaizheng
 * @version 1.0
 */
public abstract class VwTsDetailReportTopBase extends VWGeneralWorkArea{

     public VwTsDetailReportTopBase() {
         jbInit();
     }
     protected void jbInit() {
       this.setLayout(null);
       lblBeginDate.setText("rsid.timesheet.begin");
       lblBeginDate.setBounds(new Rectangle(30, 20, 56, 20));
       inputBeginDate.setBounds(new Rectangle(100, 20, 90, 20));
       inputBeginDate.setCanSelect(true);

       lblEndDate.setText("rsid.timesheet.end");
       lblEndDate.setBounds(new Rectangle(30, 60, 56, 20));
       inputEndDate.setBounds(new Rectangle(100, 60, 90, 20));
       inputEndDate.setCanSelect(true);
       this.add(inputEndDate);
       this.add(inputBeginDate);
       this.add(lblEndDate);
       this.add(lblBeginDate);
   }
   protected abstract DtoQueryCondition getDtoQueryCondition();

     protected void initData() {
           Calendar ca = Calendar.getInstance();
           int month = ca.get(Calendar.MONTH);
           int year = ca.get(Calendar.YEAR);
           ca.set(year, month-1, 1);
           int lastDay = ca.getActualMaximum(Calendar.DATE);
           inputBeginDate.setUICValue(ca.getTime());
           ca.set(year, month-1, lastDay);
           inputEndDate.setUICValue(ca.getTime());
       }
  /**
   * ¼¤»îË¢ÐÂ
   */
  public void setParameter(Parameter param) {
      super.setParameter(param);
  }

    protected void resetUI() {
        initData();
    }
    protected void foucsOnDate(String foucsOn) {
    	if(foucsOn.equals("begin")) {
    		comFORM.setFocus(inputBeginDate);
    	} else if(foucsOn.equals("end")){
    		comFORM.setFocus(inputEndDate);
    	} else if(foucsOn.equals("dept")) {
    		comFORM.setFocus(selectDept);
    	}
    }

     VWJLabel lblBeginDate = new VWJLabel();
     VWJDatePanel inputBeginDate = new VWJDatePanel();
     VWJLabel lblEndDate = new VWJLabel();
     VWJDatePanel inputEndDate = new VWJDatePanel();
     VWJLabel lblProject = new VWJLabel();
     VWJComboBox selectProject = new VWJComboBox();
     VWJLabel lblDept = new VWJLabel();
     VWJComboBox selectDept = new VWJComboBox();
     VWJLabel lblPerson = new VWJLabel();
     VWJComboBox selectPerson = new VWJComboBox();
     VWJLabel lblSelectChildren = new VWJLabel();
     VWJCheckBox chekSelectChildren = new VWJCheckBox();

}
