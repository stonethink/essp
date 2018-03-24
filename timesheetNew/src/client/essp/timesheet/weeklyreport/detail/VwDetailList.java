package client.essp.timesheet.weeklyreport.detail;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellEditor;

import c2s.dto.DtoUtil;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheet;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheetDay;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheetDetail;
import c2s.essp.timesheet.workscope.DtoWorkScopeDrag;
import client.essp.common.view.VWTableWorkArea;
import client.essp.common.view.ui.MessageUtil;
import client.essp.timesheet.ActivityChangedListener;
import client.essp.timesheet.weeklyreport.VWJTimesheetsNotes;
import client.essp.timesheet.weeklyreport.common.ColumnNumListener;
import client.essp.timesheet.weeklyreport.common.ColumnWithListener;
import client.essp.timesheet.weeklyreport.common.ResetRenderListener;
import client.essp.timesheet.weeklyreport.common.StandarHoursListener;
import client.essp.timesheet.weeklyreport.common.SubmitTimeSheetListener;
import client.essp.timesheet.weeklyreport.common.WorkHourListener;
import client.framework.common.Constant;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.VWDropTarget;
import client.framework.view.vwcomp.VWJLabel;
import client.framework.view.vwcomp.VWJPopupEditor;

import com.wits.util.Parameter;
import com.wits.util.comDate;

