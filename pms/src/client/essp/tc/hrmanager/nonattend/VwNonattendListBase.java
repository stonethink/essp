package client.essp.tc.hrmanager.nonattend;

import client.essp.common.view.VWTableWorkArea;
import client.framework.view.vwcomp.VWJReal;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.vwcomp.VWJText;
import c2s.essp.tc.leave.DtoLeave;
import client.framework.model.VMColumnConfig;
import java.awt.Dimension;
import c2s.essp.tc.nonattend.DtoNonattend;
import client.essp.common.loginId.VWJLoginId;

public class VwNonattendListBase extends VWTableWorkArea {
    private VWJReal inputHours = new VWJReal();
    private VWJDate _timeFormat=new VWJDate();
     public VwNonattendListBase() {
         try {
             jbInit();
         } catch (Exception ex) {
             ex.printStackTrace();
         }
     }

     protected void jbInit() throws Exception {
         this.setPreferredSize(new Dimension(700, 260));
         //设置标题栏位
         inputHours.setMaxInputIntegerDigit(8);
         inputHours.setMaxInputDecimalDigit(2);
         _timeFormat.setDataType(VWJDate._DATA_PRO_HM);

         Object[][] configs = new Object[][] {
                              {"Worker", "loginId",VMColumnConfig.UNEDITABLE, new VWJLoginId()},
                              {"Date", "date", VMColumnConfig.UNEDITABLE,new VWJDate()},
                              {"From", "timeFrom", VMColumnConfig.UNEDITABLE, _timeFormat},
                              {"To", "timeTo", VMColumnConfig.UNEDITABLE,_timeFormat},
                              {"Hours", "totalHours",VMColumnConfig.UNEDITABLE, inputHours},
                              {"Remark", "remark",VMColumnConfig.UNEDITABLE, new VWJText()},
         };

         try {
             super.jbInit(configs, DtoNonattend.class);
         } catch (Exception e) {
             e.printStackTrace();
         }
     }

}
