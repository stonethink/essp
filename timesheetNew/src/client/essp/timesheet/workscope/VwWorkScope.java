package client.essp.timesheet.workscope;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import c2s.essp.timesheet.activity.DtoActivityKey;
import c2s.essp.timesheet.code.DtoCodeValue;
import c2s.essp.timesheet.workscope.DtoAccount;
import c2s.essp.timesheet.workscope.DtoActivity;
import c2s.essp.timesheet.workscope.DtoWorkScopeDrag;
import client.essp.common.view.VWGeneralWorkArea;
import client.essp.common.view.VWTDWorkArea;
import client.essp.timesheet.ActivityChangedListener;
import client.framework.view.event.RowSelectedListener;
import client.framework.view.vwcomp.VWDragSource;
import com.wits.util.Parameter;

/**
 * <p>Title:VwWorkScope </p>
 *
 * <p>Description: WorkScope是最外面的框架</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author tbh
 * @version 1.0
 */
public class VwWorkScope extends VWTDWorkArea {

    private VwAccountList vwAccountList;//account卡片
    private VwActivityList vwActivityList;//Activity卡片
    private VwJobCodeList vwJobCodeList;
    private VwLeaveCodeList vwLeaveCodeList;
    private boolean refreshFlag;//是否需要刷新的标志

    private DtoAccount dtoAccount;
    public VwWorkScope() {
        super(100);
        try {
            jbInit();
        } catch (Exception e) {
            log.error(e);
        }
        addUICEvent();
    }

    /**
     * Component初始化,UI布局
     */
    private void jbInit() throws Exception {
        vwAccountList = new VwAccountList();
        vwActivityList = new VwActivityList();
        vwJobCodeList = new VwJobCodeList();
        vwLeaveCodeList = new VwLeaveCodeList();

        this.getTopArea().addTab("rsid.common.account", vwAccountList);

    }


    /**
     * 定义界面事件
     */
    private void addUICEvent() {
    	vwAccountList.getTable().getSelectionModel().addListSelectionListener(
    			new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent e) {
						if(e.getValueIsAdjusting()) {
							return;
						}
						processRowSelectedProjecItem();
						
					}});
