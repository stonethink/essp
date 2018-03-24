package client.essp.timesheet.approval;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import c2s.dto.DtoComboItem;
import c2s.essp.timesheet.approval.DtoTsApproval;
import client.essp.common.loginId.VWJLoginId;
import client.essp.common.view.VWTableWorkArea;
import client.framework.common.Constant;
import client.framework.model.VMColumnConfig;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.*;

import com.wits.util.Parameter;
import client.essp.timesheet.weeklyreport.VWJTimesheetsNotes;

import javax.swing.JButton;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

/**
 * <p>Title: VwAbstractApprovalList</p>
 *
 * <p>Description: �����б���������</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public abstract class VwAbstractApprovalList extends VWTableWorkArea implements
        AccountOrPeriodChangeListener, IVWPopupEditorEvent{

    private VWJButton btnApproval = new VWJButton();
    private VWJButton btnReject = new VWJButton();
    private JButton btnRef = new VWJButton();
    private VWJTimesheetsNotes btnNotes = new VWJTimesheetsNotes();
    private VwRejectPanel vwRjectPanel = null;
    private Long acntRid;
    private Date begin;
    private Date end;
    private String queryWay;
    public void setQueryWay(String queryWay) {
		this.queryWay = queryWay;
	}

	public VwAbstractApprovalList() {
        try {
            jbInit();
        } catch (Exception e) {
            log.error(e);
        }
        addUICEvent();

    }

    /**
     * ��ʼ��UI
     * @throws Exception
     */
    protected void jbInit() throws Exception {
        Object[][] configs = new Object[][] { {"rsid.common.user", "loginId",
                             VMColumnConfig.UNEDITABLE, new VWJLoginId(),
                             Boolean.FALSE},{"rsid.timesheet.startDate", "startDate",
                             VMColumnConfig.UNEDITABLE, new VWJDate(),
                             Boolean.FALSE}, {"rsid.timesheet.finishDate", "finishDate",
                             VMColumnConfig.UNEDITABLE, new VWJDate(),
                             Boolean.FALSE}, {"rsid.timesheet.standardHours", "standarHours",
                             VMColumnConfig.UNEDITABLE, new VWJReal(),
                             Boolean.FALSE}, {"rsid.timesheet.actualHours", "actualHours",
                             VMColumnConfig.UNEDITABLE, new VWJReal(),
                             Boolean.FALSE}, {"rsid.timesheet.overtimeHours", "overtimeHours",
                             VMColumnConfig.UNEDITABLE, new VWJReal(),
                             Boolean.FALSE}, {"rsid.timesheet.leaveHours", "leaveHours",
                             VMColumnConfig.UNEDITABLE, new VWJReal(),
                             Boolean.FALSE}, {"rsid.common.status", "statusName",
                             VMColumnConfig.UNEDITABLE, new VWJText(),
                             Boolean.FALSE}
        };
        super.jbInit(configs, DtoTsApproval.class);
//      ������
        this.getTable().setSortable(true);
    }

    /**
     * �¼�����
     */
    private void addUICEvent() {
        getTable().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        this.getButtonPanel().addButton(btnNotes);
        btnNotes.setToolTipText("rsid.common.note");
        btnApproval.setText("rsid.common.approval");
        this.getButtonPanel().addButton(btnApproval);
        btnApproval.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                processApproval();
            }
        });
        btnApproval.setEnabled(false);
        btnApproval.setToolTipText("rsid.common.approval");

        btnReject.setText("rsid.common.reject");
        this.getButtonPanel().addButton(btnReject);
        btnReject.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	processRejectPre();
            }
        });
        btnReject.setEnabled(false);
        btnReject.setToolTipText("rsid.common.reject");
        
        btnRef.setText("rsid.timesheet.query");
        this.getButtonPanel().addButton(btnRef);
        btnRef.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e) {
                resetUI();
            }
        });
        btnRef.setToolTipText("rsid.timesheet.query");
        
        this.getTable().getSelectionModel().addListSelectionListener (new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
            	if(e.getValueIsAdjusting()) {
            		return;
            	}
                if (getTable().getSelectedRowCount() < 1) {
                  btnNotes.setTsRid(null);
                  btnNotes.setEnabled(false);
                  btnApproval.setEnabled(false);
                  btnReject.setEnabled(false);
              } else {
                  setButtonStatus();
                  processSelectRow();
              }
            }
        });

    }
    /**
     * ����ѡ�м�¼�Ŀ��÷������ð�ť�Ŀ���״̬
     * ���ѡ�м�¼���пɲ�����¼��ť��ʾΪ���ã�����Ϊ������
     */
    private void setButtonStatus(){
        List canApprovalList = getCanApprovalList(getSelectedRowDataList());
        List canRejectList = getCanRejectList(getSelectedRowDataList());
        if(canApprovalList != null && canApprovalList.size() > 0) {
            btnApproval.setEnabled(true);
        } else {
            btnApproval.setEnabled(false);
        }
        if(canRejectList != null && canRejectList.size() >0) {
            btnReject.setEnabled(true);
        } else {
            btnReject.setEnabled(false);
        }
        if (getTable().getSelectedRowCount() < 1) {
        	btnNotes.setEnabled(false);
        } else {
        	btnNotes.setEnabled(true);
        }
    }
    /**
     * ѡ���¼ʱ����һ����¼��tsRid����Notes�ؼ���
     */
    private void processSelectRow() {
        List selectedRows = getSelectedRowDataList();
        DtoTsApproval dto = (DtoTsApproval)selectedRows.get(0);
        btnNotes.setTsRid(dto.getTsRid());
    }
    /**
     * ִ��ȷ�϶�������ˢ�´�ȷ�Ϲ�ʱ������
     */
    private void processApproval() {
        if (actionPerformedApproval(
                getCanApprovalList(getSelectedRowDataList()))) {
            resetUI();
        }
    }
    private void processRejectPre() {
    	vwRjectPanel = new VwRejectPanel();
    	vwRjectPanel.setPreferredSize(new Dimension(310, 100));
    	//show
        VWJPopupEditor popup = new VWJPopupEditor(this.getParentWindow(),"",
        		vwRjectPanel, this);
        int result = popup.showConfirm();
    }
    /**
     * ִ�в��ض�������ˢ�´�ȷ�Ϲ�ʱ������
     */
    private void processReject(String reason) {
//    	int option = comMSG
//				.dispConfirmDialog("rsid.timesheet.VwAbstractApprovalList.rejectTimesheet");
//		if (option != Constant.OK) {
//			return;
//		}
        if (actionPerformedReject(
               getCanRejectList(getSelectedRowDataList()), reason)) {
            resetUI();
        }
    }

    /**
     * ��ȡ��ǰѡ�е���������
     * @return List
     */
    private List getSelectedRowDataList() {
        List rowList = new ArrayList();
        int[] rows = this.getTable().getSelectedRows();
        for (int row : rows) {
            rowList.add(this.getModel().getRow(row));
        }
        return rowList;
    }

    /**
     * ���ò���
     */
    public void setParameter(Parameter param) {
        super.setParameter(param);
        acntRid = (Long) param.get("acntRid");
        begin = (Date) param.get("begin");
        end = (Date) param.get("end");
        queryWay = (String)param.get("queryWay");
    }

    /**
     * ˢ��UI
     */
    protected void resetUI() {
    	if(acntRid == null) {
    		comMSG.dispMessageDialog("rsid.common.select.project");
    		setButtonStatus();
    		return;
    	}
        List tsList = loadTsList(acntRid,
                                 begin, end, queryWay);
        this.getTable().setRows(tsList);
        setButtonStatus();
    }
    /**
     * ��ѯѡ��ļ�¼�Ƿ���Ա����
     * @param selectedList List
     * @return List
     */
    protected abstract List<DtoTsApproval> getCanApprovalList(List<DtoTsApproval> selectedList);
    /**
     * ��ѯѡ��ļ�¼�Ƿ���Ա��ܾ�
     * @param selectedList List
     * @return List
     */
    protected abstract List<DtoTsApproval> getCanRejectList(List<DtoTsApproval> selectedList);
    /**
     * ���ش�ȷ�ϵĹ�ʱ��
     * @return List
     */
    protected abstract List loadTsList(Long accountRid, Date begin, Date end, String queryWay);

    /**
     * ���ص�ǰ�û�Ϊ���ܵ�Project��Dept
     * @return Vector<DtoComboItem>
     */
    protected abstract Vector<DtoComboItem> loadAccounts();


    /**
     * ȷ�Ϲ�ʱ��
     * @param selectedRowDataList List
     * @return boolean
     */
    protected abstract boolean actionPerformedApproval(List selectedRowDataList);

    /**
     * ���ع�ʱ��
     * @param selectedRowDataList List
     * @return boolean
     */
    protected abstract boolean actionPerformedReject(List selectedRowDataList, String reason);

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;
    }
    public boolean onClickCancel(ActionEvent e) {
		return true;
	}
	public boolean onClickOK(ActionEvent e) {
		String reason = vwRjectPanel.getReason();
		processReject(reason);
		return true;
	}

}
