package server.essp.common.code.value.logic;

import java.util.Iterator;

import c2s.dto.DtoTreeNode;
import c2s.dto.DtoUtil;
import c2s.dto.IDto;
import c2s.dto.ITreeNode;
import c2s.essp.common.code.DtoCodeValue;
import db.essp.code.CodeValue;
import server.framework.common.BusinessException;
import server.framework.hibernate.HBComAccess;
import server.framework.logic.AbstractBusinessLogic;
import java.util.List;
import java.util.ArrayList;
import com.wits.util.StringUtil;
import db.essp.code.Code;
import server.framework.hibernate.HBSeq;
import java.util.*;
import itf.seq.IAcntSeqUtil;
import itf.seq.AcntSeqFactory;
import javax.sql.RowSet;

public class LgCodeValue extends AbstractBusinessLogic {

    public void update(DtoCodeValue dataBean) throws BusinessException {
        try {
            CodeValue dbBean = (CodeValue)this.getDbAccessor().load(CodeValue.class, dataBean.getRid());

            boolean bUpdatePath = false;
            if( dataBean.getValue().equals( dbBean.getValue() ) == false ){

                //update path of it and its children
                bUpdatePath = true;
            }

            DtoUtil.copyBeanToBean(dbBean, dataBean);
            getDbAccessor().update(dbBean);

            if( bUpdatePath == true ){
                updatePath( dbBean );
            }
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BusinessException("E000000", "update code value error.", ex);
        }
    }

    /**
     * update it's path and its children's path
     * @param codeValue CodeValue
     */
    private void updatePath(CodeValue codeValue)throws Exception{
        String path = "";

        if( codeValue.getParent() != null && codeValue.getParent().getPath() != null ){
            path = codeValue.getParent().getPath() + "." + codeValue.getValue();
        }else{
            path = codeValue.getValue();
        }

        codeValue.setPath(path);
        getDbAccessor().update(codeValue);

        for (Iterator iter = codeValue.getChilds().iterator(); iter.hasNext(); ) {
            CodeValue child = (CodeValue) iter.next();
            updatePath(child);
        }
    }

    public void add(DtoCodeValue dataBean) throws BusinessException {
        try {

            Long codeValueRid = new Long( HBSeq.getSEQ("SYS_CODE_VALUE") );
            dataBean.setRid(codeValueRid);

            CodeValue dbBean = new CodeValue();
            copyPbs(dbBean, dataBean);

            Long parentRid = dataBean.getParentRid();
            if (parentRid != null) {
                CodeValue parent = (CodeValue) getDbAccessor().load(CodeValue.class, parentRid);

                dbBean.setCodeName( parent.getCode().getName() );
                String parentPath = StringUtil.nvl(parent.getPath());
                if( parentPath.equals("") ){
                    dbBean.setPath( dbBean.getValue() );
                }else{
                    dbBean.setPath( parentPath + "." + dbBean.getValue());
                }
                getDbAccessor().save(dbBean);

                parent.getChilds().add(dbBean);
                getDbAccessor().update(parent);
            }else{
                throw new BusinessException("E000000", "Add code value error.");
            }
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BusinessException("E000000", "Add code value error.",ex);
        }
    }

    private void copyPbs(CodeValue codeValue, DtoCodeValue dto) {

        DtoUtil.copyBeanToBean(codeValue, dto);

        if (StringUtil.nvl(dto.getCodeRid()).equals("") == false) {
            Code code = new Code();
            code.setRid(dto.getCodeRid());
            codeValue.setCode(code);
        }

        if (StringUtil.nvl(dto.getParentRid()).equals("") == false) {
            CodeValue parent = new CodeValue();
            parent.setRid(dto.getParentRid());
            codeValue.setParent(parent);
        }
    }

    public void delete(DtoCodeValue dataBean) throws BusinessException {
        try {
            checkDelete(dataBean.getRid());

            CodeValue dbBean = (CodeValue)this.getDbAccessor().load(CodeValue.class, dataBean.getRid());
            delete( dbBean );
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("", ex);
            throw new BusinessException("E000000", "delete code value error.");
        }
    }

    public void delete(CodeValue dbBean){
        try {
            CodeValue parent = dbBean.getParent();

            //delete children
            List children = new ArrayList();
            if( dbBean.getChilds() != null ){
                for (Iterator iter = dbBean.getChilds().iterator(); iter.hasNext(); ) {
                    CodeValue child = (CodeValue) iter.next();
                    children.add(child);
                }
            }
            for (int i = 0; i < children.size(); i++) {
                CodeValue child = (CodeValue)children.get(i);
                delete(child);
            }

            //delete pbs self
            if (parent != null) {
                parent.getChilds().remove(dbBean);
                getDbAccessor().delete(dbBean);
                getDbAccessor().update(parent);
            } else {
                getDbAccessor().delete(dbBean);
            }
        } catch (Exception ex) {
            throw new BusinessException("E000000", "delete code value error.",ex);
        }
    }

