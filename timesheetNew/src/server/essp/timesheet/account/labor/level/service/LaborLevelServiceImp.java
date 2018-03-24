package server.essp.timesheet.account.labor.level.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import c2s.dto.DtoComboItem;
import c2s.dto.DtoUtil;
import c2s.essp.timesheet.labor.level.DtoLaborLevel;
import db.essp.timesheet.TsLaborLevel;
import server.essp.timesheet.account.labor.level.dao.ILaborLevelDao;
import server.framework.common.BusinessException;

/**
 * <p>Title: LaborLevelServiceImp</p>
 *
 * <p>Description: Labor level service implement</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class LaborLevelServiceImp implements ILaborLevelService {

    private ILaborLevelDao laborLevelDao;
    private static final String firstOption = "--Please Select--";

    /**
     * 列出所有LaborLevel信息 DtoComboItem
     *
     * @return Vector
     */
    public Vector ListLaborLevelDtoComboItem() {
        return beanList2DtoComboItemList(laborLevelDao.listLaborLevel());
    }

    /**
     * 删除LaborLevel信息
     *
     * @param dtoLaborLevel Long
     */
    public void deleteLaborLevel(DtoLaborLevel dtoLaborLevel) {
        laborLevelDao.delete(dto2Bean(dtoLaborLevel));
    }

    /**
     * 列出所有LaborLevel信息
     *
     * @return List
     */
    public List listLaborLevel() {
        return beanList2DtoList(laborLevelDao.listLaborLevel());
    }

    /**
     * 下移LaborLevel信息
     *
     * @param dtoLaborLevel DtoLaborLevel
     */
    public void moveDownLaborLevel(DtoLaborLevel dtoLaborLevel) {
        TsLaborLevel beanDown = laborLevelDao.getDownSeqLaborLevel(dtoLaborLevel.getSeq());
        if(beanDown == null) {
            throw new BusinessException("error.logic.common.isDownmost",
                                        "This labor level is downmost");
        }
        laborLevelDao.setSeq(dtoLaborLevel.getRid(), beanDown.getSeq());
        laborLevelDao.setSeq(beanDown.getRid(), dtoLaborLevel.getSeq());

    }

    /**
     * 上移LaborLevel信息
     *
     * @param dtoLaborLevel DtoLaborLevel
     */
    public void moveUpLaborLevel(DtoLaborLevel dtoLaborLevel) {
        TsLaborLevel beanUp = laborLevelDao.getUpSeqLaborLevel(dtoLaborLevel.getSeq());
        if(beanUp == null) {
            throw new BusinessException("error.logic.common.isUppermost",
                                        "This labor level is uppermost");
        }
        laborLevelDao.setSeq(dtoLaborLevel.getRid(), beanUp.getSeq());
        laborLevelDao.setSeq(beanUp.getRid(), dtoLaborLevel.getSeq());
    }

    /**
     * 新增或修改LaborLevel信息
     *
     * @param dtoLaborLevel DtoLaborLevel
     */
    public void saveLaborLevel(DtoLaborLevel dtoLaborLevel) {
        if(dtoLaborLevel.isInsert()) {
            dtoLaborLevel.setSeq(laborLevelDao.getMaxSeq() + 1);
            TsLaborLevel bean = dto2Bean(dtoLaborLevel);
            laborLevelDao.add(bean);
            DtoUtil.copyBeanToBean(dtoLaborLevel, bean);
        } else {
            laborLevelDao.update(dto2Bean(dtoLaborLevel));
        }
    }

    /**
     * 将TsLaborLevel list转换成DtoLaborLevel list
     * @param beanList List
     * @return List
     */
    private static List beanList2DtoList(List<TsLaborLevel> beanList) {
        List dtoList = new ArrayList();
        for(TsLaborLevel bean : beanList) {
            dtoList.add(bean2Dto(bean));
        }
        return dtoList;
    }

    /**
     * 将TsCodeType list转换成DtoComboItem list
     * @param beanList List
     * @return Vector
     */
    private static Vector beanList2DtoComboItemList(List<TsLaborLevel> beanList) {
        Vector dtoList = new Vector(beanList.size()+1);
        DtoComboItem dto = new DtoComboItem(firstOption, null);
        dtoList.add(dto);
        for(TsLaborLevel bean : beanList) {
            if(bean.getIsEnable()) {
                dto = new DtoComboItem(bean.getName(), bean.getRid());
                dtoList.add(dto);
            }
        }
        return dtoList;
    }

    /**
     * 将TsLaborLevel转换成DtoLaborLevel
     * @param bean TsLaborLevel
     * @return DtoLaborLevel
     */
    private static DtoLaborLevel bean2Dto(TsLaborLevel bean) {
        DtoLaborLevel dto = new DtoLaborLevel();
        DtoUtil.copyBeanToBean(dto, bean);
        return dto;
    }

    /**
     * 将DtoLaborLevel转换成TsLaborLevel
     * @param dto DtoLaborLevel
     * @return TsLaborLevel
     */
    private static TsLaborLevel dto2Bean(DtoLaborLevel dto) {
        TsLaborLevel bean = new TsLaborLevel();
        DtoUtil.copyBeanToBean(bean, dto);
        return bean;
    }

    public void setLaborLevelDao(ILaborLevelDao laborLevelDao) {
        this.laborLevelDao = laborLevelDao;
    }

}
