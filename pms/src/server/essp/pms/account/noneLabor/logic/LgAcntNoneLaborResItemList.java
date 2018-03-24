package server.essp.pms.account.noneLabor.logic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import c2s.dto.DtoUtil;
import c2s.dto.IDto;
import c2s.essp.pms.account.DtoNoneLaborResItem;
import db.essp.pms.Account;
import db.essp.pms.NonlaborResItem;
import db.essp.pms.NonlaborResource;
import server.essp.framework.logic.AbstractESSPLogic;
import server.framework.common.BusinessException;
import server.framework.hibernate.HBComAccess;
import java.util.*;
import net.sf.hibernate.*;

public class LgAcntNoneLaborResItemList extends AbstractESSPLogic {

    public List list(Long acntRid, Long envRid) throws BusinessException {
        List laborResItemList = new ArrayList();
        try {
            String sqlSel = " from NonlaborResItem t "
                            +" where t.account.rid =:acntRid "
                            +" and t.nonlaborResource.rid =:envRid ";
            Iterator it = getDbAccessor().getSession().createQuery( sqlSel )
                          .setLong("acntRid", acntRid.longValue())
                          .setLong("envRid", envRid.longValue())
                          .iterate();
            while( it.hasNext() ){
                NonlaborResItem nonlaborResItem = (NonlaborResItem)it.next();
                DtoNoneLaborResItem dtoNoneLaborResItem = new DtoNoneLaborResItem();

                DtoUtil.copyBeanToBean( dtoNoneLaborResItem, nonlaborResItem );

                dtoNoneLaborResItem.setAcntRid( nonlaborResItem.getAccount().getRid() );
                dtoNoneLaborResItem.setEnvRid( nonlaborResItem.getNonlaborResource().getRid() );

                laborResItemList.add( dtoNoneLaborResItem );            }
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BusinessException("E000000",
                                        "Select noneLabor resource item error.", ex);
        }

        return laborResItemList;
    }

    public void update(List laborResItemList) throws BusinessException {
        try {
            checkParameter(laborResItemList);

            for (int i = 0; i < laborResItemList.size(); i++) {
                DtoNoneLaborResItem dtoNoneLaborResItem = (DtoNoneLaborResItem) laborResItemList.get(i);

                if (dtoNoneLaborResItem.isInsert()) {
                    NonlaborResItem nonlaborResItem = new NonlaborResItem();
                    copyNoneLaborRes(nonlaborResItem, dtoNoneLaborResItem);

                    getDbAccessor().save(nonlaborResItem);

                    dtoNoneLaborResItem.setRid(nonlaborResItem.getRid());
                    dtoNoneLaborResItem.setOp(IDto.OP_NOCHANGE);
                } else if (dtoNoneLaborResItem.isDelete()) {

                    laborResItemList.remove(i);
                    i--;
                } else if (dtoNoneLaborResItem.isModify()) {
                    NonlaborResItem nonlaborResItem = (NonlaborResItem)this.getDbAccessor().load(NonlaborResItem.class, dtoNoneLaborResItem.getRid());
                    copyNoneLaborRes(nonlaborResItem, dtoNoneLaborResItem);

                    getDbAccessor().save(nonlaborResItem);

                    dtoNoneLaborResItem.setOp(IDto.OP_NOCHANGE);
                }
            }

        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BusinessException("E000000", "Update NonlaborResItem error.", ex);
        }
    }


    private void checkParameter(List items) throws BusinessException {
        try {
            String msg = "";

            for (Iterator iter = items.iterator(); iter.hasNext(); ) {
                DtoNoneLaborResItem item = (DtoNoneLaborResItem) iter.next();
                String sqlSel = " from NonlaborResItem t "
                                + " where t.account.rid =:acntRid "
                                + " and t.nonlaborResource.rid =:envRid ";
                Iterator it;
                if (item.isInsert()) {
                    sqlSel += " and t.resourceId =:resourceId";
                    it = this.getDbAccessor().getSession().createQuery(sqlSel)
                         .setLong("acntRid", item.getAcntRid().longValue())
                         .setLong("envRid", item.getEnvRid().longValue())
                         .setString("resourceId", item.getResourceId())
                         .iterate();
                    if (it.hasNext()) {
                        msg += ("Resource id[" + item.getResourceId() + "] is already exist.\r\n");
                    }
                } else {
                    sqlSel += " and t.resourceId =:resourceId and t.rid !=:rid";
                    it = this.getDbAccessor().getSession().createQuery(sqlSel)
                         .setLong("acntRid", item.getAcntRid().longValue())
                         .setLong("envRid", item.getEnvRid().longValue())
                         .setString("resourceId", item.getResourceId())
                         .setLong("rid", item.getRid().longValue())
                         .iterate();
                    if (it.hasNext()) {
                        msg += ("Resource id[" + item.getResourceId() + "] is already exist.\r\n");
                    }
                }
            }

            if( msg.equals("") == false ){
                throw new BusinessException("E0001", msg);
            }
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BusinessException("E000000", "Error when check Resource id.", ex);
        }

    }


    public void copyNoneLaborRes(NonlaborResItem dbBean, DtoNoneLaborResItem dataBean) throws Exception {
        DtoUtil.copyBeanToBean( dbBean, dataBean );

        Account account = new Account();
        account.setRid(dataBean.getAcntRid());

        NonlaborResource nonlaborResource = new NonlaborResource();
        nonlaborResource.setRid(dataBean.getEnvRid());

        dbBean.setAccount(account);
        dbBean.setNonlaborResource( nonlaborResource );
    }


    public static void main(String args[] ){
        try {
            LgAcntNoneLaborResItemList logic = new LgAcntNoneLaborResItemList();
            HBComAccess hb = new HBComAccess();
            logic.setDbAccessor(hb);
            List laborResItemList = logic.list(new Long(7) , new Long(1));
            if (laborResItemList.size() > 0) {
                System.out.println(DtoUtil.dumpBean((laborResItemList.get(0))));
            }
        } catch (BusinessException ex) {

        }
    }

}
