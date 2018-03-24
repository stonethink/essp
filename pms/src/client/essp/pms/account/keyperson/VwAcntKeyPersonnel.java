package client.essp.pms.account.keyperson;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;

import c2s.dto.DtoBase;
import c2s.dto.DtoUtil;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.pms.account.DtoAcntKeyPersonnel;
import c2s.essp.pms.account.DtoAcntLoaborRes;
import c2s.essp.pms.account.DtoPmsAcnt;
import client.essp.common.view.VWTableWorkArea;
import client.essp.common.view.VWWorkArea;
import client.framework.common.Constant;
import client.framework.model.VMColumnConfig;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.IVWPopupEditorEvent;
import client.framework.view.vwcomp.VWJButton;
import client.framework.view.vwcomp.VWJPopupEditor;
import client.framework.view.vwcomp.VWJText;
import com.wits.util.Parameter;
import com.wits.util.TestPanel;
import java.util.ArrayList;
import client.essp.pms.account.VwAccount;

public class VwAcntKeyPersonnel extends VWTableWorkArea implements IVWPopupEditorEvent {
    public static final String ACTIONID_KEYPERSON_LIST = "FAcntLKeyPersonnelListAction";
    public static final String ACTIONID_KEYPERSON_SAVE = "FAcntLKeyPersonnelSaveAction";
    public static final String ACTIONID_KEYPERSON_DELETE = "FAcntLKeyPersonnelDeleteAction";
    /**
     * define common data (globe)
     */
    private DtoPmsAcnt pmsAcc;
    private List keyPersonnelList;

    private VwKeyPersonnelPopWorkArea popWindow ;

