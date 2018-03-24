package server.framework.logic;

import server.framework.hibernate.HBComAccess;
import server.framework.common.BusinessException;
import com.wits.util.Parameter;

public interface IBusinessLogic {
    public void setDbAccessor(HBComAccess hbAccess);
    public HBComAccess getDbAccessor();
    public void executeLogic(Parameter param) throws BusinessException ;
}
