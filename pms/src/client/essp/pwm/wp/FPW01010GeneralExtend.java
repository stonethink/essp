package client.essp.pwm.wp;

import c2s.essp.pwm.wp.DtoPwWp;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;

public class FPW01010GeneralExtend extends FPW01010General{
    public void setParameter(Long wpId, Long activityRid){
         DtoPwWp dtoPwWp = getWp(wpId);
         super.setParameter(dtoPwWp, activityRid);
    }

    private DtoPwWp getWp(Long wpId){
        DtoPwWp dtoPwWp = null;
        if( wpId != null ){
            InputInfo inputInfo = new InputInfo();
            inputInfo.setFunId("getWpInfo");
            inputInfo.setInputObj("inWpId", wpId);

            ReturnInfo returnInfo = accessData(inputInfo);

            if (returnInfo.isError() == false) {
                dtoPwWp = (DtoPwWp) returnInfo.getReturnObj("dtoPwWp");
            }
        }

        return dtoPwWp;
    }
}
