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
     * �г�����LaborLevel��Ϣ
     * @return List
     * @throws BusinessException
     */
    public List listLaborLevel();

    /**
     * �������޸�LaborLevel��Ϣ
     * @param dtoLaborLevel DtoLaborLevel
     * @throws BusinessException
     */
    public void saveLaborLevel(DtoLaborLevel dtoLaborLevel);

    /**
     * ����LaborLevel��Ϣ
     * @param dtoLaborLevel DtoLaborLevel
     * @throws BusinessException
     */
    public void moveUpLaborLevel(DtoLaborLevel dtoLaborLevel);

    /**
     * ����LaborLevel��Ϣ
     * @param dtoLaborLevel DtoLaborLevel
     * @throws BusinessException
     */
    public void moveDownLaborLevel(DtoLaborLevel dtoLaborLevel);

    /**
     * ɾ��LaborLevel��Ϣ
     * @param rid Long
     * @throws BusinessException
     */
    public void deleteLaborLevel(DtoLaborLevel dtoLaborLevel);

    /**
     * �г�����LaborLevel��Ϣ DtoComboItem
     * @return Vector
     * @throws BusinessException
     */
    public Vector ListLaborLevelDtoComboItem();

}
