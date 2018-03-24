package server.essp.pms.quality.goal.logic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import c2s.dto.DtoUtil;
import c2s.dto.IDto;
import c2s.essp.pms.quality.goal.DtoReleaseRecord;
import db.essp.pms.ReleaseRecord;
import server.essp.framework.logic.AbstractESSPLogic;
import server.framework.common.BusinessException;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class LgQualityGoalRelease extends AbstractESSPLogic {
    public LgQualityGoalRelease() {
    }

    public static void main(String[] args) {
        LgQualityGoalRelease lgqualitygoalrelease = new LgQualityGoalRelease();
    }

    /**
     * listReleaseRecord
     *
     * @param goalRid Long
     * @return List
     */
    public List listReleaseRecord(Long goalRid) {
        List qualityGoalReleaseList = new ArrayList();
        try {
            String sqlSel = " from ReleaseRecord t "
                            + " where t.goalRid =:goalRid "
                            + " and t.rst='N' ";
            Iterator it = getDbAccessor().getSession().createQuery(sqlSel)
                          .setLong("goalRid", goalRid.longValue()).iterate();
            while (it.hasNext()) {
                ReleaseRecord dbBean = (ReleaseRecord) it.next();
                DtoReleaseRecord dataBean = new DtoReleaseRecord();

                DtoUtil.copyBeanToBean(dataBean, dbBean);
                Long size = dataBean.getSize();
                Long defects = dataBean.getDefects();
                Double qualificationRate = new Double(0);
                if (size != null && defects != null &&
                    defects.equals(new Long(0)) == false) {
                    qualificationRate = new Double(defects.doubleValue() /
                        size.doubleValue());
                    dataBean.setQualificationRate(qualificationRate);
                }

                dataBean.setRid(dbBean.getRid());

                qualityGoalReleaseList.add(dataBean);
            }
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BusinessException("E000000",
                                        "list ReleaseRecord error.", ex);
        }
        return qualityGoalReleaseList;
    }

    /**
     * deleteReleaseRecord
     *
     * @param dtoReleaseRecord DtoReleaseRecord
     */
    public void deleteReleaseRecord(DtoReleaseRecord dtoReleaseRecord) {
        try {

            ReleaseRecord dbBean = new ReleaseRecord();

            DtoUtil.copyBeanToBean(dbBean, dtoReleaseRecord);

            getDbAccessor().delete(dbBean);
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BusinessException("E000000",
                                        "Delete releaseRecord error.", ex);
        }

    }

    /**
     * updateReleaseRecord
     *
     * @param qualityGoalReleaseList List
     */
    public void updateReleaseRecord(List qualityGoalReleaseList) {
        for (int i = 0; i < qualityGoalReleaseList.size(); i++) {
            DtoReleaseRecord dto = (DtoReleaseRecord) (qualityGoalReleaseList.
                get(i));
            if (IDto.OP_INSERT.equals(dto.getOp())) {
                insertReleaseRecord(dto);
                dto.setOp(IDto.OP_NOCHANGE);
            } else if (IDto.OP_MODIFY.equals(dto.getOp())) {
                updateReleaseRecord(dto);
                dto.setOp(IDto.OP_NOCHANGE);
            } else if (IDto.OP_DELETE.equals(dto.getOp())) {
                deleteReleaseRecord(dto);
                qualityGoalReleaseList.remove(i);
            }
        }
    }

    public void insertReleaseRecord(DtoReleaseRecord dto) {
        ReleaseRecord bean = new ReleaseRecord();

        DtoUtil.copyBeanToBean(bean, dto);

        try {
            this.getDbAccessor().save(bean);

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new BusinessException("E000000",
                                        "Insert Release Record error.", ex);
        }
        DtoUtil.copyBeanToBean(dto, bean);
    }

    /**
     * update a qa check point
     * @param dto DtoQaCheckPoint
     */
    public void updateReleaseRecord(DtoReleaseRecord dto) {
        ReleaseRecord bean = new ReleaseRecord();
        DtoUtil.copyBeanToBean(bean, dto);
        bean.setRid(dto.getRid());
        try {
            this.getDbAccessor().update(bean);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new BusinessException("E000000",
                                        "Update Qa Check Point error.", ex);
        }
        DtoUtil.copyBeanToBean(dto, bean);
    }


}
