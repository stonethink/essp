package server.essp.pms.account.logic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import c2s.dto.DtoUtil;
import c2s.essp.common.account.IDtoAccount;
import c2s.essp.pms.account.DtoPmsAcnt;
import itf.account.IAccountListUtil;
import server.framework.common.BusinessException;
import server.framework.hibernate.HBComAccess;
import server.framework.logic.AbstractBusinessLogic;
import server.framework.taglib.util.TagUtils;
import itf.orgnization.IOrgnizationUtil;
import itf.orgnization.OrgnizationFactory;
import server.framework.taglib.util.ISelectOption;

public class LgAccountListUtilImp extends AbstractBusinessLogic implements IAccountListUtil {
    //Ĭ����ʾ��ʽ:code -- name
    private String style = IDtoAccount.SHOWSTYLE_DASHED;
    //Ĭ��״̬:Normal
    private String[] status = new String[]{IDtoAccount.ACCOUNT_STATUS_NORMAL};
    //Ĭ�Ϸ�Χ:�ڲ����ⲿ����Account
    private String[] inner = new String[]{
                                 IDtoAccount.ACCOUNT_INNER_IN,
                                 IDtoAccount.ACCOUNT_INNER_OUT
    };
    //Ĭ������:��������
    private String[] type = new String[]{
                            IDtoAccount.ACCOUNT_TYPE_PROJECT,
                            IDtoAccount.ACCOUNT_TYPE_MANAGEMENT,
                            IDtoAccount.ACCOUNT_TYPE_SALES,
                            IDtoAccount.ACCOUNT_TYPE_ADMINISTRATION,
                            IDtoAccount.ACCOUNT_TYPE_HR,
                            IDtoAccount.ACCOUNT_TYPE_FINANCE,
                            IDtoAccount.ACCOUNT_TYPE_SS,
                            IDtoAccount.ACCOUNT_TYPE_OTHERS
    };
    //Ĭ������:��Ŀ����Ͳ�����
    private String[] userType = new String[]{
                                IDtoAccount.USER_TYPE_PM,
                                IDtoAccount.USER_TYPE_PARTICIPANT
    };
    private AccountMapper mapper = null;
    //��ѯ����Account��SQL
    static final String QUERY_ALL_ACCOUNT = "SELECT * FROM pms_acnt WHERE (is_template is null OR is_template='0') ";
    private static final String ORDER_BY = " ORDER BY acnt_id ";
    public LgAccountListUtilImp(){
        super();
        mapper = new AccountMapper(getDbAccessor());
    }
    /**
     * ����Account��ʾ��ʽ
     * @param style String
     */
    public void setShowStyle(String style) {
        checkShowStyle(style);
        this.style = style;
    }
    /**
     * �������е���Ŀ
     * @return List
     * @throws BusinessException
     */
    public List listAllAccounts() throws BusinessException {
        StringBuffer sql = new StringBuffer(QUERY_ALL_ACCOUNT);
        return mapper.sql2Account(sql.append(ORDER_BY).toString());
    }
    /**
     * ����״̬����
     * @param status String[]
     */
    public void statusCondition(String[] status) {
        this.status = status;
    }
    /**
     * ������������
     * @param types String[]
     */
    public void typeCondition(String[] types) {
        this.type = types;
    }
    /**
     *
     * @param inner String[]
     */
    public void innerCondition(String[] inner) {
        this.inner = inner;
    }
    /**
     * ���� ״̬ AND ���� AND �������� ��ѯAccount
     * @return List
     * @throws BusinessException
     */
    public List listAccounts() throws BusinessException {
        StringBuffer sql = new StringBuffer(QUERY_ALL_ACCOUNT);
        sql.append(appendFieldsCondition());
        sql.append(ORDER_BY);
        return mapper.sql2Account(sql.toString());
    }
    public Vector listComboAccounts() throws BusinessException {
        return DtoUtil.list2Combo(listAccounts(),"displayName","rid");
    }
    public List listOptAccounts() throws BusinessException {
        return TagUtils.list2Options(listAccounts(),"displayName","rid");
    }
    /**
     * ����Ҫ��ѯ����Ա����
     * @param userTypes String[]
     */
    public void userTypeCondition(String[] userTypes) {
        this.userType = userTypes;
    }
    /**
     * ���� ״̬ AND ���� AND (��Ա����) ��ѯAccount
     * @param loginID String
     * @return List
     * @throws BusinessException
     */
    public List listAccounts(String loginID) throws BusinessException {
        StringBuffer sql = new StringBuffer(QUERY_ALL_ACCOUNT);
        sql.append(appendFieldsCondition());
        StringBuffer personCondition = appendPersonCondition(loginID);
        if(personCondition != null && personCondition.length() > 0){
            sql.append(" AND ( " + personCondition + " )");
        }
        sql.append(ORDER_BY);
        return mapper.sql2Account(sql.toString());
    }
    public Vector listComboAccounts(String loginID) throws BusinessException {
        return DtoUtil.list2Combo(listAccounts(loginID),"displayName","rid");
    }
    public List listOptAccounts(String loginID) throws BusinessException {
        return TagUtils.list2Options(listAccounts(loginID),"displayName","rid");
    }
    /**
     * ���������Ա������,���LoginId��Ϊ��ͬ�Ľ�ɫ��������Ŀ,Ȼ������Щ��Ŀ��Ϊ��ѯ����
     * @return StringBuffer
     */
    protected StringBuffer appendPersonCondition(String loginID) {
         StringBuffer sql = new StringBuffer("");
         if( userType == null || userType.length == 0 )
             return sql;
         List typeList =  new ArrayList(Arrays.asList(userType));
         //����LoginId��ΪManager��Dept Code,����ЩDeptCode��Ϊ��������
         if(typeList.contains(IDtoAccount.USER_TYPE_EXEC_UNIT_MANAGER)){
             List orgId = getOrgIdOfManager(loginID);
             if(orgId != null && !orgId.isEmpty()){
                 StringBuffer OrgIdCon = contructFieldCondition("acnt_organization",list2Array(orgId));
                 sql.append(OrgIdCon);
             }
             typeList.remove(IDtoAccount.USER_TYPE_EXEC_UNIT_MANAGER);
         }
         //��LoginId��ΪPM��������
         if(typeList.contains(IDtoAccount.USER_TYPE_PM)){
             String managerCon = " ( acnt_manager='"+loginID+"' ) ";
             if(sql != null && sql.length() > 0)
                 sql.append(" OR " +managerCon+ " ");
             else
                 sql.append(managerCon);
         }
         //��LoginId��ΪTC_Signer��������
         if(typeList.contains(IDtoAccount.USER_TYPE_TC_SIGNER)){
             String signerCon = " ( tc_signer='"+loginID+"' ) ";
             if(sql != null && sql.length() > 0)
                 sql.append(" OR " +signerCon+ " ");
             else
                 sql.append(signerCon);
         }
         Set acntRidSet = new HashSet();
         //����LoginId��ΪEBS Manager��EBS������������Ŀ
         if(typeList.contains(IDtoAccount.USER_TYPE_EBS_MANAGER)){
             acntRidSet.addAll(getAcntRidOfEBSManager(loginID));
             typeList.remove(IDtoAccount.USER_TYPE_EBS_MANAGER);
         }
         //������Ϊ�����߹�������Ŀ
         if(typeList.contains(IDtoAccount.USER_TYPE_PARTICIPANT)){
             acntRidSet.addAll(getAcntRidOfParticipant(loginID));
             typeList.remove(IDtoAccount.USER_TYPE_PARTICIPANT);
         }
         //������Ϊ��ϵ�˹�������Ŀ
         if(typeList.contains(IDtoAccount.USER_TYPE_KEY_PERSONAL)){
             acntRidSet.addAll(getAcntRidOfKeyPersonal(loginID));
             typeList.remove(IDtoAccount.USER_TYPE_KEY_PERSONAL);
         }
         if(!acntRidSet.isEmpty()){
             StringBuffer ridCon = contructFieldCondition("rid",list2Array(acntRidSet));
             if(sql != null && sql.length() > 0)
                 sql.append(" OR " +ridCon+ " ");
             else
                 sql.append(ridCon);
         }
         return sql;
    }
    //����״̬�����͵Ĳ�ѯ����
    protected StringBuffer appendFieldsCondition() {
        StringBuffer condition = new StringBuffer();
        //����״̬��ѯ����
        StringBuffer statusCondition= contructFieldCondition("acnt_status" , status);
        if(statusCondition != null && statusCondition.length() > 0)
            condition.append(" AND " + statusCondition );
        //�������Ͳ�ѯ����
        StringBuffer typeCondition= contructFieldCondition("acnt_type" , type);
        if(typeCondition != null && typeCondition.length() > 0)
            condition.append(" AND " + typeCondition );
        //���췶Χ��ѯ����
        StringBuffer innerCondition= contructFieldCondition("acnt_inner" , inner);
        if(innerCondition != null && innerCondition.length() > 0)
            condition.append(" AND " + innerCondition );
        return condition;
    }
    //����ĳ�ֶεĲ�ѯ����
    protected StringBuffer contructFieldCondition(String field,String[] fieldValues){
        StringBuffer result = new StringBuffer("");
        if(fieldValues == null || fieldValues.length == 0)
            return result;
        result.append("( " +field + " IN ( ");
        for(int i = 0;i < fieldValues.length ;i ++){
            result.append("'"+fieldValues[i]+"'");
            if(i < fieldValues.length - 1)
                result.append(",");
        }
        result.append(" ))");
        return result;
    }
    //���show style
    protected void checkShowStyle(String style) throws BusinessException {
        if(!IDtoAccount.SHOWSTYLE_DASHED.equalsIgnoreCase(style)
           && !IDtoAccount.SHOWSTYLE_BRACKET.equalsIgnoreCase(style)
           && !IDtoAccount.SHOWSTYLE_ONLYCODE.equalsIgnoreCase(style)
           && !IDtoAccount.SHOWSTYLE_ONLYNAME.equalsIgnoreCase(style) ){
            throw new BusinessException("","illegal style: " + style);
        }
    }
    //������Ա��ΪEBS Manager ����������Ŀ
    protected Set getAcntRidOfEBSManager(String loginId){
        String sql = "SELECT acnt_rid FROM ebs_ebs9acnt t WHERE ebs_rid IN (SELECT rid FROM ebs_ebs t WHERE t.ebs_manager='"+loginId+"')";
        ResultSet rt = this.getDbAccessor().executeQuery(sql);
        return resultSet2AcntRid( rt);
    }
    //������Ա��Ϊ���������ڵ���Ŀ
    protected Set getAcntRidOfParticipant(String loginId){
        String sql = "SELECT acnt_rid FROM pms_labor_resources WHERE login_id='"+loginId+"' ORDER BY acnt_rid";
        ResultSet rt = this.getDbAccessor().executeQuery(sql);
        return resultSet2AcntRid( rt);
    }
    //������Ա��ΪKey Person���ڵ���Ŀ
    protected Set getAcntRidOfKeyPersonal(String loginId){
        String sql = "SELECT acnt_rid FROM pms_keypesonal WHERE login_id='"+loginId+"' ORDER BY acnt_rid";
        ResultSet rt = this.getDbAccessor().executeQuery(sql);
        return resultSet2AcntRid( rt);
    }
    private Set resultSet2AcntRid(ResultSet rt) throws BusinessException{
        Set result = new HashSet();
        try {
            while (rt.next()) {
                long acntRid = rt.getLong("acnt_rid");
                result.add(acntRid+"");
            }
        } catch (SQLException ex) {
            throw new BusinessException("error.system.db", ex);
        }
        return result;
    }