    private boolean isParameterValid = true;
    /**
     * default constructor
     */
    public VwAcntKeyPersonnel() {
        //添加按钮
        this.getButtonPanel().addAddButton(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                actionPerformedAdd(e);
            }
        });

        this.getButtonPanel().addEditButton(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                actionPerformedEdit();
            }
        });

        this.getButtonPanel().addDelButton(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                actionPerformedDel(e);
            }
        });

        this.getButtonPanel().addLoadButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedLoad(e);
            }
        });


        //设置标题栏位
        Object[][] configs = new Object[][] {
                                              {"Type", "typeName",VMColumnConfig.UNEDITABLE, new VWJText()},
                                              {"User ID","loginId",VMColumnConfig.UNEDITABLE, new VWJText()},
                                              {"User Name","userName",VMColumnConfig.UNEDITABLE, new VWJText()},
                                              {"Organization","organization",VMColumnConfig.UNEDITABLE, new VWJText()},
                                              {"Title","title",VMColumnConfig.UNEDITABLE, new VWJText()},
                                              {"Phone","phone",VMColumnConfig.UNEDITABLE, new VWJText()},
                                              {"Fax","fax",VMColumnConfig.UNEDITABLE, new VWJText()},
                                              {"Email","email",VMColumnConfig.UNEDITABLE, new VWJText()},
                                              {"Enable","enable",VMColumnConfig.UNEDITABLE, new VWJText()}
                                             };
        try {
            super.jbInit(configs, DtoAcntLoaborRes.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * 新增KeyPersonnel事件
     * @param e ActionEvent
     */
    public void actionPerformedAdd(ActionEvent e){
        if(pmsAcc == null)
            return;
        popWindow = new VwKeyPersonnelPopWorkArea();
        Parameter param = new Parameter();
        param.put("acntRid",pmsAcc.getRid());
        popWindow.setParameter(param);
        popWindow.refreshWorkArea();
        VWJPopupEditor pop = new VWJPopupEditor(this.getParentWindow(),"Key Personnel",popWindow,this);
        pop.show();
    }
    /**
     * 修改KeyPersonnel事件
     */
    public void actionPerformedEdit(){
        if(pmsAcc == null)
            return;
        popWindow = new VwKeyPersonnelPopWorkArea();
        DtoAcntKeyPersonnel oldDto = (DtoAcntKeyPersonnel) this.getTable().getSelectedData();
        DtoAcntKeyPersonnel dto = new DtoAcntKeyPersonnel();
        try {
            DtoUtil.copyBeanToBean(dto, oldDto);
        } catch (Exception ex) {
            log.error(ex);
            throw new RuntimeException("error while editing key personnel:[" + dto.getLoginId() + "]");
        }
        Parameter param = new Parameter();
        param.put("acntRid",pmsAcc.getRid());
        param.put("dto",dto);
        popWindow.setParameter(param);
        popWindow.refreshWorkArea();
        VWJPopupEditor pop = new VWJPopupEditor(this.getParentWindow(),"Key Personnel",popWindow,this);
        pop.show();
    }

    private boolean saveData() {
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.ACTIONID_KEYPERSON_SAVE);
        inputInfo.setInputObj("keyPersonnelList", keyPersonnelList);
        ReturnInfo returnInfo = accessData(inputInfo);
        return !returnInfo.isError();
    }
    private boolean checkModified() {
        if(keyPersonnelList == null || keyPersonnelList.size() <= 0)
            return false;
        for (Iterator it = this.keyPersonnelList.iterator();
                           it.hasNext(); ) {
            DtoBase dto = (DtoBase) it.next();
            if (dto.isChanged()) {
                return true;
            }
        }
        return false;
    }
    /**
     * 删除KeyPersonnel事件
     * @param e ActionEvent
     */
    public void actionPerformedDel(ActionEvent e){
        int f = comMSG.dispConfirmDialog("Confirm to delete selected record!");
        if( f == Constant.OK ){
            InputInfo inputInfo = new InputInfo();
            inputInfo.setActionId(this.ACTIONID_KEYPERSON_DELETE);
            inputInfo.setInputObj("dto", this.getTable().getSelectedData());
            ReturnInfo returnInfo = accessData(inputInfo);
            if(!returnInfo.isError())
                resetUI();
        }
    }
    /**
     * 刷新界面事件
     * @param e ActionEvent
     */
    public void actionPerformedLoad(ActionEvent e){
        resetUI();
    }

    public void resetUI() {
        setButtonVisible();
        if(pmsAcc.getRid() == null){
            getTable().setRows(new ArrayList());
            return;
        }
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.ACTIONID_KEYPERSON_LIST);
        inputInfo.setInputObj("acntRid", pmsAcc.getRid());
        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == false) {
            keyPersonnelList = (List) returnInfo.getReturnObj("keyPersonnelList");
            if(keyPersonnelList != null)
                this.getTable().setRows(keyPersonnelList);
        }
    }

    public void setParameter(Parameter param) {
        this.pmsAcc = (DtoPmsAcnt) param.get("dtoAccount");
        if(pmsAcc == null){
            pmsAcc = new DtoPmsAcnt();
            isParameterValid = false;
        }else{
            isParameterValid = true;
        }
        super.setParameter(param);
    }
    private void setButtonVisible(){
        this.getButtonPanel().setVisible(isParameterValid);
    }
    public boolean onClickOK(ActionEvent e) {
       boolean isSuccess = popWindow.saveOrUpdate();
       if(isSuccess)
           resetUI();
       return isSuccess;
    }

    public boolean onClickCancel(ActionEvent e) {
        return true;
    }

    public static void main(String[] args) {
        VWWorkArea w1 = new VWWorkArea();
        VwAcntKeyPersonnel ll = new VwAcntKeyPersonnel();

        w1.addTab("Key Personnel",ll);
        Parameter param = new Parameter();
        DtoPmsAcnt pmsAcc = new DtoPmsAcnt();
        Long lAccRid = new Long(6);
        pmsAcc.setRid(lAccRid);
        param.put("dtoAccount", pmsAcc);
        ll.setParameter(param);

        TestPanel.show(w1);
        ll.resetUI();
    }
}
