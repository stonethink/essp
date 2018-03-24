package client.essp.cbs.config;

import client.essp.common.view.VWTDWorkArea;
import com.wits.util.Parameter;
import client.framework.view.event.RowSelectedListener;
import c2s.dto.ITreeNode;
import c2s.essp.cbs.DtoSubject;
import client.framework.view.event.RowSelectedLostListener;
import client.framework.view.event.DataChangedListener;
import client.framework.view.vwcomp.IVWAppletParameter;
/**
 *
 * CBS科目定义界面
 * @author XR
 * @version 1.0
 */
public class VwCbs extends VWTDWorkArea implements IVWAppletParameter {
    VwCbsList vwCbsList;
    VwCbsGeneral vwCbsGeneral;

    public VwCbs() {
        super(300);
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        addUICEvent();
    }

    private void addUICEvent() {
        vwCbsList.getTreeTable().addRowSelectedListener(new RowSelectedListener(){
            public void processRowSelected() {
                processSubjectSelected();
            }
        });
        vwCbsList.getTreeTable().addRowSelectedLostListener(new RowSelectedLostListener(){
            public boolean processRowSelectedLost(int oldSelectedRow,
                                                  Object oldSelectedData) {
                return processSubjectSelectedLost();
            }
        });
        vwCbsGeneral.addDataChangedListener(new DataChangedListener(){
            public void processDataChanged() {
                vwCbsList.isModified = true;
                vwCbsList.getTreeTable().refreshTree();
            }
        });
    }

    private void jbInit() throws Exception {
        vwCbsList = new VwCbsList();
        this.getTopArea().addTab("CBS",vwCbsList);

        vwCbsGeneral = new VwCbsGeneral();
        this.getDownArea().addTab("General",vwCbsGeneral);

        Parameter param = new Parameter();
        param.put("vwCbsGeneral",vwCbsGeneral);
        vwCbsList.setParameter(param);
    }

    public void refreshWorkArea() {
        vwCbsList.refreshWorkArea();
        vwCbsGeneral.refreshWorkArea();
    }
    public void saveWorkArea() {
        vwCbsGeneral.saveWorkArea();
        if(vwCbsGeneral.isSaveOk())
            vwCbsList.saveWorkArea();
    }

    public boolean isSaveOk(){
        return vwCbsGeneral.isSaveOk() && vwCbsList.isSaveOk();
    }

    public void processSubjectSelected(){
        ITreeNode root = (ITreeNode) vwCbsList.getModel().getRoot();
        ITreeNode node = (ITreeNode) vwCbsList.getTreeTable().getSelectedNode();
        if(root != null && node != null){
            DtoSubject dto = (DtoSubject) node.getDataBean();
            Parameter param = new Parameter();
            param.put("dto",dto);
            param.put("root",root);
            vwCbsGeneral.setParameter(param);
            vwCbsGeneral.resetUI();

            Parameter param2 = new Parameter();
            param2.put("vwCbsGeneral",vwCbsGeneral);
            vwCbsList.setParameter(param2);
        }
    }

    private boolean processSubjectSelectedLost(){
       this.getDownArea().getSelectedWorkArea().saveWorkArea();
        return this.getDownArea().getSelectedWorkArea().isSaveOk();
    }

    public String[][] getParameterInfo() {
        return new String[][]{};
    }
}