    private String[] list2Array(Collection typeList) {
        String[] typeArray = new String[0];
        typeArray = (String[]) typeList.toArray(typeArray);
        return typeArray;
    }
    //����LoginIdΪ���ž���Ĳ��Ŵ���
    protected List getOrgIdOfManager(String loginId){
        IOrgnizationUtil orgUtil = OrgnizationFactory.createOrgnizationUtil();
        List options = orgUtil.listAllOrgByManager(loginId);
        List result = new ArrayList();
        if(options != null && !options.isEmpty()){
            for(int i = 0;i < options.size() ;i ++){
                ISelectOption op = (ISelectOption) options.get(i);
                result.add(op.getValue());
            }
        }
        return result;
    }

    //ִ��sql,�����ת����Account����
    class AccountMapper{
        private HBComAccess DbAccessor;
        public AccountMapper(HBComAccess DbAccessor){
            this.DbAccessor = DbAccessor;
        }
        public List sql2Account(String sql) {
            log.info("sql:"+sql);
            List result = new ArrayList();
            try {
                ResultSet rt = DbAccessor.executeQuery(sql);
                while (rt.next()) {
                    IDtoAccount account = new DtoPmsAcnt();

                    account.setActualFinish(rt.getDate("acnt_actual_finish"));
                    account.setActualStart(rt.getDate("acnt_actual_start"));

                    account.setAnticipatedFinish(rt.getDate(
                        "acnt_anticipated_finish"));
                    account.setAnticipatedStart(rt.getDate(
                        "acnt_anticipated_start"));
                    account.setBrief(rt.getString("acnt_brief"));
                    account.setCurrency(rt.getString("acnt_currency"));
                    account.setId(rt.getString("acnt_id"));
                    account.setInner(rt.getString("acnt_inner"));
                    account.setName(rt.getString("acnt_name"));
                    account.setOrganization(rt.getString("acnt_organization"));
                    account.setPlannedFinish(rt.getDate("acnt_planned_finish"));
                    account.setPlannedStart(rt.getDate("acnt_planned_start"));
                    account.setRid(new Long(rt.getLong("rid")));
                    account.setStatus(rt.getString("acnt_status"));
                    account.setType(rt.getString("acnt_type"));
                    account.setManager(rt.getString("acnt_manager"));
                    account.setShowStyle(style);

                    result.add(account);
                }
            } catch (SQLException ex) {
                log.error("illegal sql:"+sql);
                throw new BusinessException("error.system.db", ex);
            }
            return result;
        }
    }
}
