package client.essp.timesheet.admin.level;

import client.essp.timesheet.admin.common.VwGeneralBase;
import client.essp.timesheet.admin.common.VwListBase;
import client.essp.timesheet.admin.common.VwMaintenanceBase;
import c2s.essp.timesheet.labor.level.DtoLaborLevel;

/**
 * <p>Title: VwLaborLevel</p>
 *
 * <p>Description: Labor level main card</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class VwLaborLevel extends VwMaintenanceBase {

    /**
     * ��ȡ����Dto��Key
     *
     * @return String
     */
    protected String getDtoKey() {
        return DtoLaborLevel.DTO;
    }

    /**
     * ��ȡ�б�Ƭ����
     *
     * @return VwListBase
     */
    protected String getTopTabTitle() {
        return "rsid.timesheet.laborLevel";
    }

    /**
     * ��ȡ���ÿ�Ƭʵ��
     *
     * @return VwListBase
     */
    protected VwGeneralBase getVwGeneral() {
        return new VwLaborLevelGeneral();
    }

    /**
     * ��ȡ�б�Ƭʵ��
     *
     * @return VwListBase
     */
    protected VwListBase getVwList() {
        return new VwLaborLevelList();
    }
}
