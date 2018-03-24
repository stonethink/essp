package client.essp.pwm.wpList;

import java.awt.event.ActionEvent;
import c2s.dto.ITreeNode;
import c2s.essp.pms.wbs.DtoKEY;
import c2s.essp.pms.wbs.DtoWbsActivity;
import c2s.essp.pwm.wp.DtoPwWp;
import client.essp.common.view.VWWorkArea;
import client.essp.pwm.wp.FPW01000PwWp;
import client.framework.view.event.DataChangedListener;
import client.framework.view.vwcomp.IVWPopupEditorEvent;
import client.framework.view.vwcomp.VWJPopupEditor;
import com.wits.util.Parameter;
import com.wits.util.TestPanel;
import client.framework.view.event.DataCreateListener;

public class VwWpListActivity extends VwWpList implements
    IVWPopupEditorEvent {

    DtoWbsActivity dtoActivity = null;

    FPW01000PwWp fPW01000PwWp;
    boolean isInsert = false;
    public VwWpListActivity(){
        super();
        fPW01000PwWp = new FPW01000PwWp();

        this.fPW01000PwWp.getGeneralWorkArea().addDataChangedListener(new DataChangedListener(){
            public void processDataChanged(){
                getTable().refreshTable();
            }
        });
        this.fPW01000PwWp.getGeneralWorkArea().addDataCreateListener(new DataCreateListener(){
            public void processDataCreate(){
                processDataCreateWp();
            }
        });

    }

    public void setParameter(Parameter param) {
       ITreeNode treeNode = (ITreeNode) param.get(DtoKEY.WBSTREE);

       DtoWbsActivity dtoActivity = (DtoWbsActivity) treeNode.getDataBean();
       if( dtoActivity != null ){
           Parameter p = new Parameter();

//           p.put("activityRid", dtoActivity.getActivityRid() );
//           p.put("isReadonly", new Boolean(dtoActivity.isReadonly()) );
           this.dtoActivity = dtoActivity;
           p.put("dtoActivity", dtoActivity);
           super.setParameter(p);
       }else{
           super.setParameter(new Parameter());
           isParameterValid = false;
       }
    }

    protected void actionPerformedUpdate(ActionEvent e) {
        log.info("1:actionPerformedUpdate");
        DtoPwWp dtoPwWp = (DtoPwWp)this.getSelectedData();
        if( dtoPwWp != null ){
            updateData(dtoPwWp);
        }
    }

    protected void actionPerformedAdd(ActionEvent e) {
        DtoPwWp dtoPwWp = new DtoPwWp();
        //this.getTable().addRow(dtoPwWp);
        isInsert = true;
        updateData(dtoPwWp);
    }

    private void updateData(DtoPwWp wp) {
        if (wp != null) {
            Parameter param1 = new Parameter();
            param1.put("inActivityId", getParameterActivityRid());
            param1.put("dtoPwWp", wp);
            log.info("2:activityrid - " + getParameterActivityRid());
            this.fPW01000PwWp.setParameter(param1);
        } else {
            this.fPW01000PwWp.setParameter(new Parameter());
        }

        fPW01000PwWp.refreshWorkArea();
        VWJPopupEditor popupEditor = new VWJPopupEditor(getParentWindow(), "Work Package"
                        , fPW01000PwWp, VwWpListActivity.this);
                    popupEditor.show();
    }

    protected void setButtonVisible() {
        if (isParameterValid == true &&
            this.isReadonly == false) {
            lblAccountName.setVisible(false);
            this.btnAdd.setVisible(true);
            this.btnDel.setVisible(true);
            this.btnUpdate.setVisible(true);
        } else {
            lblAccountName.setVisible(false);
            this.btnAdd.setVisible(false);
            this.btnDel.setVisible(false);
            this.btnUpdate.setVisible(false);
        }
    }

    public boolean onClickOK(ActionEvent e) {
        if (fPW01000PwWp != null) {
            fPW01000PwWp.saveWorkAreaDirect();
            boolean ok = fPW01000PwWp.isSaveOk();
//            if( ok && isInsert ){
//                DtoPwWp dto = fPW01000PwWp.getGeneralWorkArea().getDto();
//
//                //防止 fPW01000PwWp.getGeneralWorkArea().checkModify() == false
//                //时isSaveOk == true 但实际没有真正新增
//                if( dto.getRid() != null ){
//                    getTable().addRow(dto);
//                    dto.setOp(IDto.OP_NOCHANGE);
//                }
//                isInsert = false;
//            }

            return ok;
        }

        return true;
    }

    protected void processDataCreateWp() {
        DtoPwWp dto = fPW01000PwWp.getGeneralWorkArea().getDto();

        if (dto.getRid() != null) {
            getTable().addRow(dto);
        }
    }

    public boolean onClickCancel(ActionEvent e) {
//        if (fPW01000PwWp != null) {
//            //fPW01000PwWp.saveWorkAreaDirect();
//            boolean ok = fPW01000PwWp.isSaveOk();
//            if( ok && isInsert ){
//                DtoPwWp dto = fPW01000PwWp.getGeneralWorkArea().getDto();
//
//                //防止 fPW01000PwWp.getGeneralWorkArea().checkModify() == false
//                //时isSaveOk == true 但实际没有真正新增
//                if( dto.getRid() != null ){
//                    getTable().addRow(dto);
//                    dto.setOp(IDto.OP_NOCHANGE);
//                }
//                isInsert = false;
//            }
//        }

        return true;

        //isInsert = false;
        //return true;
    }

    protected Long getParameterActivityRid(){
        log.info("getParameterActivityRid - dto is "+dtoActivity);
        if( this.dtoActivity != null ){
            log.info("getParameterActivityRid - activity rid is "+dtoActivity);
            return dtoActivity.getActivityRid();
        }else{
            return null;
        }
    }

    protected boolean getIsReadOnly(){
        if( this.dtoActivity != null ){
            return dtoActivity.isReadonly();
        }else{
            return true;
        }
    }


    public static void main(String args[]) {
        VWWorkArea w1 = new VWWorkArea();
        VWWorkArea workArea = new VwWpListActivity();

        w1.addTab("Wp", workArea);
        TestPanel.show(w1);
    }
}
