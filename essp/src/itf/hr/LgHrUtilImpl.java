package itf.hr;

import java.sql.*;
import java.util.*;

import c2s.dto.*;
import c2s.essp.common.user.*;
import server.framework.common.*;
import server.framework.logic.*;
import server.framework.taglib.util.*;
import server.framework.hibernate.HBSeq;
import javax.sql.RowSet;
import itf.orgnization.OrgnizationFactory;
import com.wits.util.StringUtil;


public class LgHrUtilImpl extends AbstractBusinessLogic implements
    IHrUtil {
  /**
   * 组织结构表*/
  public static final String ORG_TABLE = "ESSP_UPMS_UNIT";


  /**
   * 人力资源表*/
  public static final String HR_TABLE = "ESSP_HR_EMPLOYEE_MAIN_T";


  /**
   * 用户登录表*/
  public static final String LOGIN_TABLE = "UPMS_LOGINUSER";

  /**
   * 组织结构人员分配表
   */
  public static final String ORG_USER_TABLE = "ESSP_UPMS_UNIT_USER_T";

  /**
   * cache names
   */
  private static HashMap hmNames = new HashMap();

  LgHrUtilImpl() {
  }
  /**
   * 查找系统中所有的人员
   * @return List
   * @throws BusinessException
   */
  public List listAllUser() throws BusinessException {
      String sql = "select login.login_id as loginid,hr.position_level as jobcodeid,hr.name as username,hr.home_phone as phone,hr.e_mail as email,hr.types as types,hr.indate as indate,hr.outdate as outdate,hr.productivity as hasProductivity " +
                   "from essp_hr_employee_main_t hr,"+LOGIN_TABLE+" login " +
                   "where hr.code=login.user_id";
      List result = new ArrayList();
      try {
          RowSet rt = this.getDbAccessor().executeQuery(sql);
          while(rt.next()){
              DtoUser dto = new DtoUser();
              dto.setUserLoginId(rt.getString("loginId"));
              String jobcodeId = rt.getString("jobcodeid") ;
              dto.setJobCode(jobcodeId);
              dto.setUserName(rt.getString("username"));
              dto.setPhone(rt.getString("phone"));
              dto.setEmail(rt.getString("email"));
              dto.setInDate(rt.getDate("indate"));
              dto.setOutDate(rt.getDate("outdate"));
              result.add(dto);
          }
      } catch (Exception ex) {
          throw new BusinessException(ex);
      }
      return result;
  }

  public Vector listComboJobCode() throws BusinessException {
    List list = listAllPostRank();
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

  public List listOptJobCode() throws BusinessException {
    return listAllPostRank();
  }


  /**
   * 获得所有JobCode的下拉列表
   * @return java.util.List*/

  /**
   * 查找所有的PostRank
   * @return List
   * @throws BusinessException
   */
  public List listAllPostRank() throws BusinessException {
    return listHrConfig("RANK", "NAME_ENGLISH", "CODE");
  }


  /**
   * 查找人力资源有关的配置参数,指定下拉列表显示的label和value,查询结果封装成SelectOptionImpl
   * @param kindCode String
   * @param <any> unknown
   * @return List
   */
  public List listHrConfig(String kindCode, String lableField,
                            String valueField) throws BusinessException {
    String sql = "select trim(" + lableField + ") as label, trim(" + valueField +
                 ") as value " +
                 "from essp_hr_code_t " +
                 "where KIND_CODE='" + kindCode +
                 "' and del_flag='0' order by CODE";
    try {
      List result = this.getDbAccessor().executeQueryToList(sql,
          SelectOptionImpl.class);
      log.info(sql);
      return result;
    } catch (Exception ex) {
      throw new BusinessException();
    }
  }

  /**
   * 获得人员的职位编号
   * @return String
   * @throws BusinessException
   */
  public String getUserJobCode(String loginId) throws BusinessException {
      String sql = "select hr.position_level as jobcode " +
                   "from essp_hr_employee_main_t hr,"+LOGIN_TABLE+" login " +
                   "where hr.code=login.user_id and login.login_id='"+loginId+"' ";
      try {
          log.info(sql);
          ResultSet rt = this.getDbAccessor().executeQuery(sql);
          if(rt.next())
              return rt.getString("jobcode");
      } catch (Exception ex) {
          throw new BusinessException("error while get user: [" +loginId+ "] 's jobcode");
      }
      return null;
    }

    /**
     * 根据loginid取得用户的email
     * @param loginIds String  当有多个用户时用逗号隔开，如[stone.shi,yuxiang.yang]
     * @return String
     * @throws BusinessException
     */
    public String getUserEmail(String loginIds) throws BusinessException {
        StringBuffer sb = new StringBuffer();
        loginIds = loginIds.replaceAll(",", "','");
        loginIds = "'" + loginIds + "'";

        String sql = "select hr.e_mail as email " +
                     "from essp_hr_employee_main_t hr,"+LOGIN_TABLE+" login " +
                     "where hr.code=login.user_id and login.login_id in(" + loginIds + ") ";
        try {
            log.info(sql);
            ResultSet rt = this.getDbAccessor().executeQuery(sql);
            while (rt.next()) {
                sb.append(rt.getString("email"));
            }
            return sb.toString();
        } catch (Exception ex) {
            throw new BusinessException("error while get user: [" + loginIds + "] 's email");
        }
    }

    /**
     * 根据登录Id查找人员信息
     * @param loginId String
     * @return String
     * @throws BusinessException
     */
    public DtoUser findResouce(String loginId) throws BusinessException {
        String sql = "select login.login_id as loginId,hr.position_level as jobcodeid,hr.name as username,hr.home_phone as phone,hr.e_mail as email,hr.types as types,hr.indate as indate,hr.outdate as outdate ,hr.dept as orgId,hr.chinese_name as chinesename,hr.position_type as ptype " +
                     "from essp_hr_employee_main_t hr,"+LOGIN_TABLE+" login " +
                     "where hr.code=login.user_id and lower(login.login_id)=lower('"+loginId+"') ";
        DtoUser dto = null;
        try {
            log.info(sql);
            ResultSet rt  = this.getDbAccessor().executeQuery(sql);
            if(rt.next()){
                dto = new DtoUser();
                dto.setUserLoginId(rt.getString("loginId"));
                String jobcodeId = rt.getString("jobcodeid") ;
                dto.setJobCode(jobcodeId);
                dto.setUserName(rt.getString("username"));
                dto.setPhone(rt.getString("phone"));
                dto.setEmail(rt.getString("email"));
                dto.setInDate(rt.getDate("indate"));
                dto.setOutDate(rt.getDate("outdate"));
                dto.setChineseName(rt.getString("chinesename"));
                String type = rt.getString("types");
                String pttype = rt.getString("ptype");
                List list = listHrConfig("JOBTYP", "NAME_ENGLISH", "CODE");
                List list1 = listHrConfig("PTYPE", "NAME_ENGLISH", "CODE");
                if(list != null)
                    for(int i = 0;i < list.size() ;i ++){
                        SelectOptionImpl option = (SelectOptionImpl) list.get(i);
                        if(option.getValue().equals(type)){
                            dto.setUserType(option.getLabel());
                            break;
                        }
                    }
                if(list1 != null)
                   for(int i = 0;i < list1.size() ;i ++){
                       SelectOptionImpl option = (SelectOptionImpl) list1.get(i);
                       if(option.getValue().equals(pttype)){
                           dto.setPtype(option.getLabel());
                           break;
                       }
                   }

                String orgId = rt.getString("orgId");
                dto.setOrgId(orgId);
                String orgName = OrgnizationFactory.create().getOrgName(orgId);
                dto.setOrganization(orgName);
            }
        } catch (Exception ex) {
            throw new BusinessException("","error while get DtoUser: [" +loginId+ "] ",ex);
        }
        return dto;
    }

    /**
     * 查找客户信息
     * @param loginId String
     * @return DtoUser
     * @throws BusinessException
     */
    public DtoCustomer findCustomer(String loginId) throws BusinessException {
        String sql = "select * from essp_sys_project_customer customer " +
                     "where customer.user_name='"+loginId+"'";
        log.info(sql);
        DtoCustomer dto = null;
        try {
            ResultSet rt = this.getDbAccessor().executeQuery(sql);
            if(rt.next()){
                dto = new DtoCustomer();
                dto.setUserLoginId(loginId);
                dto.setUserName(rt.getString("name"));
                dto.setOrganization(rt.getString("organization"));
                dto.setTitle(rt.getString("title"));
                dto.setPhone(rt.getString("phone"));
                dto.setFax(rt.getString("fax"));
                dto.setEmail(rt.getString("email"));
                dto.setEnable(rt.getString("enable"));
                dto.setPassword(rt.getString("password"));
            }
        } catch (Exception ex) {
            throw new BusinessException("error while get Customer: [" +loginId+ "] ");
        }
        return dto;
    }

    public String getJobCodeById(String jobCodeId) throws BusinessException {
        if(jobCodeId == null)
            return null;
        List jobs = this.listAllPostRank();
        return getJobCode(jobCodeId,jobs);
    }
    private String getJobCode(String jobCodeId,List jobs){
        if(jobCodeId == null || jobs == null)
            return null;
        Iterator i = jobs.iterator();
        while(i.hasNext()){
            SelectOptionImpl opt = (SelectOptionImpl) i.next();
            if(jobCodeId.equals(opt.getValue()))
                return opt.getLabel();
        }
        return null;
    }

    public void addCustomer(DtoCustomer dto) throws BusinessException {
        if(dto == null)
            return ;
        long id = generateCustomerId();
        String sql = "insert into essp_sys_project_customer " +
                    "(id,accountid,user_name,password,name,organization,title,is_manager,phone,fax,email) " +
                    "values('"+id+"','"+0+"','"+dto.getUserLoginId()+"','"+dto.getPassword()+"','"+dto.getUserName()+"','"+dto.getOrganization()+"','"+dto.getTitle()+"','Yes','"+dto.getPhone()+"','"+dto.getFax()+"','"+dto.getEmail()+"')";
       log.info(sql);
       try {
           this.getDbAccessor().executeUpdate(sql);
       } catch (Exception ex) {
           log.error(ex);
           throw new BusinessException("","error while adding a customer!");
       }
    }

    private long generateCustomerId(){
       return HBSeq.getSEQ("essp_sys_project_customer");
    }
    public static void main(String[] args) {
        LgHrUtilImpl lg = new LgHrUtilImpl();
//        String[] orgId = lg.getUserOrg("stone.shi");
//        System.out.println(orgId[0] +  "  " + orgId[1]);
//        DtoCustomer cus = new DtoCustomer();
//        cus.setPassword("abc");
//        cus.setUseLoginName("abc");
//        cus.setUserName("abc");
//        cus.setEmail("eee");
//        cus.setFax("fff");
//        cus.setOrganization("ooo");
//        cus.setPhone("ppp");
//        cus.setTitle("ttt");
//        lg.updateCustomer(cus);
        System.out.println(lg.getChineseName("aidong.huang"));
        ;
    }

    public void updateCustomer(DtoCustomer dto) throws BusinessException {
        if(dto == null)
            return ;
        String sql = "update essp_sys_project_customer set password=?,organization=?,title=?,phone=?,fax=?,email=? where user_name=?";
        log.info(sql);
        List param = new ArrayList();
        param.add(dto.getPassword());
        param.add(dto.getOrganization());
        param.add(dto.getTitle());
        param.add(dto.getPhone());
        param.add(dto.getFax());
        param.add(dto.getEmail());
        param.add(dto.getUserLoginId());
        try {
            this.getDbAccessor().executeUpdate(sql, param);
        } catch (Exception ex) {
            log.error(ex);
            throw new BusinessException("","error while updating customer!");
        }
    }

    public boolean hasProductivity(String loginId) throws BusinessException {
        String sql = "select hr.productivity as hasProductivity " +
                     "from essp_hr_employee_main_t hr,"+LOGIN_TABLE+" login " +
                     "where hr.code=login.user_id and login.login_id='"+loginId+"'";
        try {
            ResultSet rt = this.getDbAccessor().executeQuery(sql);
            while(rt.next()){
                String hasProductivty = rt.getString("hasProductivity");
                return "1".equals(hasProductivty);
            }
        } catch (Exception ex) {
            throw new BusinessException(ex);
        }
        return false;
    }

    public String getChineseName(String loginId) throws BusinessException {
        String sql = "select hr.chinese_name as name from " + HR_TABLE + " hr left join "+LOGIN_TABLE+" login on hr.code=login.user_id where login.login_id='"+loginId+"'";
        log.info(sql);
        try {
            ResultSet rt = this.getDbAccessor().executeQuery(sql);
            if(rt.next()){
                return rt.getString("name");
            }
            return null;
        } catch (Exception ex) {
            throw new BusinessException(ex);
        }
    }

    /**
     * get employee name, if inexistence return loginId
     * @param loginId String
     * @return String
     * @throws BusinessException
     */
    public String getName(String loginId) throws BusinessException {
        if("".equals(StringUtil.nvl(loginId))) {
            return "";
        }
        if(hmNames.containsKey(loginId)) {
            return (String) hmNames.get(loginId);
        }
        String sql = "select hr.name as name from " + HR_TABLE + " hr left join "+LOGIN_TABLE+" login on hr.code=login.user_id where login.login_id='"+loginId+"'";
        log.info(sql);
        try {
            ResultSet rt = this.getDbAccessor().executeQuery(sql);
            if(rt.next()){
                String name = rt.getString("name");
                hmNames.put(loginId, name);
                return name;
            }
            return loginId;
        } catch (Exception ex) {
            throw new BusinessException(ex);
        }
    }

    /**
     * get customer name, if inexistence return loginId
     * @param loginId String
     * @return DtoUser
     * @throws BusinessException
     */
    public String getCustomerName(String loginId) throws BusinessException {
        if("".equals(StringUtil.nvl(loginId))) {
            return "";
        }
        String sql = "select customer.name from essp_sys_project_customer customer " +
                     "where customer.user_name='"+loginId+"'";
        log.info(sql);
        try {
            ResultSet rt = this.getDbAccessor().executeQuery(sql);
            if(rt.next()){
                return rt.getString("name");
            }
        } catch (Exception ex) {
            throw new BusinessException("error while get Customer Name: [" +loginId+ "] ");
        }
        return loginId;
    }

}
