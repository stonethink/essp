package client.essp.pms.account.labor.plan;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

import c2s.dto.DtoBase;
import c2s.dto.IDto;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.pms.account.DtoAcntLaborResDetail;
import c2s.essp.pms.account.DtoAcntLoaborRes;
import client.essp.common.view.VWTableWorkArea;
import client.framework.common.Constant;
import client.framework.model.VMColumnConfig;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.vwcomp.VWJNumber;
import client.framework.view.vwcomp.VWJTable;
import com.wits.util.Parameter;
import client.framework.view.vwcomp.VWJReal;
import java.util.Calendar;
import c2s.essp.common.calendar.WorkCalendar;
import c2s.essp.common.calendar.WrokCalendarFactory;
import c2s.essp.pms.account.DtoPmsAcnt;


public class VwLaborPlanDetailList extends VWTableWorkArea {
    public static final String ACTIONID_RECORD_SAVE="FAcntLaborResourcePlanSaveAction";
    public static final String ACTIONID_RECORD_LOAD="FAcntLaborResourcePlanLoadAction";

    VWJDate inputDate = new VWJDate();
    VWJNumber inputPercent = new VWJNumber();
    VWJReal inputHour = new VWJReal();

    private DtoAcntLoaborRes laborResource;
    private DtoPmsAcnt pmsAcc ;
    public VwLaborPlanDetailList(){
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        addUIEvent();
    }

    public void addUIEvent(){
        getButtonPanel().addAddButton(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                actionPerformedAdd();
            }
        });
        getButtonPanel().addDelButton(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                actionPerformedDelete();
            }
        });
        getButtonPanel().addLoadButton(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                actionPerformedLoad();
            }
        });
    }
    //ˢ�¶���
    protected void actionPerformedLoad(){
        resetUI();
    }
    //ɾ��Detail
    protected void actionPerformedDelete(){
        if(laborResource == null)
            return ;
        List planDetailList = laborResource.getPlanDetail();
        if( planDetailList == null)
            return ;
        this.getTable().deleteRow();
    }
    //����ʱ��֤����
    boolean validateData(){
        if(laborResource == null)
            return false;
        List planDetailList = laborResource.getPlanDetail();
        if( planDetailList == null)
            return true;
        StringBuffer msg = new StringBuffer();
        for(int i = 0;i < planDetailList.size() ;i ++){
            DtoAcntLaborResDetail detail = (DtoAcntLaborResDetail) planDetailList.get(i);
            Date begin = detail.getBeginDate();
            Date end = detail.getEndDate();
            Long percent = detail.getPercent();
            if(begin == null || end == null){
                msg.append("Line["+i+"]:Begin and End Date must be filled!\n");
            }else if(begin.getTime() > end.getTime()){
                msg.append("Line["+i+"]:End date must be greater than begin date!\n");
            }
            if(percent == null || percent.longValue() > 100 || percent.longValue() < 0){
                msg.append("Line["+i+"]:Usage percent be between 0 and 100!\n");
            }
        }
        if(msg.toString().trim().length() == 0)
            return true;
        comMSG.dispErrorDialog(msg.toString());
        return false;
    }

    boolean checkModified(){
        List planDetailList = laborResource.getPlanDetail();
        if(laborResource == null || planDetailList == null)
            return false;
        for(int i = 0;i < planDetailList.size() ;i ++){
            DtoBase dto = (DtoBase)planDetailList.get(i);
            if(dto.isChanged())
                return true;
        }
        return false;
    }
    //����Detail
    protected void actionPerformedAdd(){
        if(laborResource != null){
            DtoAcntLaborResDetail detail = (DtoAcntLaborResDetail)this.getTable().
                                           addRow();
            detail.setAcntRid(laborResource.getAcntRid());
            detail.setLoginId(laborResource.getLoginId());
            detail.setResRid(laborResource.getRid());
            detail.setPercent(new Long(100));
        }
    }
    public void setParameter(Parameter param){
        laborResource = (DtoAcntLoaborRes) param.get("dto");
        pmsAcc = (DtoPmsAcnt) param.get("dtoAccount");
    }
    /**
     * ˢ��Detail List
     * 1.����Labor Resourceʱ,Ĭ�ϻ���ʾһ��Detail��¼
     * 2.�޸�Labor Resourceʱ,�ӷ����ȡLabor Resource��Ӧ��Detail��¼
     */
    public void resetUI(){
        if(laborResource == null )
            return;
        if(laborResource.getRid() == null){
            laborResource.removeAllDetail();

            DtoAcntLaborResDetail defDetail = new DtoAcntLaborResDetail();
            defDetail.setBeginDate(pmsAcc.getPlannedStart());
            defDetail.setEndDate(pmsAcc.getPlannedFinish());
            defDetail.setPercent(new Long(100));
            WorkCalendar wc = WrokCalendarFactory.clientCreate();
            Calendar start = Calendar.getInstance();
            Calendar finish = Calendar.getInstance();
            start.setTime(pmsAcc.getPlannedStart());
            finish.setTime(pmsAcc.getPlannedFinish());
            double workHour = wc.getWorkHours(start, finish) ;
            defDetail.setHour(new Double(workHour));
            laborResource.addDetail(defDetail);

        }else{
            InputInfo inputInfo = new InputInfo();
            inputInfo.setActionId(this.ACTIONID_RECORD_LOAD);
            inputInfo.setInputObj("laborResource", laborResource);
            ReturnInfo returnInfo = accessData(inputInfo);
        }
        if (laborResource != null &&
            laborResource.getPlanDetail() != null){
            getTable().setRows(laborResource.getPlanDetail());
        }
    }

    protected void jbInit() throws Exception {
        inputDate.setCanSelect(true);
        inputPercent.setMaxInputIntegerDigit(3);
        inputHour.setMaxInputIntegerDigit(8);
        inputHour.setMaxInputDecimalDigit(2);
        Object[][] configs = new Object[][] {
                             {"Begin Date", "beginDate",VMColumnConfig.EDITABLE, inputDate},
                             {"End Date", "endDate",VMColumnConfig.EDITABLE, inputDate},
                             {"Percent(%)", "percent",VMColumnConfig.EDITABLE, inputPercent},
                             {"Hour", "hour",VMColumnConfig.EDITABLE, inputHour},
        };
        try {
            model = new LaborPlanListTableModel(configs);
            model.setDtoType(DtoAcntLaborResDetail.class);
            table = new VWJTable(model);
            this.add(table.getScrollPane(), null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
