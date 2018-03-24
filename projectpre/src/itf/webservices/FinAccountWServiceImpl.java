package itf.webservices;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Map;

import org.apache.log4j.Category;

import server.framework.common.BusinessException;

import c2s.dto.DtoUtil;

import com.wits.util.Config;

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
public class FinAccountWServiceImpl implements IAccountWService {
    protected static Category log = Category.getInstance("server");

    private Connection conn = null;
    public FinAccountWServiceImpl() {
    }
    private void initDbCon(){
        try {
          Config config = null;
          try {
              config = new Config("db");
          } catch (Exception exception) {}
          String DriverStr = config.getValue("SQLSERVER.Driver");
          String URL = config.getValue("SQLSERVER.URL");
          String userName = config.getValue("SQLServer.UserName");
          String password = config.getValue("SQLServer.Password");
          Class.forName(DriverStr);
          log.info("Connect to Finance DB:"+DriverStr+"\n"+URL+"\n"+userName+"\n"+password);
          conn = DriverManager.getConnection(URL, userName, password);
      } catch (Exception ex) {
          log.error("error create finance db connection!",ex);
      }

    }
    public void addAccount(Map accountFields) {
//        //如果是部门资料则不结转到财务系统
//        if(accountFields.containsKey("deptFlag")){
//            String deptFlag = (String)accountFields.get("deptFlag");
//            if("1".equals(deptFlag)){
//                return;
//            }
//        }
        initDbCon();
        PreparedStatement st = null;
        try {
//            printMap(accountFields);
            ProjectBase projectBase = new ProjectBase();
            DtoUtil.copyMapToBean(projectBase, accountFields);
            //使用PreparedStatement来处理SQL语句，可以避免数据中包含单引号的异常，同时也可以防止中文文字存入数据库中变成乱码
            String sSql = "insert into PROJBASE (PROJID,PROJNAME,NICKNAME,MANAGER,LEADER,COSTDEPT,TCSIGNER,"
                          +"PLANFROM,PLANTO,CUSTSHORT,EST_MANMONTH,CLOSEMARK,"
                          +"ACHIEVE_BELONG,SALES,ACTUFROM,ACTUTO,MANMONTH,PROJTYPENO,"
                          +"PRODUCT_NAME,PROJECT_DESC,APPLICANT,DIVISION_MANAGER,"
                          +"PROJECT_MANAGER,PROJECT_EXEC_UNIT,CUST_SERVICE_MANAGER,"
                          +"ENGAGE_MANAGER,BIZ_SOURCE,MASTER_PROJECTS,PROJECT_PROPERTY,"
                          +"ESTIMATED_WORDS,PRODUCT_TYPE,PRODUCT_PROPERTY,"
                          +"WORK_ITEM,SKILL_DOMAIN,SUPPORT_LANG_ORIGINAL,"
                          +"SUPPORT_LANG_TRANSLATED,DEV_ENV_OS,DEV_ENV_DB,DEV_ENV_TOOL,"
                          +"DEV_ENV_NETWORK,DEV_ENV_LANG,DEV_ENV_OTHER,TRAN_ENV_OS,"
                          +"TRAN_ENV_DB,TRAN_ENV_TOOL,TRAN_ENV_NETWORK,TRAN_ENV_LANG,"
                          +"TRAN_ENV_OTHER,HARDWARE_REQUIRE,SOFTWARE_REQUIRE,CCONTACT,"
                          +"CCONT_TEL,CCONT_EMAIL,PCONTACT,PCONT_TEL,PCONT_EMAIL,ICONTACT,"
                          +"ICONT_TEL,ICONT_EMAIL) values(?,?,?,?,?,?,?,?,?,?,"+
                          "CONVERT(money,'" + projectBase.getEstManmonth() +"'),?,?,?,?,?,"
                          +"CONVERT(money,'" + projectBase.getManMonth() +"'),?,?,?,?,?,?,?,?,?,?,?,?,"
                          +"ROUND('" + projectBase.getEstimatedWords() +"',0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            System.out.println(sSql);
            st = conn.prepareStatement(sSql);
            st.setString(1, projectBase.getProjId());
            st.setString(2, projectBase.getProjName());
            st.setString(3, projectBase.getNickName());
            st.setString(4, projectBase.getManager());
            st.setString(5, projectBase.getLeader());
            st.setString(6, projectBase.getCostDept());
            st.setString(7, projectBase.getTcSigner());
            st.setString(8, projectBase.getPlanFrom());
            st.setString(9, projectBase.getPlanTo());
            st.setString(10, projectBase.getCustShort());
            st.setString(11, projectBase.getCloseMark());
            st.setString(12, projectBase.getAchieveBelong());
            st.setString(13, projectBase.getSales());
            st.setString(14, projectBase.getActuFrom());
            st.setString(15, projectBase.getActuTo());
            st.setString(16, projectBase.getProjTypeNo());
            st.setString(17, projectBase.getProductName());
            st.setString(18, projectBase.getProjectDesc());
            st.setString(19, projectBase.getApplicant());
            st.setString(20, projectBase.getDivisionManager());
            st.setString(21, projectBase.getProjectManager());
            st.setString(22, projectBase.getProjectExecUnit());
            st.setString(23, projectBase.getCustServiceManager());
            st.setString(24, projectBase.getEngageManager());
            st.setString(25, projectBase.getBizSource());
            st.setString(26, projectBase.getMasterProjects());
            st.setString(27, projectBase.getProjectProperty());
            st.setString(28, projectBase.getProductType());
            st.setString(29, projectBase.getProductProperty());
            st.setString(30, projectBase.getWorkItem());
            st.setString(31, projectBase.getSkillDomain());
            st.setString(32, projectBase.getOriginalLan());
            st.setString(33, projectBase.getTransLan());
            st.setString(34, projectBase.getDevEnvOs());
            st.setString(35, projectBase.getDevEnvDb());
            st.setString(36, projectBase.getDevEnvTool());
            st.setString(37, projectBase.getDevEnvNetwork());
            st.setString(38, projectBase.getDevEnvLang());
            st.setString(39, projectBase.getDevEnvOther());
            st.setString(40, projectBase.getTranEnvOs());
            st.setString(41, projectBase.getTranEnvDb());
            st.setString(42, projectBase.getTranEnvTool());
            st.setString(43, projectBase.getTranEnvNetwork());
            st.setString(44, projectBase.getTranEnvLang());
            st.setString(45, projectBase.getTranEnvOther());
            st.setString(46, projectBase.getHardWareReq());
            st.setString(47, projectBase.getSoftWareReq());
            st.setString(48, projectBase.getCcontact());
            st.setString(49, projectBase.getCcontTel());
            st.setString(50, projectBase.getCcontEmail());
            st.setString(51, projectBase.getPcontact());
            st.setString(52, projectBase.getPcontTel());
            st.setString(53, projectBase.getPcontEmail());
            st.setString(54, projectBase.getIcontact());
            st.setString(55, projectBase.getIcontTel());
            st.setString(56, projectBase.getIcontEmail());
            st.executeUpdate();
            conn.commit();

        } catch (Exception ex) {
            try {
                log.error("error while adding account from web services!",ex);
                conn.rollback();
                throw new BusinessException("INSERT Finance ERROR", ex);
            } catch (Exception ex1) {
                ex1.printStackTrace();
                throw new BusinessException("INSERT Finance Rollback ERROR", ex1);
            }
        } finally {
            if (st != null) {
                try {
                    st.close();
                    conn.close();
                } catch (Exception e) {

                }
            }
        }

    }

