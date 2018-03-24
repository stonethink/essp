package server.essp.common.code.logic;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import c2s.dto.DtoTreeNode;
import c2s.dto.DtoUtil;
import c2s.dto.ITreeNode;
import c2s.essp.common.code.DtoCode;
import db.essp.code.Code;
import server.essp.common.syscode.LgSysParameter;
import server.framework.common.BusinessException;
import server.framework.logic.AbstractBusinessLogic;
import server.framework.hibernate.HBComAccess;
import java.util.*;
import c2s.dto.IDto;
import javax.sql.RowSet;

public class LgCode extends AbstractBusinessLogic {
    static final String kindAccountType = "ACCOUNT_TYPE";

    public void delete(DtoCode dtoCode) {
        String type = dtoCode.getType();
        String catalog = dtoCode.getCatalog();
        Long rid = dtoCode.getRid();

        try {

            checkDelete(rid);

            List codeList = load(type, catalog);
            int i = 0;
            Code deleteCode = null;
            for (; i < codeList.size(); i++) {
                Code code = (Code) codeList.get(i);

                //调整seq
                if (deleteCode != null) {

                    code.setSeq(new Long(i - 1));
                    getDbAccessor().update(code);
                } else {

                    if (code.getRid().equals(rid)) {
                        deleteCode = code;
                    }
                }
            }

            //delete code value of this code
            String sDelSql = " from CodeValue t where t.code.rid =" +
                             rid.longValue() + " ";
            getDbAccessor().getSession().delete(sDelSql);

            //delete this code
            getDbAccessor().delete(deleteCode);
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BusinessException("E0000", "Delete code error.", ex);
        }
    }

    public void downMove(DtoCode dtoCode) {
        String type = dtoCode.getType();
        String catalog = dtoCode.getCatalog();
        Long rid = dtoCode.getRid();

        List codeList = load(type, catalog);
        int i = 0;
        Code code = null;
        for (; i < codeList.size(); i++) {
            code = (Code) codeList.get(i);
            if (code.getRid().equals(rid)) {
                break;
            }
        }

        if (i + 1 < codeList.size()) {
            Code downCode = (Code) codeList.get(i + 1);
            downCode.setSeq(new Long(i));

            code.setSeq(new Long(i + 1));

            try {
                this.getDbAccessor().update(downCode);
                this.getDbAccessor().update(code);
            } catch (Exception ex) {
                throw new BusinessException("E0000", "Down move code error.",
                                            ex);
            }
        }
    }

    public void upMove(DtoCode dtoCode) {
        String type = dtoCode.getType();
        String catalog = dtoCode.getCatalog();
        Long rid = dtoCode.getRid();

        List codeList = load(type, catalog);
        int i = 0;
        Code code = null;
        for (; i < codeList.size(); i++) {
            code = (Code) codeList.get(i);
            if (code.getRid().equals(rid)) {
                break;
            }
        }

        if (i - 1 >= 0) {
            Code upCode = (Code) codeList.get(i - 1);
            upCode.setSeq(new Long(i));

            code.setSeq(new Long(i - 1));

            try {
                this.getDbAccessor().update(upCode);
                this.getDbAccessor().update(code);
            } catch (Exception ex) {
                throw new BusinessException("E0000", "Up move code error.", ex);
            }
        }
    }


    public ITreeNode list(String type) {
        ITreeNode root = new DtoTreeNode(new DtoCode());

        try {

            LgSysParameter lgSysParameter = new LgSysParameter();
            Vector accountTypeList = lgSysParameter.listComboSysParas(
                    kindAccountType);

            //the son of root are account types
            for (Iterator iter = accountTypeList.iterator(); iter.hasNext(); ) {
                String accountType = iter.next().toString();

                DtoCode typeCode = new DtoCode();
                typeCode.setName(accountType);
                typeCode.setIsCode(false);

                ITreeNode typeNode = new DtoTreeNode(typeCode);
                root.addChild(typeNode, IDto.OP_NOCHANGE);

                buildNode(typeNode, type, accountType);
            }

        } catch (Exception ex) {
            throw new BusinessException("E0000", "Select code error.", ex);
        }

        return root;
    }

    private void buildNode(ITreeNode parent, String type, String catalog) {

        try {
            List codeList = load(type, catalog);

            for (Iterator iter = codeList.iterator(); iter.hasNext(); ) {
                Code code = (Code) iter.next();

                DtoTreeNode son = new DtoTreeNode(createDto(code));
                parent.addChild(son, IDto.OP_NOCHANGE);
            }
        } catch (Exception ex) {
            throw new BusinessException("E000001",
                                        "Error when select code of[type, catalog]-[" +
                                        type + "," +
                                        catalog + "]", ex);
        }
    }

