package server.essp.pms.activity.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.ITreeNode;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import c2s.essp.common.code.DtoCodeValueChoose;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.pms.account.code.ActivityCodeConfig;
import server.essp.pms.activity.code.logic.LgActivityCode;
import server.essp.pms.activity.logic.LgActivity;
import server.framework.common.BusinessException;

public class AcActivityArea extends AbstractESSPAction{
    public void executeAct(HttpServletRequest request, HttpServletResponse response, TransactionData data)
        throws BusinessException
    {
        InputInfo inputInfo = data.getInputInfo();
        ReturnInfo returnInfo = data.getReturnInfo();
        Long acntRid = (Long)inputInfo.getInputObj("acntRid");
        Long activityRid = (Long)inputInfo.getInputObj("Activity");
        LgActivity activity = new LgActivity();
        ITreeNode node = (ITreeNode)activity.getActivity(acntRid,activityRid);

        //default activity code  SD-软件开发.SD_070-编码和单元测试
        Long codingUtRid = new Long(59);
        String codingUtRidStr = ActivityCodeConfig.getCodingUtRid();
        try {
            codingUtRid = new Long(codingUtRidStr);
        } catch (Exception e) {
        }

        Long activityCodeRid = codingUtRid;
        LgActivityCode logic = new LgActivityCode(acntRid, activityRid);
        List codeList = logic.list();
        if(codeList != null && codeList.size() > 0) {
            DtoCodeValueChoose dtoCode = (DtoCodeValueChoose) codeList.get(0);
            activityCodeRid = dtoCode.getValueRid();
        }
        returnInfo.setReturnObj("activityCodeRid", activityCodeRid);
        returnInfo.setReturnObj("WBSTREE", node);
    }
}
