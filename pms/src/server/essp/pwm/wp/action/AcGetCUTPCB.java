package server.essp.pwm.wp.action;

import server.essp.framework.action.AbstractESSPAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.framework.common.BusinessException;
import server.essp.pms.account.pcb.logic.LgPcb;
import db.essp.pms.PmsPcbParameter;
import c2s.dto.ReturnInfo;
import java.math.BigDecimal;
import server.essp.pms.quality.QualityConfig;
import server.essp.pms.account.code.ActivityCodeConfig;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class AcGetCUTPCB extends AbstractESSPAction{


    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data)
        throws BusinessException {
        
        Long acntRid = (Long) data.getInputInfo().getInputObj("acntRid");
        //if no account rid input, get current account rid
        if(acntRid == null) {
            acntRid = getAccount().getRid();
        }
        //get PCB
        LgPcb lg = new LgPcb();
        PmsPcbParameter defectRateP =
            lg.findFromPcb(acntRid, QualityConfig.getCutDefectRatePcbId());
        PmsPcbParameter densityP =
            lg.findFromPcb(acntRid, QualityConfig.getCutDensityPcbId());

        ReturnInfo returnInfo = new ReturnInfo();
        BigDecimal density = new BigDecimal(0);
        BigDecimal defectRate = new BigDecimal(0);
        String densityUnit = "";
        String defectRateUnit = "";
        String sizeUnit = "";

        if(defectRateP != null) {
            defectRate = new BigDecimal(defectRateP.getPlan());
            defectRateUnit = defectRateP.getUnit();
        }
        if(densityP != null) {
            density = new BigDecimal(densityP.getPlan());
            densityUnit = densityP.getUnit();
        }
        if(defectRateUnit != null && defectRateUnit.indexOf("/") > 0) {
            sizeUnit = defectRateUnit.substring(defectRateUnit.indexOf("/") + 1);
        }

        Long codingUtRid = new Long(59);
        String codingUtRidStr = ActivityCodeConfig.getCodingUtRid();
        try {
            codingUtRid = new Long(codingUtRidStr);
        } catch (Exception e) {
        }
        returnInfo.setReturnObj("density", density);
        returnInfo.setReturnObj("defectRate", defectRate);
        returnInfo.setReturnObj("densityUnit", densityUnit);
        returnInfo.setReturnObj("defectRateUnit", defectRateUnit);
        returnInfo.setReturnObj("sizeUnit", sizeUnit);
        returnInfo.setReturnObj("codingUtRid", codingUtRid);
        data.setReturnInfo(returnInfo);
    }
}
