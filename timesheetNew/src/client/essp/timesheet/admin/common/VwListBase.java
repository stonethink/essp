package client.essp.timesheet.admin.common;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import c2s.dto.IDto;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import client.essp.common.view.VWTableWorkArea;
import client.framework.common.Constant;
import client.framework.model.VMColumnConfig;
import client.framework.view.common.comMSG;
import client.framework.view.event.RowSelectedListener;
import client.framework.view.vwcomp.VWJCheckBox;
import client.framework.view.vwcomp.VWJText;
import com.wits.util.Parameter;
import c2s.dto.DtoBase;
import c2s.essp.timesheet.code.DtoCodeType;

/**
 * <p>Title: VwListBase</p>
 *
 * <p>Description: �б����</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public abstract class VwListBase extends VWTableWorkArea {
    private List dataList;
    private JButton btnDel;
    private JButton btnDown;
    private JButton btnUp;
    private JButton btnAdd;
    private JButton btnRef;
    protected boolean isLeaveType = false;
    public VwListBase() {
        try {
            jbInit();
        } catch (Exception e) {
            log.error(e);
        }
        addUICEvent();
    }

    private void jbInit() throws Exception {
        Object[][] configs = new Object[][] { {"rsid.common.name", "name",
                             VMColumnConfig.UNEDITABLE, new VWJText(),
                             Boolean.FALSE}, {"rsid.common.description",
                             "description",
                             VMColumnConfig.UNEDITABLE, new VWJText(),
                             Boolean.FALSE}, {"rsid.common.enable", "isEnable",
                             VMColumnConfig.UNEDITABLE, new VWJCheckBox(),
                             Boolean.FALSE}
        };
        super.jbInit(configs, DtoBase.class);
    }

    /**
     * �¼�����
     */
    private void addUICEvent() {

        //Add
        btnAdd = this.getButtonPanel().addAddButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedAdd();
            }
        });
        btnAdd.setToolTipText("rsid.common.add");

        //Delete
        btnDel = this.getButtonPanel().addDelButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedDel();
            }
        });
        btnDel.setToolTipText("rsid.common.delete");
        
        //Up
        btnUp = this.getButtonPanel().addButton("up.png");
        btnUp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedUp();
            }
        });
        btnUp.setToolTipText("rsid.common.moveUp");

        //Down
        btnDown = this.getButtonPanel().addButton("down.png");
        btnDown.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedDown();
            }
        });
        btnDown.setToolTipText("rsid.common.moveDown");

        //Load
        btnRef = this.getButtonPanel().addLoadButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetUI();
            }
        });
        btnRef.setToolTipText("rsid.common.refresh");
        
        this.getTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(e.getValueIsAdjusting() == false) {
					actionPerformedBtnStatus();
				}
            }
        });
    }

    /**
     * ����
     */
    private void actionPerformedAdd() {
        //�ӵ����һ��
        this.getTable().setSelectRow(this.getTable().getRowCount() - 1);
        this.getTable().addRow(getNewDto());
    }


    /**
     * ɾ��
     */
    private void actionPerformedDel() {
        int option = comMSG.dispConfirmDialog(
                "error.client.common.deleteRecord");
        if (option != Constant.OK) {
            return;
        }

        IDto data = (IDto)this.getSelectedData();
        //����CodeValueֱ��ɾ��
        if (data.isInsert()) {
            this.getTable().deleteRow();
            return;
        }

        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(getDeleteActionId());
        inputInfo.setInputObj(getDtoKey(), data);
        ReturnInfo returnInfo = this.accessData(inputInfo);
        if (!returnInfo.isError()) {
            this.getTable().deleteRow();
        }
    }

    /**
     * ���ð�ť״̬
     */
    protected void actionPerformedBtnStatus() {
        int selectRow = getTable().getSelectedRow();
        if (selectRow < 0) {
            btnUp.setEnabled(false);
            btnDown.setEnabled(false);
            btnDel.setEnabled(false);
            return;
        }

        btnDel.setEnabled(true);

        int maxRow = getTable().getRowCount() - 1;
        if (selectRow == 0) {
            btnUp.setEnabled(false);
        } else {
            btnUp.setEnabled(true);
        }

        if (selectRow == maxRow) {
            btnDown.setEnabled(false);
        } else {
            btnDown.setEnabled(true);
        }

        //�����һ��Ϊ�����Ľڵ㣬����������
        IDto downDto = (IDto)this.getModel().getRow(selectRow + 1);
        if (downDto != null && downDto.isInsert()) {
            btnDown.setEnabled(false);
        }

        //�����һ��Ϊ�����Ľڵ㣬����������
        IDto upDto = (IDto)this.getModel().getRow(selectRow - 1);
        if (upDto != null && upDto.isInsert()) {
            btnUp.setEnabled(false);
        }

        //�����Ľڵ㲻�����ƶ�
        IDto dto = (IDto)this.getSelectedData();
        if (dto != null && dto.isInsert()) {
            btnUp.setEnabled(false);
            btnDown.setEnabled(false);
        }

    }

    /**
     * ˢ��Table���ݣ���Button״̬
     */
    public void refeshTable() {
        this.getTable().refreshTable();
        actionPerformedBtnStatus();
    }

    /**
     * ˢ�½���
     */
    protected void resetUI() {
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(getListActionId());
        inputInfo.setInputObj(DtoCodeType.DTO_IS_LEAVE_TYPE, isLeaveType);
        ReturnInfo returnInfo = this.accessData(inputInfo);
        if (returnInfo.isError()) {
            return;
        }
        dataList = (List) returnInfo.getReturnObj(getListKey());
        this.getTable().setRows(dataList);
    }

    /**
     * ����ˢ��
     */
    public void setParameter(Parameter param) {
        super.setParameter(param);
    }

    /**
     * ����
     */
    protected abstract void actionPerformedDown();

    /**
     * ����
     */
    protected abstract void actionPerformedUp();

    /**
     * ��ȡһ�������ݡ�
     * @return IDto
     */
    protected abstract IDto getNewDto();

    /**
     * ��ȡɾ�����ݵ�ActionId
     * @return String
     */
    protected abstract String getDeleteActionId();

    /**
     * ��ȡ�����б��ActionId
     * @return String
     */
    protected abstract String getListActionId();

    /**
     * ��ȡ����Dto��Key
     * @return String
     */
    protected abstract String getDtoKey();

    /**
     * ��ȡ����List�� Key
     * @return String
     */
    protected abstract String getListKey();

}
