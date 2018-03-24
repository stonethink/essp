package client.essp.timesheet.admin.common;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

import c2s.dto.*;
import client.essp.common.view.VWGeneralWorkArea;
import client.framework.view.common.comFORM;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.*;
import com.wits.util.Parameter;

/**
 * <p>Title: VwGeneralBase</p>
 *
 * <p>Description: 常用界面基类</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public abstract class VwGeneralBase extends VWGeneralWorkArea {
    protected VWJLabel lblName = new VWJLabel();
    protected VWJLabel lblEnable = new VWJLabel();
    protected VWJLabel lblDescription = new VWJLabel();
    protected VWJText txtName = new VWJText();
    protected VWJCheckBox chkEnable = new VWJCheckBox();
    protected VWJTextArea txaDescription = new VWJTextArea();
    protected boolean isParameterValid = false;
    protected JButton btnSave = null;
    protected IDto dtoData;


    public VwGeneralBase() {
        try {
            jbInit();
            setUIComponentName();
            setEnterOrder();
            setTabOrder();
            addUICEvent();
            setButtonVisible();
            setEnableMode();
        } catch (Exception ex) {
            log.error(ex);
        }
    }

    /**
     * 初始化UI
     * @throws Exception
     */
    private void jbInit() throws Exception {
        this.setLayout(null);
        this.setPreferredSize(new Dimension(700, 200));

        lblName.setText("rsid.common.name");
        lblName.setBounds(new Rectangle(35, 24, 84, 20));
        txtName.setBounds(new Rectangle(126, 24, 193, 20));
        txtName.setMaxByteLength(50);

        lblEnable.setText("rsid.common.enable");
        lblEnable.setBounds(new Rectangle(375, 24, 49, 20));
        chkEnable.setBounds(new Rectangle(430, 24, 20, 20));

        lblDescription.setText("rsid.common.description");
        lblDescription.setBounds(new Rectangle(34, 67, 84, 20));
        txaDescription.setBounds(new Rectangle(126, 67, 505, 70));

        this.add(lblName);
        this.add(txaDescription);
        this.add(txtName);
        this.add(lblDescription);
        this.add(lblEnable);
        this.add(chkEnable);
        txaDescription.setMaxByteLength(100);
    }

    /**
     * 设置控件名称
     */
    private void setUIComponentName() {
        txtName.setName("name");
        chkEnable.setName("isEnable");
        txaDescription.setName("description");
    }

    /**
     * 设置Tab键跳转顺序
     */
    private void setTabOrder() {
        List compList = new ArrayList();
        compList.add(txtName);
        compList.add(chkEnable);
        compList.add(txaDescription);
        comFORM.setTABOrder(this, compList);
    }

    /**
     * 设置Enter键跳转顺序
     */
    private void setEnterOrder() {
        List compList = new ArrayList();
        compList.add(txtName);
        compList.add(chkEnable);
        compList.add(txaDescription);
        comFORM.setEnterOrder(this, compList);
    }

    /**
     * 捕获事件
     */
    private void addUICEvent() {
        //保存
        btnSave = this.getButtonPanel().addSaveButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedSave();
            }
        });
        btnSave.setToolTipText("rsid.common.save");
    }

    /**
     * 刷新画面
     */
    protected void resetUI() {
        VWUtil.bindDto2UI(dtoData, this);

        setButtonVisible();
        setEnableMode();

        comFORM.setFocus(this.txtName);
    }

    /**
     * 设置控件可用性
     */
    private void setEnableMode() {
        if (this.isParameterValid == true) {
            VWUtil.setUIEnabled(this, true);
        } else {
            VWUtil.setUIEnabled(this, false);
        }
    }

    /**
     * 设置Button可见性
     */
    private void setButtonVisible() {
        if (this.isParameterValid == true) {
            btnSave.setVisible(true);
        } else {
            btnSave.setVisible(false);
        }
    }

    /**
     * 保存事件处理
     */
    private void actionPerformedSave() {
        if (validateData() == true) {
            saveData();
            this.fireDataChanged();
        }
    }

//    /**
//     * 保存WorkArea
//     */
//    public void saveWorkArea() {
//        actionPerformedSave();
//    }

    /**
     * 校验数据
     * @return boolean
     */
    public boolean validateData() {
        String name = this.txtName.getUICValue().toString();
        if (name.trim().equals("") == true && isParameterValid) {
            comMSG.dispErrorDialog("error.client.VwCodeTypeGeneral.NameReq");
            comFORM.setFocus(txtName);
            txtName.setErrorField(true);
            return false;
        } else {
            txtName.setErrorField(false);
        }
        return true;
    }

    /**
     * 传入参数
     * @param param Parameter
     */
    public void setParameter(Parameter param) {
        super.setParameter(param);
        dtoData = (IDto) param.get(getDtoKey());

        if (dtoData == null) {
            isParameterValid = false;
        } else {
            isParameterValid = true;
        }
    }

    /**
     * 给Enable check box填加事件兼听
     * @param l ActionListener
     */
    public void addEnableCheckBoxListener(ActionListener l) {
        this.chkEnable.addActionListener(l);
    }

    /**
     * 保存当前数据
     */
    protected void saveData() {
        VWUtil.convertUI2Dto(dtoData, this);
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(getSaveActionId());
        inputInfo.setInputObj(getDtoKey(), dtoData);

        ReturnInfo returnInfo = accessData(inputInfo);
        if (!returnInfo.isError()) {
            IDto dto = (IDto) returnInfo.getReturnObj(getDtoKey());
            dto.setOp(IDto.OP_NOCHANGE);
            DtoUtil.copyBeanToBean(dtoData, dto);
            VWUtil.bindDto2UI(dtoData, this);
            comFORM.setFocus(this.txtName);
            comMSG.dispMessageDialog("rsid.common.saveComplete");
        }
    }

    /**
     * 返回保存数据的ActionId
     * @return String
     */
    protected abstract String getSaveActionId();

    /**
     * 返回传递数据的Dto key
     * @return String
     */
    protected abstract String getDtoKey();

}
