package client.essp.timesheet.admin.code.codetype;

import c2s.essp.timesheet.code.DtoCodeType;
import client.essp.timesheet.admin.common.VwGeneralBase;

/**
 *
 * <p>Title: Code Type General</p>
 *
 * <p>Description: Code Type 维护界面</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class VwCodeTypeGeneral extends VwGeneralBase {

    private final static String actionIdSave = "FTSSaveOrUpdateCodeType";

    /**
     * 返回保存数据的ActionId
     * @return String
     */
    protected String getSaveActionId() {
        return actionIdSave;
    }

    /**
     * 返回传递数据的Dto key
     * @return String
     */
    protected String getDtoKey() {
        return DtoCodeType.DTO;
    }
}
