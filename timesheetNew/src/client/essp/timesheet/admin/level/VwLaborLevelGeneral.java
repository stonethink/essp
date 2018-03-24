package client.essp.timesheet.admin.level;

import c2s.essp.timesheet.labor.level.DtoLaborLevel;
import client.essp.timesheet.admin.common.VwGeneralBase;

/**
 * <p>Title: VwLaborLevelGeneral</p>
 *
 * <p>Description: Labor level general card</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class VwLaborLevelGeneral extends VwGeneralBase {

    private final static String actionIdSave = "FTSSaveLaborLevel";

    /**
     * ���ش������ݵ�Dto key
     *
     * @return String
     */
    protected String getDtoKey() {
        return DtoLaborLevel.DTO;
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