    public void updateAccount(Map accountFields) {
//        //如果是部门资料则不结转到财务系统
//        if(accountFields.containsKey("deptFlag")){
//            String deptFlag = (String)accountFields.get("deptFlag");
//            if("1".equals(deptFlag)){
//                return;
//            }
//        }
        initDbCon();
        PreparedStatement st = null;
        try {
            ProjectBase projectBase = new ProjectBase();
            DtoUtil.copyMapToBean(projectBase, accountFields);
            //使用PreparedStatement来处理SQL语句，可以避免数据中包含单引号的异常，同时也可以防止中文文字存入数据库中变成乱码
            String sSql = "update PROJBASE set PROJNAME=?"
                          +",NICKNAME=?,MANAGER=?,"
                          +"LEADER=?,COSTDEPT=?,TCSIGNER=?,PLANFROM=?,PLANTO=?,"
                          +"CUSTSHORT=?,EST_MANMONTH=CONVERT(money,'" + projectBase.getEstManmonth() + "'),"
                          +"ACHIEVE_BELONG=?,SALES=?,ACTUFROM=?,ACTUTO=?,"
                          +"MANMONTH=CONVERT(money,'" + projectBase.getManMonth() +"'),"
                          +"PROJTYPENO=?,PRODUCT_NAME=?,PROJECT_DESC=?,APPLICANT=?,"
                          +"DIVISION_MANAGER=?,PROJECT_MANAGER=?,PROJECT_EXEC_UNIT=?,"
                          +"CUST_SERVICE_MANAGER=?,ENGAGE_MANAGER=?,BIZ_SOURCE=?,"
                          +"MASTER_PROJECTS=?,PROJECT_PROPERTY=?,"
                          +"ESTIMATED_WORDS=ROUND('" + projectBase.getEstimatedWords() +"',0),"
                          +"PRODUCT_TYPE=?,PRODUCT_PROPERTY=?,WORK_ITEM=?,"
                          +"SKILL_DOMAIN=?,SUPPORT_LANG_ORIGINAL=?,SUPPORT_LANG_TRANSLATED=?,"
                          +"DEV_ENV_OS=?,DEV_ENV_DB=?,DEV_ENV_TOOL=?,DEV_ENV_NETWORK=?,"
                          +"DEV_ENV_LANG=?,DEV_ENV_OTHER=?,TRAN_ENV_OS=?,"
                          +"TRAN_ENV_DB=?,TRAN_ENV_TOOL=?,TRAN_ENV_NETWORK=?,"
                          +"TRAN_ENV_LANG=?,TRAN_ENV_OTHER=?,HARDWARE_REQUIRE=?,"
                          +"SOFTWARE_REQUIRE=?,CCONTACT=?,CCONT_TEL=?,CCONT_EMAIL=?,"
                          +"PCONTACT=?,PCONT_TEL=?,PCONT_EMAIL=?,ICONTACT=?,"
                          +"ICONT_TEL=?,ICONT_EMAIL=? "
                          +"where PROJID=?";
            System.out.println(sSql);
            st = conn.prepareStatement(sSql);
            st.setString(1, projectBase.getProjName());
            st.setString(2, projectBase.getNickName());
            st.setString(3, projectBase.getManager());
            st.setString(4, projectBase.getLeader());
            st.setString(5, projectBase.getCostDept());
            st.setString(6, projectBase.getTcSigner());
            st.setString(7, projectBase.getPlanFrom());
            st.setString(8, projectBase.getPlanTo());
            st.setString(9, projectBase.getCustShort());
            st.setString(10, projectBase.getAchieveBelong());
            st.setString(11, projectBase.getSales());
            st.setString(12, projectBase.getActuFrom());
            st.setString(13, projectBase.getActuTo());
            st.setString(14, projectBase.getProjTypeNo());
            st.setString(15, projectBase.getProductName());
            st.setString(16, projectBase.getProjectDesc());
            st.setString(17, projectBase.getApplicant());
            st.setString(18, projectBase.getDivisionManager());
            st.setString(19, projectBase.getProjectManager());
            st.setString(20, projectBase.getProjectExecUnit());
            st.setString(21, projectBase.getCustServiceManager());
            st.setString(22, projectBase.getEngageManager());
            st.setString(23, projectBase.getBizSource());
            st.setString(24, projectBase.getMasterProjects());
            st.setString(25, projectBase.getProjectProperty());
            st.setString(26, projectBase.getProductType());
            st.setString(27, projectBase.getProductProperty());
            st.setString(28, projectBase.getWorkItem());
            st.setString(29, projectBase.getSkillDomain());
            st.setString(30, projectBase.getOriginalLan());
            st.setString(31, projectBase.getTransLan());
            st.setString(32, projectBase.getDevEnvOs());
            st.setString(33, projectBase.getDevEnvDb());
            st.setString(34, projectBase.getDevEnvTool());
            st.setString(35, projectBase.getDevEnvNetwork());
            st.setString(36, projectBase.getDevEnvLang());
            st.setString(37, projectBase.getDevEnvOther());
            st.setString(38, projectBase.getTranEnvOs());
            st.setString(39, projectBase.getTranEnvDb());
            st.setString(40, projectBase.getTranEnvTool());
            st.setString(41, projectBase.getTranEnvNetwork());
            st.setString(42, projectBase.getTranEnvLang());
            st.setString(43, projectBase.getTranEnvOther());
            st.setString(44, projectBase.getHardWareReq());
            st.setString(45, projectBase.getSoftWareReq());
            st.setString(46, projectBase.getCcontact());
            st.setString(47, projectBase.getCcontTel());
            st.setString(48, projectBase.getCcontEmail());
            st.setString(49, projectBase.getPcontact());
            st.setString(50, projectBase.getPcontTel());
            st.setString(51, projectBase.getPcontEmail());
            st.setString(52, projectBase.getIcontact());
            st.setString(53, projectBase.getIcontTel());
            st.setString(54, projectBase.getIcontEmail());
            st.setString(55, projectBase.getProjId());
            st.executeUpdate();
            conn.commit();

        } catch (Exception ex) {
            try {
                log.error("error while updating account from web services!",ex);
                conn.rollback();
                throw new BusinessException("UPDATE Finance ERROR", "UPDATE Finance ERROR",ex);
            } catch (Exception ex1) {
                log.error(ex1);
                throw new BusinessException("UPDATE Finance Rollback ERROR", ex1);
            }
        } finally {
            if (st != null) {
                try {
                    st.close();
                    conn.close();
                } catch (Exception e) {

                }
            }
        }

    }

//    private void printMap(Map map) {
//        if (map == null) {
//            System.out.println("null map");
//        }
//
//        Iterator it = map.keySet().iterator();
//        while (it.hasNext()) {
//            Object key = it.next();
//            Object value = map.get(key);
//            System.out.println("[" + key + "]=" + value);
//        }
//    }

    public void closeAccount(String acntId) {
        initDbCon();
        Statement st = null;
         try {

             String sSql = "update PROJBASE set CLOSEMARK='Y' where PROJID='" + acntId + "'";
//             System.out.println(sSql);
             st = conn.createStatement();
             st.executeUpdate(sSql);
             conn.commit();

         } catch (Exception ex) {
             try {
                 log.error("error while closing account from web services!",ex);
                 conn.rollback();
                 throw new BusinessException("Close Finance ERROR", ex);
             } catch (Exception ex1) {
                 log.error(ex1);
                 throw new BusinessException("Close Finance Rollback ERROR", ex1);
             }
         } finally {
             if (st != null) {
                 try {
                     st.close();
                     conn.close();
                 } catch (Exception e) {

                 }
             }
         }


    }
}
