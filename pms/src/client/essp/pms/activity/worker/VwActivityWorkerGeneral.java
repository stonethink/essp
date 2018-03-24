package client.essp.pms.activity.worker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Date;
import java.util.Vector;

import c2s.dto.DtoComboItem;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.pms.wbs.DtoActivityWorker;
import client.framework.model.VMComboBox;
import com.wits.util.Parameter;
import com.wits.util.StringUtil;
import client.framework.view.common.comMSG;
import c2s.essp.pms.wbs.DtoWbsActivity;
import client.framework.view.vwcomp.VWUtil;
import c2s.essp.common.calendar.WrokCalendarFactory;
import c2s.essp.common.calendar.WorkCalendar;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class VwActivityWorkerGeneral extends VwActivityWorkerGeneralBase {
    public static final String ACTIONID_WORKS_GETLABOR = "FWbsActivityGetWorkerAction";
    public static final String ACTIONID_WORKS_ADD =
        "FWbsActivityWorkerAddAction";
    private DtoActivityWorker dto;
    private DtoWbsActivity dtoActivity ;
    private Vector resourceList;

    public VwActivityWorkerGeneral() {
        super();
        addUICEvent();
    }
    //ע���¼�
    public void addUICEvent() {
        inputLoginId.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearUI();
                actionPerformedGetLabor();
            }
        });

        inputPlanWorkTime.addFocusListener(new FocusListener(){
            public void focusGained(FocusEvent e) {
            }
            public void focusLost(FocusEvent e) {
//                actionPerformedCaculate();
            }
        });

        inputPlannedStart.addFocusListener(new FocusListener(){
            public void focusGained(FocusEvent e) {
            }
            public void focusLost(FocusEvent e) {
                actionPerformedCacu();
            }
        });

        inputPlannedFinish.addFocusListener(new FocusListener(){
            public void focusGained(FocusEvent e) {
            }
            public void focusLost(FocusEvent e) {
                actionPerformedCacu();
            }
        });


//        inputActualWorkTime.addFocusListener(new FocusListener(){
//            public void focusGained(FocusEvent e) {
//            }
//            public void focusLost(FocusEvent e) {
//                actionPerformedCaculate();
//            }
//        });

    }

    //�����¼�
    public void actionPerformedSave() {
        VWUtil.convertUI2Dto(dto,this);
    }

    private boolean validateData(){
        Date begin = dto.getPlanStart();
        Date end = dto.getPlanFinish();
        if (begin == null || end == null) {
            comMSG.dispErrorDialog("Please input plan start date and end date!");
            return false;
        }
        if (begin.after(end)) {
            comMSG.dispErrorDialog("Planned finish date must be larger than planned start date");
            return false;
        }
        if(begin.before(dtoActivity.getPlannedStart())){
            comMSG.dispErrorDialog("Planned begin date must be between activity start date and activity end date");
            return false;
        }
        if(end.after(dtoActivity.getPlannedFinish())){
            comMSG.dispErrorDialog("Planned finish date must be between activity start date and activity end date");
            return false;
        }

        begin = dto.getActualStart();
        end = dto.getActualFinish();
        if ( begin != null && end != null && begin.after(end)) {
            comMSG.dispErrorDialog("Actual finish date must be larger than actual start date");
            return false;
        }
        Long planWorkTime = dto.getPlanWorkTime();
        if(planWorkTime != null && planWorkTime.longValue() < 0 ){
            inputPlanWorkTime.setErrorField(true);
            comMSG.dispErrorDialog("Plan work time must be larger than 0 !");
            return false;
        }
//        Long acCompleteTime = dto.getEtcWorkTime();
//        Long actualWorkTime = dto.getActualWorkTime();
//        if(acCompleteTime != null && actualWorkTime!= null && actualWorkTime.longValue() > acCompleteTime.longValue()){
//            inputAcCompletion.setErrorField(true);
//            comMSG.dispErrorDialog("Ac.Complete Time can't be less than actual work time!");
//            return false;
//        }
        return true;
    }
    //������Ŀ��Ӧ��Labor Resource
    public void actionPerformedGetLabor() {
        String loginId = StringUtil.nvl(inputLoginId.getUICValue());
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.ACTIONID_WORKS_GETLABOR);
        inputInfo.setInputObj("acntRid", dtoActivity.getAcntRid());
        inputInfo.setInputObj("loginId", loginId);
        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == false) {
            dto = (DtoActivityWorker) returnInfo.getReturnObj("dto");
            if(dto != null){
                dto.setAcntRid(dtoActivity.getAcntRid());
                dto.setActivityRid(dtoActivity.getActivityRid());
                Date planStart = dtoActivity.getPlannedStart();
                Date planFinish = dtoActivity.getPlannedFinish();
                dto.setPlanStart(planStart);
                dto.setPlanFinish(planFinish);
                if(planStart != null && planFinish != null){
                    //����Activity�ļƻ�����ʱ��,��Ϊ��Labor��Ĭ�ϼƻ�����ʱ��
                    WorkCalendar wc = WrokCalendarFactory.clientCreate();
                    Calendar begin = new GregorianCalendar();
                    begin.setTime(planStart);
                    Calendar end = new GregorianCalendar();
                    end.setTime(planFinish);
                    int iWH = (int) wc.getWorkHours(begin, end);

                    dto.setPlanWorkTime(new Long(iWH));
                }
                VWUtil.bindDto2UI(dto,this);
                //actionPerformedCaculate();
                inputName.setEnabled(false);
                inputRoleName.setEnabled(false);
                inputJobCode.setEnabled(false);
            }
        }
    }

