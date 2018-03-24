package server.essp.timesheet.code.codetype.service;

import java.util.List;
import java.util.Vector;

import c2s.essp.timesheet.code.DtoCodeType;

public interface ICodeTypeService {

    /**
     * ���ݼٱ������г�����CodeType��Ϣ
     * @param isLeaveType boolean
     * @return List
     * @throws BusinessException
     */
    public List listCodeType(boolean isLeaveType);

    /**
     * �������޸�CodeType��Ϣ
     * @param dtoCodeType DtoCodeType
     * @throws BusinessException
     */
    public void saveCodeType(DtoCodeType dtoCodeType);

    /**
     * ����CodeType��Ϣ
     * @param dtoCodeType DtoCodeType
     * @throws BusinessException
     */
    public void moveUpCodeType(DtoCodeType dtoCodeType);

    /**
     * ����CodeType��Ϣ
     * @param dtoCodeType DtoCodeType
     * @throws BusinessException
     */
    public void moveDownCodeType(DtoCodeType dtoCodeType);

    /**
     * ɾ��CodeType��Ϣ
     * @param rid Long
     * @throws BusinessException
     */
    public void deleteCodeType(DtoCodeType dtoCodeType);

    /**
     * �г�����CodeType��Ϣ DtoComboItem
     * @param isLeaveType boolean
     * @return Vector
     * @throws BusinessException
     */
    public Vector ListCodeTypeDtoComboItem(boolean isLeaveType);
}
