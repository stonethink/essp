package server.essp.common.code.choose.logic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import c2s.dto.IDto;
import c2s.essp.common.code.DtoCodeValueChoose;
import db.essp.code.CodeValue;
import server.framework.common.BusinessException;
import server.framework.logic.AbstractBusinessLogic;

public abstract class LgCodeChoose extends AbstractBusinessLogic {

    protected abstract Object getCodeOwner();
    protected abstract Set getCodeSet();

    public List list() {
        List codeValueList = new ArrayList();

        try {
            if (getCodeSet() != null) {

                for (Iterator iter = getCodeSet().iterator();
                                     iter.hasNext(); ) {
                    CodeValue codeValue = (CodeValue) iter.next();

                    DtoCodeValueChoose dtoCodeValueChoose = new
                        DtoCodeValueChoose();
                    dtoCodeValueChoose.setCodeName(codeValue.getCodeName());
                    dtoCodeValueChoose.setCodeRid(codeValue.getCode().getRid());
                    dtoCodeValueChoose.setCodeValue(codeValue.getValue());
                    dtoCodeValueChoose.setCodeValuePath(codeValue.getPath());
                    dtoCodeValueChoose.setDescription(codeValue.getDescription());
                    dtoCodeValueChoose.setIsCodeValue(true);
                    dtoCodeValueChoose.setValueRid(codeValue.getRid());
                    dtoCodeValueChoose.setOldValueRid(codeValue.getRid());

                    codeValueList.add(dtoCodeValueChoose);
                }
            }
        } catch (Exception ex) {
            throw new BusinessException("E0000",
                                        "Error when select code value.",
                                        ex);
        }

        return codeValueList;
    }

    public void update(List codeValueList) {
        if (codeValueList == null || codeValueList.size() == 0) {
            return;
        }

        Set codes = getCodeSet();

        for (int i = 0; i < codeValueList.size(); i++) {
            DtoCodeValueChoose dto = (DtoCodeValueChoose) codeValueList.get(i);

            if (dto.isInsert() == true) {

                CodeValue codeValue = new CodeValue();
                codeValue.setRid(dto.getValueRid());
                codes.add(codeValue);

                dto.setOp(IDto.OP_NOCHANGE);
            } else if (dto.isDelete() == true) {

                codeValueList.remove(i);
                i--;
            } else if (dto.isModify()) {
                Long valueRid = dto.getValueRid();
                Long oldValueRid = dto.getOldValueRid();

                //delete
                CodeValue oldCodeValue = null;
                for (Iterator iter = codes.iterator(); iter.hasNext(); ) {
                    oldCodeValue = (CodeValue) iter.next();
                    if (oldCodeValue.getRid().equals(oldValueRid)) {
                        break;
                    }
                }
                if (oldCodeValue != null) {
                    codes.remove(oldCodeValue);
                }

                //add
                CodeValue newCodeValue = new CodeValue();
                newCodeValue.setRid(valueRid);
                codes.add(newCodeValue);

                dto.setOp(IDto.OP_NOCHANGE);
            }

            try {
                getDbAccessor().update(getCodeOwner());
            } catch (Exception ex) {
                throw new BusinessException("E0000",
                                            "Error when update code value.",
                                            ex);
            }
        }
    }

    public void delete( DtoCodeValueChoose dto ) {
        Long codeValueRid = dto.getValueRid();
        if (codeValueRid == null) {
            return;
        }

        Set codes = getCodeSet();

        //delete
        CodeValue codeValue = null;
        for (Iterator iter = codes.iterator(); iter.hasNext(); ) {
            codeValue = (CodeValue) iter.next();
            if (codeValue.getRid().equals(codeValueRid)) {
                break;
            }
        }
        if (codeValue != null) {
            codes.remove(codeValue);
        }

        try {
            getDbAccessor().update(getCodeOwner());
        } catch (Exception ex) {
            throw new BusinessException("E0000",
                                        "Error when delete code value.",
                                        ex);
        }
    }


}
