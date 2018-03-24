package client.essp.timesheet.admin.role;

import c2s.essp.timesheet.labor.role.DtoLaborRole;
import client.essp.timesheet.admin.common.VwGeneralBase;

/**
 * <p>Title: VwLaborRoleGeneral</p>
 *
 * <p>Description: Labor role general card</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class VwLaborRoleGeneral extends VwGeneralBase {

    private final static String actionIdSave = "FTSSaveLaborRole";

    /**
     * ���ش������ݵ�Dto key
     *
     * @return String
     */
    protected String getDtoKey() {
        return DtoLaborRole.DTO;
    }

    /**
     * ���ر������ݵ�ActionId
     *
     * @return String
     */
    protected String getSaveActionId() {
        return actionIdSave;
    }
}
