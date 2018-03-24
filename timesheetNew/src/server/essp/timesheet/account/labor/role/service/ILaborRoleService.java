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
     * 列出所有LaborRole信息
     * @return List
     * @throws BusinessException
     */
    public List listLaborRole();

    /**
     * 新增或修改LaborRole信息
     * @param dtoLaborRole DtoLaborRole
     * @throws BusinessException
     */
    public void saveLaborRole(DtoLaborRole dtoLaborRole);

    /**
     * 上移LaborRole信息
     * @param dtoLaborRole DtoLaborRole
     * @throws BusinessException
     */
    public void moveUpLaborRole(DtoLaborRole dtoLaborRole);

    /**
     * 下移LaborRole信息
     * @param dtoLaborRole DtoLaborRole
     * @throws BusinessException
     */
    public void moveDownLaborRole(DtoLaborRole dtoLaborRole);

    /**
     * 删除LaborRole信息
     * @param rid Long
     * @throws BusinessException
     */
    public void deleteLaborRole(DtoLaborRole dtoLaborRole);

    /**
     * 列出所有LaborRole信息 DtoComboItem
     * @return Vector
     * @throws BusinessException
     */
    public Vector ListLaborRoleDtoComboItem();

}
