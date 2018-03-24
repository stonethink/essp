package server.essp.pms.account.labor.logic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import c2s.dto.DtoComboItem;
import c2s.dto.DtoUtil;
import c2s.dto.IDto;
import c2s.essp.pms.account.DtoAcntLaborResDetail;
import c2s.essp.pms.account.DtoAcntLoaborRes;
import db.essp.pms.Account;
import db.essp.pms.LaborResource;
import db.essp.pms.LaborResourceDetail;
import itf.hr.HrFactory;
import server.essp.pms.account.logic.LgAccount;
import server.framework.common.BusinessException;
import java.util.HashSet;


/**
 *
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: WITS-WH</p>
 * @version 1.0
 * @author stone*/
public class LgAccountLaborRes extends LgAccountLaborResBase {

    public LgAccountLaborRes() {
        super();
    }

    public LgAccountLaborRes(Account account) {
        super(account);
    }

    /**
     * 增加Labor Resources
     * @param dto DtoAccountLoaborRes
     * @throws BusinessException*/
    public void add(DtoAcntLoaborRes dto) throws BusinessException {
        LaborResource res = getDBModel(dto);
        this.add(res);
        List detailList = dto.getPlanDetail();
            if(detailList != null && detailList.size() != 0){
                for(int i = 0;i < detailList.size() ;i ++){
                    DtoAcntLaborResDetail detailDto = (DtoAcntLaborResDetail) detailList.get(i);
                    //若在界面上是删除了的记录,不保存
                    if(detailDto.isDelete())
                        continue;
                    LaborResourceDetail detail = new LaborResourceDetail();
                    DtoUtil.copyBeanToBean(detail, detailDto);
                    detail.setLoginId(res.getLoginId());
                    detail.setLaborResource(res);
                    detail.setAcntRid(res.getAccount().getRid());
                    this.getDbAccessor().save(detail);
                }
            }


    }


    /**
     * 更新Labor Resources
     * @param dto DtoAccountLoaborRes
     */
    public void update(DtoAcntLoaborRes dto) {
        LaborResource res = getDBModel(dto);
        LaborResource primary = this.findResource(res.getAccount(),res.getLoginId());
        if(primary == null){
            this.update(res);
            return;
        }
        if(primary.getRid().longValue() != res.getRid().longValue())
            throw new BusinessException("ACNT_LABOR_003","LoginId:["+dto.getLoginId()+"] has exsited");
        else{
            primary.setLoginId(dto.getLoginId());
            primary.setLoginidStatus(dto.getLoginidStatus());
            primary.setEmpName(dto.getEmpName());
            primary.setJobcodeId(dto.getJobcodeId());
            primary.setPlanStart(dto.getPlanStart());
            primary.setPlanFinish(dto.getPlanFinish());
            primary.setPlanWorkTime(dto.getPlanWorkTime());
            primary.setRoleName(dto.getRoleName());
            primary.setResStatus(dto.getResStatus());
            primary.setJobDescription(dto.getJobDescription());
            primary.setRemark(dto.getRemark());
            this.update(primary);

            List detailList = dto.getPlanDetail();
            if(detailList != null && detailList.size() > 0)
                this.updatePlanDtoList(detailList);
        }
    }


    /**
     * 删除Labor Resources,级联删除该Resource所有Plan记录
     * @param dto DtoAccountLoaborRes
     */
    public void delete(DtoAcntLoaborRes dto) {
        LaborResource res = getDBModel(dto);
        this.delete(res);
    }


    /**
     * 将DTO转换为Hibernate Model
     * @param dto DtoAccountLoaborRes
     * @return LaborResource
     * @throws BusinessException
     */
    private LaborResource getDBModel(DtoAcntLoaborRes dto) throws
        BusinessException {
        Long acntRid = dto.getAcntRid();
        LgAccount acntLogic = new LgAccount();
        Account acnt = acntLogic.load(acntRid);
        try {
            LaborResource res = new LaborResource();
            DtoUtil.copyBeanToBean(res, dto);
            res.setAccount(acnt);
            return res;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new BusinessException("ACNT_LABOR_006",
                "error in copying propties while add labor resource [" +
                dto.getLoginId() + "]",ex);
        }
    }


