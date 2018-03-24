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
import client.framework.view.vwcomp.VWJCheckBox;
import c2s.essp.tc.weeklyreport.DtoTcSearchCondition;
import client.framework.view.common.comMSG;
import client.framework.view.common.comFORM;

public class VwTcSearchConditionByDm extends VwTcSearchConditionBase {
    public static final String actionIdInitObsList = "FTCDmViewInitObsListAction";

    VWJComboBox cmbObs = null;
    VWJLabel lblOrg = null;
    VWJCheckBox chkIncSub = null;

    public VwTcSearchConditionByDm() {
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

    private void jbInit() throws Exception {

        cmbObs = new VWJComboBox();
        lblOrg = new VWJLabel();
        chkIncSub = new VWJCheckBox();

        lblOrg.setBounds(26, 10, 100, 20);
        lblOrg.setText("OBS");
        cmbObs.setBounds(new Rectangle(130, 10, 282, 20));

        chkIncSub.setText("Include Sub OBS");
        chkIncSub.setBounds(new Rectangle(422, 10, 120, 20));

        this.add(lblOrg);
        this.add(cmbObs);
        this.add(chkIncSub);
    }

    protected void setUIComponentName() {
        super.setUIComponentName();
        cmbObs.setName("orgId");
        chkIncSub.setName("incSub");
    }

    private void setTabOrder() {
        List compList = getCompList();
        comFORM.setTABOrder(this, compList);
    }

    private void setEnterOrder() {
        List compList = getCompList();
        comFORM.setEnterOrder(this, compList);
    }

    protected List getCompList() {
        List compList = super.getCompList();
        compList.add(0, cmbObs);
        return compList;
    }

    public boolean getCondition(DtoTcSearchCondition dto) {
        if (super.getCondition(dto)) {
            if (dto.getOrgId() == null) {
                comMSG.dispMessageDialog("Must choose an OBS.");
                cmbObs.setErrorField(true);
                comFORM.setFocus(cmbObs);
                return false;
            } else {
                cmbObs.setErrorField(false);
            }

            if (dto.getBeginPeriod() == null) {
                comMSG.dispMessageDialog("Must input period.");
                dteBeginPeriod.setErrorField(true);
                comFORM.setFocus(dteBeginPeriod);
                return false;
            } else {
                dteBeginPeriod.setErrorField(false);
            }

            if (dto.getEndPeriod() == null) {
                comMSG.dispMessageDialog("Must input period.");
                dteEndPeriod.setErrorField(true);
                comFORM.setFocus(dteEndPeriod);
                return false;
            } else {
                dteEndPeriod.setErrorField(false);
            }

            return true;
        }else{
            return false;
        }

    }

    private void initUI() {
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(actionIdInitObsList);
        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == false) {
            List orgIdList = (List) returnInfo.getReturnObj(DtoTcKey.ORG_ID_LIST);
            List orgNameList = (List) returnInfo.getReturnObj(DtoTcKey.ORG_NAME_LIST);
            String orgID = (String) returnInfo.getReturnObj(DtoTcKey.ORG);

            String[] names = new String[orgNameList.size()];
            for (int i = 0; i < orgNameList.size(); i++) {
                names[i] = (String) orgNameList.get(i);
            }

            VMComboBox vmComboBox = VMComboBox.toVMComboBox(names, orgIdList.toArray());
            this.cmbObs.setModel(vmComboBox);
            if( orgIdList.size() >  0 ){
                this.cmbObs.setUICValue(orgID);
            }
        }
    }

//    //for test
//    public void refreshWorkArea() {
//        initUI();
//    }

    public static void main(String args[]) {
        TestPanel.show(new VwTcSearchConditionByDm());
    }
}
