package client.essp.cbs.cost;

import client.essp.common.view.VWGeneralWorkArea;
import client.framework.view.vwcomp.IVWPopupEditorEvent;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import com.wits.util.Parameter;

public class VwCostItemPopWin extends VWGeneralWorkArea  implements IVWPopupEditorEvent{
    public VwCostItemPopWin() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    VwCostItemList vwCostItemList;
    public boolean onClickOK(ActionEvent e) {
        if(!vwCostItemList.checkModified())
            return true;
        if(vwCostItemList.validateData())
            return vwCostItemList.saveData();
        else
            return false;
    }

    public boolean onClickCancel(ActionEvent e) {
        return true;
    }

    public void jbInit() throws Exception {
        this.setPreferredSize(new Dimension(800,600));
        vwCostItemList = new VwCostItemList();

        this.addTab("Cost Item",vwCostItemList);
    }
    public void resetUI(){
        vwCostItemList.resetUI();
    }
    public boolean isRefreshParent(){
        return vwCostItemList.isRefreshParent();
    }
    public void setParameter(Parameter param){
        vwCostItemList.setParameter(param);
    }
}
