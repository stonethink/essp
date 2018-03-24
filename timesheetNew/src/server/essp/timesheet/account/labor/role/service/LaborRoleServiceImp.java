package server.essp.timesheet.account.labor.role.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import c2s.dto.DtoComboItem;
import c2s.dto.DtoUtil;
import c2s.essp.timesheet.labor.role.DtoLaborRole;
import db.essp.timesheet.TsLaborRole;
import server.essp.timesheet.account.labor.role.dao.ILaborRoleDao;
import server.framework.common.BusinessException;

/**
 * <p>Title: LaborRoleServiceImp</p>
 *
 * <p>Description: Labor role service implement</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class LaborRoleServiceImp implements ILaborRoleService {

    private ILaborRoleDao laborRoleDao;
    private static final String firstOption = "--Please Select--";

    /**
     * 列出所有LaborRole信息 DtoComboItem
     *
     * @return Vector
     */
    public Vector ListLaborRoleDtoComboItem() {
        return beanList2DtoComboItemList(laborRoleDao.listLaborRole());
    }

    /**
     * 删除LaborRole信息
     *
     * @param dtoLaborRole Long
     */
    public void deleteLaborRole(DtoLaborRole dtoLaborRole) {
        laborRoleDao.delete(dto2Bean(dtoLaborRole));
    }

    /**
     * 列出所有LaborRole信息
     *
     * @return List
     */
    public List listLaborRole() {
        return beanList2DtoList(laborRoleDao.listLaborRole());
    }

    /**
     * 下移LaborRole信息
     *
     * @param dtoLaborRole DtoLaborRole
     */
    public void moveDownLaborRole(DtoLaborRole dtoLaborRole) {
        TsLaborRole beanDown = laborRoleDao.getDownSeqLaborRole(dtoLaborRole.getSeq());
        if(beanDown == null) {
            throw new BusinessException("error.logic.common.isDownmost",
                                        "This labor role is downmost");
        }
        laborRoleDao.setSeq(dtoLaborRole.getRid(), beanDown.getSeq());
        laborRoleDao.setSeq(beanDown.getRid(), dtoLaborRole.getSeq());
    }

    /**
     * 上移LaborRole信息
     *
     * @param dtoLaborRole DtoLaborRole
     */
    public void moveUpLaborRole(DtoLaborRole dtoLaborRole) {
        TsLaborRole beanUp = laborRoleDao.getUpSeqLaborRole(dtoLaborRole.getSeq());
        if(beanUp == null) {
            throw new BusinessException("error.logic.common.isUppermost",
                                        "This labor role is uppermost");
        }
        laborRoleDao.setSeq(dtoLaborRole.getRid(), beanUp.getSeq());
        laborRoleDao.setSeq(beanUp.getRid(), dtoLaborRole.getSeq());
    }

    /**
     * 新增或修改LaborRole信息
     *
     * @param dtoLaborRole DtoLaborRole
     */
    public void saveLaborRole(DtoLaborRole dtoLaborRole) {
        if(dtoLaborRole.isInsert()) {
            dtoLaborRole.setSeq(laborRoleDao.getMaxSeq() + 1);
            TsLaborRole bean = dto2Bean(dtoLaborRole);
            laborRoleDao.add(bean);
            DtoUtil.copyBeanToBean(dtoLaborRole, bean);
        } else {
            laborRoleDao.update(dto2Bean(dtoLaborRole));
        }
    }

    /**
     * 将TsLaborRole list转换成DtoLaborRole list
     * @param beanList List
     * @return List
     */
    private static List beanList2DtoList(List<TsLaborRole> beanList) {
        List dtoList = new ArrayList();
        for(TsLaborRole bean : beanList) {
            dtoList.add(bean2Dto(bean));
        }
        return dtoList;
    }

    /**
     * 将TsLaborRole list转换成DtoComboItem list
     * @param beanList List
     * @return Vector
     */
    private static Vector beanList2DtoComboItemList(List<TsLaborRole> beanList) {
        Vector dtoList = new Vector(beanList.size());
        DtoComboItem dto = new DtoComboItem(firstOption, null);
        dtoList.add(dto);
        for(TsLaborRole bean : beanList) {
            if(bean.getIsEnable()) {
                dto = new DtoComboItem(bean.getName(), bean.getRid());
                dtoList.add(dto);
            }
        }
        return dtoList;
    }

    /**
     * 将TsLaborRole转换成DtoLaborRole
     * @param bean TsLaborRole
     * @return DtoLaborRole
     */
    private static DtoLaborRole bean2Dto(TsLaborRole bean) {
        DtoLaborRole dto = new DtoLaborRole();
        DtoUtil.copyBeanToBean(dto, bean);
        return dto;
    }

    /**
     * 将DtoLaborRole转换成TsLaborRole
     * @param dto DtoLaborRole
     * @return TsLaborRole
     */
    private static TsLaborRole dto2Bean(DtoLaborRole dto) {
        TsLaborRole bean = new TsLaborRole();
        DtoUtil.copyBeanToBean(bean, dto);
        return bean;
    }

    public void setLaborRoleDao(ILaborRoleDao laborRoleDao) {
        this.laborRoleDao = laborRoleDao;
    }

}
