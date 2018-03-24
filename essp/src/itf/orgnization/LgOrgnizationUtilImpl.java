package itf.orgnization;

import server.framework.logic.AbstractBusinessLogic;
import java.util.Vector;
import java.util.List;
import server.framework.common.BusinessException;
import server.framework.taglib.util.SelectOptionImpl;
import c2s.dto.DtoComboItem;
import java.sql.ResultSet;
import javax.sql.RowSet;
import java.util.ArrayList;
import java.util.Iterator;
import itf.hr.LgHrUtilImpl;

public class LgOrgnizationUtilImpl extends AbstractBusinessLogic implements
        IOrgnizationUtil {
    /**
     * 组织结构表*/
    public static final String ORG_TABLE = "ESSP_UPMS_UNIT";


    /**
     * 人力资源表*/
    public static final String HR_TABLE = "ESSP_HR_EMPLOYEE_MAIN_T";


    /**组织结构人员分配表*/
    public static final String ORG_USER_TABLE = "ESSP_UPMS_UNIT_USER_T";

    public static final String ACCOUNT_TABLE = "PMS_ACNT";


    LgOrgnizationUtilImpl() {
    }

    public Vector listComboOrgnization() {
        List list = listAllOrg();
        if (list == null) {
            return null;
        }
        int iSize = list.size();
        Vector vector = new Vector(iSize);
        for (int i = 0; i < iSize; i++) {
            SelectOptionImpl opt = (SelectOptionImpl) list.get(i);
            DtoComboItem ci = new DtoComboItem(opt.getLabel(), opt.getValue());
            vector.add(ci);
        }
        return vector;
    }

    public List listOptOrgnization() {
        return listAllOrg();
    }

    public String getOrgManagerLoginId(String orgId) throws BusinessException {
        String loginId = "";
        String sql = "select login.login_id as loginid  from "
                     +LgHrUtilImpl.LOGIN_TABLE+"  login , "
                     + ORG_TABLE + " org where login.user_id = org.manager and org.unit_id = '"
                     + orgId + "'";

        try {
            ResultSet result = this.getDbAccessor().executeQuery(sql);
            if (result != null) {
                while (result.next()) {
                    loginId = result.getString(1);
                }
            }
            return loginId;
        } catch (Exception ex) {
            log.error(ex);
            throw new BusinessException();
        }
        //return null;
    }


    public String getOrgName(String orgId) throws BusinessException {
        String name = "";
        String sql = "select org.name as name  from "
                     + ORG_TABLE + " org where org.unit_id = '" + orgId + "'";

        try {
            ResultSet result = this.getDbAccessor().executeQuery(sql);
            if (result != null) {
                while (result.next()) {
                    name = result.getString(1);
                }
            }
            return name;
        } catch (Exception ex) {
            log.error(ex);
            throw new BusinessException();
        }
        //return null;
    }

    public String getOrgCode(String orgId) throws BusinessException {
        String code = "";
        String sql = "select org.unit_code as name  from "
                     + ORG_TABLE + " org where org.unit_id = '" + orgId + "'";

        try {
            ResultSet result = this.getDbAccessor().executeQuery(sql);
            if (result != null) {
                while (result.next()) {
                    code = result.getString(1);
                }
            }
            return code;
        } catch (Exception ex) {
            log.error(ex);
            throw new BusinessException();
        }
        //return null;
    }


    /**
     * 获得所有Orgnization的下拉列表
     * @return List*/
    public List listAllOrg() throws BusinessException {
        //查询结果别名即是显示结果Label和Value
        String sql = "select trim(unit_id) as value,trim(name) as label from " +
                     ORG_TABLE + " order by NAME";
        log.info(sql);
        try {
            List result = this.getDbAccessor().executeQueryToList(sql,
                    SelectOptionImpl.class);
            return result;
        } catch (Exception ex) {
            log.error(ex);
            throw new BusinessException();
        }
    }

    public List listAllOrgByManager(String managerName) throws BusinessException {
        //查询结果别名即是显示结果Label和Value
        String sql = "select trim(org.unit_id) as value,trim(org.name) as label from "
                    + LgHrUtilImpl.LOGIN_TABLE + " login , "
                    + ORG_TABLE + " org where login.user_id = org.manager and login.login_id = '"
                    + managerName + "'";
        log.info(sql);
        try {
            List result = this.getDbAccessor().executeQueryToList(sql,
                    SelectOptionImpl.class);
            return result;
        } catch (Exception ex) {
            log.error(ex);
            throw new BusinessException();
        }
    }


    public static void main(String[] args) {
        LgOrgnizationUtilImpl lg = new LgOrgnizationUtilImpl();
        List l = lg.listAllOrgByManager("stone.shi");
//        lg.getOrgManagerLoginId("UNIT00000248");
    }

    public String getParentOrgManager(String orgId) {
        String parentOrg = getParentOrg(orgId);
        if(parentOrg != null)
            return getOrgManagerLoginId(parentOrg);
        return null;
    }

    public String getParentOrg(String orgId) {
        String sql = "select parent_id " +
                     "from " + ORG_TABLE + " org " +
                     "where org.unit_id = '" + orgId + "'";
        try {
            RowSet rt = this.getDbAccessor().executeQuery(sql);
            if(rt.next()){
                return rt.getString("parent_id");
            }
        } catch (Exception ex) {
            throw new BusinessException("","error get parent organization!",ex);
        }
        return null;
    }



    public String getSubOrgs(String orgId) throws BusinessException {
        try {
            List orgList = new ArrayList();
            //get orgnazition and its children
            StringBuffer orgSb = new StringBuffer("''");
            String sqlSelOrgIncSub =
                    "select UNIT_ID "
                    + " from " + ORG_TABLE
                    + " start with UNIT_ID='" + orgId + "'"
                    + " connect by prior UNIT_ID=PARENT_ID";
            RowSet orgRowSet = this.getDbAccessor().executeQuery(sqlSelOrgIncSub);
            while (orgRowSet.next()) {
                String unitId = orgRowSet.getString("UNIT_ID");
                if (orgList.contains(unitId) == false) {
                    orgSb.append(",");
                    orgSb.append("'");
                    orgSb.append(orgRowSet.getString("UNIT_ID"));
                    orgSb.append("'");

                    orgList.add(unitId);
                }
            }
            String orgStr = orgSb.toString();
            return orgStr;
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BusinessException("E00", "Error when get the user in orgnazition - " + orgId + " including sub one.", ex);
        }
    }

    public List getUserListInOrgs(String orgStr) throws BusinessException {
        try {
            //removed by xr:查找组织中的人应根据ESSP_HR_EMPLOYEE_MAIN_T的Dept字段
//            String sql = "select login.loginname as loginid " +
//                         "from " + LOGIN_TABLE + " login " +
//                         "left join " + ORG_USER_TABLE + " org_user on login.user_id = org_user.user_id " +
//                         "where org_user.unit_id in (" + orgStr + ") " +
//                         "order by login.loginname";
            String sql = "select login.login_id as loginid " +
                         "from "+ LgHrUtilImpl.HR_TABLE +" hr left join " + LgHrUtilImpl.LOGIN_TABLE + " login on hr.code=login.user_id " +
                         "where hr.dept in (" + orgStr + ") " +
                         "order by UPPER(login.login_id)";
            log.info(sql);
            RowSet rset = this.getDbAccessor().executeQuery(sql);
            List userList = new ArrayList();
            while (rset.next()) {
                String userId = rset.getString("loginid");
                if (userList.contains(userId) == false) {

                    userList.add(userId);
                }
            }

            return userList;
        } catch (Exception ex) {
            throw new BusinessException("E000", "Error when get the user of orgnazition - " + orgStr, ex);
        }
    }

    public String getUserStrInOrgs(List userList) throws BusinessException {
        StringBuffer userStr = new StringBuffer("''");

        for (Iterator iter = userList.iterator(); iter.hasNext(); ) {
            String userId = (String) iter.next();
            userStr.append(",");
            userStr.append("'");
            userStr.append(userId);
            userStr.append("'");
        }

        return userStr.toString();
    }

    /**
     * 查找orgnazition下的项目
     * @return String
     * @throws BusinessException
     */
    public List getAcntListInOrgs(String orgStr) throws BusinessException {
        try {
            String sql = "select acnt.RID" +
                         " from " + ACCOUNT_TABLE + " acnt" +
                         " where acnt.ACNT_ORGANIZATION in (" + orgStr + ") " +
                         " order by acnt.acnt_id";

            RowSet rset = this.getDbAccessor().executeQuery(sql);
            List acntList = new ArrayList();
            while (rset.next()) {
                Long rid = new Long( rset.getLong("RID") );
                if( acntList.contains(rid) == false ){

                    acntList.add(rid);
                }
            }

            return acntList;
        } catch (Exception ex) {
            throw new BusinessException("E000", "Error when get the account list of orgnazition - " + orgStr, ex);
        }
    }


    public String getAcntStrInOrgs(List acntList) throws BusinessException {
        StringBuffer acntStr = new StringBuffer("''");

        for (Iterator iter = acntList.iterator(); iter.hasNext(); ) {
            Long acntRid = (Long) iter.next();
            acntStr.append(",");
            acntStr.append("'");
            acntStr.append(acntRid);
            acntStr.append("'");
        }

        return acntStr.toString();
    }


}