//    private void actionPerformedCaculate(){
//        Long planWorkTime = (Long) inputPlanWorkTime.getUICValue();
//        Long actualWorkTime = (Long) inputActualWorkTime.getUICValue();
//        Long acCompleteTime = (Long) inputAcCompletion.getUICValue();
//        if(acCompleteTime.longValue() < actualWorkTime.longValue() || actualWorkTime.longValue() == 0){
//            if (planWorkTime.longValue() > actualWorkTime.longValue())
//                inputAcCompletion.setUICValue(planWorkTime);
//            else
//                inputAcCompletion.setUICValue(actualWorkTime);
//        }
//    }
    private void actionPerformedCacu(){
        Date satrt = (Date)inputPlannedStart.getUICValue();
        Date finish = (Date)inputPlannedFinish.getUICValue();
        dto.setPlanStart(satrt);
        dto.setPlanFinish(finish);
        if(satrt != null && finish != null){
            //����Activity�ļƻ�����ʱ��,��Ϊ��Labor��Ĭ�ϼƻ�����ʱ��
            WorkCalendar wc = WrokCalendarFactory.clientCreate();
            Calendar begin = new GregorianCalendar();
            begin.setTime(satrt);
            Calendar end = new GregorianCalendar();
            end.setTime(finish);
            int iWH = (int) wc.getWorkHours(begin, end);
            dto.setPlanWorkTime(new Long(iWH));
        }
        VWUtil.bindDto2UI(dto,this);
    }

    public void setParameter(Parameter param) {
        super.setParameter(param);
        this.dtoActivity = (DtoWbsActivity) param.get("dtoActivity");
        this.resourceList = (Vector) param.get("resourceList");
        /**
         * ��ʼ��ʱҪ��ERROR��־ add by yery on 2005/10/18
         */
        VWUtil.clearErrorField(this);
    }


    public void resetUI() {
        if(resourceList == null)
            resourceList = new Vector();
        inputLoginId.setVMComboBox(new VMComboBox(resourceList));
        if (resourceList != null && resourceList.size() > 0) {
            DtoComboItem first = (DtoComboItem) resourceList.get(0);
            inputLoginId.setUICValue(first.getItemValue());
            actionPerformedGetLabor();
        }
    }

    /**
     * ���ҳ��ؼ���ֵ
     */
    public void clearUI(){
        inputPlannedStart.setUICValue(null);
        inputPlannedFinish.setUICValue(null);
        inputPlanWorkTime.setUICValue(null);
//        inputActualStart.setUICValue(null);
//        inputActualFinish.setUICValue(null);
//        inputActualWorkTime.setUICValue(null);
//        inputAcCompletion.setUICValue(null);
        inputRemark.setText(null);
    }

    public DtoActivityWorker getDto() {
        if(validateData())
            return dto;
        else
            return null;
    }
    public boolean saveData(){
        actionPerformedSave();
        if(validateData()){
            InputInfo inputInfo = new InputInfo();
            inputInfo.setActionId(this.ACTIONID_WORKS_ADD);
            inputInfo.setInputObj("dto", dto);
            ReturnInfo returnInfo = accessData(inputInfo);

            /**
             * ����ɹ���Ҫ��ERROR��־ add by yery on 2005/10/18
             */
            if(!returnInfo.isError()) {
                VWUtil.clearErrorField(this);
            }
            return (!returnInfo.isError());
        }else
            return false;
    }
}
