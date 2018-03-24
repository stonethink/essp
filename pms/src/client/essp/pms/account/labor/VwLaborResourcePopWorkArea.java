package client.essp.pms.account.labor;

import java.awt.Dimension;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.pms.account.DtoAcntLoaborRes;
import client.essp.common.view.VWGeneralWorkArea;
import client.framework.view.common.comMSG;
import com.wits.util.Parameter;
import client.framework.view.common.comFORM;
import java.util.Date;
import com.wits.util.ProcessVariant;
import java.util.List;
import c2s.dto.DtoBase;
import c2s.essp.pms.account.DtoAcntLaborResDetail;

public class VwLaborResourcePopWorkArea extends VWGeneralWorkArea {
    public static final String ACTIONID_RES_UPDATE = "FAcntLaborResourceUpdateAction";
    public static final String ACTIONID_RES_ADD = "FAcntLaborResourceAddAction";
    private VwAcntLaborResourceGeneral worker = new VwAcntLaborResourceGeneral();

    public VwLaborResourcePopWorkArea() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void jbInit() throws Exception {
        this.setPreferredSize(new Dimension(780, 500));
        this.addTab("Worker", worker, true);
    }

    public void setParameter(Parameter param) {
         super.setParameter(param);
         worker.setParameter(param);
    }

    public void resetUI(){
        worker.resetUI();
    }

    public DtoAcntLoaborRes getDto(){
       return worker.getDto();
    }
    /**
     * 保存或更新Labor Resource
     * @return boolean
     */
    public boolean saveOrUpdate(){
        boolean isSuccess = true;
        DtoAcntLoaborRes dto = worker.getDto();
        if(dto == null || !checkModified(dto))
            return isSuccess;
        if( vaildateData(dto)) {
            if(dto.getRid() == null)
                isSuccess = save(dto);
            else
                isSuccess = update(dto);
        }else{
            isSuccess = false;
        }
        return isSuccess;
    }
    //验证数据
    private boolean vaildateData(DtoAcntLoaborRes dto) {
        if(  "".equals(dto.getLoginId()) || "".equals(dto.getEmpName()) ||
             dto.getJobcodeId() == null || "".equals(dto.getJobcodeId()) || "0".equals(dto.getJobcodeId())){
            comMSG.dispErrorDialog("Please Fill loginId,name and jobcode!");
            return false;
        }
        Date begin = dto.getPlanStart();
        Date end = dto.getPlanFinish();
        if(begin == null || end == null){
            comMSG.dispErrorDialog(
                "Please Fill begin date and end date!");
            return false;
        }
        if (begin.after(end)) {
            comMSG.dispErrorDialog(
                "End date must be larger than begin date!");
            return false;
        }
        List planDetailList = dto.getPlanDetail();
        if( planDetailList == null)
            return true;
        StringBuffer msg = new StringBuffer();
        for(int i = 0;i < planDetailList.size() ;i ++){
            DtoAcntLaborResDetail detail = (DtoAcntLaborResDetail) planDetailList.get(i);
            begin = detail.getBeginDate();
            end = detail.getEndDate();
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
    //验证数据是否修改过
    private boolean checkModified(DtoAcntLoaborRes dto){
        if(dto == null)
            return false;
        if(dto.isChanged())
            return true;
        List detail = dto.getPlanDetail();
        if(detail == null || detail.size() == 0)
            return false;
        for(int i = 0;i < detail.size() ; i ++){
            DtoBase d = (DtoBase) detail.get(i);
            if(d.isChanged())
                return true;
        }
        return false;
    }
    //至Server端更新操作
    private boolean update(DtoAcntLoaborRes dto){
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.ACTIONID_RES_UPDATE);
        inputInfo.setInputObj("dto", dto);
        ReturnInfo returnInfo = accessData(inputInfo);
        if(!returnInfo.isError()){
            return true;
        }else{
            comFORM.setFocus(worker.inputLoginId);
            return false;
        }
    }
    //至Server端新增操作
    private boolean save(DtoAcntLoaborRes dto){
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.ACTIONID_RES_ADD);
        inputInfo.setInputObj("dto", dto);
        ReturnInfo returnInfo = accessData(inputInfo);
        if(!returnInfo.isError()){
            ProcessVariant.fireDataChange("labor");
            return true;
        }else{
            comFORM.setFocus(worker.inputLoginId);
            return false;
        }
    }
}
