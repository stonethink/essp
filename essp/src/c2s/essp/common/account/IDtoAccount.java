package c2s.essp.common.account;

import java.util.*;

public interface IDtoAccount {
    public final static String SESSION_KEY = "SESSION_KEY_ACCOUNT";

    /*
      �Ƿ����ⲿ��Ŀ����
     */
    public final static String ACCOUNT_INNER_OUT = "O";
    public final static String ACCOUNT_INNER_IN = "I";

    /**
     * ��Ŀ״̬ ����Initial/Approved/Cancel/Pending/Closed
     */
    public final static String ACCOUNT_STATUS_INIT = "Initial";
    public final static String ACCOUNT_STATUS_APPROVED = "Approved";
    public final static String ACCOUNT_STATUS_CANCEL = "Cancel";
    public final static String ACCOUNT_STATUS_PENDING = "Pending";
    /**
     * ��Ŀ״̬ ACNT_STATUS: Normal/Closed
     */
    public final static String ACCOUNT_STATUS_NORMAL = "Normal";
    public final static String ACCOUNT_STATUS_CLOSED = "Closed";
    /**
     * ��Ŀ��������
     */
    public final static String PROJECT_RELATION_MASTER = "Master";//����ר��
    public final static String PROJECT_RELATION_SUB = "Sub";//����ר��
    public final static String PROJECT_RELATION_RELATED= "Related";//����ר��
    public final static String PROJECT_RELATION_FINANCE= "Finance";//����ר��
    /**
     * ��Ŀ����  ACNT_ATTRIBUTE: 1���ͻ���2���з���3-����
     */
    public final static String ACCOUNT_ATTRIBUTE_CUSTOMER = "Customer";
    public final static String ACCOUNT_ATTRIBUTE_RESEARCH = "Research";
    public final static String ACCOUNT_ATTRIBUTE_DEPT = "Dept";

    /**
     * ��Ŀ����  ACNT_TYPE: Project/Management/Sales/Administration/HR/Finance/SSE/Others
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
     * ��Ŀ��������͡���PERSON_TYPE,����������Ŀ����,�I�ս���,BD����,���r������,�I�մ����
     */
    public final static String USER_TYPE_APPLICANT = "Applicant"; //��Ո��
    public final static String USER_TYPE_LEADER = "Leader"; //�I�ս���
    public final static String USER_TYPE_PM = "PM"; //��Ŀ����
    public final static String USER_TYPE_BD_MANAGER = "BD_Manager"; //BD����
    public final static String USER_TYPE_TC_SIGNER = "TC_Signer"; //���r������
    public final static String USER_TYPE_SALES = "Sales"; //�I�մ���
    public final static String USER_TYPE_CUST_SERVICE_MANAGER = "Cust_Service_Manager"; //�͑����տ��O
    public final static String USER_TYPE_ENGAGE_MANAGER = "Engage_Manager"; //�N��֧Ԯ����

    public final static String USER_TYPE_EXEC_UNIT_MANAGER ="Exec_Unit_Manager"; //ִ�е�λ�����ţ�������

    public final static String USER_TYPE_PARTICIPANT = "Participant"; //��Ŀ������
    public final static String USER_TYPE_KEY_PERSONAL = "Key_Personal"; //��Ŀ��Ҫ��ϵ��

    public final static String USER_TYPE_EBS_MANAGER = "Ebs_Manager";//EBS������
    
    public final static String USER_TYPE_DEPT_MANAGER = "Dept_Manager";//���ž���
    
    /**
     * �ͻ���ϵ��
     */
    public final static String CUSTOMER_CONTACTOR_CONTRACT="Contract";    //�ͻ���Լ��ϵ��
    public final static String CUSTOMER_CONTACTOR_EXE="Executive";    //�ͻ�ִ����ϵ��
    public final static String CUSTOMER_CONTACTOR_FINANCIAL="Financial";    //�ͻ�������ϵ��

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
     * ��ȡ��Ŀ��ʾ�����ƣ����磬��ʱ��Ҫ����Ŀ��ʾ��AcntID--AcntName
     */
    public String getDisplayName();
    public static final String SHOWSTYLE_DASHED = "id -- name";
    public static final String SHOWSTYLE_BRACKET = "(id)name";
    public static final String SHOWSTYLE_ONLYCODE = "id";
    public static final String SHOWSTYLE_ONLYNAME = "name";
}
