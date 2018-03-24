package server.framework.logic;

import com.wits.util.Parameter;
import server.framework.common.BusinessException;
import server.framework.hibernate.HBComAccess;
import org.apache.log4j.Category;

public abstract class AbstractBusinessLogic implements IBusinessLogic {
    protected static Category log = Category.getInstance("server");
    private HBComAccess hbAccess=null;

    public AbstractBusinessLogic() {
    }

    public AbstractBusinessLogic(HBComAccess hbAccess) {
        setDbAccessor(hbAccess);
    }

    public void setDbAccessor(HBComAccess hbAccess) {
        this.hbAccess=hbAccess;
    }

    public HBComAccess getDbAccessor() {
        if(hbAccess==null) {
            hbAccess=HBComAccess.getInstance();
        }
        return hbAccess;
    }

    public void executeLogic(Parameter param) throws BusinessException {};
}
