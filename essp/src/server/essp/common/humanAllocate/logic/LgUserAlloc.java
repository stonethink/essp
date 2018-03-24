package server.essp.common.humanAllocate.logic;

import server.framework.logic.*;
import java.util.List;
import server.essp.common.humanAllocate.form.AfUserAllocDirect;
import server.essp.common.humanAllocate.viewbean.VbGeneralUser;
import java.util.ArrayList;
import server.framework.common.BusinessException;
import server.framework.hibernate.HBComAccess;
import server.essp.common.ldap.LDAPUtil;
import java.util.Iterator;
import server.framework.taglib.util.SelectOptionImpl;
import server.essp.common.humanAllocate.form.AfUserAllocCondition;
import server.essp.common.humanAllocate.form.AfUserAllocInAD;
import server.essp.common.humanAllocate.viewbean.VbAllocByCon;
import server.essp.common.humanAllocate.viewbean.VbDetailUser;
import net.sf.hibernate.Session;
import itf.orgnization.OrgnizationFactory;
import itf.orgnization.IOrgnizationUtil;
import itf.hr.HrFactory;
import itf.hr.IHrUtil;
import itf.hr.LgHrUtilImpl;

/**
 * @author not attributable
 * @version 1.0
 */
public class LgUserAlloc extends AbstractBusinessLogic {
        /**
         * ������Դ��
         */
        public static final String HR_TABLE = "ESSP_HR_EMPLOYEE_MAIN_T";
        /**
         * ��֯�ṹ��
         */
        public static final String ORG_TABLE = "ESSP_UPMS_UNIT";
        /**
         * ��֯�ṹ��Ա�����
         */
        public static final String ORG_USER_TABLE = "ESSP_UPMS_UNIT_USER_T";

