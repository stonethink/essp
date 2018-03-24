package server.essp.ebs.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import c2s.dto.DtoTreeNode;
import c2s.dto.DtoUtil;
import c2s.dto.IDto;
import c2s.dto.ITreeNode;
import c2s.essp.ebs.DtoAssignAcnt;
import c2s.essp.ebs.DtoEbsEbs;
import c2s.essp.ebs.DtoPmsAcnt;
import db.essp.pms.Account;
import db.essp.pms.Ebs;
import server.framework.common.BusinessException;
import server.framework.hibernate.HBComAccess;
import server.framework.logic.AbstractBusinessLogic;

public class LgEbsList extends AbstractBusinessLogic {

    //保证同一个rid的account使用同一个DtoPmsAcnt对象
    private Map acntMap = new HashMap();

    public ITreeNode list() throws BusinessException {
        ITreeNode root = null;
        try {
            //get root
//            String sqlSel = " from Ebs t where t.rid = 0 ";
            String sqlSel = " from Ebs t where t.parent is null ";

            Iterator it = getDbAccessor().getSession().createQuery(sqlSel).
                          iterate();
            if (it.hasNext()) {
                Ebs ebs = (Ebs) it.next();
                root = buildTree(ebs);
            }
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("", ex);
            throw new BusinessException("E000000",
                                        "Select ebs error.");
        }

        return root;
    }


    //acntMap中的元素在list()方法中设置，所以在调用list()之前，acntMap可能为空。
    public Map getAcntMap() {
        return this.acntMap;
    }

    private ITreeNode buildTree(Ebs ebs) {
        if (ebs == null) {
            return null;
        }

        ITreeNode root;
        DtoEbsEbs dto = new DtoEbsEbs();

        try {
            DtoUtil.copyBeanToBean(dto, ebs);
        } catch (Exception ex) {
        }
        if (ebs.getParent() != null) {
            dto.setEbsParentRid(ebs.getParent().getRid());
        }
        root = new DtoTreeNode(dto);

        //process ebs - ebs
        for (Iterator it = ebs.getChilds().iterator(); it.hasNext(); ) {
            Ebs child = (Ebs) it.next();

            ITreeNode childTreeRoot = buildTree(child);
            if (childTreeRoot != null) {
                root.addChild(childTreeRoot, IDto.OP_NOCHANGE);
            }
        }

        //process ebs -acnt
        for (Iterator it = ebs.getAccounts().iterator(); it.hasNext(); ) {
            Account account = (Account) it.next();

            DtoPmsAcnt dtoPmsAcnt = (DtoPmsAcnt) acntMap.get(account.getRid().
                toString());
            if (dtoPmsAcnt == null) {
                dtoPmsAcnt = new DtoPmsAcnt();

                try {
                    DtoUtil.copyBeanToBean(dtoPmsAcnt, account);
                } catch (Exception ex) {
                }

                acntMap.put(dtoPmsAcnt.getRid(), dtoPmsAcnt);
            }
            ITreeNode node = new DtoTreeNode(dtoPmsAcnt);
            root.addChild(node, IDto.OP_NOCHANGE);
        }

        return root;
    }

    public List addExistAcnt(List accountList, Long ebsRid) throws
        BusinessException {
        List retAccountList = new ArrayList();

        LgAcnt acntLogic = new LgAcnt();
        LgEbs9Acnt ebs9AcntLogic = new LgEbs9Acnt();
        //acntLogic.setDbAccessor(this.getDbAccessor());
        ebs9AcntLogic.setDbAccessor(this.getDbAccessor());
        for (int i = 0; i < accountList.size(); i++) {
            DtoAssignAcnt dtoAssignAcnt = (DtoAssignAcnt) accountList.get(i);
            ebs9AcntLogic.insert(dtoAssignAcnt.getRid(), ebsRid);

            DtoPmsAcnt dtoPmsAcnt = acntLogic.select(dtoAssignAcnt.getRid());
            retAccountList.add(dtoPmsAcnt);
        }

        return retAccountList;
    }


    public static void main(String args[]) {
        try {
            LgEbsList logic = new LgEbsList();
            HBComAccess hb = new HBComAccess();
            logic.setDbAccessor(hb);
            ITreeNode root = logic.list();
            if (root != null) {
                System.out.println(DtoUtil.dumpBean(((DtoTreeNode) root).
                    getDataBean()));
            }
        } catch (BusinessException ex) {

        }
    }
}
