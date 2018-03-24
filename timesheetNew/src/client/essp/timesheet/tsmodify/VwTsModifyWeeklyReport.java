package client.essp.timesheet.tsmodify;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheet;
import client.essp.timesheet.weeklyreport.VwWeeklyReportBase;
import client.framework.view.vwcomp.VWUtil;

import com.wits.util.Parameter;

/**
 * <p>Title: VwApprovalGeneral</p>
 *
 * <p>Description: 工时审批，工时单明细卡片</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class VwTsModifyWeeklyReport extends VwWeeklyReportBase {

    private final static String actionId_LoadTimeSheet = "FTSLoadTimeSheetByRid";
    private Long tsRid;
    private JButton btnRefresh;

    public VwTsModifyWeeklyReport() {
        super();
        addUICEvent();
    }

    /**
     * 事件处理
     */
    private void addUICEvent() {
    	detail.getButtonPanel().remove(3);
    	
    	this.getButtonPanel().add(detail.getButtonPanel());
        btnRefresh = this.getButtonPanel().addLoadButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetUI();
            }
        });
        btnRefresh.setToolTipText("rsid.common.refresh");
        
        
    }

    /**
     * 设置参数
     */
    public void setParameter(Parameter param) {
        tsRid = (Long) param.get(DtoTimeSheet.DTO_RID);
        super.setParameter(param);
    }

    /**
     * 获取数据
     * @return DtoTimeSheet
     */
    protected DtoTimeSheet loadData() {
        if(tsRid == null) return null;
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(actionId_LoadTimeSheet);
        inputInfo.setInputObj(DtoTimeSheet.DTO_RID, tsRid);
        ReturnInfo returnInfo = this.accessData(inputInfo);
        if(!returnInfo.isError()) {
           DtoTimeSheet dto = (DtoTimeSheet) returnInfo.getReturnObj(DtoTimeSheet.DTO);
           //设置修改模式，始终可修改
           if(dto != null) {
        	   dto.setUiModel(DtoTimeSheet.MODEL_MODIFY);
           }
           return dto;
        }
        return null;
    }
}