//        this.vwAccountList.getTable().addRowSelectedListener(new
//            RowSelectedListener() {
//            public void processRowSelected() {
//                processRowSelectedProjecItem();
//            }
//        });
        new VwWorkScopeDragSource(vwActivityList.getTable());
        new VwWorkScopeDragSource(vwJobCodeList.getTable());
        new VwWorkScopeDragSource(vwLeaveCodeList.getTable());
    }

    /**
     * 根据选择的项目不同显示对应的详细卡片
     */
    public void processRowSelectedProjecItem() {

        dtoAccount = (DtoAccount)this.vwAccountList.getSelectedData();
        Parameter para = new Parameter();
        if (dtoAccount != null) {
            if (dtoAccount.getIsP3()) {
                VWGeneralWorkArea p3WorkArea = new VWGeneralWorkArea();
                p3WorkArea.addTab("rsid.common.activity", vwActivityList);
                p3WorkArea.addTab("rsid.timesheet.jobCode", vwJobCodeList);
                para.put(DtoActivityKey.ACTIVITY_LIST, dtoAccount.getActivities());
                para.put(DtoAccount.DTO_ACCOUNT, dtoAccount);
                vwJobCodeList.setParameter(para);
                vwActivityList.setParameter(para);
                vwJobCodeList.refreshWorkArea();
                vwActivityList.refreshWorkArea();
                this.setDownArea(p3WorkArea);
            } else if (dtoAccount.getIsDept()) {
                VWGeneralWorkArea deptWrokArea = new VWGeneralWorkArea();
                deptWrokArea.addTab("rsid.timesheet.jobCode", vwJobCodeList);
                deptWrokArea.addTab("rsid.timesheet.leaveCode", vwLeaveCodeList);
                para.put(DtoAccount.DTO_ACCOUNT, dtoAccount);
                vwJobCodeList.setParameter(para);
                vwLeaveCodeList.setParameter(para);
                vwJobCodeList.refreshWorkArea();
                vwLeaveCodeList.refreshWorkArea();
                this.setDownArea(deptWrokArea);
            } else if(!dtoAccount.getIsDept()){
                VWGeneralWorkArea projectWrokArea = new VWGeneralWorkArea();
                projectWrokArea.addTab("rsid.timesheet.jobCode", vwJobCodeList);
                para.put(DtoAccount.DTO_ACCOUNT, dtoAccount);
                vwJobCodeList.setParameter(para);
                vwJobCodeList.refreshWorkArea();
                this.setDownArea(projectWrokArea);
            }
        } else {
        	vwJobCodeList.setParameter(para);
        	vwLeaveCodeList.setParameter(para);
        	vwActivityList.setParameter(para);
        	vwJobCodeList.refreshWorkArea();
        	vwLeaveCodeList.refreshWorkArea();
            vwActivityList.refreshWorkArea();
        }

    }

    public void addActivityChangedListener(ActivityChangedListener l) {
        vwActivityList.addActivityChangedListener(l);
        vwJobCodeList.addActivityChangedListener(l);
        vwLeaveCodeList.addActivityChangedListener(l);
    }


    /**
     * 设置参数
     *   卡片间传递数据，一般情况下不允许卡片间变量直接引用，或调用其它getter, setter
     * @param p Parameter
     */
    public void setParameter(Parameter p) {
        this.refreshFlag = true;
        this.vwAccountList.setParameter(p);
    }

    /**
     * 页面刷新事件响应方法
     */
    public void refreshWorkArea() {
        if (refreshFlag == true) {
            this.vwAccountList.refreshWorkArea();
            refreshFlag = false;
        }
    }

    private class VwWorkScopeDragSource extends VWDragSource {
        VwWorkScopeDragSource(Component c) {
            super(c);
            create();
        }

        /**
         * 获取要拖放的数据，可根据情况重载，返回响应数据
         * @return Object
         */
        protected Object getDragData() {
            Object data = super.getDragData();
            if (data instanceof DtoActivity) {
                return getDragActivity((DtoActivity) data);
            } else if (data instanceof DtoCodeValue) {
                return getDragCodeValue((DtoCodeValue) data);
            } else {
                return null;
            }
        }
    }

    private DtoWorkScopeDrag getDragActivity(DtoActivity act) {
        if(act == null || dtoAccount == null) {
            return null;
        }
        DtoWorkScopeDrag dto = new DtoWorkScopeDrag();
        dto.setIsActivity(true);
        dto.setAccountRid(dtoAccount.getRid());
        dto.setAccountName(dtoAccount.getShowName());
        if(act.getId() != null) {
        	dto.setActivityId(act.getId().longValue());
        }
        if(act.getAssignmentId() != null) {
        	dto.setAssignmentId(act.getAssignmentId().longValue());
        }
        dto.setActivityName(act.getShowName());
        dto.setPlannedStartDate(act.getPlannedStartDate());
        dto.setPlannedFinishDate(act.getPlannedFinishDate());
        dto.setActivityStartDate(act.getActivityStart());
        dto.setActivityFinishDate(act.getActivityFinish());
        dto.setCodeValueRid(act.getCodeValueRid());
        dto.setCodeValueName(act.getCodeShowName());
        dto.setIsLeaveType(false);
        return dto;
    }

    private DtoWorkScopeDrag getDragCodeValue(DtoCodeValue code) {
        if(code == null || dtoAccount == null) {
            return null;
        }
        DtoWorkScopeDrag dto = new DtoWorkScopeDrag();
        dto.setAccountRid(dtoAccount.getRid());
        dto.setAccountName(dtoAccount.getShowName());
        dto.setCodeValueRid(code.getRid());
        dto.setCodeValueName(code.getShowLeaveCodeName());
        dto.setIsLeaveType(code.getIsLeaveType());
        return dto;
    }

}
