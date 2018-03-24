package client.essp.pms.account.baseline.compare.timing;

import client.essp.common.view.VWTableWorkArea;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.vwcomp.VWJText;
import client.framework.view.vwcomp.VWJNumber;
import client.framework.model.VMColumnConfig;
import com.wits.util.Parameter;
import c2s.essp.pms.account.DtoAcntTiming;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import java.util.List;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class VwAcntMilestoneList extends VWTableWorkArea {

//    static final String actionTimingList = "FAcntBaseLineTimingListAction";
//    private DtoAcntTiming dtoAcntTiming;
    private List milestoneList;


    public VwAcntMilestoneList() {
        try {
           jbInit();
       } catch (Exception ex) {
           ex.printStackTrace();
       }
    }
    public void jbInit(){
        VWJDate date = new VWJDate();
        date.setCanSelect(true);
        Object[][] configs = new Object[][] {
                           {"Name", "name", VMColumnConfig.UNEDITABLE, new VWJText()},
                           {"Code", "code", VMColumnConfig.UNEDITABLE, new VWJText()},
                           {"Anticipated Finish", "anticipatedFinish", VMColumnConfig.UNEDITABLE, date},
      };
      try {
           super.jbInit(configs, DtoAcntTiming.class);
       } catch (Exception e) {
           e.printStackTrace();
       }

    }

    public void setParameter(Parameter param){
        super.setParameter(param);
        milestoneList =(List) param.get("currentMilestone") ;
    }
    public void resetUI(){
//       InputInfo inputInfo = new InputInfo();
//       inputInfo.setActionId(actionTimingList);
//       inputInfo.setInputObj("acntRid",dtoAcntTiming.getAcntRid());
//
//      ReturnInfo returnInfo= accessData(inputInfo);
//       if(returnInfo.isError()==false){
//           milestoneList = (List) returnInfo.getReturnObj("currentMilestone");
//           getTable().setRows(milestoneList);
//       }
        if(milestoneList!=null){
            DefaultTableCellRenderer cellRenderer =
                new VwAcntBLTableCellRenderer(milestoneList);

            this.getTable().getColumnModel().getColumn(0).setCellRenderer(cellRenderer);
            this.getTable().getColumnModel().getColumn(1).setCellRenderer(cellRenderer);
            this.getTable().getColumnModel().getColumn(2).setCellRenderer(cellRenderer);

            getTable().setRows(milestoneList);
        }
    }

}
