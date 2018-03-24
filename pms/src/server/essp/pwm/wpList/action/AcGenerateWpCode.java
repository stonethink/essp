package server.essp.pwm.wpList.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import c2s.essp.common.code.DtoCodeValueChoose;
import server.essp.pms.account.code.ActivityCodeConfig;
import server.essp.pms.activity.code.logic.LgActivityCode;
import server.essp.pwm.wp.logic.FPW01000CommonLogic;
import server.framework.action.AbstractBusinessAction;
import server.framework.common.BusinessException;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class AcGenerateWpCode extends AbstractBusinessAction {

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData transData) throws BusinessException {

        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();

        Long acntRid = (Long) inputInfo.getInputObj("acntRid");
        Long activityRid = (Long) inputInfo.getInputObj("activityRid");
        Long generatedWpCodeNum = FPW01000CommonLogic.generatorWpCode(getDbAccessor(), acntRid);

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
        returnInfo.setReturnObj("generatedWpCodeNum", generatedWpCodeNum);
    }
}
