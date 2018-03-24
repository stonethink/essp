package client.essp.pms.wbs.earnedvalue;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.math.BigDecimal;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;

import c2s.dto.IDto;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.pms.wbs.DtoKEY;
import c2s.essp.pms.wbs.DtoWbsActivity;
import client.essp.common.view.VWGeneralWorkArea;
import client.essp.common.view.VWWorkArea;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.VWJLabel;
import client.framework.view.vwcomp.VWJRadioButton;
import client.framework.view.vwcomp.VWJReal;
import client.framework.view.vwcomp.VWUtil;
import com.wits.util.Parameter;
import com.wits.util.TestPanel;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class VwEarnedValue extends VWGeneralWorkArea {

    public static final String ACTIONID_EARNEDVALUE_SAVE = "FWbsUpdateAction";

    private DtoWbsActivity wbs;

    VWJLabel lbHead = new VWJLabel();
    VWJRadioButton radioActivity = new VWJRadioButton();
    VWJRadioButton radioCheckPoint = new VWJRadioButton();
    VWJRadioButton radioCustom = new VWJRadioButton();
    ButtonGroup buttonGroup1 = new ButtonGroup();
    JButton saveBt ;
    VWJReal inputCustom = new VWJReal();
    JLabel jLabel1 = new JLabel();

    private boolean isSaveOk = true;
    private boolean isParameterValid = true;
    public VwEarnedValue() {
        super();
        try {
            jbInit();
            setUIComponentName();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        addUICEvent();
    }


    public void jbInit() throws Exception {
        this.setLayout(null);
        lbHead.setText("Technique for computing performance percent complete");
        lbHead.setBounds(new Rectangle(30, 25, 388, 15));
        radioCustom.setBounds(new Rectangle(25, 135, 215, 25));
        radioCheckPoint.setBounds(new Rectangle(25, 95, 215, 25));
        radioActivity.setBounds(new Rectangle(25, 55, 215, 25));

        radioActivity.setText("Active Percent");
        radioCheckPoint.setText("WBS CheckPoint percent complete");
        radioCustom.setText("Custom percent complete");

        inputCustom.setBounds(new Rectangle(250, 135, 140, 25));
        inputCustom.setMaxInputIntegerDigit(3);
        inputCustom.setMaxInputDecimalDigit(2);
        jLabel1.setText("%");
        jLabel1.setBounds(new Rectangle(390, 132, 22, 28));
        buttonGroup1.add(radioActivity);
        buttonGroup1.add(radioCheckPoint);
        buttonGroup1.add(radioCustom);

        this.add(radioCustom);
        this.add(inputCustom);
        this.add(radioCheckPoint);
        this.add(radioActivity);
        this.add(lbHead);
        this.add(jLabel1);
    }

    public void addUICEvent() {
        radioActivity.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                chooseActivity();
            }
        });
        radioCheckPoint.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                chooseMilestone();
            }
        });
        radioCustom.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                chooseCustom();
            }
        });
        saveBt = this.getButtonPanel().addSaveButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(wbs == null || wbs.isReadonly())
                    return;
                actionPerformedSave();
            }
        });

    }
    private void setUIComponentName() {
        inputCustom.setName("completeRate");
    }
    //检查输入值是否合法
    public boolean checkCustomPercent() {
        if (radioCustom.isSelected()){
            BigDecimal b = (BigDecimal) inputCustom.getUICValue();
            double value = b.doubleValue();
            if (value > 100 || value < 0 ) {
                comMSG.dispErrorDialog("The percent must be between 0 and 100");
                inputCustom.setUICValue("0");
                return false;
            }
        }
        return true;
    }

    /**
     * 保存事件
     */
    public void actionPerformedSave() {
        if(checkModified() && checkCustomPercent() ){
            saveData();
        }
    }
    private boolean saveData() {
        if (wbs == null) {
            return true;
        }
        if (radioActivity.isSelected()) {
            wbs.setCompleteMethod(DtoWbsActivity.WBS_COMPLETE_BY_ACTIVITY);
        } else if (radioCheckPoint.isSelected()) {
            wbs.setCompleteMethod(DtoWbsActivity.WBS_COMPLETE_BY_CHECKPOINT);
        } else if (radioCustom.isSelected()) {
            wbs.setCompleteMethod(DtoWbsActivity.WBS_COMPLETE_BY_MANUAL);
        }
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.ACTIONID_EARNEDVALUE_SAVE);
        inputInfo.setInputObj(DtoKEY.DTO, wbs);
        ReturnInfo returnInfo = accessData(inputInfo);
        wbs.setOp(IDto.OP_NOCHANGE);
        return !returnInfo.isError();
    }
    private boolean checkModified() {
        VWUtil.convertUI2Dto(wbs,this);
        if (wbs.isDelete() == true) {
            return false;
        } else {
            return wbs.isChanged();
        }
    }
    public void saveWorkArea() {
        if (checkModified()){
            isSaveOk = false;
            if(confirmSaveWorkArea()){
               if(checkCustomPercent()){
                   isSaveOk = saveData();
               }
            }else{
                isSaveOk = true;
            }
        }else{
            isSaveOk = true;
        }
    }
    public boolean isSaveOk(){
        return this.isSaveOk;
    }
    /**
     * 选择Activity事件
     */
    public void chooseActivity() {
        radioActivity.setEnabled(true);
        radioCheckPoint.setEnabled(true);
        radioCustom.setEnabled(true);

        radioActivity.setSelected(true);

        inputCustom.setEnabled(false);
        wbs.setOp(IDto.OP_MODIFY);
    }

    /**
     * 选择Milestone事件
     */
    public void chooseMilestone() {
        radioActivity.setEnabled(true);
        radioCheckPoint.setEnabled(true);
        radioCustom.setEnabled(true);

        radioCheckPoint.setSelected(true);

        inputCustom.setEnabled(false);
        wbs.setOp(IDto.OP_MODIFY);
    }

    /**
     * 选择custom事件
     */
    public void chooseCustom() {
        radioActivity.setEnabled(true);
        radioCheckPoint.setEnabled(true);
        radioCustom.setEnabled(true);

        radioCustom.setSelected(true);

        inputCustom.setEnabled(true);
        wbs.setOp(IDto.OP_MODIFY);
    }

    public void resetUI() {
        setButtonVisible();
        if (wbs != null) {
            initUI();
            if (wbs.isReadonly()) {
                disableUI();
            }
        } else {
            disableUI();
        }
    }

    /**
     * 界面不可用
     */
    private void disableUI() {
        radioActivity.setEnabled(false);
        radioCheckPoint.setEnabled(false);
        radioCustom.setEnabled(false);
        inputCustom.setEnabled(false);
    }

    /**
     * 初始界面，默认选中第一个Radio
     */
    private void initUI() {
        radioActivity.setEnabled(true);
        radioCheckPoint.setEnabled(true);
        radioCustom.setEnabled(true);
        Double defaultCompleteRate = wbs.getCompleteRate();
        if(defaultCompleteRate == null)
            defaultCompleteRate = new Double("0");
        inputCustom.setUICValue(defaultCompleteRate);
        saveBt.setEnabled(true);
        if (DtoWbsActivity.WBS_COMPLETE_BY_CHECKPOINT.equals(wbs.
            getCompleteMethod())) {
            radioCheckPoint.setSelected(true);
            inputCustom.setEnabled(false);
        } else if (DtoWbsActivity.WBS_COMPLETE_BY_MANUAL.equals(wbs.
            getCompleteMethod())) {
            radioCustom.setSelected(true);
            inputCustom.setEnabled(true);
            VWUtil.bindDto2UI(wbs,this);
        } else if (DtoWbsActivity.WBS_COMPLETE_BY_ACTIVITY.equals(wbs.
            getCompleteMethod())){
            radioActivity.setSelected(true);
            inputCustom.setEnabled(false);
        }else{
            radioActivity.setSelected(true);
            inputCustom.setEnabled(false);
            wbs.setCompleteMethod(DtoWbsActivity.WBS_COMPLETE_BY_ACTIVITY);
            InputInfo inputInfo = new InputInfo();
            inputInfo.setActionId(this.ACTIONID_EARNEDVALUE_SAVE);
            inputInfo.setInputObj(DtoKEY.DTO, wbs);
            ReturnInfo returnInfo = accessData(inputInfo);
        }
    }

    public void setParameter(Parameter param) {
        super.setParameter(param);
        wbs = (DtoWbsActivity) param.get("wbs");
        if(wbs == null || wbs.isReadonly())
            isParameterValid = false;
        else
            isParameterValid = true;
    }
    private void setButtonVisible(){
        this.getButtonPanel().setVisible(isParameterValid);
    }
    public static void main(String[] args) {

        VWWorkArea w1 = new VWWorkArea();
        VwEarnedValue vwearnedvaluebase = new VwEarnedValue();

        Parameter param = new Parameter();
        DtoWbsActivity wbs = new DtoWbsActivity();
        wbs.setAcntRid(new Long(1));
        wbs.setWbsRid(new Long(3));
        wbs.setCompleteMethod(DtoWbsActivity.WBS_COMPLETE_BY_MANUAL);
        wbs.setCompleteRate(new Double(10.5));
        //wbs.setReadonly(true);
        wbs.setReadonly(false);
        param.put("wbs", wbs);
        vwearnedvaluebase.setParameter(param);

        w1.addTab("EarnedValue", vwearnedvaluebase);
        TestPanel.show(w1);
        vwearnedvaluebase.resetUI();
    }
}
