package client.essp.tc.hrmanager.overtime;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import com.wits.util.Parameter;
import java.util.Date;
import c2s.essp.tc.weeklyreport.DtoTcKey;
import c2s.essp.tc.overtime.DtoOverTime;
import c2s.essp.common.code.DtoKey;
import client.framework.view.vwcomp.VWUtil;
import c2s.dto.ReturnInfo;
import c2s.dto.InputInfo;
import client.framework.model.VMComboBox;
import java.util.Vector;
import client.framework.view.vwcomp.IVWPopupEditorEvent;
import client.framework.view.common.comMSG;
import client.framework.view.common.comFORM;
import com.wits.util.comDate;
import java.util.List;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;
import com.wits.util.StringUtil;
import c2s.essp.attendance.Constant;
import c2s.dto.IDto;
import c2s.essp.tc.overtime.DtoOverTimeDetail;
import java.math.BigDecimal;
import c2s.essp.tc.leave.DtoLeave;

public class VwOverTimeGeneral extends VwOverTimeGeneralBase implements
    IVWPopupEditorEvent,DetailChangedListener {
    static final String actionIdAccountList = "FTCHrManageOverTimAccountListAction";
    static final String actionIdCaculateList = "FTCHrManageOverTimCaculateAction";
    static final String actionIdSave = "FTCHrManageOverTimSaveAction";

    private DtoOverTime dto;
    private Date beginPeriod;
    private Date endPeriod;
    private boolean reCaculate = false;//提交前是否要重新计算
    private boolean needRefrehParent = false;//提交后是否刷新父窗口
    public VwOverTimeGeneral() {
        super();
        try {
            addUICEvent();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void addUICEvent() {
        btCaculate.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                actionPerformedCaculate();
            }
        });
        //判断时间是否修改了,是否需重新计算
        inputDateFrom.addFocusListener(new FocusListener(){
            public void focusGained(FocusEvent e) {
            }
            public void focusLost(FocusEvent e) {
                Date inputDate = (Date) inputDateFrom.getUICValue();
                Date dtoDate = dto.getActualDateFrom();
               if(!comDate.dateToString(inputDate)
                  .equals(comDate.dateToString(dtoDate))){
                    reCaculate = true;
                }
            }
        });
        inputDateTo.addFocusListener(new FocusListener(){
            public void focusGained(FocusEvent e) {
            }
            public void focusLost(FocusEvent e) {
                Date inputDate = (Date) inputDateTo.getUICValue();
                Date dtoDate = dto.getActualDateTo();
               if(!comDate.dateToString(inputDate)
                  .equals(comDate.dateToString(dtoDate))){
                    reCaculate = true;
                }
            }
        });
        inputTimeFrom.addFocusListener(new FocusListener(){
            public void focusGained(FocusEvent e) {
            }
            public void focusLost(FocusEvent e) {
                Date inputDate = (Date) inputTimeFrom.getUICValue();
                String dtoDate = dto.getActualTimeFrom();
               if(!comDate.dateToString(inputDate,Constant.TIME_FORMAT).equals(dtoDate)){
                    reCaculate = true;
                }
            }
        });
        inputTimeTo.addFocusListener(new FocusListener(){
            public void focusGained(FocusEvent e) {
            }
            public void focusLost(FocusEvent e) {
                Date inputDate = (Date) inputTimeTo.getUICValue();
                String dtoDate = dto.getActualTimeTo();
               if(!comDate.dateToString(inputDate,Constant.TIME_FORMAT).equals(dtoDate)){
                    reCaculate = true;
                }
            }
        });
        inputEachDay.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                reCaculate = true;
            }
        });
        vwOverTimeDetailList.addDetailChangeListener(this);
    }

    private void actionPerformedCaculate(){
        if (validateDate()) {
            convertUI2Dto();
            InputInfo inputInfo = new InputInfo();
            inputInfo.setActionId(this.actionIdCaculateList);
            inputInfo.setInputObj(DtoKey.DTO, dto);
            ReturnInfo returnInfo = accessData(inputInfo);
            if (returnInfo.isError() == false) {
                dto = (DtoOverTime) returnInfo.getReturnObj(DtoKey.DTO);
                bindDto2UI();
                reCaculate = false;
                dto.setOp(IDto.OP_MODIFY);
            }
        }
    }
    /**
     * 日期验证:
     * 1.所有日期时间栏位不能为空
     * 2.起始日期小于结束日期
     * 3.若eachDay==true,起始时间小于结束时间
     * 4.在选择的时间区间内
     */
    private boolean validateDate() {
        Date dateFrom = (Date) inputDateFrom.getUICValue();
        Date dateTo = (Date) inputDateTo.getUICValue();
        Date timeFrom = (Date) inputTimeFrom.getUICValue();
        Date timeTo = (Date) inputTimeTo.getUICValue();
        Boolean isEachDay = new Boolean(inputEachDay.getUICValue().toString());
        if(dateFrom == null){
            comMSG.dispErrorDialog("Please fill from date!");
            comFORM.setFocus(inputDateFrom);
            return false;
        }else if(dateTo == null){
            comMSG.dispErrorDialog("Please fill to date!");
            comFORM.setFocus(inputDateTo);
            return false;
        }else if(timeFrom == null){
            comMSG.dispErrorDialog("Please fill from time!");
            comFORM.setFocus(inputTimeFrom);
            return false;
        }else if(timeTo == null){
            comMSG.dispErrorDialog("Please fill to time!");
            comFORM.setFocus(inputTimeTo);
            return false;
        }else if(dateFrom.getTime() > dateTo.getTime()){
            comMSG.dispErrorDialog("The to date must be after from date!");
            comFORM.setFocus(inputDateFrom);
            return false;
        }else if(isEachDay.booleanValue() && timeFrom.getTime() > timeTo.getTime()){
            comMSG.dispErrorDialog("The to time must be larger than from time!");
            comFORM.setFocus(inputTimeFrom);
            return false;
        }else if(beginPeriod.getTime() > dateFrom.getTime() ||
                endPeriod.getTime() < dateTo.getTime()){
            comMSG.dispErrorDialog("The overtime period must be between "+
                                   comDate.dateToString(beginPeriod)+" and " +
                                   comDate.dateToString(endPeriod)+"!");
            comFORM.setFocus(inputDateFrom);
            return false;
        }
        return true;
    }
    public boolean vildateData(){
        Object account = inputAccount.getUICValue();
        Object hours = inputTotalHours.getUICValue();
        if(account == null)
            comMSG.dispErrorDialog("Please select a account!");
        if(hours == null)
            comMSG.dispErrorDialog("Please calculate overtime hours!");
        if(reCaculate)
            comMSG.dispErrorDialog("Please ReCalculate overtime hours!");
        return validateDate() && vwOverTimeDetailList.validateData()
                && account != null && hours!= null && !reCaculate;
    }

    public void setParameter(Parameter param) {
        super.setParameter(param);
        this.beginPeriod = (Date) (param.get(DtoTcKey.BEGIN_PERIOD));
        this.endPeriod = (Date) (param.get(DtoTcKey.END_PERIOD));
        //新增传入的Dto只有LoginId
        this.dto = (DtoOverTime) (param.get(DtoKey.DTO));
        reCaculate = false;
        needRefrehParent = false;
    }
    public void resetUI(){
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdAccountList);
        inputInfo.setInputObj(DtoTcKey.USER_ID, this.dto.getLoginId());
        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == false) {
            Vector accountList = (Vector) returnInfo.getReturnObj("accountList");
            VMComboBox vmAccount = new VMComboBox(accountList);
            inputAccount.setModel(vmAccount);
        }
        bindDto2UI();
    }
    private void convertUI2Dto(){
        VWUtil.convertUI2Dto(dto,this);
        Date timeFrom = (Date) inputTimeFrom.getUICValue();
        Date timeTo = (Date) inputTimeTo.getUICValue();
        dto.setActualTimeFrom(comDate.dateToString(timeFrom,Constant.TIME_FORMAT));
        dto.setActualTimeTo(comDate.dateToString(timeTo,Constant.TIME_FORMAT));
        dto.setDetailList(vwOverTimeDetailList.getDetailList());
    }
    private void bindDto2UI(){
        dto.setCause(StringUtil.nvl(dto.getCause()));
        VWUtil.bindDto2UI(dto, this);
        String timeFromStr = dto.getActualTimeFrom();
        String timeToStr = dto.getActualTimeTo();
        if(timeFromStr != null && timeToStr != null){
            Date timeFrom = comDate.toDate(timeFromStr, Constant.TIME_FORMAT);
            Date timeTo = comDate.toDate(timeToStr, Constant.TIME_FORMAT);
            inputTimeFrom.setUICValue(timeFrom);
            inputTimeTo.setUICValue(timeTo);
        }else{
            inputTimeFrom.setUICValue(null);
            inputTimeTo.setUICValue(null);
        }
        List detailList = dto.getDetailList();
        vwOverTimeDetailList.setDetailList(detailList);
        vwOverTimeDetailList.resetUI();
    }
    private boolean chechModified(){
        return dto.isChanged() || vwOverTimeDetailList.checkModified();
    }
    private boolean saveData(){
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdSave);
        inputInfo.setInputObj(DtoKey.DTO, dto);
        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == false) {

            return true;
        }
        return false;
    }
    public boolean needRefrehParent(){
        return this.needRefrehParent;
    }
    public boolean onClickOK(ActionEvent e) {
        if(vildateData()){
            convertUI2Dto();
            if(dto.getRid() == null ){//新增
                if (saveData()) {
                    needRefrehParent = true;
                    return true;
                }
            }
            if(dto.getRid() != null){//更新
                if(!chechModified()){
                    needRefrehParent = false;
                    return true;
                }else{
                    if (saveData()) {
                        needRefrehParent = true;
                        return true;
                    }
                }
            }
        }
        needRefrehParent = false;
        return false;
    }
    public boolean onClickCancel(ActionEvent e) {
        return true;
    }

    public void proccessDetailChange() {
        List detailList = dto.getDetailList();
        if(detailList != null){
            double totalHours = 0D;
            for (int i = 0; i < detailList.size() ;i ++){
                DtoOverTimeDetail detail = (DtoOverTimeDetail) detailList.get(i);
                Double hours = detail.getHours();
                totalHours += (hours == null ? 0D : hours.doubleValue());
            }
            dto.setActualTotalHours(new Double(totalHours));
            inputTotalHours.setUICValue(new BigDecimal(totalHours));
        }
    }
}

