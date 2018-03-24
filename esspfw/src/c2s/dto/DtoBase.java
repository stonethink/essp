package c2s.dto;

import java.io.Serializable;

/**
 *
 * <p>Title: DtoBase</p>
 *
 * <p>Description: DtoBase</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: Wistron ITS Wuhan SDC</p>
 *
 * @author not attributable
 * @version 1.0
 */
public class DtoBase implements IDto, Serializable {
    private static final long serialVersionUID = -955057269376220437L;
    private boolean needSave=true;
    /**
     * OP Type
     */
    private String op = OP_NOCHANGE;

    /**
     * getOp()
     * @return String
     */
    public String getOp() {
        return this.op;
    }

    /**
     * setOp(String op)
     * @param op String
     */
    public void setOp(String op) {
        if (!(IDto.OP_MODIFY.equals(op) &&
            (IDto.OP_INSERT.equals(this.op) || IDto.OP_DELETE.equals(this.op)))) {
          this.op = op;
        }
    }

    /**
     * isChanged()
     * @return boolean
     */
    public boolean isChanged() {
        if (IDto.OP_NOCHANGE.equals(this.op)) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isInsert() {
        if (IDto.OP_INSERT.equals(this.op)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isModify() {
        if (IDto.OP_MODIFY.equals(this.op)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isDelete() {
        if (IDto.OP_DELETE.equals(this.op)) {
            return true;
        } else {
            return false;
        }
    }

    public void setNeedSave(boolean flag) {
        needSave=flag;
    }

    public boolean isNeedSave() {
        return needSave;
    }

    public boolean isReadonly(){
        return false;
    }
}
