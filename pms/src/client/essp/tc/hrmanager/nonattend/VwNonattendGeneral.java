package client.essp.tc.hrmanager.nonattend;

import client.framework.view.vwcomp.IVWPopupEditorEvent;
import c2s.dto.ReturnInfo;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.math.BigDecimal;
import com.wits.util.Parameter;
import java.util.Date;
import c2s.essp.common.code.DtoKey;
import java.awt.event.ActionListener;
import client.framework.view.vwcomp.VWUtil;
import c2s.dto.InputInfo;
import c2s.essp.tc.weeklyreport.DtoTcKey;
import client.framework.view.common.comFORM;
import client.framework.view.common.comMSG;
import com.wits.util.StringUtil;
import java.awt.event.FocusListener;
import com.wits.util.comDate;
import c2s.essp.tc.nonattend.DtoNonattend;
import java.util.Calendar;
import c2s.dto.DtoUtil;

public class VwNonattendGeneral extends VwNonattendGeneralBase implements
        IVWPopupEditorEvent{
    static final String actionIdInit = "FTCHrManageNonattendInitAction";
    static final String actionIdCaculate = "FTCHrManageNonattendCaculateAction";
    static final String actionIdSave = "FTCHrManageNonattendSaveAction";

    private DtoNonattend dto;
    private Date beginPeriod;
    private Date endPeriod;
    private boolean reCaculate = false;//提交前是否要重新计算
    private boolean needRefrehParent = false;//提交后是否刷新父窗口

    public VwNonattendGeneral() {
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
        this.dto = (DtoNonattend) (param.get(DtoKey.DTO));
        reCaculate = false;
        needRefrehParent = false;
    }

    public void resetUI(){
        InputInfo inputInfo = new InputInfo();
//        inputInfo.setActionId(this.actionIdInit);
//        inputInfo.setInputObj(DtoTcKey.USER_ID, this.dto);
//        ReturnInfo returnInfo = accessData(inputInfo);
        bindDto2UI();

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
                if(!checkModified()){
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
        calculate.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                actionPerformedCaculate();
            }
        });

        //判断时间是否修改了,是否需重新计算
        inputTimeFrom.addFocusListener(new FocusListener(){
            public void focusGained(FocusEvent e) {
            }
            public void focusLost(FocusEvent e) {
                Date inputTime = (Date) inputTimeFrom.getUICValue();
                Date dtoDate = dto.getTimeFrom();
               if(!comDate.dateToString(inputTime)
                  .equals(comDate.dateToString(dtoDate))){
                    reCaculate = true;
                }
            }
        });
        inputTimeTo.addFocusListener(new FocusListener(){
            public void focusGained(FocusEvent e) {
            }
            public void focusLost(FocusEvent e) {
                Date inputTime = (Date) inputTimeTo.getUICValue();
                Date dtoDate = dto.getTimeTo();
               if(!comDate.dateToString(inputTime)
                  .equals(comDate.dateToString(dtoDate))){
                    reCaculate = true;
                }
            }
        });
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
                DtoNonattend returnDto = (DtoNonattend)returnInfo.getReturnObj(DtoKey.DTO);

                DtoUtil.copyBeanToBean(dto, returnDto);


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
        Date date = (Date) inputDate.getUICValue();
        Date timeFrom = (Date) inputTimeFrom.getUICValue();
        Date timeTo = (Date) inputTimeTo.getUICValue();
        if(date == null){
            comMSG.dispErrorDialog("Please fill in date!");
            comFORM.setFocus(inputDate);
            return false;
        }else if(timeFrom == null){
            comMSG.dispErrorDialog("Please fill in time From!");
            comFORM.setFocus(inputTimeFrom);
            return false;
        }else if(timeTo == null){
            comMSG.dispErrorDialog("Please fill in time To!");
            comFORM.setFocus(inputTimeTo);
            return false;
        }else if(timeFrom.getTime()>timeTo.getTime()){
            comMSG.dispErrorDialog("The time To date must be after time From !");
            comFORM.setFocus(inputTimeTo);
            return false;
        }else if(beginPeriod.getTime() > date.getTime() ||
                 endPeriod.getTime() < date.getTime()){
             comMSG.dispErrorDialog("The nonattend period must be between "+
                                    comDate.dateToString(beginPeriod)+" and " +
                                    comDate.dateToString(endPeriod)+"!");
             comFORM.setFocus(inputDate);
             return false;
         }else if("00:00".equalsIgnoreCase(comDate.dateToString(timeTo,"HH:mm"))){
             comMSG.dispErrorDialog("please fill with 23:59 !");
             comFORM.setFocus(inputTimeTo);
             return false;

         }

        return true;
    }
    private boolean vildateData(){
       BigDecimal hours = (BigDecimal)inputTotalHours.getUICValue();
       Object timeFrom = inputTimeFrom.getUICValue();
       Object timeTo = inputTimeTo.getUICValue();
       Object date = inputDate.getUICValue();
       if(date==null){
           comMSG.dispErrorDialog("Please fill in date!");
           comFORM.setFocus(inputDate);
           return false;
       }
       if(timeFrom==null){
           comMSG.dispErrorDialog("Please fill in time From!");
           comFORM.setFocus(inputTimeFrom);
           return false;
       }
       if(timeTo==null){
           comMSG.dispErrorDialog("Please fill in time To!");
           comFORM.setFocus(inputTimeTo);
           return false;
       }


        if(hours == null){
            comMSG.dispErrorDialog("Please calculate nonattend hours!");
            comFORM.setFocus(inputTotalHours);
            return false;
        }else if(reCaculate){
            comMSG.dispErrorDialog("Please ReCalculate nonattend hours!");
            comFORM.setFocus(calculate);
            return false;
        }
        return true;
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
    private boolean checkModified(){
        return dto.isChanged() ;
    }

    private void convertUI2Dto(){
        Date date=(Date) inputDate.getUICValue();
        Date timeFrom=(Date)inputTimeFrom.getUICValue();
        Calendar calDateFrom=Calendar.getInstance();
        Calendar calDateTo=Calendar.getInstance();
        calDateFrom.setTime(date);
        calDateFrom.set(Calendar.HOUR,timeFrom.getHours());
        calDateFrom.set(Calendar.MINUTE,timeFrom.getMinutes());
        Date timeTo=(Date)inputTimeTo.getUICValue();
        dto.setDateFrom(calDateFrom.getTime());
        calDateTo.setTime(date);
        calDateTo.set(Calendar.HOUR,timeTo.getHours());
        calDateTo.set(Calendar.MINUTE,timeTo.getMinutes());
        dto.setDateTo(calDateTo.getTime());
        VWUtil.convertUI2Dto(dto,this);

    }
    private void bindDto2UI(){
        dto.setRemark(StringUtil.nvl(dto.getRemark()));

        VWUtil.bindDto2UI(dto, this);
//
//        Date dateFrom = dto.getDateFrom();
//        Date dateTo = dto.getDateTo();
        Date date=dto.getDate();
        Date timeFrom =dto.getTimeFrom() ;
        Date timeTo =dto.getTimeTo();
        if(timeFrom != null && timeTo != null){
            inputDate.setUICValue(date);
            inputTimeFrom.setUICValue(timeFrom);
            inputTimeTo.setUICValue(timeTo);
        }else{
            inputDate.setUICValue(null);
            inputTimeFrom.setUICValue(null);
            inputTimeTo.setUICValue(null);
        }

    }

}
