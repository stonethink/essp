package server.essp.timesheet.code.codetype.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import c2s.dto.DtoComboItem;
import c2s.dto.DtoUtil;
import c2s.essp.timesheet.code.DtoCodeType;
import db.essp.timesheet.TsCodeType;
import server.essp.timesheet.code.codetype.dao.ICodeTypeDao;
import server.framework.common.BusinessException;

public class CodeTypeServiceImp implements ICodeTypeService{
    private ICodeTypeDao codeTypeDao;

    /**
     * 列出所有CodeType信息
     * @param isLeaveType boolean
     * @return List
     * @throws BusinessException
     */
    public List listCodeType(boolean isLeaveType) {
    	if(isLeaveType) {
    		return beanList2DtoList(codeTypeDao.listCodeType(DtoCodeType.DTO_LEAVE_TYPE));
    	} else {
    		return beanList2DtoList(codeTypeDao.listCodeType(DtoCodeType.DTO_NON_LEAVE_TYPE));
    	}
        
    }

    /**
     * 新增或修改CodeType信息
     * @param dtoCodeType DtoCodeType
     * @throws BusinessException
     */
    public void saveCodeType(DtoCodeType dtoCodeType) {
        if(dtoCodeType.isInsert()) {
            dtoCodeType.setSeq(codeTypeDao.getMaxSeq() + 1);
            TsCodeType bean = dto2Bean(dtoCodeType);
            codeTypeDao.add(bean);
            DtoUtil.copyBeanToBean(dtoCodeType, bean);
        } else {
            codeTypeDao.update(dto2Bean(dtoCodeType));
        }
    }

    /**
     * 删除CodeType信息
     * @param rid Long
     * @throws BusinessException
     */
    public void deleteCodeType(DtoCodeType dtoCodeType) {
        codeTypeDao.delete(dto2Bean(dtoCodeType));
    }

    /**
     * 上移CodeType信息
     * @param dtoCodeType DtoCodeType
     * @throws BusinessException
     */
    public void moveUpCodeType(DtoCodeType dtoCodeType) {
        TsCodeType beanUp = codeTypeDao.getUpSeqCodeType(dtoCodeType.getSeq());
        if(beanUp == null) {
            throw new BusinessException("error.logic.common.isUppermost",
                                        "This code type is uppermost");
        }
        codeTypeDao.setSeq(dtoCodeType.getRid(), beanUp.getSeq());
        codeTypeDao.setSeq(beanUp.getRid(), dtoCodeType.getSeq());
    }

    /**
     * 下移CodeType信息
     * @param dtoCodeType DtoCodeType
     * @throws BusinessException
     */
    public void moveDownCodeType(DtoCodeType dtoCodeType) {
        TsCodeType beanDown = codeTypeDao.getDownSeqCodeType(dtoCodeType.getSeq());
        if(beanDown == null) {
            throw new BusinessException("error.logic.common.isDownmost",
                                        "This code type is downmost");
        }
        codeTypeDao.setSeq(dtoCodeType.getRid(), beanDown.getSeq());
        codeTypeDao.setSeq(beanDown.getRid(), dtoCodeType.getSeq());
    }

    /**
     * 列出所有CodeType信息 DtoComboItem
     * @param isLeaveType boolean
     * @return Vector
     * @throws BusinessException
     */
    public Vector ListCodeTypeDtoComboItem(boolean isLeaveType) {
    	if(isLeaveType) {
    		return beanList2DtoComboItemList(codeTypeDao.listCodeType(DtoCodeType.DTO_LEAVE_TYPE));
    	} else {
    		return beanList2DtoComboItemList(codeTypeDao.listCodeType(DtoCodeType.DTO_NON_LEAVE_TYPE));
    	}
        
    }

    /**
     * 将TsCodeType list转换成DtoCodeType list
     * @param beanList List
     * @return List
     */
    private static List beanList2DtoList(List<TsCodeType> beanList) {
        List dtoList = new ArrayList();
        for(TsCodeType bean : beanList) {
            dtoList.add(bean2Dto(bean));
        }
        return dtoList;
    }

    /**
     * 将TsCodeType list转换成DtoComboItem list
     * @param beanList List
     * @return Vector
     */
    private static Vector beanList2DtoComboItemList(List<TsCodeType> beanList) {
        Vector dtoList = new Vector(beanList.size());
        for(TsCodeType bean : beanList) {
            if(bean.getIsEnable()) {
                DtoComboItem dto = new DtoComboItem(bean.getName()+" -- "+bean.getDescription(), 
                		                            bean.getRid());
                dtoList.add(dto);
            }
        }
        return dtoList;
    }

    /**
     * 将TsCodeType转换成DtoCodeType
     * @param bean TsCodeType
     * @return DtoCodeType
     */
    private static DtoCodeType bean2Dto(TsCodeType bean) {
        DtoCodeType dto = new DtoCodeType();
        DtoUtil.copyBeanToBean(dto, bean);
        return dto;
    }

    /**
     * 将DtoCodeType转换成TsCodeType
     * @param dto DtoCodeType
     * @return TsCodeType
     */
    private static TsCodeType dto2Bean(DtoCodeType dto) {
        TsCodeType bean = new TsCodeType();
        DtoUtil.copyBeanToBean(bean, dto);
        return bean;
    }

    public void setCodeTypeDao(ICodeTypeDao codeTypeDao) {
        this.codeTypeDao = codeTypeDao;
    }
}
