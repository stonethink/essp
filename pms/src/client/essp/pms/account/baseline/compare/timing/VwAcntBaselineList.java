package client.essp.pms.account.baseline.compare.timing;

import client.essp.common.view.VWTableWorkArea;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.vwcomp.VWJText;
import client.framework.view.vwcomp.VWJNumber;
import client.framework.model.VMColumnConfig;
import com.wits.util.Parameter;
import java.util.List;
import c2s.essp.pms.account.DtoAcntTiming;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Color;

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
public class VwAcntBaselineList extends VWTableWorkArea {

    private List baselineList;
    public VwAcntBaselineList() {
        VWJDate date = new VWJDate();
       date.setCanSelect(true);
       VWJNumber number = new VWJNumber();
       number.setMaxInputIntegerDigit(8);
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
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    public void setParameter(Parameter param){
        super.setParameter(param);
        baselineList=(List)param.get("lastBaseLine");
    }
    public void resetUI(){
       if(baselineList!=null){
           DefaultTableCellRenderer  cellRenderer=
                      new VwAcntBLTableCellRenderer(baselineList);
           //this.getTable().getColumnModel()
           this.getTable().getColumnModel().getColumn(0).setCellRenderer(cellRenderer);
           this.getTable().getColumnModel().getColumn(1).setCellRenderer(cellRenderer);
           this.getTable().getColumnModel().getColumn(2).setCellRenderer(cellRenderer);
//           this.getTable().setDefaultRenderer(new VWJDate().getClass(),cellRenderer);
           getTable().setRows(baselineList);
        }
    }

    protected void jbInit() throws Exception {
    }
}