        private SelectOptionImpl defOpt = new SelectOptionImpl("","title");
        /**
         * ����loginid,empcode��empname��ѯ
         * @return List
         */
        public List allocDirect(AfUserAllocDirect form) throws BusinessException {
            return this.allocDirect(form.getLoginId(),form.getEmpCode(),form.getEmpName());
        }
        public List allocDirect(String loginId,String empCode,String empName) throws BusinessException {
            List params = new ArrayList();
            //ƥ������
            params.add(convert(loginId));
            params.add(convert(empCode));
            params.add(convert(empName));
            //����LOGIN_TABLE��HR_TABLE���������loginid,code,name��sex
            String sql = "select hr.emp_code as code ,hr.name as name,hr.sex as sex,login.login_id as loginid " +
                         "from " + LgHrUtilImpl.LOGIN_TABLE + " login left join " + HR_TABLE + "  hr on hr.code=login.user_id " +
                         "where UPPER(login.login_id) like ? or UPPER(hr.emp_code) like ? or UPPER(hr.name) like ?"
                        ;

           try {
               List result = this.getDbAccessor().executeQueryToList(sql,params,VbGeneralUser.class);
               log.info(sql);
               return result;
           } catch (Exception ex) {
               log.error(ex);
               throw new BusinessException();
           }
        }
        /**
         * ����loginid,empcode��empname��AD�в�ѯ
         * @return List
         */
        public List allocInAD(AfUserAllocInAD form) throws BusinessException {
            return this.allocInAD(form.getSelectDomain(),form.getLoginId(),form.getEmpName(),form.getEmpMail());
        }
        public List allocInAD(String domain,String loginId,String empName,String empMail) throws BusinessException {
           try {
               LDAPUtil ldap = new LDAPUtil();
               List result = ldap.searchUserBases(domain,loginId,empName,empMail);
               return result;
           } catch (Exception ex) {
               log.error(ex);
               throw new BusinessException(ex);
           }
        }
        /**
         * ����ѯ��������ƥ��
         * @param condition String
         * @return String
         */
        private String convert(String condition){
          if("".equals(condition) || "null".equalsIgnoreCase(condition) || condition == null)
              return "";
          else
              return "%" + condition.toUpperCase() + "%";
        }
        /**
         * �������Orgnization�������б�
         * @return List
         */
        public List listAllOrg() throws BusinessException {
            //��ѯ�������������ʾ���Label��Value
            IOrgnizationUtil orgUtil = OrgnizationFactory.create();
            return orgUtil.listOptOrgnization();
        }
        /**
         * ��ѯOrgnization���г�Ա
         * @param orgId String
         * @return List
         */
        public List allocByOBS(String orgId) throws BusinessException {
            List param = new ArrayList();
//            param.add(orgId);
            //�����Ų�ѯ��Ӧ����HR_TABLE��Dept�ֶ�
//            String sql = "select hr.emp_code as code ,hr.name as name,hr.sex as sex,login.loginname as loginid " +
//                         "from " + LOGIN_TABLE + " login " +
//                         "left join " + HR_TABLE + "  hr on hr.code=login.user_id " +
//                         "left join " + ORG_USER_TABLE + " org_user on login.user_id = org_user.user_id " +
//                         "where org_user.unit_id = ?";
            String sql = "select hr.emp_code as code ,hr.name as name,hr.sex as sex,login.login_id as loginid " +
                         "from "+ HR_TABLE +" hr join " + LgHrUtilImpl.LOGIN_TABLE + " login on hr.code=login.user_id " +
                         "where hr.dept in ( "+orgId+" )" +
                         "order by login.login_id";
            try {
                log.info(sql);
                List result = this.getDbAccessor().executeQueryToList(sql, param,VbGeneralUser.class);
                return result;
            } catch (Exception ex) {
                throw new BusinessException(ex);
            }
        }
        /**
         * ����������Դ�йص����ò���,ָ�������б���ʾ��label��value,��ѯ�����װ��SelectOptionImpl
         * @param kindCode String
         * @param <any> unknown
         * @return List
         */
        private List searchHrConfig(String kindCode,String lableField,String valueField) throws BusinessException {
           IHrUtil hrUtil = HrFactory.create() ;
           return hrUtil.listHrConfig(kindCode,lableField,valueField);
        }
        /**
         * �������е�PostRank
         * @return List
         * @throws BusinessException
         */
        public List searchAllPostRank() throws BusinessException {
            List result =  searchHrConfig("RANK","NAME_ENGLISH","CODE");
            if(result != null)
                result.add(0,defOpt);
            return result;
        }
        /**
         * ��������Skill1
         * @return List
         * @throws BusinessException
         */
        public List searchAllSkillLeve11() throws BusinessException {
            List result = searchHrConfig("SKILL1","NAME_ENGLISH","CODE");
            if(result != null)
                result.add(0,defOpt);
            return result;
        }
        /**
         * ��������Skill2
         * @return List
         * @throws BusinessException
         */
        public List searchAllSkillLeve12() throws BusinessException {
            List result = searchHrConfig("SKILL2","NAME_ENGLISH","CODE");
            if(result != null)
                result.add(0,defOpt);
            return result;
        }
        /**
         * ��������Industry
         * @return List
         * @throws BusinessException
         */
        public List searchAllIndustry() throws BusinessException {
            List result = searchHrConfig("OPERA1","NAME_ENGLISH","CODE");
            if(result != null)
                result.add(0,defOpt);
            return result;
        }
        /**
         * ��������Language
         * @return List
         * @throws BusinessException
         */
        public List searchAllLanguage() throws BusinessException {
            List result = searchHrConfig("LANGUAGE","NAME_ENGLISH","CODE");
            if(result != null)
                result.add(0,defOpt);
            return result;
        }
        /**
         * ��������Management
         * @return List
         * @throws BusinessException
         */
        public List searchAllManagement() throws BusinessException {
            List result = searchHrConfig("DOMAIN","NAME_ENGLISH","CODE");
            if(result != null)
                result.add(0,defOpt);
            return result;
        }
        /**
         * ����ͨ�õĵȼ�
         * @return List
         * @throws BusinessException
         */
        public List searchGeneralRanks() throws BusinessException {
            List result = searchHrConfig("LEVEL","NAME_ENGLISH","CODE");
            if(result != null)
                result.add(0,defOpt);
            return result;
        }
        /**
         * �����������������û�
         * @param form AfUserAllocCondition
         * @return VbAllocByCon
         */
        public VbAllocByCon allocByCondition(AfUserAllocCondition form) throws
            BusinessException {
            String typeList = "";  //������ʾResult�ұߵ�ҳ��
            //��ѯ�ı�
            String sTable = " essp_hr_employee_main_t f ,"+LgHrUtilImpl.LOGIN_TABLE+" login ";
            //��ѯ��ʾ���
            String sItemQuery = "f.emp_code as code , f.name as name,f.sex as sex,login.login_id as loginid";
            //��ѯ����
            String sCon = "  login.user_id=f.code ";
            //����SQL���
            if(!"title".equals(form.getManagement())) {
                    typeList +="5";
                    sItemQuery += ",b.domain as industry";
                    sTable = sTable+",essp_hr_management_t b";
                    sCon = sCon+" and b.DOMAIN='"+form.getManagement()+"'";
                    if(!"title".equals(form.getManagementRank()) ) {
                            sCon = sCon+" and b.MYLEVEL='"+form.getManagementRank()+"'";
                            sItemQuery += ",b.MYLEVEL as industryRank";
                    }
                    sCon = sCon+ " and f.CODE = b.CODE";
            }
            if(!"title".equals(form.getLanguage() )) {
                    typeList +="4";
                    sItemQuery += ",g.kind as language";
                    sTable = sTable+", essp_hr_language_t g";
                    sCon = sCon+" and g.KIND='"+form.getLanguage()+"'";
                    if(!"title".equals(form.getLanguageRank())) {
                            sCon = sCon+" and g.MYLEVEL='"+form.getLanguageRank()+"'";
                            sItemQuery += ",g.MYLEVEL as languageRank";
                    }
                    sCon = sCon+ " and f.CODE = g.CODE";
            }

            if(!"title".equals(form.getIndustry() )) {
                    typeList +="3";
                    sItemQuery += ",d.kind as industry";
                    sTable = sTable+",essp_hr_operation_t d";
                    sCon = sCon+" and d.KIND='"+form.getIndustry()+ "'";
                    if(!"title".equals(form.getIndustryRank())) {
                        sItemQuery += ",d.MYLEVEL as industryRank";
                        sCon = sCon+" and d.MYLEVEL='"+form.getIndustryRank()+"'";
                    }
                    sCon = sCon+ " and f.CODE = d.CODE";
            }
            if(!"title".equals(form.getSkill1())) {
                    typeList +="2";
                    sItemQuery += ",c.kind as skill1";
                    String temp_skill = form.getSkill1();
                    if(!"title".equals(form.getSkill2())) {
                            temp_skill = form.getSkill2();
                            sCon = sCon+" and c.DETAILS='"+temp_skill+"'";
                            sItemQuery += ",c.DETAILS as skill2";
                    }else{
                            sCon = sCon+" and c.KIND='"+temp_skill+"'";
                    }
                    sTable = sTable+",essp_hr_skill_t c";

                    if(!"title".equals(form.getSkillRank() )) {
                          sItemQuery += ",c.MYLEVEL as skillRank";
                          sCon = sCon+" and c.MYLEVEL='"+form.getSkillRank()+"'";
                    }
                    sCon = sCon+ " and f.CODE = c.CODE";
            }
            if(!"title".equals(form.getPostRank())) {
                    typeList +="1";
                    sItemQuery += ",f.POSITION_LEVEL as postRank";
                    sCon = sCon+" and f.POSITION_LEVEL = '"+form.getPostRank()+"'";
            }
            String sSql = "select DISTINCT " + sItemQuery + " from " + sTable + " where " + sCon;
            log.info(sSql);
            try {
               List result = this.getDbAccessor().executeQueryToList(sSql, VbDetailUser.class);
               VbAllocByCon vb = new  VbAllocByCon();
               vb.setDetail(result);
               vb.setTypeList(typeList);
               return vb;
            } catch (Exception ex) {
                throw new BusinessException();
            }
        }

