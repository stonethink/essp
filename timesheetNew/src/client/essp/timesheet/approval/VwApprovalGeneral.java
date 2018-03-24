package client.essp.timesheet.approval;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheet;
import client.essp.timesheet.weeklyreport.VwWeeklyReportBase;
import com.wits.util.Parameter;

/**
 * <p>Title: VwApprovalGeneral</p>
 *
 * <p>Description: ��ʱ��������ʱ����ϸ��Ƭ</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class VwApprovalGeneral extends VwWeeklyReportBase {

    private final static String actionId_LoadTimeSheet = "FTSLoadTimeSheetByRid";
    private Long tsRid;
    private JButton btnRefresh;

    public VwApprovalGeneral() {
        super();
        addUICEvent();
    }

    /**
     * �¼�����
     */
    private void addUICEvent() {

        btnRefresh = this.getButtonPanel().addLoadButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetUI();
            }
        });
        btnRefresh.setToolTipText("rsid.common.refresh");
    }

    /**
     * ���ò���
     */
    public void setParameter(Parameter param) {
        tsRid = (Long) param.get(DtoTimeSheet.DTO_RID);
        super.setParameter(param);
    }

    /**
     * ��ȡ����
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
            //���ò鿴ģʽ��ʼ�ղ����޸�
            if(dto != null) {
         	   dto.setUiModel(DtoTimeSheet.MODEL_VIEW);
            }
            return dto;
        }
        return null;
    }

}