    private List load(String type, String catalog) {
        try {
            String selSql =
                    " from Code t where t.type =:type and t.catalog =:catalog order by t.seq ";
            List codeList = getDbAccessor().getSession().createQuery(selSql)
                            .setString("type", type)
                            .setString("catalog", catalog)
                            .list();

            return codeList;
        } catch (Exception ex) {
            throw new BusinessException("E000001",
                                        "Error when select code of[type, catalog]-[" +
                                        type + "," +
                                        catalog + "]", ex);
        }
    }

    private DtoCode createDto(Code db) {
        DtoCode dto = new DtoCode();
        DtoUtil.copyBeanToBean(dto, db);

        return dto;
    }

    public void insert(DtoCode dto) {

        try {
            Code code = new Code();
            //DtoUtil.copyBeanToBean(code, dto);
            copyDtoToDb(dto, code);

            String type = dto.getType();
            String catalog = dto.getCatalog();
            //if( code.getSeq() == null ){

            //加到最后,seq将为最大
            String sCountSql = " select count(*) as count from SYS_CODE t "
                               + " where t.CODE_TYPE ='" + type + "' "
                               + " AND t.CATALOG = '" + catalog + "' ";
            RowSet rset = getDbAccessor().executeQuery(sCountSql);
            while (rset.next()) {
                long count = rset.getLong("count");
                code.setSeq(new Long(count));
            }

            getDbAccessor().save(code);
            dto.setRid(code.getRid());
//                dto.setSeq(code.getSeq());
//            }else{
//
//                //加到db.seq处, 此时要调整seq
//                List codeList = load(type, catalog);
//                int seq = code.getSeq().intValue();
//                for (int i = seq; i < codeList.size(); i++) {
//                    Code belowCode = (Code)codeList.get(i);
//
//                    belowCode.setSeq( new Long(i+1) );
//                    getDbAccessor().update(belowCode);
//                }
//
//                getDbAccessor().save(code);
//            }


        } catch (Exception ex) {
            throw new BusinessException("E000001", "Error when update code.",
                                        ex);
        }
    }


    public void update(DtoCode dto) {
        Long rid = dto.getRid();
        if (rid == null) {
            return;
        }

        try {
            Code db = (Code) getDbAccessor().load(Code.class, rid);

            if (dto.getName().equals(db.getName()) == false) {

                //同步更新code value表
                String sUpdateSql =
                        " update SYS_CODE_VALUE t set t.CODE_NAME = '" +
                        dto.getName() + "' where t.CODE_RID =" + rid;
                getDbAccessor().executeUpdate(sUpdateSql);
            }

            //update不改变seq
            //DtoUtil.copyBeanToBean(db, dto);
            copyDtoToDb(dto, db);

            getDbAccessor().update(db);
        } catch (Exception ex) {
            throw new BusinessException("E000001", "Error when update code.",
                                        ex);
        }
    }

    private void copyDtoToDb(DtoCode dto, Code db) {
        DtoUtil.copyBeanToBean(db, dto);
        if (dto.isStatus() == false) {
            db.setStatus("N");
        } else {
            db.setStatus("Y");
        }
    }

    private void checkDelete(Long codeRid) {
        //如果此code有人引用，那么不能删除它

        String sqlSelectCodeValue =
                "select code.rid from sys_code_value code where code.code_rid='" +
                codeRid + "'";
        String[] tables = {"pms_acnt_code", "pms_activity_code", "pms_wbs_code"};

        try {
            for (int i = 0; i < tables.length; i++) {
                String sql = "select count(*) as num from " + tables[i] + " t "
                             + " where t.value_rid in ( " + sqlSelectCodeValue +
                             " )";
                log.info(sql);
                RowSet rset = getDbAccessor().executeQuery(sql);
                if (rset.next()) {
                    int num = rset.getInt("num");
                    if (num > 0) {
                        if (i == 0) {
                            throw new BusinessException( "E000",
                                    "Can't delete this code.Find account has this code.");
                        } else if (i == 1) {
                            throw new BusinessException( "E000",
                                    "Can't delete this code.Find activity has this code.");
                        } else {
                            throw new BusinessException( "E000",
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
        LgCode logic = new LgCode();
        HBComAccess acc = new HBComAccess();
        logic.setDbAccessor(acc);
//        ITreeNode root = logic.list("activity");
        DtoCode code = new DtoCode();
        code.setRid(new Long(1));
        logic.delete(code);
        System.out.println("ok");
    }

}
