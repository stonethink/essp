package client.essp.tc.hrmanager.mail;

import client.essp.common.view.VWGeneralWorkArea;
import java.awt.Dimension;
import com.wits.util.Parameter;

public class VwEmployListPopArea extends VWGeneralWorkArea {
    public VwEmployeeList vwEmployeeList = new VwEmployeeList();
    public VwEmployListPopArea() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void jbInit() throws Exception {
        this.setPreferredSize(new Dimension(850, 450));
        this.addTab("Mail List", vwEmployeeList, true);
    }

    public void setParameter(Parameter param) {
         super.setParameter(param);
         vwEmployeeList.setParameter(param);
    }

    public void resetUI(){
        vwEmployeeList.resetUI();
    }
}
