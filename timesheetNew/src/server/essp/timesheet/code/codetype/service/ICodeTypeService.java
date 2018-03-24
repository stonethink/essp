package server.essp.timesheet.code.codetype.service;

import java.util.List;
import java.util.Vector;

import c2s.essp.timesheet.code.DtoCodeType;

public interface ICodeTypeService {

    /**
     * 根据假别类型列出所有CodeType信息
     * @param isLeaveType boolean
     * @return List
     * @throws BusinessException
     */
    public List listCodeType(boolean isLeaveType);

    /**
     * 新增或修改CodeType信息
     * @param dtoCodeType DtoCodeType
     * @throws BusinessException
     */
    public void saveCodeType(DtoCodeType dtoCodeType);

    /**
     * 上移CodeType信息
     * @param dtoCodeType DtoCodeType
     * @throws BusinessException
     */
    public void moveUpCodeType(DtoCodeType dtoCodeType);

    /**
     * 下移CodeType信息
     * @param dtoCodeType DtoCodeType
     * @throws BusinessException
     */
    public void moveDownCodeType(DtoCodeType dtoCodeType);

    /**
     * 删除CodeType信息
     * @param rid Long
     * @throws BusinessException
     */
    public void deleteCodeType(DtoCodeType dtoCodeType);

    /**
     * 列出所有CodeType信息 DtoComboItem
     * @param isLeaveType boolean
     * @return Vector
     * @throws BusinessException
     */
    public Vector ListCodeTypeDtoComboItem(boolean isLeaveType);
}
