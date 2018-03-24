package server.framework.logic;

import org.apache.log4j.Category;
import server.framework.common.BusinessException;
import server.framework.hibernate.HBComAccess;
import com.wits.util.Parameter;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public abstract class AbstractMultiLogic {
    protected static Category log = Category.getInstance("server");
    private HBComAccess hbAccess=null;

    public AbstractMultiLogic() {
    }

    public AbstractMultiLogic(HBComAccess hbAccess) {
        setDbAccessor(hbAccess);
    }

    public void setDbAccessor(HBComAccess hbAccess) {
        this.hbAccess=hbAccess;
    }

    public HBComAccess getDbAccessor() {
        return hbAccess;
    }

    public void executeQuery(Parameter param) throws BusinessException {};
    public void executeAdd(Parameter param) throws BusinessException {};
    public void executeDelete(Parameter param) throws BusinessException {};
    public void executeUpdate(Parameter param) throws BusinessException {};
    public void executeDetail(Parameter param) throws BusinessException {};
}
