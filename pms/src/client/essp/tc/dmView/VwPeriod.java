package client.essp.tc.dmView;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.tc.weeklyreport.DtoTcKey;
import client.essp.common.view.VWGeneralWorkArea;
import client.essp.tc.common.VWJDatePeriod;
import client.framework.model.VMComboBox;
import client.framework.view.event.DataChangedListener;
import client.framework.view.vwcomp.VWJCheckBox;
import client.framework.view.vwcomp.VWJComboBox;
import client.framework.view.vwcomp.VWJLabel;
import com.wits.util.TestPanel;
import client.framework.common.Global;
import java.util.Date;
import com.wits.util.comDate;

public class VwPeriod extends VWGeneralWorkArea {
    public static final String actionIdInitObsList = "FTCDmViewInitObsListAction";

    protected VWJComboBox cmbObs = new VWJComboBox();
    protected VWJLabel lblObs = new VWJLabel();
    public VWJDatePeriod dtePeriod = null;
    VWJCheckBox chkIncSub = new VWJCheckBox();

    private String orgId = null;
    private boolean incSub = false;

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

        lblObs.setText("Obs: ");
        lblObs.setBounds(new Rectangle(10, 6, 59, 20));
        cmbObs.setBounds(new Rectangle(68, 6, 183, 20));

        chkIncSub.setText("Include Sub OBS");
        chkIncSub.setBounds(new Rectangle(255, 6, 120, 20));

        dtePeriod = new VWJDatePeriod();
        dtePeriod.setBounds(new Rectangle(400, 6, 345, 24));

        this.add(lblObs);
        this.add(cmbObs);
        this.add(dtePeriod);
        this.add(chkIncSub);

        cmbObs.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    itemStateChangedObs();
                }
            }
        });

        chkIncSub.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedIncSub();
            }
        });

        this.dtePeriod.addDataChangedListener(new DataChangedListener() {
            public void processDataChanged() {
                //日期选择
                fireDataChanged();
            }
        });
    }

    private void initUI() {
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(actionIdInitObsList);
        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == false) {
            List orgIdList = (List) returnInfo.getReturnObj(DtoTcKey.ORG_ID_LIST);
            List orgNameList = (List) returnInfo.getReturnObj(DtoTcKey.ORG_NAME_LIST);
            String orgId = (String) returnInfo.getReturnObj(DtoTcKey.ORG);

            String[] names = new String[orgNameList.size()];
            for (int i = 0; i < orgNameList.size(); i++) {
                names[i] = (String) orgNameList.get(i);
            }

            VMComboBox vmComboBox = VMComboBox.toVMComboBox(names, orgIdList.toArray());
            this.cmbObs.setModel(vmComboBox);

            //初始设置时不触发事件
            if (orgId != null) {
                if (orgIdList.size() > 0) {
                    this.orgId = orgId;
                    this.cmbObs.setUICValue(orgId);
                } else {
                    this.orgId = null;
                    this.cmbObs.setUICValue(null);
                }
            }
        }

        setIncSub(false);

        dtePeriod.setTheType(VWJDatePeriod.PERIOD_ONE_WEEK);
    }

    public void setToday() {
//        //for test
//        Global.todayDate = "20051203";
//        Global.todayDatePattern = "yyyyMMdd";
        Date todayDate = Global.todayDate;

        dtePeriod.setTheDate(todayDate);
    }

    protected void itemStateChangedObs() {
        String selectOrg = (String) cmbObs.getUICValue();
        if (selectOrg != null && selectOrg.equals(orgId) == false) {
            this.orgId = selectOrg;
            fireDataChanged();
        }
    }

    protected void actionPerformedIncSub() {
        boolean isInc = this.chkIncSub.isSelected();
        if (isInc != this.incSub) {
            this.incSub = isInc;
            fireDataChanged();
        }
    }

    private void setIncSub(boolean incSub) {
        this.incSub = incSub;
        this.chkIncSub.setSelected(incSub);
    }

    public String getSelectOrg() {
        return this.orgId;
    }

    public boolean isIncSub() {
        return this.incSub;
    }

//    //for test
//    public void refreshWorkArea(){
//        initUI();
//    }

    public static void main(String args[]) {
        TestPanel.show(new VwPeriod());
    }
}
