package client.essp.timesheet.admin.code.codevalue;

import c2s.essp.timesheet.code.DtoCodeValue;
import client.essp.timesheet.admin.common.VwGeneralBase;

/**
 *
 * <p>Title: Code Value General</p>
 *
 * <p>Description: ����Code Value��Ϣά��</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class VwCodeValueGeneral extends VwGeneralBase {

    private final static String actionIdSave = "FTSSaveOrUpdateCodeValue";

    /**
     * ���ر������ݵ�ActionId
     * @return String
     */
    protected String getSaveActionId() {
        return actionIdSave;
    }

    /**
     * ���ش������ݵ�Dto key
     * @return String
     */
    protected String getDtoKey() {
        return DtoCodeValue.DTO;
    }
}
