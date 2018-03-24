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
     * 返回传递数据的Dto key
     *
     * @return String
     */
    protected String getDtoKey() {
        return DtoLaborLevel.DTO;
    }

    /**
     * 返回保存数据的ActionId
     *
     * @return String
     */
    protected String getSaveActionId() {
        return actionIdSave;
    }
}
