package client.essp.tc.pmmanage;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import java.util.ArrayList;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.tc.weeklyreport.DtoTcKey;
import client.essp.common.view.VWGeneralWorkArea;
import client.essp.tc.common.VWJDatePeriod;
import client.framework.model.VMComboBox;
import client.framework.view.event.DataChangedListener;
import client.framework.view.vwcomp.VWJComboBox;
import client.framework.view.vwcomp.VWJLabel;
import com.wits.util.TestPanel;
import client.framework.common.Global;
import java.util.Date;
import com.wits.util.comDate;
import javax.swing.BorderFactory;
import java.awt.Color;

public class VwPeriod extends VWGeneralWorkArea {
    public static final String actionIdInitAccountList = "FTCPmManageInitAccountListAction";

    protected VWJComboBox cmbAccount = new VWJComboBox();
    protected VWJLabel lblAccount = new VWJLabel();
    public VWJDatePeriod dtePeriod = null;

    List acntRidList = new ArrayList();
    private Long acntRid = null;

    public VwPeriod() {
        try {
            jbInit();
            initUI();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        setLayout(null);
        this.setMinimumSize(new Dimension( -1, 30));
        this.setPreferredSize(new Dimension( -1, 30));
        this.setMaximumSize(new Dimension( -1, 30));

        lblAccount.setText("Account: ");
        lblAccount.setBounds(new Rectangle(10, 6, 59, 20));
        cmbAccount.setBounds(new Rectangle(68, 6, 183, 20));

        dtePeriod = new VWJDatePeriod();
        dtePeriod.setBounds(new Rectangle(372, 6, 350, 24));
        //dtePeriod.setBorder(BorderFactory.createLineBorder(Color.blue));

        this.add(lblAccount);
        this.add(cmbAccount);
        this.add(dtePeriod);

        cmbAccount.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    itemStateChangedAccount();
                }
            }
        });

        this.dtePeriod.addDataChangedListener(new DataChangedListener() {
            public void processDataChanged() {
                //日期选择
                fireDataChanged();
            }
        });


//        //init date
//        Global.todayDate = "20051203"; //for test
//        Global.todayDatePattern = "yyyyMMdd";
        Date todayDate = Global.todayDate;

        dtePeriod.setTheType(VWJDatePeriod.PERIOD_ONE_WEEK);
        dtePeriod.setTheDate(todayDate);
    }

    private void initUI() {
        //init account comboBox
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(actionIdInitAccountList);
        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == false) {
            acntRidList = (List) returnInfo.getReturnObj(DtoTcKey.ACCOUNT_RID_LIST);
            List acntNameList = (List) returnInfo.getReturnObj(DtoTcKey.ACCOUNT_NAME_LIST);
            Long acntRid = (Long) returnInfo.getReturnObj(DtoTcKey.ACNT_RID);

            String[] names = new String[acntNameList.size()];
            for (int i = 0; i < acntNameList.size(); i++) {
                names[i] = (String) acntNameList.get(i);
            }

            VMComboBox vmComboBox = VMComboBox.toVMComboBox(names, acntRidList.toArray());
            this.cmbAccount.setModel(vmComboBox);

            //初始设置时不触发事件
            if (acntRid != null){
                if (acntRidList.size() > 0) {
                    this.acntRid =(Long)acntRidList.get(0);
                    this.cmbAccount.setUICValue(acntRid);
                } else {
                    this.acntRid = null;
                    this.cmbAccount.setUICValue(null);
                }
            }
        }
    }

    protected void itemStateChangedAccount() {
        Long selectAcnt = (Long) cmbAccount.getUICValue();
        if (selectAcnt != null && selectAcnt.equals(acntRid) == false) {
            this.acntRid = selectAcnt;
            fireDataChanged();
        }
    }

    public Long getSelectAcnt() {
        return this.acntRid;
    }

    public void setSelectedAcnt(Long acntRid){
        if( acntRid != null && acntRidList.contains(acntRid)  ){
            this.cmbAccount.setUICValue(acntRid);
        }
    }

//    //for test
//    void refresh(){
//        initUI();
//    }

    public static void main(String args[]) {
        TestPanel.show(new VwPeriod());
    }
}