    public ITreeNode list(Long codeRid) throws BusinessException {
        ITreeNode root = null;
        try {
            IAcntSeqUtil util = AcntSeqFactory.create();
            Long rootRid = util.getRootRid(codeRid,CodeValue.class);
            if( rootRid == null ){

                //如果没有root,则新增一个
                //root由code改造而来
                Long rootCodeValueRid = new Long( HBSeq.getSEQ("SYS_CODE_VALUE") );

                root = createNewRoot( codeRid, rootCodeValueRid );
                return root;
            }

            //get root
            CodeValue codeValue = null;
            try {
                getDbAccessor().newTx();
                codeValue = (CodeValue) getDbAccessor().load(CodeValue.class,
                    rootRid);
                if (codeValue != null) {

                    root = buildTree(codeValue);
                }

                getDbAccessor().endTxCommit();
            } catch (net.sf.hibernate.ObjectNotFoundException ex) {
                root = createNewRoot( codeRid, rootRid );
            }
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BusinessException("E000000",
                                        "Select codeValue error.",ex);
        }

        return root;
    }

    private ITreeNode createNewRoot(Long codeRid, Long rootRid) {
        ITreeNode root = null;
        try {
            Code code = (Code) getDbAccessor().load(Code.class, codeRid);
            CodeValue rootCodeValue = new CodeValue();

            rootCodeValue.setRid(rootRid);
            rootCodeValue.setCode(code);
            getDbAccessor().save(rootCodeValue);
            IAcntSeqUtil util = AcntSeqFactory.create();
            util.setRootRid(codeRid, CodeValue.class, rootRid);

            DtoCodeValue dto = createDto(rootCodeValue);
            root = new DtoTreeNode(dto);
        } catch (Exception ex) {
        }

        return root;
    }

    private ITreeNode buildTree(CodeValue codeValue) {
        if (codeValue == null) {
            return null;
        }

        ITreeNode root;
        DtoCodeValue dto = createDto(codeValue);

        root = new DtoTreeNode(dto);

        //process codeValue - codeValue
        for (Iterator it = codeValue.getChilds().iterator(); it.hasNext(); ) {
            CodeValue child = (CodeValue) it.next();

            ITreeNode childTreeRoot = buildTree(child);
            if (childTreeRoot != null) {
                root.addChild(childTreeRoot, IDto.OP_NOCHANGE);
            }
        }

        return root;
    }

    private DtoCodeValue createDto(CodeValue codeValue) {
        DtoCodeValue dto = new DtoCodeValue();

        DtoUtil.copyBeanToBean(dto, codeValue);

        if( codeValue.getCode() != null ){
            dto.setCodeRid(codeValue.getCode().getRid());
        }

        if (codeValue.getParent() != null) {
            dto.setParentRid(codeValue.getParent().getRid());
        }

        return dto;
    }

    private void checkDelete(Long codeValueRid) {

        try {
            String sql = "select rid from sys_code_value start with rid=" +
                         codeValueRid.longValue() +
                         " connect by prior rid=parent_rid ";
            String codeValueStr = "''";
            RowSet rset = getDbAccessor().executeQuery(sql);
            while (rset.next()) {
                String rid = rset.getString("rid");
                codeValueStr += ",'" + rid + "'";
            }
            log.info(codeValueStr);
            if( codeValueStr.length() <= 2 ){
                return;
            }

            String[] tables = {"pms_acnt_code", "pms_activity_code",
                              "pms_wbs_code"};

            for (int i = 0; i < tables.length; i++) {
                String sql2 = "select count(*) as num from " + tables[i] +
                              " t "
                              + " where t.value_rid in ( " + codeValueStr +
                              " )";
                log.info(sql2);
                RowSet rset2 = getDbAccessor().executeQuery(sql2);
                if (rset2.next()) {
                    int num = rset2.getInt("num");
                    if (num > 0) {
                        if (i == 0) {
                            throw new BusinessException("E000",
                                    "Can't delete this code.Find account has this code.");
                        } else if (i == 1) {
                            throw new BusinessException("E000",
                                    "Can't delete this code.Find activity has this code.");
                        } else {
                            throw new BusinessException("E000",
                                    "Can't delete this code.Find wbs has this code.");
                        }
                    }
                }
            }
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BusinessException("E00000",
                                        "Error when check if the code could be deleted.",
                                        ex);
        }

    }

    public static void main(String args[]) {
        try {
            LgCodeValue logic = new LgCodeValue();
            HBComAccess hb = new HBComAccess();
            logic.setDbAccessor(hb);
//            ITreeNode root = logic.list(new Long(1));
//            if (root != null) {
//                System.out.println(DtoUtil.dumpBean(((DtoTreeNode) root).
//                    getDataBean()));
//            }
            logic.checkDelete(new Long(1));
        } catch (BusinessException ex) {
            ex.printStackTrace();
        }
    }
}
