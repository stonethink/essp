package server.essp.pms.pbs.logic;

import java.util.Iterator;

import c2s.dto.DtoTreeNode;
import c2s.dto.DtoUtil;
import c2s.dto.IDto;
import c2s.dto.ITreeNode;
import c2s.essp.common.user.DtoUser;
import c2s.essp.pms.pbs.DtoPmsPbs;
import db.essp.pms.Pbs;
import server.essp.framework.logic.AbstractESSPLogic;
import server.framework.common.BusinessException;
import server.framework.hibernate.HBComAccess;
import com.wits.util.StringUtil;
import c2s.essp.common.account.IDtoAccount;
import server.essp.pms.wbs.logic.LgAcntSeq;
import db.essp.pms.PbsPK;

public class LgPmsPbsList extends AbstractESSPLogic {
    String loginId = "";
    IDtoAccount account = null;
    Long acntrid = null;
    public LgPmsPbsList() {
    }

    public ITreeNode list() throws BusinessException {
        ITreeNode root = null;
        try {
            account = getAccount();
            if (account == null) {
                throw new BusinessException("E0000","Can't get account information.Please choose one account first.");
            }

            Long inAcntRid = account.getRid();
            Long inPbsRid = LgAcntSeq.getRootRid(inAcntRid,Pbs.class);

            //get root
            PbsPK pk = new PbsPK(inAcntRid, inPbsRid);
            Pbs pbs = null;
            try {
                pbs = (Pbs) getDbAccessor().load(Pbs.class, pk);
                if (pbs != null) {

                    if (account.isPm() == true) {
                        root = buildTree(pbs, false);
                    } else {
                        root = buildTree(pbs, true);
                    }
                }
            } catch (net.sf.hibernate.ObjectNotFoundException ex) {
            }
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BusinessException("E000000",
                                        "Select pbs error.",ex);
        }

        return root;
    }
    //根据项目Rid查PBS树,树上节点默认为只读的
    public ITreeNode listPBSByAcntRid(Long acntRid) throws BusinessException {
        ITreeNode root = null;
        try {
            Long inPbsRid = LgAcntSeq.getRootRid(acntRid, Pbs.class);

            //get root
            PbsPK pk = new PbsPK(acntRid, inPbsRid);
            Pbs pbs = null;
            try {
                pbs = (Pbs) getDbAccessor().load(Pbs.class, pk);
                if (pbs != null) {
                    //Dto中的Readonly都设置成true
                    root = buildTree(pbs, true);
                }
            } catch (net.sf.hibernate.ObjectNotFoundException ex) {
            }
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new BusinessException("E000000",
                                        "Select pbs error.", ex);
        }

        return root;
    }

    public static void main(String args[]) {
        try {
            LgPmsPbsList logic = new LgPmsPbsList();
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

    private ITreeNode buildTree(Pbs pbs, boolean isReadonly) {
        if (pbs == null) {
            return null;
        }
        DtoUser user = getUser();
        if (user != null) {
            loginId = user.getUserLoginId();
        }
        ITreeNode root;
        DtoPmsPbs dto = createDto(pbs);
        if( isReadonly == false ){
            dto.setReadonly(false);
        }else{
            if (StringUtil.nvl(loginId).equals("") == false &&
                StringUtil.nvl(loginId).equals(dto.getPbsManager()) == true) {
                dto.setReadonly(false);
            } else {
                dto.setReadonly(true);
            }
        }

        root = new DtoTreeNode(dto);

        //process pbs - pbs
        for (Iterator it = pbs.getChilds().iterator(); it.hasNext(); ) {
            Pbs child = (Pbs) it.next();

            ITreeNode childTreeRoot = buildTree(child, dto.isReadonly());
            if (childTreeRoot != null) {
                root.addChild(childTreeRoot, IDto.OP_NOCHANGE);
            }
        }

        return root;
    }

    private DtoPmsPbs createDto(Pbs pbs) {
        DtoPmsPbs dto = new DtoPmsPbs();

        try {
            DtoUtil.copyBeanToBean(dto, pbs);
        } catch (Exception ex) {
        }
        dto.setAcntRid(pbs.getPk().getAcntRid());
        dto.setPbsRid(pbs.getPk().getPbsRid());
        if( pbs.getParent() != null ){
            dto.setPbsParentRid( pbs.getParent().getPk().getPbsRid() );
        }

        return dto;
    }
}