/**
 *
 * <p>Title: VwDetailList</p>
 *
 * <p>Description: ��ʱ����ϸ���棬�̳�VWTableWorkArea</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class VwDetailList extends VWTableWorkArea {

    private final static String actionId_Delete = "FTSDeleteTimeSheetDetail";
    private final static String actionId_Save = "FTSSaveTimeSheet";
    private final static String actionId_Submit = "FTSSubmitTimeSheet";
    private final static String actionId_Init = "FTSInitTimeSheet";
    private final static String actionId_Copy = "FTSCopyLastTimeSheet";

    private List<ActivityChangedListener> activityChangedListeners
            = new ArrayList<ActivityChangedListener>();
    private List<SubmitTimeSheetListener> submitTimeSheetListeners
            = new ArrayList<SubmitTimeSheetListener>();

    private VMTableDetail myModel = new VMTableDetail(new Object[0][0]);
    private VwDetailJTable myTable = new VwDetailJTable(myModel) ;
    private DtoTimeSheet dtoTimeSheet;
    private VWJLabel lblPeriod = new VWJLabel();
    private VWJLabel lblStatus = new VWJLabel();
    private VWJLabel lblReason = new VWJLabel();
    private VWJTimesheetsNotes btnNotes;
    private JButton btnDelete;
    private JButton btnSave;
    private JButton btnSubmit;
    private JButton btnGenTs;
    private JButton btnCopyTs;

    public VwDetailList() {
        try {
            jbInit();
        } catch (Exception e) {
            log.error(e);
        }
        addUICEvent();
    }

    /**
     * Component��ʼ��,UI����
     */
    private void jbInit() throws Exception {
        //Table��List(Tree)���ݰ�����
        model = myModel;
        model.setDtoType(DtoTimeSheetDetail.class);
        table = myTable;
        //�������ƶ���
        table.getTableHeader().setReorderingAllowed(false);
        this.add(table.getScrollPane(), null);
        
        lblReason.setForeground(Color.red);
    }


    /**
     * ��������¼�
     */
    private void addUICEvent() {
        this.getButtonPanel().add(lblPeriod);
        this.getButtonPanel().add(lblStatus);
        this.getButtonPanel().add(lblReason);
        
        btnNotes = new VWJTimesheetsNotes();
        this.getButtonPanel().addButton(btnNotes);
        btnNotes.setToolTipText("rsid.common.note");
        
        btnGenTs = this.getButtonPanel().addButton("init.gif");
        btnGenTs.setToolTipText("rsid.timesheet.initFromDailyReport");
        btnGenTs.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				processInit();
			}
        });
        
        btnCopyTs = this.getButtonPanel().addButton("copy.png");
        btnCopyTs.setToolTipText("rsid.timesheet.copyLastTs");
        btnCopyTs.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				processCopy();
			}
        });
        
        btnDelete = this.getButtonPanel().addDelButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                processDelete();
            }
        });
        btnDelete.setToolTipText("rsid.common.delete");
        
        btnSave = this.getButtonPanel().addSaveButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                processSave();
            }
        });
        btnSave.setToolTipText("rsid.common.save");

        btnSubmit = this.getButtonPanel().addButton("lock_unopen.png");
        btnSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                processSubmit();
            }
        });
        btnSubmit.setToolTipText("rsid.common.lock");

        //����btnDelete�Ƿ����
        myTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(e.getValueIsAdjusting() == false) {
					processSelectedRowChanged();
				}
			}
        });

        //ΪTable��WorkArea��ע����Ϸš��ŵ�Table��Ϊ�޸ģ��ŵ�WorkAreaΪ����
        new VwTsDetailDropTarget(myTable);
        new VwTsDetailDropTarget(this);
    }
    
    private void processInit() {
    	 int iRet = comMSG.dispConfirmDialog("rsid.timesheet.VwDetailList.initTimesheet");
         if(iRet != Constant.OK) {
        	 return;
         }
        DtoTimeSheet bakDto = new DtoTimeSheet();
        DtoUtil.copyBeanToBean(bakDto, dtoTimeSheet);
    	InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(actionId_Init);
        inputInfo.setInputObj(DtoTimeSheet.DTO, dtoTimeSheet);
        ReturnInfo returnInfo = this.accessData(inputInfo);
        
        if(returnInfo.isError() == false) {
        	bakDto = (DtoTimeSheet) returnInfo.getReturnObj(DtoTimeSheet.DTO);
        	myTable.setRows(bakDto.getTsDetails());
        }
        DtoUtil.copyBeanToBean(dtoTimeSheet, bakDto);
        dtoTimeSheet.setTsDetails(null);
    }
    
    private void processCopy() {
    	if(dtoTimeSheet == null) {
    		return;
    	}
   	 	int iRet = comMSG.dispConfirmDialog("rsid.timesheet.VwDetailList.initTimesheet");
        if(iRet != Constant.OK) {
       	 return;
        }
       DtoTimeSheet bakDto = new DtoTimeSheet();
       DtoUtil.copyBeanToBean(bakDto, dtoTimeSheet);
   	   InputInfo inputInfo = new InputInfo();
       inputInfo.setActionId(actionId_Copy);
       inputInfo.setInputObj(DtoTimeSheet.DTO, dtoTimeSheet);
       ReturnInfo returnInfo = this.accessData(inputInfo);
       
       if(returnInfo.isError() == false) {
	       	bakDto = (DtoTimeSheet) returnInfo.getReturnObj(DtoTimeSheet.DTO);
	       	myTable.setRows(bakDto.getTsDetails());
       }
       DtoUtil.copyBeanToBean(dtoTimeSheet, bakDto);
   }
    
    /**
     * ִ��ɾ����ǰ�еĲ���
     */
    private void processDelete() {
        int iRet = comMSG.dispConfirmDialog("rsid.timesheet.VwDetailList.deleteConfirm");
        if(iRet == Constant.OK) {
        	stopCellEditing();
        	//�޸�ģʽɾ��ʱ��ֱ����Ч
        	if(DtoTimeSheet.MODEL_MODIFY.equals(dtoTimeSheet.getUiModel())) {
        		myTable.deleteRow();
        		return;
        	}
            DtoTimeSheetDetail dto = (DtoTimeSheetDetail) this.getSelectedData();
            if(dto == null || dto.isInsert()) {
                myTable.deleteRow();
                return;
            }
            InputInfo inputInfo = new InputInfo();
            inputInfo.setActionId(actionId_Delete);
            inputInfo.setInputObj(DtoTimeSheetDetail.DTO, dto);
            ReturnInfo returnInfo = this.accessData(inputInfo);
            if(!returnInfo.isError()) {
                myTable.deleteRow();
            }
        }
    }

    /**
     * ִ�б���TimeSheet
     */
    private void processSave() {
    	stopCellEditing();
    	if(DtoTimeSheet.MODEL_MODIFY.equals(dtoTimeSheet.getUiModel())) {
    		if(submitCheck() == false) return;
            if(fireSubmitTimeSheet() == false) return;
    	}
        DtoTimeSheet bakDto = new DtoTimeSheet();
        DtoUtil.copyBeanToBean(bakDto, dtoTimeSheet);
        dtoTimeSheet.setTsDetails(myModel.getRows());
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(actionId_Save);
        inputInfo.setInputObj(DtoTimeSheet.DTO, dtoTimeSheet);
        ReturnInfo returnInfo = this.accessData(inputInfo);
        if (!returnInfo.isError()) {
            bakDto = (DtoTimeSheet) returnInfo.getReturnObj(DtoTimeSheet.DTO);
            comMSG.dispMessageDialog("rsid.common.saveComplete");
        }
        DtoUtil.copyBeanToBean(dtoTimeSheet, bakDto);
        resetUI();
    }
    
    /**
     * ǿ�����ڱ༭��Cell����
     *
     */
    public void stopCellEditing() {
    	int column = this.getTable().getSelectedColumn();
    	int row = this.getTable().getSelectedRow();
    	if(column < 0 || row < 0) {
    		return;
    	}
    	TableCellEditor editor = this.getTable().getCellEditor(row, column);
    	if(editor != null && getTable().isEditing()) {
    		try {
    			editor.stopCellEditing();
    		} catch (Exception e){
    			//��editorû�д��ڼ���״̬
    		}
    	}
    	this.getTable().setSelectRow(row);
    }

    /**
     * �ύ��ʱ��
     */
    private void processSubmit() {
    	stopCellEditing();
    	if(submitCheck() == false) return;
        if(fireSubmitTimeSheet() == false) return;
        
        DtoTimeSheet bakDto = new DtoTimeSheet();
        DtoUtil.copyBeanToBean(bakDto, dtoTimeSheet);
        dtoTimeSheet.setTsDetails(myModel.getRows());
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(actionId_Submit);
        inputInfo.setInputObj(DtoTimeSheet.DTO, dtoTimeSheet);
        ReturnInfo returnInfo = this.accessData(inputInfo);
        if (!returnInfo.isError()) {
            bakDto = (DtoTimeSheet) returnInfo.getReturnObj(DtoTimeSheet.DTO);
        }
        DtoUtil.copyBeanToBean(dtoTimeSheet, bakDto);
        resetStatusArea();
        resetUI();
    }
    
    private boolean submitCheck() {
    	List<DtoTimeSheetDetail> details = myModel.getRows();
        for(int i = 0; i < details.size(); i ++) {
        	if(myModel.getRowSumValue(i).getWorkHours() == 0D) {
        		String msg = MessageUtil.getMessage("error.client.VwDetailList.rowSumZeroPre");
                msg += " " + (i + 1) + " ";
                msg += MessageUtil.getMessage("error.client.VwDetailList.rowSumZeroPost");
                comMSG.dispErrorDialog(msg);
                this.getTable().setSelectRow(i);
                return false;
        	} else if(details.get(i).getCodeValueRid() == null) {
                String msg = MessageUtil.getMessage("error.client.VwDetailList.codeValuePre");
                msg += " " + (i + 1) + " ";
                msg += MessageUtil.getMessage("error.client.VwDetailList.codeValuePost");
                comMSG.dispErrorDialog(msg);
                this.getTable().setSelectRow(i);
                return false;
            }
        }
        if(comMSG.dispConfirmDialog("rsid.timesheet.VwDetailList.submitConfirm") == Constant.OK) {
        	return true;
        } else {
        	return false;
        }
        
    }

    /**
     * ѡ���б仯
     *    1.��������ɾ����ť��״̬
     *    2.����Activity�仯���¼��������ActivityDetail��Ƭ
     */
    private void processSelectedRowChanged() {
        if (myTable.getSelectedData() == null) {
            btnDelete.setEnabled(false);
            btnSubmit.setEnabled(false);
            btnSave.setEnabled(false);
        } else {
            btnDelete.setEnabled(editable());
            btnSubmit.setEnabled(editable());
            btnSave.setEnabled(editable());
            DtoTimeSheetDetail dto =
                   (DtoTimeSheetDetail) myTable.getSelectedData();
           this.fireactivityChanged(dto.getActivityId());
        }
    }
    public Long getSelectActivityId() {
    	if (myTable.getSelectedData() == null) {
    		return null;
    	} else {
    		DtoTimeSheetDetail dto =
                (DtoTimeSheetDetail) myTable.getSelectedData();
    		return dto.getActivityId();
    	}
    	
    }
    /**
     * �Ƿ�ɱ༭
     * @return boolean
     */
    private boolean editable() {
        return dtoTimeSheet == null ? false : dtoTimeSheet.editable();
    }


    /**
     * ���ò���
     * @param p Parameter
     */
    public void setParameter(Parameter param) {
        dtoTimeSheet = (DtoTimeSheet) param.get(DtoTimeSheet.DTO);
        super.setParameter(param);
    }

    /**
     * ����UI
     *   ��refresFlagΪtrue��super.setParameter(p);�ɽ�refresFlag��Ϊtrue��ʱ,
     *   super.refreshWorkArea()�����ô˷���
     */
    protected void resetUI() {
//        dtoTimeSheet = getTS();
        resetButtonStatus();
        resetStatusArea();
        if(dtoTimeSheet != null) {
            btnNotes.setTsRid(dtoTimeSheet.getRid());
        } else {
        	btnNotes.setTsRid(null);
        }
        myTable.setData(dtoTimeSheet);
    }

    /**
     * ���ð�ť״̬
     * @param editable boolean
     */
    private void resetButtonStatus() {
    	btnDelete.setEnabled(false);
        btnSave.setEnabled(false);
        btnSubmit.setEnabled(false);
        btnGenTs.setEnabled(editable());
        btnCopyTs.setEnabled(editable());
        boolean modifyMode = dtoTimeSheet != null 
		&& !DtoTimeSheet.MODEL_MODIFY.equals(dtoTimeSheet.getUiModel());
        btnSubmit.setVisible(modifyMode);
        btnGenTs.setVisible(modifyMode);
        btnCopyTs.setVisible(modifyMode);
//        btnNotes.setEnabled(editable());
        myModel.setCellEditable(editable());
    }

    /**
     * ����״̬��ʾ
     */
    private void resetStatusArea() {
        if(dtoTimeSheet != null) {
            Date begin = dtoTimeSheet.getBeginDate();
            Date end = dtoTimeSheet.getEndDate();
            String strDate = comDate.dateToString(begin)
                             + " ~ " + comDate.dateToString(end) + "  ";
            lblPeriod.setText(strDate);
            lblStatus.setText(dtoTimeSheet.getStatusName() + "  ");
            lblReason.setText(dtoTimeSheet.getReasonName());
        } else {
            lblPeriod.setText("");
            lblStatus.setText("");
            lblReason.setText("");
        }
    }




    public void addColumnWithListener(ColumnWithListener l) {
        myTable.addColumnWithListener(l);
    }

    public void addWorkHourListener(WorkHourListener l) {
        myModel.addWorkHourListener(l);
    }

    public void addColumnNumListener(ColumnNumListener l) {
        myModel.addColumnNumListener(l);
    }

    public void addStandarHoursListener(StandarHoursListener l) {
        myModel.addStandarHoursListener(l);
    }

    public void addResetRenderListener(ResetRenderListener l) {
        myTable.addResetRenderListener(l);
    }

    public void addActivityChangedListener(ActivityChangedListener l) {
        activityChangedListeners.add(l);
    }

    public void addSubmitTimeSheetListener(SubmitTimeSheetListener l) {
        submitTimeSheetListeners.add(l);
    }

    private void fireactivityChanged(Long activityRid) {
        for(ActivityChangedListener l : activityChangedListeners) {
            l.processActivityChanged(activityRid);
        }
    }

    private boolean fireSubmitTimeSheet() {
        for(SubmitTimeSheetListener l : submitTimeSheetListeners) {
            if(l.submitTimeSheet() == false) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * ���û�ѡ��������׼��ʱ��ԭ��
     * @return boolean
     */
    private boolean checkReason() {
    	VwSubmitChoiceset choiceset = new VwSubmitChoiceset();
    	choiceset.setPreferredSize(new Dimension(300, 200));
    	VWJPopupEditor popup = new VWJPopupEditor(this.getParentWindow(),
    							"Reason for abnormal actual hour", choiceset);
    	if(Constant.OK == popup.showConfirm()) {
    		String reason = choiceset.getReason();
    		dtoTimeSheet.setReason(reason);
    		if(reason == null) {
    			return false;
    		} else {
    			return true;
    		}
    	}
    	return false;
    }


    private class VwTsDetailDropTarget extends VWDropTarget {
        protected VwTsDetailDropTarget(Component c) {
            super(c);
            create();
        }

        /**
         * �����ڴ�ȷ���ؼ�����Щ�ط��ܷţ���Щ���ܷš�
         * @param event DropTargetDragEvent
         */
        public void dragOver(DropTargetDragEvent event) {
            //�����������Ϊ�գ���ܾ��¼�
            Object dropData = this.getDropData(event.getTransferable());
            if(dropData == null) {
                event.rejectDrag();
                return;
            }

            //�Ǳ༭״̬,�����Ϸ�
            if(dtoTimeSheet.editable() == false) {
                event.rejectDrag();
                return;
            }

            //������ŵ�ĳһ�У�����ܣ�����һ�С�
            DtoTimeSheetDetail dto = getRowData(event.getLocation());
            if(dto == null) {
                return;
            }

            DtoWorkScopeDrag dropDto = (DtoWorkScopeDrag) dropData;
            //����ͬһ����Ŀ�����÷�
            if(dto.getAccountRid().equals(dropDto.getAccountRid()) == false
            		&& event.getDropAction() != DnDConstants.ACTION_COPY) {
                event.rejectDrag();
                return;
            }
            //��ʾ���ܹ��
            event.acceptDrag(event.getDropAction());
        }

        /**
         * ��ȡҪ�ͷ��е�����
         * @param event DropTargetDragEvent
         * @return DtoTimeSheetDetail
         */
        private DtoTimeSheetDetail getRowData(Point p) {
            int row = myTable.rowAtPoint(p);
            if(row < 0 || row >= myModel.getRowCount()) {
                return null;
            }
            return (DtoTimeSheetDetail)myModel.getRow(row);
        }


        protected void acceptData(DropTargetDropEvent event, Object data) {
            DtoWorkScopeDrag drop = (DtoWorkScopeDrag) data;
            DtoTimeSheetDetail dto = getRowData(event.getLocation());
            int row =  myTable.rowAtPoint(event.getLocation());

            if(dto == null || event.getDropAction() == DnDConstants.ACTION_COPY) {
                row = addRow(drop);
            } else if(drop.isActivity()) {
                dto.setActivityId(drop.getActivityId());
                dto.setActivityName(drop.getActivityName());
                dto.setActPlanStart(drop.getPlannedStartDate());
                dto.setActPlanFinish(drop.getPlannedFinishDate());
                dto.setActivityStart(drop.getActivityStartDate());
                dto.setActivityFinish(drop.getActivityFinishDate());
                dto.setRsrcAssignmentId(drop.getAssignmentId());
                dto.setCodeValueRid(drop.getCodeValueRid());
                dto.setCodeValueName(drop.getCodeValueName());
                dto.setIsLeaveType(false);
            } else {
                dto.setCodeValueRid(drop.getCodeValueRid());
                dto.setCodeValueName(drop.getCodeValueName());
                Boolean leave = drop.getIsLeaveType();
                dto.setIsLeaveType(leave);
                if(leave != null && leave) {
                	clearOverTime(dto);
                }
            }
            myModel.fireTableDataChanged();
            myTable.setSelectRow(row);
        }
        
        /**
         * ��ҵ����ת���ٱ����ʱ�����Ӱ�ʱ�������
         * @param dto
         */
        private void clearOverTime(DtoTimeSheetDetail dto) {
        	stopCellEditing();
        	Map dayMap = dto.getTsDays();
        	if(dayMap == null || dayMap.size() == 0) {
        		return;
        	}
        	Iterator<DtoTimeSheetDay> days = dayMap.values().iterator();
        	while(days.hasNext()) {
        		DtoTimeSheetDay day = days.next();
        		day.setOvertimeHours(null);
        	}
        }

        private int addRow(DtoWorkScopeDrag drag) {
            int row = myModel.getRowCount();
            DtoTimeSheetDetail dto = new DtoTimeSheetDetail();
            DtoUtil.copyBeanToBean(dto, drag);
            dto.setActPlanStart(drag.getPlannedStartDate());
            dto.setActPlanFinish(drag.getPlannedFinishDate());
            dto.setActivityStart(drag.getActivityStartDate());
            dto.setActivityFinish(drag.getActivityFinishDate());
            dto.setRsrcAssignmentId(drag.getAssignmentId());
            if(DtoTimeSheet.MODEL_MODIFY.endsWith(dtoTimeSheet.getUiModel())) {
            	dto.setStatus(dtoTimeSheet.getStatus());
            } else {
            	dto.setStatus(DtoTimeSheet.STATUS_ACTIVE);
            }
            
            dto.setIsLeaveType(drag.getIsLeaveType());
            myModel.addRow(row, dto);
            return row;
        }

        protected Class getAcceptClass() {
            return DtoWorkScopeDrag.class;
        }



    }

//    private static DtoTimeSheet getTS() {
//        DtoTimeSheet dto = new DtoTimeSheet();
//        dto.setBeginDate(comDate.toDate("2007-08-04"));
//        dto.setEndDate(comDate.toDate("2007-08-10"));
//        dto.setDecimalDigit(1);
//        dto.setLoginId("WH0607014");
//        List standarHours = new ArrayList();
//        standarHours.add(Double.valueOf(0));
//        standarHours.add(Double.valueOf(0));
//        standarHours.add(Double.valueOf(8));
//        standarHours.add(Double.valueOf(8));
//        standarHours.add(Double.valueOf(8));
//        standarHours.add(Double.valueOf(8));
//        standarHours.add(Double.valueOf(8));
//        standarHours.add(Double.valueOf(40));
//        dto.setStandarHours(standarHours);
//        DtoTimeSheetDetail dtoDetail = new DtoTimeSheetDetail();
//        dtoDetail.setJobDescription("description");
//        dtoDetail.setStatus(DtoTimeSheet.STATUS_ACTIVE);
//        dtoDetail.setAccountName("002645W -- ESSP");
//        dtoDetail.setAccountRid(Long.valueOf(4));
//        dtoDetail.setActivityName("A0110 -- timesheet");
//        dtoDetail.setActPlanStart(comDate.toDate("2007-08-07"));
//        dtoDetail.setActPlanFinish(comDate.toDate("2007-08-08"));
//        dtoDetail.setHour(comDate.toDate("2007-08-07"), Double.valueOf(8), Double.valueOf(2));
//        List details = new ArrayList();
//        details.add(dtoDetail);
//        dto.setTsDetails(details);
//        return dto;
//    }
//
//    public static void main(String[] args) {
//        VwDetailList a = new VwDetailList();
//        a.resetUI();
//        TestPanel.show(a);
//    }

}
