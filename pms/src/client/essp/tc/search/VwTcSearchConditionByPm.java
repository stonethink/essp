package client.essp.tc.search;

import java.awt.Rectangle;
import java.util.List;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.tc.weeklyreport.DtoTcKey;
import client.framework.model.VMComboBox;
import client.framework.view.vwcomp.VWJComboBox;
import client.framework.view.vwcomp.VWJLabel;
import com.wits.util.TestPanel;
import c2s.essp.tc.weeklyreport.DtoTcSearchCondition;
import client.framework.view.common.comMSG;
import client.framework.view.common.comFORM;

public class VwTcSearchConditionByPm extends VwTcSearchConditionBase{
    final static String actionIdInitAccountList = "FTCPmManageInitAccountListAction";

    VWJComboBox cmbAcnt = null;
    VWJLabel lblAcnt = null;

    public VwTcSearchConditionByPm(){
        super();

        try {
            jbInit();

            setUIComponentName();
            setEnterOrder();
            setTabOrder();

            initUI();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    protected void jbInit()throws Exception{

        cmbAcnt = new VWJComboBox();
        lblAcnt = new VWJLabel();

        lblAcnt.setBounds(26, 10, 100, 20);
        lblAcnt.setText("Account");
        cmbAcnt.setBounds(new Rectangle(130, 10, 282, 20));

        this.add(lblAcnt);
        this.add(cmbAcnt);
    }

    protected void setUIComponentName() {
        super.setUIComponentName();
        cmbAcnt.setName("acntRid");
    }

    private void setTabOrder() {
        List compList = getCompList();
        comFORM.setTABOrder(this, compList);
    }

    private void setEnterOrder() {
        List compList = getCompList();
        comFORM.setEnterOrder(this, compList);
    }

    protected List getCompList(){
        List compList = super.getCompList();
        compList.add(0, cmbAcnt);
        return compList;
    }

    public boolean getCondition(DtoTcSearchCondition dto){
        if (super.getCondition(dto)) {
            if (dto.getAcntRid() == null) {
                comMSG.dispMessageDialog("Must choose an account.");
                cmbAcnt.setErrorField(true);
                comFORM.setFocus(cmbAcnt);
                return false;
            }else{
                cmbAcnt.setErrorField(false);
            }

            if( dto.getBeginPeriod() == null ){
                comMSG.dispMessageDialog("Must input period.");
                dteBeginPeriod.setErrorField(true);
                comFORM.setFocus(dteBeginPeriod);
                return false;
            }else{
                dteBeginPeriod.setErrorField(false);
            }

            if( dto.getEndPeriod() == null ){
                comMSG.dispMessageDialog("Must input period.");
                dteEndPeriod.setErrorField(true);
                comFORM.setFocus(dteEndPeriod);
                return false;
            }else{
                dteEndPeriod.setErrorField(false);
            }

            return true;
        }else{
            return false;
        }
    }

    private void initUI() {
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(actionIdInitAccountList);
        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == false) {
            List acntRidList = (List) returnInfo.getReturnObj(DtoTcKey.ACCOUNT_RID_LIST);
            List acntNameList = (List) returnInfo.getReturnObj(DtoTcKey.ACCOUNT_NAME_LIST);
            Long acntRid = (Long) returnInfo.getReturnObj(DtoTcKey.ACNT_RID);

            String[] names = new String[acntNameList.size()];
            for (int i = 0; i < acntNameList.size(); i++) {
                names[i] = (String) acntNameList.get(i);
            }

            Long[] rids = new Long[acntRidList.size()];
            for (int i = 0; i < acntRidList.size(); i++) {
                rids[i] = (Long) acntRidList.get(i);
            }

            VMComboBox vmComboBox = VMComboBox.toVMComboBox(names, acntRidList.toArray());
            this.cmbAcnt.setModel(vmComboBox);

            if( acntRidList.size() > 0 ){
                this.cmbAcnt.setUICValue(acntRidList.get(0));
            }else{
                this.cmbAcnt.setUICValue(null);
            }
        }
    }

//    //for test
//    public void refreshWorkArea(){
//        initUI();
//    }

    public static void main(String args[]){
        TestPanel.show(new VwTcSearchConditionByPm());
    }
}