    /**
     * 将用户操作后的Labor Resource List写入数据库中
     * @param laborResourceList List
     */
    public void updateDtoList(List laborResourceList) {
        Iterator i = laborResourceList.iterator();
        while (i.hasNext()) {
            DtoAcntLoaborRes dto = (DtoAcntLoaborRes) i.next();
            if (dto.isInsert()) {
                this.add(dto);
                dto.setOp(IDto.OP_NOCHANGE);
            } else if (dto.isDelete()) {
                this.delete(dto);
                i.remove();
            } else if (dto.isModify()) {
                this.update(dto);
                dto.setOp(IDto.OP_NOCHANGE);
            }
        }
    }
    /**
     * 更新Labor Resource的Plan
     * @param planDetailList List
     */
    public void updatePlanDtoList(List planDetailList){
        if(planDetailList == null)
            return;
        Iterator i = planDetailList.iterator();
        while (i.hasNext()) {
            DtoAcntLaborResDetail dto = (DtoAcntLaborResDetail) i.next();
            LaborResourceDetail detail = new LaborResourceDetail();
            try {
                DtoUtil.copyProperties(detail, dto);
            } catch (Exception ex) {
                throw new BusinessException("ACNT_LABOR_008",
                                            "error in copying propties of resource plan! [" +
                                            dto.getLoginId() + "]",ex);
            }
            if (dto.isInsert()) {
                //this.add(dto);
                try {
                    LaborResource labor = new LaborResource();
                    labor.setRid(dto.getResRid());
                    detail.setLaborResource(labor);
                    this.getDbAccessor().save(detail);
                    dto.setOp(IDto.OP_NOCHANGE);
                } catch (Exception ex1) {
                    throw new BusinessException("ACNT_LABOR_009",
                            "error while add labor resource plan! [" +
                            dto.getLoginId() + "]",ex1);
                }
            } else if (dto.isDelete()) {
                try {
                    this.getDbAccessor().delete(detail);
                    i.remove();
                } catch (Exception ex2) {
                    throw new BusinessException("ACNT_LABOR_010",
                            "error while delete labor resource plan! [" +
                            dto.getLoginId() + "]",ex2);
                }
                //this.delete(dto);
            } else if (dto.isModify()) {
                //this.update(dto);
                try {
                    LaborResourceDetail primay = (LaborResourceDetail)
                                                 this.getDbAccessor()
                                                 .load(LaborResourceDetail.class,detail.getRid());
                    primay.setBeginDate(detail.getBeginDate());
                    primay.setEndDate(detail.getEndDate());
                    primay.setPercent(detail.getPercent());
                    primay.setHour(detail.getHour());
                    this.getDbAccessor().update(primay);
                    dto.setOp(IDto.OP_NOCHANGE);
                } catch (Exception ex2) {
                    throw new BusinessException("ACNT_LABOR_010",
                            "error while update labor resource plan! [" +
                            dto.getLoginId() + "]",ex2);
                }
            }
        }

    }
    public DtoAcntLoaborRes findResourceDto(Long acntRid, String loginId) {
        LgAccount acntLogic = new LgAccount();
        Account acnt = acntLogic.load(acntRid);
        LaborResource resource = this.findResource(acnt, loginId);
        DtoAcntLoaborRes dto = new DtoAcntLoaborRes();
        dto.setAcntRid(resource.getAccount().getRid());
        try {
            DtoUtil.copyBeanToBean(dto, resource);
        } catch (Exception ex) {
            throw new BusinessException("ACNT_LABOR_006",
                "error in copying propties while find labor resource [" +
                loginId + "]",ex);
        }
        String jobCode =  HrFactory.create().getJobCodeById(dto.getJobcodeId());
        dto.setJobCode(jobCode);
        return dto;
    }


