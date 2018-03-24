package itf.orgnization;

import java.util.List;
import java.util.Vector;

import server.framework.common.BusinessException;
import server.framework.logic.IBusinessLogic;
import c2s.essp.common.user.DtoUser;

public interface IOrgnizationUtil extends IBusinessLogic {
  public Vector listComboOrgnization() throws BusinessException;

  public List listOptOrgnization() throws BusinessException;

//  public User getOrgManager(String orgId) throws BusinessException;
  public String getOrgManagerLoginId(String orgId) throws BusinessException;

  public String getOrgName(String orgId) throws BusinessException;
  public String getOrgCode(String orgId) throws BusinessException;

   public List listAllOrgByManager(String managerName) throws BusinessException;

   public String getParentOrgManager(String orgId);
   public String getParentOrg(String orgId);

   String getSubOrgs(String orgId) throws BusinessException;
   List getUserListInOrgs(String orgStr) throws BusinessException;

   String getUserStrInOrgs(List userList) throws BusinessException;

   List getAcntListInOrgs(String orgStr) throws BusinessException;
   String getAcntStrInOrgs(List acntList) throws BusinessException;
}
