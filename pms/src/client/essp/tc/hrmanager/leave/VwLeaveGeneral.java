package client.essp.tc.hrmanager.leave;

import c2s.essp.tc.leave.DtoLeave;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Date;
import client.framework.view.common.comMSG;
import client.framework.view.common.comFORM;
import com.wits.util.comDate;
import c2s.essp.common.code.DtoKey;
import c2s.essp.tc.weeklyreport.DtoTcKey;
import com.wits.util.Parameter;
import c2s.dto.ReturnInfo;
import client.framework.model.VMComboBox;
import c2s.dto.InputInfo;
import java.util.Vector;
import com.wits.util.StringUtil;
import client.framework.view.vwcomp.VWUtil;
import client.framework.view.vwcomp.IVWPopupEditorEvent;
import java.util.Map;
import c2s.dto.DtoComboItem;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.math.BigDecimal;
import c2s.essp.attendance.Constant;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.util.List;
import client.essp.tc.hrmanager.overtime.DetailChangedListener;
import c2s.essp.tc.leave.DtoLeaveDetail;

public class VwLeaveGeneral extends VwLeaveGeneralBase implements
        IVWPopupEditorEvent,DetailChangedListener{
    static final String actionIdInit = "FTCHrManageLeaveInitAction";
    static final String actionIdCaculate = "FTCHrManageLeaveCaculateAction";
    static final String actionIdSave = "FTCHrManageLeaveSaveAction";

    private DtoLeave dto;
    private Date beginPeriod;
    private Date endPeriod;
    private boolean reCaculate = false;//提交前是否要重新计算
    private boolean needRefrehParent = false;//提交后是否刷新父窗口
    private Map leaveUsableMap;
    private Map leaveMaxHoursMap;
    public VwLeaveGeneral() {
        super();
        try {
            addUICEvent();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setParameter(Parameter param) {
        super.setParameter(param);
        this.beginPeriod = (Date) (param.get(DtoTcKey.BEGIN_PERIOD));
        this.endPeriod = (Date) (param.get(DtoTcKey.END_PERIOD));
        //新增传入的Dto只有LoginId
        this.dto = (DtoLeave) (param.get(DtoKey.DTO));
        reCaculate = false;
        needRefrehParent = false;
    }

    public void resetUI(){
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdInit);
        inputInfo.setInputObj(DtoTcKey.USER_ID, this.dto);
        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == false) {
//            String organization = (String) returnInfo.getReturnObj("organization");
//            dto.setOrgName(organization);
            leaveUsableMap = (Map) returnInfo.getReturnObj("leaveUsableMap");
            leaveMaxHoursMap = (Map) returnInfo.getReturnObj("leaveMaxHoursMap");
            Vector leaveTypeComList = (Vector) returnInfo.getReturnObj("leaveTypeComList");
            VMComboBox vmLeaveType  = new VMComboBox(leaveTypeComList);
            inputLeaveType.setVMComboBox(vmLeaveType);
        }
        bindDto2UI();
        //新增可修改LeaveType,更新不能修改LeaveType
        inputLeaveType.setEnabled(dto.getRid() == null);
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
    private void addUICEvent(){
        btCaculate.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                actionPerformedCaculate();
            }
        });
        inputLeaveType.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e) {
                actionPerformedChooseLeaveType();
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
        vwLeaveDetailList.addDetailChangeListener(this);
    }
    //选择假别事件
    private void actionPerformedChooseLeaveType(){
        DtoComboItem leaveItem = (DtoComboItem) inputLeaveType.getModel().getSelectedItem();
        if(leaveItem != null){
            String leaveName = leaveItem.getItemName();
            Object usableHours = leaveUsableMap.get(leaveName);
            inputUsableHours.setUICValue(StringUtil.nvl(usableHours));
            Object settlementWay = leaveItem.getItemRelation();
            inputSettlteMentWay.setUICValue(settlementWay);
            Double maxHours = (Double) leaveMaxHoursMap.get(leaveName);
            if(maxHours != null)
                inputMaxHours.setUICValue(new BigDecimal(maxHours.doubleValue()));
        }
    }
    //重新计算事件
    private void actionPerformedCaculate(){
        if(validateDate()){
            convertUI2Dto();
            InputInfo inputInfo = new InputInfo();
            inputInfo.setActionId(this.actionIdCaculate);
            inputInfo.setInputObj(DtoKey.DTO, dto);
            ReturnInfo returnInfo = accessData(inputInfo);
            if (returnInfo.isError() == false) {
                dto = (DtoLeave) returnInfo.getReturnObj(DtoKey.DTO);
                bindDto2UI();
                reCaculate = false;
            }
        }
    }
    /**
     * 日期验证:
     * 1.所有日期时间栏位不能为空
     * 2.起始日期小于结束日期
     * 3.在选择的时间区间内
     */
    private boolean validateDate() {
        Date dateFrom = (Date) inputDateFrom.getUICValue();
        Date dateTo = (Date) inputDateTo.getUICValue();
        Date timeFrom = (Date) inputTimeFrom.getUICValue();
        Date timeTo = (Date) inputTimeTo.getUICValue();
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
    private boolean vildateData(){
        Object leaveType = inputLeaveType.getUICValue();
        BigDecimal hours = (BigDecimal)inputTotalHours.getUICValue();
        if(leaveType == null){
            comMSG.dispErrorDialog("Please select a leave type!");
            comFORM.setFocus(inputLeaveType);
            return false;
        }else if(hours == null){
            comMSG.dispErrorDialog("Please calculate leave hours!");
            comFORM.setFocus(inputTotalHours);
            return false;
        }else if(reCaculate){
            comMSG.dispErrorDialog("Please ReCalculate leave hours!");
            comFORM.setFocus(btCaculate);
            return false;
        }else{
            Object obj = inputUsableHours.getUICValue();
            BigDecimal maxHours =  (BigDecimal) inputMaxHours.getUICValue();
            double appHours = hours == null ? 0D : hours.doubleValue();
            if(Constant.INFINITE_USABLE_HOURS.equals(StringUtil.nvl(obj))){
                return true;
            }
            double usableHours = Double.parseDouble(StringUtil.nvl(obj));
            if(usableHours < appHours){
                comMSG.dispErrorDialog("You don't have enough hours for "+leaveType+"!");
                return false;
            }
            if(maxHours.doubleValue() != 0d && maxHours.doubleValue() < appHours){
                comMSG.dispErrorDialog("The applying hours is larger than the max hours ("+maxHours+") of this leave!");
                return false;
            }
            return true;
        }
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
    private boolean chechModified(){
        return dto.isChanged() || vwLeaveDetailList.checkModified();
    }

    private void convertUI2Dto(){
        VWUtil.convertUI2Dto(dto,this);
        Date timeFrom = (Date) inputTimeFrom.getUICValue();
        Date timeTo = (Date) inputTimeTo.getUICValue();
        dto.setActualTimeFrom(comDate.dateToString(timeFrom,Constant.TIME_FORMAT));
        dto.setActualTimeTo(comDate.dateToString(timeTo,Constant.TIME_FORMAT));
    }
    private void bindDto2UI(){
        dto.setCause(StringUtil.nvl(dto.getCause()));
        VWUtil.bindDto2UI(dto, this);
        actionPerformedChooseLeaveType();
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
        vwLeaveDetailList.setDetailList(detailList);
        vwLeaveDetailList.resetUI();
    }

    public void proccessDetailChange() {
        List detailList = dto.getDetailList();
        if(detailList != null && detailList.size() != 0 ){
            double totalHours = 0D;
            for (int i = 0; i < detailList.size() ;i ++){
                DtoLeaveDetail detail = (DtoLeaveDetail) detailList.get(i);
                Double hours = detail.getHours();
                totalHours += (hours == null ? 0D : hours.doubleValue());
            }
            dto.setActualTotalHours(new Double(totalHours));
            inputTotalHours.setUICValue(new BigDecimal(totalHours));
        }
    }
}
