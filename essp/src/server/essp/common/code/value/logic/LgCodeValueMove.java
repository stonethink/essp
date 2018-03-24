package server.essp.common.code.value.logic;

import server.essp.framework.logic.AbstractESSPLogic;
import server.framework.common.BusinessException;
import c2s.essp.common.code.DtoCodeValue;
import db.essp.code.CodeValue;

public class LgCodeValueMove extends AbstractESSPLogic {

    public void upMove(DtoCodeValue dataBean) {
        try {
           CodeValue codeValue = (CodeValue)this.getDbAccessor().load(CodeValue.class, dataBean.getRid());

            CodeValue parent = codeValue.getParent();
            if (parent != null) {
                int index = parent.getChilds().indexOf(codeValue);
                if (index - 1 >= 0) {

                    parent.getChilds().remove(codeValue);
                    parent.getChilds().add(index - 1, codeValue);
                    this.getDbAccessor().update(parent);
                }
            }
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BusinessException("E0000", "Error when up right.", ex);
        }
    }

    public void downMove(DtoCodeValue dataBean) {
        try {
             CodeValue codeValue = (CodeValue)this.getDbAccessor().load(CodeValue.class, dataBean.getRid());

            CodeValue parent = codeValue.getParent();
            if (parent != null) {
                int index = parent.getChilds().indexOf(codeValue);
                if (index + 1 < parent.getChilds().size()) {

                    parent.getChilds().remove(codeValue);
                    parent.getChilds().add(index + 1, codeValue);
                    this.getDbAccessor().update(parent);
                }
            }
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BusinessException("E0000", "Error when down right.", ex);
        }
    }
}
