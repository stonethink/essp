package c2s.essp.common.account;

import java.util.*;

public interface IDtoAccount {
    public final static String SESSION_KEY = "SESSION_KEY_ACCOUNT";

    /*
      是否由外部项目导入
     */
    public final static String ACCOUNT_INNER_OUT = "O";
    public final static String ACCOUNT_INNER_IN = "I";

    /**
     * 项目状态 包括Initial/Approved/Cancel/Pending/Closed
     */
    public final static String ACCOUNT_STATUS_INIT = "Initial";
    public final static String ACCOUNT_STATUS_APPROVED = "Approved";
    public final static String ACCOUNT_STATUS_CANCEL = "Cancel";
    public final static String ACCOUNT_STATUS_PENDING = "Pending";
    /**
     * 项目状态 ACNT_STATUS: Normal/Closed
     */
    public final static String ACCOUNT_STATUS_NORMAL = "Normal";
    public final static String ACCOUNT_STATUS_CLOSED = "Closed";
    /**
     * 项目关联类型
     */
    public final static String PROJECT_RELATION_MASTER = "Master";//主属专案
    public final static String PROJECT_RELATION_SUB = "Sub";//从属专案
    public final static String PROJECT_RELATION_RELATED= "Related";//联属专案
    public final static String PROJECT_RELATION_FINANCE= "Finance";//财务专案
    /**
     * 项目属性  ACNT_ATTRIBUTE: 1－客户；2－研发；3-部门
     */
    public final static String ACCOUNT_ATTRIBUTE_CUSTOMER = "Customer";
    public final static String ACCOUNT_ATTRIBUTE_RESEARCH = "Research";
    public final static String ACCOUNT_ATTRIBUTE_DEPT = "Dept";

    /**
     * 项目类型  ACNT_TYPE: Project/Management/Sales/Administration/HR/Finance/SSE/Others
     */
    public final static String ACCOUNT_TYPE_PROJECT = "Project";
    public final static String ACCOUNT_TYPE_MANAGEMENT = "Management";
    public final static String ACCOUNT_TYPE_SALES = "Sales";
    public final static String ACCOUNT_TYPE_ADMINISTRATION = "Administration";
    public final static String ACCOUNT_TYPE_HR = "Human Resource";
    public final static String ACCOUNT_TYPE_FINANCE = "Finance";
    public final static String ACCOUNT_TYPE_SS = "Sales Support";
    public final static String ACCOUNT_TYPE_OTHERS = "Others";

    /**
     * 项目相关人类型　　PERSON_TYPE,　现在有项目经理,業務經理,BD主管,工時表簽核者,業務代表等
     */
    public final static String USER_TYPE_APPLICANT = "Applicant"; //申請人
    public final static String USER_TYPE_LEADER = "Leader"; //業務經理
    public final static String USER_TYPE_PM = "PM"; //项目经理
    public final static String USER_TYPE_BD_MANAGER = "BD_Manager"; //BD主管
    public final static String USER_TYPE_TC_SIGNER = "TC_Signer"; //工時簽核人
    public final static String USER_TYPE_SALES = "Sales"; //業務代表
    public final static String USER_TYPE_CUST_SERVICE_MANAGER = "Cust_Service_Manager"; //客戶服務總監
    public final static String USER_TYPE_ENGAGE_MANAGER = "Engage_Manager"; //銷售支援經理

    public final static String USER_TYPE_EXEC_UNIT_MANAGER ="Exec_Unit_Manager"; //执行单位（部门）管理者

    public final static String USER_TYPE_PARTICIPANT = "Participant"; //项目参与者
    public final static String USER_TYPE_KEY_PERSONAL = "Key_Personal"; //项目重要干系人

    public final static String USER_TYPE_EBS_MANAGER = "Ebs_Manager";//EBS管理者
    
    public final static String USER_TYPE_DEPT_MANAGER = "Dept_Manager";//部门经理
    
    /**
     * 客户联系人
     */
    public final static String CUSTOMER_CONTACTOR_CONTRACT="Contract";    //客户合约联系人
    public final static String CUSTOMER_CONTACTOR_EXE="Executive";    //客户执行联系人
    public final static String CUSTOMER_CONTACTOR_FINANCIAL="Financial";    //客户财务联系人

    public Date getActualFinish();

    public Date getActualStart();

    public Date getAnticipatedFinish();

    public Date getAnticipatedStart();

    public String getBrief();

    public String getCurrency();

    public String getId();

    public String getInner();

    public boolean isKeyPersonal();

    public String getLoginId();

    public boolean isManagement();

    public String getManager();
    
    public String getName();

    public String getStatus();

    public String getType();

    public String getOrganization();

	public boolean isParticipant();
    public Date getPlannedFinish();

    public Date getPlannedStart();

    public boolean isPm();
    public Long getRid();

    public String getShowStyle();

    public void setShowStyle(String showStyle);

    public void setType(String type);

    public void setStatus(String status);

    public void setRid(Long rid);

    public void setPlannedStart(Date plannedStart);

    public void setPlannedFinish(Date plannedFinish);

    public void setParticipant(boolean participant);

    public void setOrganization(String organization);

    public void setName(String name);

    public void setInner(String inner);

    public void setId(String id);

    public void setCurrency(String currency);

    public void setBrief(String brief);

    public void setAnticipatedStart(Date anticipatedStart);

    public void setAnticipatedFinish(Date anticipatedFinish);

    public void setActualStart(Date actualStart);

    public void setActualFinish(Date actualFinish);

    public void setManager(String manager);
    /*
     * 获取项目显示用名称，例如，有时需要将项目显示成AcntID--AcntName
     */
    public String getDisplayName();
    public static final String SHOWSTYLE_DASHED = "id -- name";
    public static final String SHOWSTYLE_BRACKET = "(id)name";
    public static final String SHOWSTYLE_ONLYCODE = "id";
    public static final String SHOWSTYLE_ONLYNAME = "name";
}
