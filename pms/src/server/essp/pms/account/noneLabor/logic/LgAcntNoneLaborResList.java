package server.essp.pms.account.noneLabor.logic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import c2s.dto.DtoUtil;
import c2s.dto.IDto;
import c2s.essp.pms.account.DtoNoneLaborRes;
import db.essp.pms.Account;
import db.essp.pms.NonlaborResource;
import server.essp.framework.logic.AbstractESSPLogic;
import server.framework.common.BusinessException;
import server.framework.hibernate.HBComAccess;

public class LgAcntNoneLaborResList extends AbstractESSPLogic {

    public List list(Long acntRid) throws BusinessException {
        List laborResList = new ArrayList();
        try {
            String sqlSel = " from NonlaborResource t "
                            + " where t.account.rid =:acntRid ";
            Iterator it = getDbAccessor().getSession().createQuery(sqlSel)
                          .setLong("acntRid", acntRid.longValue())
                          .iterate();
            while (it.hasNext()) {
                NonlaborResource nonlaborResource = (NonlaborResource) it.next();

                //select pbs
                DtoNoneLaborRes dtoNoneLaborRes = new DtoNoneLaborRes();
                dtoNoneLaborRes.setRid(nonlaborResource.getRid());
                dtoNoneLaborRes.setAcntRid(nonlaborResource.getAccount().getRid());
                dtoNoneLaborRes.setEnvName(nonlaborResource.getEnvName());

                laborResList.add(dtoNoneLaborRes);
            }
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BusinessException("E000000",
                                        "Select noneLabor resource error.",ex);
        }

        return laborResList;
    }

    public void update(List noneLaborResList) throws BusinessException {
        try {
            checkParameter(noneLaborResList);

            for (int i = 0; i < noneLaborResList.size(); i++) {
                DtoNoneLaborRes dtoNoneLaborRes = (DtoNoneLaborRes) noneLaborResList.get(i);

                if (dtoNoneLaborRes.isInsert()) {
                    NonlaborResource nonlaborResource = new NonlaborResource();
                    copyNoneLaborRes(nonlaborResource, dtoNoneLaborRes);

                    getDbAccessor().save(nonlaborResource);

                    dtoNoneLaborRes.setRid(nonlaborResource.getRid());
                    dtoNoneLaborRes.setOp(IDto.OP_NOCHANGE);
                } else if (dtoNoneLaborRes.isDelete()) {

                    noneLaborResList.remove(i);
                    i--;
                } else if (dtoNoneLaborRes.isModify()) {
                    NonlaborResource nonlaborResource = (NonlaborResource)this.getDbAccessor().load(NonlaborResource.class, dtoNoneLaborRes.getRid());
                    copyNoneLaborRes(nonlaborResource, dtoNoneLaborRes);

                    getDbAccessor().save(nonlaborResource);

                    dtoNoneLaborRes.setOp(IDto.OP_NOCHANGE);
                }
            }

        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BusinessException("E000000", "Update NonlaborResource error.",ex);
        }
    }

    public void copyNoneLaborRes(NonlaborResource dbBean, DtoNoneLaborRes dataBean) throws Exception {
        Account account = new Account();
        account.setRid(dataBean.getAcntRid());

        dbBean.setRid(dataBean.getRid());
        dbBean.setAccount(account);
        dbBean.setEnvName(dataBean.getEnvName());
    }

    private void checkParameter(List noneLaborResList) {
        for (int i = 0; i < noneLaborResList.size(); i++) {
            DtoNoneLaborRes dtoNoneLaborRes = (DtoNoneLaborRes)
                                              noneLaborResList.get(i);

            try {
                if (dtoNoneLaborRes.isModify() || dtoNoneLaborRes.isInsert()) {
                    String name = dtoNoneLaborRes.getEnvName();
                    String sql =
                        " from NonlaborResource t where t.account.rid =:acntRid and t.envName=:envName ";
                    List ret = this.getDbAccessor().getSession().createQuery(sql)
                               .setLong("acntRid", dtoNoneLaborRes.getAcntRid().longValue())
                               .setString("envName", name)
                               .list();
                    if (ret != null && ret.size() > 0) {
                        if (dtoNoneLaborRes.isInsert() == true) {
                            throw new BusinessException( "E0000",
                                "The enviroment name is already exist.");
                        } else {

                            for (int j = 0; j < ret.size(); j++) {
                                NonlaborResource nonlaborResource = (NonlaborResource) ret.get(j);
                                if (nonlaborResource.getRid().equals(dtoNoneLaborRes.getRid()) == false) {
                                    throw new BusinessException( "E0000", "The enviroment name is already exist.");
                                }
                            }
                        }
                    }
                }
            } catch(BusinessException ex){
                throw ex;
            } catch (Exception ex) {
                throw new BusinessException("E000000","Error when check the noneLabor resource.",ex);
            }
        }
    }

    public static void main(String args[]) {
        try {
            LgAcntNoneLaborResList logic = new LgAcntNoneLaborResList();
            HBComAccess hb = new HBComAccess();
            logic.setDbAccessor(hb);
            List laborResList = logic.list(new Long(7));
            if (laborResList.size() > 0) {
                System.out.println(DtoUtil.dumpBean((laborResList.get(0))));
            }
        } catch (BusinessException ex) {

        }
    }

}
