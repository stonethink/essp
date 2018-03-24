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
     * ��ȡ����Dto��Key
     *
     * @return String
     */
    protected String getDtoKey() {
        return DtoLaborRole.DTO;
    }

    /**
     * ��ȡ�б�Ƭ����
     *
     * @return VwListBase
     */
    protected String getTopTabTitle() {
        return "rsid.common.role";
    }

    /**
     * ��ȡ���ÿ�Ƭʵ��
     *
     * @return VwListBase
     */
    protected VwGeneralBase getVwGeneral() {
        return new VwLaborRoleGeneral();
    }

    /**
     * ��ȡ�б�Ƭʵ��
     *
     * @return VwListBase
     */
    protected VwListBase getVwList() {
        return new VwLaborRoleList();
    }
}
