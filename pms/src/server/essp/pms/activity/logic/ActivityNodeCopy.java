package server.essp.pms.activity.logic;

import c2s.essp.pms.wbs.DtoWbs;
import c2s.essp.pwm.wp.DtoActivity;
import c2s.essp.pms.wbs.DtoWbsActivity;

public interface ActivityNodeCopy {
    public DtoWbsActivity copyActivity(DtoWbsActivity srcActivity,DtoWbsActivity desActivity);
}