        public static void main(String[] args) throws Exception{
            HBComAccess access = new HBComAccess();
            //LgUserAlloc logic = new LgUserAlloc();
            //logic.setDbAccessor(access);
            /*
            List l = logic.allocDirect(null,null,"Rong.Xiao");
            System.out.println(l.size());
            Iterator i = l.iterator();
            while(i.hasNext()){
                VbGeneralUser vb = (VbGeneralUser) i.next();
                System.out.println(vb.getLoginid()+"   "+vb.getCode() + "  " + vb.getSex());
            }
           List l = logic.listAllOrg();
           Iterator i = l.iterator();
           while(i.hasNext()){
               SelectOptionImpl s = (SelectOptionImpl) i.next();
               System.out.println(s.getLabel() + "------" +s.getValue());
           }


           List l = logic.allocByOBS("UNIT00000182");
           Iterator i = l.iterator();
           while(i.hasNext()){
               VbGeneralUser vb = (VbGeneralUser) i.next();
               System.out.println(vb.getLoginid()+"   "+vb.getCode() + "  " + vb.getSex());
           }

           List l = logic.searchGeneralRanks();
           Iterator i = l.iterator();
           while(i.hasNext()){
               SelectOptionImpl s = (SelectOptionImpl) i.next();
               System.out.println(s.getLabel() + "------" +s.getValue());
           }

           AfUserAllocCondition form = new AfUserAllocCondition();
           //form.setPostRank("111");//ceo
           //form.setSkill1("2");//db
           //form.setSkill2("23");//oralce
          // form.setSkillRank("1");//a
           //form.setLanguage("1");//englist
           //form.setLanguageRank("1");
           VbAllocByCon vbean = logic.allocByCondition(form);
           List l = vbean.getDetail();
           Iterator i = l.iterator();
           while(i.hasNext()){
                VbDetailUser vb = (VbDetailUser) i.next();
                System.out.println(vb.getLoginid() + "------" +vb.getName() +"-------" +vb.getCode()+"---"+vb.getSex() +
                                  "-----" + vb.getPostrank()+"===" +vb.getSkill1()+"---"+vb.getLanguage());
           }
           System.out.println(vbean.getTypeList());
*/
        }
}
