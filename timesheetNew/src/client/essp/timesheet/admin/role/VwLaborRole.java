package client.essp.timesheet.admin.role;

import c2s.essp.timesheet.labor.role.DtoLaborRole;
import client.essp.timesheet.admin.common.VwGeneralBase;
import client.essp.timesheet.admin.common.VwListBase;
import client.essp.timesheet.admin.common.VwMaintenanceBase;

/**
 * <p>Title: VwLaborRole</p>
 *
 * <p>Description: Labor role main card</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class VwLaborRole extends VwMaintenanceBase {

    /**
     * 获取传递Dto的Key
     *
     * @return String
     */
    protected String getDtoKey() {
        return DtoLaborRole.DTO;
    }

    /**
     * 获取列表卡片标题
     *
     * @return VwListBase
     */
    protected String getTopTabTitle() {
        return "rsid.common.role";
    }

    /**
     * 获取常用卡片实例
     *
     * @return VwListBase
     */
    protected VwGeneralBase getVwGeneral() {
        return new VwLaborRoleGeneral();
    }

    /**
     * 获取列表卡片实例
     *
     * @return VwListBase
     */
    protected VwListBase getVwList() {
        return new VwLaborRoleList();
    }
}
