package server.essp.timesheet.account.labor.level.service;

import java.util.List;
import java.util.Vector;

import c2s.essp.timesheet.labor.level.DtoLaborLevel;

/**
 * <p>Title: ILaborLevelService</p>
 *
 * <p>Description: Labor level service interface</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public interface ILaborLevelService {

    /**
     * 列出所有LaborLevel信息
     * @return List
     * @throws BusinessException
     */
    public List listLaborLevel();

    /**
     * 新增或修改LaborLevel信息
     * @param dtoLaborLevel DtoLaborLevel
     * @throws BusinessException
     */
    public void saveLaborLevel(DtoLaborLevel dtoLaborLevel);

    /**
     * 上移LaborLevel信息
     * @param dtoLaborLevel DtoLaborLevel
     * @throws BusinessException
     */
    public void moveUpLaborLevel(DtoLaborLevel dtoLaborLevel);

    /**
     * 下移LaborLevel信息
     * @param dtoLaborLevel DtoLaborLevel
     * @throws BusinessException
     */
    public void moveDownLaborLevel(DtoLaborLevel dtoLaborLevel);

    /**
     * 删除LaborLevel信息
     * @param rid Long
     * @throws BusinessException
     */
    public void deleteLaborLevel(DtoLaborLevel dtoLaborLevel);

    /**
     * 列出所有LaborLevel信息 DtoComboItem
     * @return Vector
     * @throws BusinessException
     */
    public Vector ListLaborLevelDtoComboItem();

}
