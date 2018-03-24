package client.essp.pwm.workbench.workitem;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.text.JTextComponent;

import c2s.dto.DtoComboItem;
import client.essp.common.view.VWGeneralWorkArea;
import client.essp.pwm.wp.GetAccountList;
import client.essp.pwm.wp.GetActivityList;
import client.framework.model.VMComboBox;
import client.framework.view.common.comFORM;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.VWJComboBox;
import client.framework.view.vwcomp.VWJDisp;
import client.framework.view.vwcomp.VWJEditComboBox;
import client.framework.view.vwcomp.VWJLabel;
import com.wits.util.TestPanel;
import com.wits.util.StringUtil;

public class VwWorkItemBelongTo extends VWGeneralWorkArea {
    boolean bLockAcntCodeSelected = false;
    boolean bLockAcntNameSelected = false;
    boolean bLockActivityCodeSelected = false;
    boolean bLockActivityNameSelected = false;
    private GetAccountList getAccountList = new GetAccountList();
    private GetActivityList getActivityList = new GetActivityList();

    protected VWJEditComboBox cmbAcntCode = new VWJEditComboBox();
    protected VWJComboBox cmbAcntName = new VWJComboBox();
    protected VWJDisp dspWpName = new VWJDisp();
    protected VWJEditComboBox cmbActivityCode = new VWJEditComboBox();
    protected VWJComboBox cmbActivityName = new VWJComboBox();
    protected VWJLabel lblAccountName = new VWJLabel();
    protected VWJLabel lblProjectId = new VWJLabel();
    protected VWJLabel lblAccountType = new VWJLabel();
    protected VWJLabel lblClnitem = new VWJLabel();
    protected VWJLabel lblActivityName = new VWJLabel();

