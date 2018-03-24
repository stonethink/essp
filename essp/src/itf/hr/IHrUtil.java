package itf.hr;

import server.framework.common.BusinessException;
import server.framework.logic.IBusinessLogic;
import java.util.Vector;
import java.util.List;
import c2s.essp.common.user.DtoUser;
import c2s.essp.common.user.DtoCustomer;

public interface IHrUtil extends IBusinessLogic {
    public List listAllUser() throws BusinessException;

    public Vector listComboJobCode() throws BusinessException;

    public List listOptJobCode() throws BusinessException;

    public String getUserJobCode(String loginId) throws BusinessException;

    public String getUserEmail(String loginIds) throws BusinessException;

    public String getJobCodeById(String jobCodeId) throws BusinessException;

    public DtoUser findResouce(String loginId) throws BusinessException;

    public DtoCustomer findCustomer(String loginId) throws BusinessException;

    public void addCustomer(DtoCustomer dto) throws BusinessException;

    public void updateCustomer(DtoCustomer dto) throws BusinessException;

    public boolean hasProductivity(String loginId) throws BusinessException;

    public List listHrConfig(String kindCode, String lableField,
                             String valueField) throws BusinessException;

    public String getChineseName(String loginId) throws BusinessException;

    public String getName(String loginId) throws BusinessException;

    public String getCustomerName(String loginId) throws BusinessException;
}
