package server.essp.timesheet.account.labor.role.service;

import java.util.Vector;
import c2s.essp.timesheet.labor.role.DtoLaborRole;
import java.util.List;

/**
 * <p>Title: ILaborRoleService</p>
 *
 * <p>Description: Labor role service interface</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public interface ILaborRoleService {

    /**
     * �г�����LaborRole��Ϣ
     * @return List
     * @throws BusinessException
     */
    public List listLaborRole();

    /**
     * �������޸�LaborRole��Ϣ
     * @param dtoLaborRole DtoLaborRole
     * @throws BusinessException
     */
    public void saveLaborRole(DtoLaborRole dtoLaborRole);

    /**
     * ����LaborRole��Ϣ
     * @param dtoLaborRole DtoLaborRole
     * @throws BusinessException
     */
    public void moveUpLaborRole(DtoLaborRole dtoLaborRole);

    /**
     * ����LaborRole��Ϣ
     * @param dtoLaborRole DtoLaborRole
     * @throws BusinessException
     */
    public void moveDownLaborRole(DtoLaborRole dtoLaborRole);

    /**
     * ɾ��LaborRole��Ϣ
     * @param rid Long
     * @throws BusinessException
     */
    public void deleteLaborRole(DtoLaborRole dtoLaborRole);

    /**
     * �г�����LaborRole��Ϣ DtoComboItem
     * @return Vector
     * @throws BusinessException
     */
    public Vector ListLaborRoleDtoComboItem();

}