    public VwWorkItemBelongTo() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        addUICEvent();
        initUI();
    }

    private void jbInit() throws Exception {
        setPreferredSize(new Dimension(700, 170));
        setLayout(null);

        lblProjectId.setText("Account Code");
        lblProjectId.setBounds(new Rectangle(26, 20, 130, 20));
        cmbAcntCode.setBounds(new Rectangle(165, 20, 160, 22));

        lblAccountName.setText("Account Name");
        lblAccountName.setBounds(new Rectangle(362, 20, 120, 20));
        cmbAcntName.setBounds(new Rectangle(502, 20, 160, 22));

        lblActivityName.setText("Activity ID");
        lblActivityName.setBounds(new Rectangle(26, 45, 130, 20));
        cmbActivityCode.setBounds(new Rectangle(165, 45, 160, 22));

        lblClnitem.setText("Activity Name");
        lblClnitem.setBounds(new Rectangle(362, 45, 120, 20));
        cmbActivityName.setBounds(new Rectangle(502, 45, 160, 22));

        lblAccountType.setText("WP Name");
        lblAccountType.setBounds(new Rectangle(26, 70, 130, 20));
        dspWpName.setBounds(new Rectangle(165, 70, 160, 20));

        this.add(cmbAcntCode, null);
        this.add(cmbAcntName, null);
        this.add(dspWpName, null);
        this.add(cmbActivityCode, null);
        this.add(cmbActivityName, null);

        this.add(lblAccountName, null);
        this.add(lblProjectId, null);
        this.add(lblAccountType, null);
        this.add(lblClnitem, null);
        this.add(lblActivityName, null);
        setTabOrder();
        setEnterOrder();
        setUIComponentName();
    }

    private void initUI(){
        getAccountList();
    }

    private void addUICEvent(){
        cmbAcntCode.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    itemDataChangedAcntCode();
                }
            }
        });

        cmbAcntCode.getEditor().getEditorComponent().addKeyListener(new
            KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    keyPressedAcntCode();
                }
            }
        });

        cmbAcntName.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    itemDataChangedAcntName();
                }
            }
        });

        cmbActivityCode.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    itemDataChangedActivityCode();
                }
            }
        });

        cmbActivityCode.getEditor().getEditorComponent().addKeyListener(new
            KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    keyPressedActivityCode();
                }
            }
        });

        cmbActivityName.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    itemDataChangedActivityName();
                }
            }
        });
    }

    /////// 段4，事件处理
    public void itemDataChangedAcntCode() {
        if ( cmbAcntCode.isEnabled() == true &&
            this.bLockAcntCodeSelected == false
            ) {
            //先判断输入的值是否在下拉列表中
            VMComboBox vmComboBox = (VMComboBox) cmbAcntCode.getModel();
            Object obj = cmbAcntCode.getSelectedItem(); //对于可输入的ComboBox, 求选择的item要先getselectitem， 再finditembyname
            Object selectItem = vmComboBox.findItemByName(obj);
            if (selectItem == null ||
                !(selectItem instanceof DtoComboItem)) {

                this.bLockAcntNameSelected = true;
                this.cmbAcntName.setUICValue(null);
                this.bLockAcntNameSelected = false;

                getActivityList(null);


                return;
            }

            Long lId = (Long) cmbAcntCode.getUICValue();

            VMComboBox vmComboBoxCmbAccountName = (VMComboBox) cmbAcntName.
                                                  getModel();
            Object objAccountName = vmComboBoxCmbAccountName.findItemByValue(lId);
            bLockAcntNameSelected = true;
            cmbAcntName.setSelectedItem(objAccountName);
            bLockAcntNameSelected = false;

            getActivityList(lId);
        }
    }

    public void keyPressedAcntCode() {
        //判断输入的值是否在下拉列表中
        VMComboBox vmComboBox = (VMComboBox) cmbAcntCode.getModel();
        Object obj = ( ( JTextComponent)( cmbAcntCode.getEditor().getEditorComponent() ) ).getText();
        Object selectItem = vmComboBox.findItemByName(obj);
        if (selectItem == null ||
            ! (selectItem instanceof DtoComboItem)) {
                comMSG.dispErrorDialog("The account code is not existed.");
                cmbAcntCode.setErrorField(true);
                cmbAcntCode.requestFocus();
                return;
        }
    }


    public void itemDataChangedAcntName() {
        if ( cmbAcntName.isEnabled() == true && bLockAcntNameSelected == false) {
            Long projectId = null;
            projectId = (Long) cmbAcntName.getUICValue();
            log.debug("in itemDataChangedAcntName projectId="
                      + projectId);

            this.bLockAcntCodeSelected = true;
            cmbAcntCode.setUICValue(projectId);
            this.bLockAcntCodeSelected = false;

            getActivityList(projectId);
        }
    }

    public void itemDataChangedActivityCode() {
        if ( cmbActivityCode.isEnabled() == true && this.bLockActivityCodeSelected == false) {
            //先判断输入的值是否在下拉列表中
            VMComboBox vmComboBox = (VMComboBox) cmbActivityCode.getModel();
            Object obj = cmbActivityCode.getSelectedItem();
            Object selectItem = vmComboBox.findItemByName(obj);
            if (selectItem == null ||
                !(selectItem instanceof DtoComboItem)) {

                this.bLockActivityNameSelected = true;
                this.cmbActivityName.setUICValue(null);
                this.bLockActivityNameSelected = false;
                return;
            }

            Long lId = (Long) cmbActivityCode.getUICValue();
            String activityName = getActivityList.getActivityNameById(lId);

            VMComboBox vmComboBoxCmbClnitem = (VMComboBox) cmbActivityName.getModel();
            Object objActivityName = vmComboBoxCmbClnitem.findItemByValue(
                lId);
            this.bLockActivityNameSelected = true;
            cmbActivityName.setSelectedItem(objActivityName);
            this.bLockActivityNameSelected = false;
        }
    }

    public void keyPressedActivityCode() {
        //判断输入的值是否在下拉列表中
        VMComboBox vmComboBox = (VMComboBox) cmbActivityCode.getModel();
        Object obj = ( (JTextComponent) (cmbActivityCode.getEditor().getEditorComponent())).getText(); //对于可输入的ComboBox, 求选择的item要先getselectitem， 再finditembyname
        Object selectItem = vmComboBox.findItemByName(obj);
        if (selectItem == null ||
            ! (selectItem instanceof DtoComboItem)) {
            comMSG.dispErrorDialog("The activity id is not existed.");
            cmbActivityCode.setErrorField(true);
            cmbActivityCode.requestFocus();
            return;
        }
    }

    private void getAccountList() {
        Vector[] vaAccountList = getAccountList.getAccountList();
        DtoComboItem nullElement = new DtoComboItem("", null);

        if (vaAccountList != null) {
            VMComboBox vmProjectIdList = new VMComboBox(vaAccountList[0]);
            //vmProjectIdList.addNullElement();
            vmProjectIdList.insertElementAt(nullElement,0);
            cmbAcntCode.setModel(vmProjectIdList);

            VMComboBox vmAccountNameList = new VMComboBox((vaAccountList[1]).
                                                          toArray());
            //vmAccountNameList.addNullElement();
            vmAccountNameList.insertElementAt(nullElement,0);
            cmbAcntName.setModel(vmAccountNameList);

            VMComboBox vmActivityIdList = new VMComboBox();
            //vmActivityIdList.addNullElement();
            vmActivityIdList.insertElementAt(nullElement,0);
            cmbActivityCode.setModel(vmActivityIdList);

            VMComboBox vmActivityNameList = new VMComboBox();
            //vmActivityNameList.addNullElement();
            vmActivityNameList.insertElementAt(nullElement,0);
            cmbActivityName.setModel(vmActivityNameList);

            cmbAcntCode.setUICValue(null);
        } else {
            comMSG.dispErrorDialog("Get Account List Error!!");
        }
    }

    private void getActivityList(Long lProjectId) {
        DtoComboItem nullElement = new DtoComboItem("", null);
        if ((lProjectId == null) || (lProjectId.intValue() == 0)) {
            VMComboBox vmActivityIdList = new VMComboBox();
            //vmActivityIdList.addNullElement();
            vmActivityIdList.insertElementAt(nullElement,0);
            cmbActivityCode.setModel(vmActivityIdList);

            VMComboBox vmActivityNameList = new VMComboBox();
            //vmActivityNameList.addNullElement();
            vmActivityNameList.insertElementAt(nullElement,0);
            cmbActivityName.setModel(vmActivityNameList);

            cmbActivityCode.setUICValue(null);
            return;
        }

        Vector[] vaActivityList = getActivityList.getActivityList(lProjectId
            .toString());

        if (vaActivityList != null) {
            VMComboBox vmActivityIdList = new VMComboBox(vaActivityList[0]);
            //vmActivityIdList.addNullElement();
            vmActivityIdList.insertElementAt(nullElement,0);
            cmbActivityCode.setModel(vmActivityIdList);

            VMComboBox vmActivityNameList = new VMComboBox(vaActivityList[1]);
            //vmActivityNameList.addNullElement();
            vmActivityNameList.insertElementAt(nullElement,0);
            cmbActivityName.setModel(vmActivityNameList);
        }
        cmbActivityCode.setUICValue(null);
    }

    public void itemDataChangedActivityName() {
        if ( this.bLockActivityNameSelected == false) {
            Long lId = (Long) cmbActivityName.getUICValue();
            this.bLockActivityCodeSelected = true;
            cmbActivityCode.setUICValue(lId);
            this.bLockActivityCodeSelected = false;
        }
    }

    private void setTabOrder() {
        List tabList = new ArrayList();
        tabList.add(cmbAcntCode.getEditor().getEditorComponent());
        tabList.add(cmbAcntName);

        tabList.add(cmbActivityCode.getEditor().getEditorComponent());
        tabList.add(cmbActivityName);

        tabList.add(cmbAcntCode.getEditor().getEditorComponent());
        comFORM.setTABOrder(this, tabList);
    }

    private void setEnterOrder() {
        List tabList = new ArrayList();
        tabList.add(cmbAcntCode.getEditor().getEditorComponent());
        tabList.add(cmbAcntName);

        tabList.add(cmbActivityCode.getEditor().getEditorComponent());
        tabList.add(cmbActivityName);

        tabList.add(cmbAcntCode.getEditor().getEditorComponent());
        comFORM.setEnterOrder(this, tabList);

    }

    private void setUIComponentName() {
        cmbAcntCode.setName("acntCode");
        cmbAcntName.setName("acntName");
        dspWpName.setName("wpName");
        cmbActivityCode.setName("activityCode");
        cmbActivityName.setName("activityName");
    }

    public void setParameter(Long acntRid, Long activityRid, String wpRid){
        if (acntRid == null) {
            cmbAcntCode.setUICValue(null);
            cmbAcntCode.setEnabled(true);
            cmbAcntName.setEnabled(true);
            cmbActivityCode.setEnabled(true);
            cmbActivityName.setEnabled(true);
            dspWpName.setUICValue(null);
            return;
        }
        if (checkAcntRidExist(acntRid) == false) {
            comMSG.dispErrorDialog("The account which this work item belong to is not exist.");
            cmbAcntCode.setUICValue(null);
            cmbAcntCode.setEnabled(true);
            cmbAcntName.setEnabled(true);
            cmbActivityCode.setEnabled(true);
            cmbActivityName.setEnabled(true);
            dspWpName.setUICValue(null);
            return;
        }
        cmbAcntCode.setUICValue(acntRid);
        cmbAcntCode.setEnabled(false);
        cmbAcntName.setEnabled(false);

        if (activityRid == null) {
            cmbActivityCode.setUICValue(null);
            cmbActivityCode.setEnabled(true);
            cmbActivityName.setEnabled(true);
            dspWpName.setUICValue(null);
            return;
        }

        if (checkActivityRidExist(activityRid) == false) {
            comMSG.dispErrorDialog("The activity which this work item belong to is not exist.");
            cmbActivityCode.setUICValue(null);
            cmbActivityCode.setEnabled(true);
            cmbActivityName.setEnabled(true);
            dspWpName.setUICValue(null);
            return;
        }
        cmbActivityCode.setUICValue(activityRid);
        cmbActivityCode.setEnabled(false);
        cmbActivityName.setEnabled(false);

        this.dspWpName.setUICValue(wpRid);
    }

    private boolean checkActivityRidExist(Long activityRid){
        VMComboBox vmComboBox = (VMComboBox) cmbActivityCode.getModel();
        Object item = vmComboBox.findItemByValue(activityRid);
        if (item == null ||
            ! (item instanceof DtoComboItem)) {
            return false;
        }else{
            return true;
        }
    }

    private boolean checkAcntRidExist(Long acntRid){
        VMComboBox vmComboBox = (VMComboBox) cmbAcntCode.getModel();
        Object item = vmComboBox.findItemByValue(acntRid);
        if (item == null ||
            ! (item instanceof DtoComboItem)) {
            return false;
        }else{
            return true;
        }
    }

    public Long getAcntRid(){
        return (Long)cmbAcntCode.getUICValue();
    }

    public Long getActivityRid(){
        return (Long)cmbActivityCode.getUICValue();
    }

    public String getBelongTo() {
        String wpName = StringUtil.nvl(dspWpName.getUICValue());
        if( wpName.equals("") == false ){
            return wpName;
        }

        VMComboBox vmComboBoxActivityCode = (VMComboBox)cmbActivityCode.getModel();
        VMComboBox vmComboBoxActivityName = (VMComboBox)cmbActivityName.getModel();
        Long activityRid = (Long)cmbActivityCode.getUICValue();
        if( activityRid != null ){
            DtoComboItem selObjActivityCode = (DtoComboItem)vmComboBoxActivityCode.findItemByValue(activityRid);
            DtoComboItem selObjActivityName = (DtoComboItem)vmComboBoxActivityName.findItemByValue(activityRid);

            return selObjActivityCode.getItemName() + " -- "
                + selObjActivityName.getItemName();
        }

        VMComboBox vmComboBoxAcntCode = (VMComboBox)cmbAcntCode.getModel();
        VMComboBox vmComboBoxAcntName = (VMComboBox)cmbAcntName.getModel();
        Long acntRid = (Long)cmbAcntCode.getUICValue();
        if( acntRid != null ){
            DtoComboItem selObjAcntCode = (DtoComboItem)vmComboBoxAcntCode.findItemByValue(acntRid);
            DtoComboItem selObjAcntName = (DtoComboItem)vmComboBoxAcntName.findItemByValue(acntRid);

            return selObjAcntCode.getItemName() + " -- "
                + selObjAcntName.getItemName();
        }

        return "";
    }

    public static void main(String args[]){
        VwWorkItemBelongTo w = new VwWorkItemBelongTo();
        w.getAccountList();
        TestPanel.show(new VwWorkItemBelongTo() );
    }
}
