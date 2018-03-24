package client.essp.tc.weeklyreport;

import c2s.essp.tc.weeklyreport.DtoTcKey;
import com.wits.util.Parameter;

public class VwWeeklyReportGeneralByPm extends VwWeeklyReportGeneral {

    Long acntRid = null;

    public void setParameter(Parameter param) {
        super.setParameter(param);

        this.acntRid = (Long) param.get(DtoTcKey.ACNT_RID);

        if (acntRid == null) {
            throw new RuntimeException("Parameter - dtoweeklyreport or accountRid is null.");
        }
    }

    protected void resetUI() {
        super.resetUI();

        this.vwBelongTo.cmbAcntCode.setUICValue(acntRid);
    }

    protected void setEnabledMode() {
        super.setEnabledMode();

        this.vwBelongTo.cmbAcntCode.setEnabled(false);
        this.vwBelongTo.cmbAcntName.setEnabled(false);

        this.txaComments.setEnabled(true);
    }
}