    /**
     * 列出Account下所有Resouce
     * @param acntRid Long
     * @return List
     * @throws BusinessException
     */
    public List listLaborResDto(Long acntRid) {
        List result = new ArrayList();
        List l = this.listLaborRes(acntRid);
        Iterator i = l.iterator();
        while (i.hasNext()) {
            LaborResource res = (LaborResource) i.next();
            DtoAcntLoaborRes dto = new DtoAcntLoaborRes();
            try {
                DtoUtil.copyBeanToBean(dto, res);
            } catch (Exception ex) {
                throw new BusinessException("ACNT_LABOR_007",
                    "error in copying propties while list labor resource dto,[" +
                    res.getLoginId() + "]",ex);
            }
            dto.setAcntRid(acntRid);
            String jobCode = HrFactory.create().getJobCodeById(dto.getJobcodeId());
            dto.setJobCode(jobCode);
            result.add(dto);
        }
        return result;
    }
    /**
    * 列出Account下所有Resouce,包括计划的每个区间
    * @param acntRid Long
    * @return List
    * @throws BusinessException
    */
    public List listLaborResWithPlan(Long acntRid) {
        List result = new ArrayList();
        List l = this.listLaborRes(acntRid);
        Iterator i = l.iterator();
        while (i.hasNext()) {
            LaborResource res = (LaborResource) i.next();
            DtoAcntLoaborRes dto = new DtoAcntLoaborRes();
            try {
                DtoUtil.copyBeanToBean(dto, res);
            } catch (Exception ex) {
                throw new BusinessException("ACNT_LABOR_007",
                                            "error in copying propties while list labor resource dto,[" +
                                            res.getLoginId() + "]",ex);
            }
            dto.setAcntRid(acntRid);
            String jobCode = HrFactory.create().getJobCodeById(dto.getJobcodeId());
            dto.setJobCode(jobCode);
            Set details = res.getLaborResourceDetails();
            Iterator i2 = details.iterator();
            while(i2.hasNext()){
                LaborResourceDetail detail = (LaborResourceDetail) i2.next();
                DtoAcntLaborResDetail detailDto = new DtoAcntLaborResDetail();
                detailDto.setAcntRid(detail.getAcntRid());
                detailDto.setBeginDate(detail.getBeginDate());
                detailDto.setEndDate(detail.getEndDate());
                detailDto.setHour(detail.getHour());
                detailDto.setPercent(detail.getPercent());
                detailDto.setLoginId(detail.getLoginId());
                detailDto.setRid(detail.getRid());
                dto.addDetail(detailDto);
            }
            result.add(dto);
        }
        return result;
    }

    public List getLaborResPlan(Long resRid){
        List result = new ArrayList();
        LaborResource res = null;
        try {
            res = (LaborResource)this.getDbAccessor().load(LaborResource.class,
                resRid);
        } catch (Exception ex) {
            throw new BusinessException("ACNT_LABOR_008","error while loading labor resource:["+resRid+"]",ex);
        }
        Set details = res.getLaborResourceDetails();
        Iterator i2 = details.iterator();
        while(i2.hasNext()){
            LaborResourceDetail detail = (LaborResourceDetail) i2.next();
            DtoAcntLaborResDetail detailDto = new DtoAcntLaborResDetail();
            detailDto.setAcntRid(detail.getAcntRid());
            detailDto.setBeginDate(detail.getBeginDate());
            detailDto.setEndDate(detail.getEndDate());
            detailDto.setHour(detail.getHour());
            detailDto.setPercent(detail.getPercent());
            detailDto.setLoginId(detail.getLoginId());
            detailDto.setRid(detail.getRid());
            result.add(detailDto);
        }
        return result;
    }
    public Vector listComboLaborRes() {
        List list = listLaborRes();
        return listComboLaborRes(list);
    }

    public Vector listComboLaborRes(Long accountRid) {
        List list = listLaborRes(accountRid);
        return listComboLaborRes(list);
    }

    private Vector listComboLaborRes(List list) {
        if (list == null) {
            return null;
        }
        int iSize = list.size();
        Vector vector = new Vector(iSize);
        for (int i = 0; i < iSize; i++) {
            LaborResource laborResource = (LaborResource) list.get(i);
            if ((!laborResource.getResStatus().equals(DtoAcntLoaborRes.
                RESOURCE_STATUS_OUT)) &&
                laborResource.getLoginidStatus().equals(DtoAcntLoaborRes.
                LOGINID_STATUS_IN)) {
//                DtoComboItem ci = new DtoComboItem(laborResource.getEmpName(),
//                    laborResource.getLoginId());
                DtoComboItem ci = new DtoComboItem(laborResource.getEmpName(),
                    laborResource.getLoginId());
                vector.add(ci);
            }
        }
        //vector.add(0,new DtoComboItem("",""));
        return vector;
    }

    public static void main(String args[]) throws SQLException {
    }
}
