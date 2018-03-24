package server.essp.projectpre.service.constant;

public class Constant {
	
	//专案记录的状态
	public static final String UNSUBMIT="UnSubmit";
	public static final String SUBMIT="Submitted";
	public static final String REJECTED="Rejected";
	public static final String CONFIRMED="Confirmed";
	public static final String CLOSED="Y";
    public static final String UNAPPLY="UnApply";
    public static final String NORMAL="N";
    public static final String ACTIVE="Active";
    public static final String INACTIVE="InActive";
	
	//资料维护的Kind
	public static final String PROJECT_TYPE="ProjectType";
	public static final String PRODUCT_TYPE="ProductType";
	public static final String PRODUCT_ATTRIBUTE="ProductAttribute";
	public static final String WORK_ITEM="WorkItem";
	public static final String TECHNICAL_DOMAIN="TechnicalDomain";
    public static final String ORIGINAL_LANGUAGE="OriginalLanguage";
    public static final String TRANSLATION_LANGUANGE="TranslationLanguage";
    public static final String BUSINESS_TYPE="BusinessType";
    public static final String COUNTRY_CODE="CountryCode";
    public static final String PROJEC_TYPE="ProjectTypeMaintenance";
    
    //IdSetting维护
    public static final String PROJECT_CODE="ProjectCode";
    public static final String GROUP_CODE="GroupCode";
    public static final String CLIENT_CODE="ClientCode";
    
    //IdSetting编码方式
    public static final String YEAR_MONTH_SERIAL_NUMBER ="YearMonthSerialNumber";
    public static final String SERIAL_NUMBER = "SerialNumber";
    
    //专案申请类型
    public static final String PROJECTADDAPP="ProjectAddApp";
    public static final String PROJECTCHANGEAPP="ProjectChangeApp";
    public static final String PROJECTCONFIRMAPP="ProjectConfirmApp";
    public static final String CONTRACTCHANGEAPP="ContractChangeApp";
    
    public static final String PROJECTEDIT="ProjectEdit";
    
    //部门申请类型
    public static final String DEPARTMENTADDAPP="DeptAddApp";
    public static final String DEPARTMENTCHANGEAPP="DeptChangeApp";
    public static final String DEPARTMENTCONFIRMAPP="DeptConfirmApp";
    
    
    //客户申请类型
    public static final String CUSTOMERADDAPP="CustomerAddApp";
    public static final String CUSTOMERCHANGEAPP="CustomerChangeApp";
    public static final String CUSTOMERCONFIRMAPP="CustomerConfirmApp";
     
    //客户属性
    public static final String GROUP = "Group";
    public static final String COMPANY = "Company";
    
    //技术资料补充
    public static final String DEVELOPENVIRONMENT="DevelopEnvironment";
    public static final String TRNSLATEPUBLISHTYPE="TranslatePublishType";
    public static final String JOBSYSTEM="JobSystem";
    public static final String DATABASE="DataBase";
    public static final String TOOL="Tool";
    public static final String NETWORK="NetWork";
    public static final String PROGRAMLANGUAGE="ProgramLanguage";
    public static final String OTHERS="Others";
    public static final String HARDREQ = "HardReq";
    public static final String SOFTREQ = "SoftReq";
    
    //复核专案类型
    public static final String MASTERPROJECTADDCONFIRM="MasterProjectAddConfirm";
    public static final String SUBPROJECTADDCONFIRM="SubProjectAddConfirm";
    public static final String RELATEDPROJECTADDCONFIRM="RelatedProjectAddConfirm";
    public static final String FINANCEPROJECTADDCONFIRM="FinanceProjectAddConfirm";
    public static final String PROJECTCHANGECONFIRM="ProjectChangeConfirm";
    public static final String PROJECTCLOSECONFIRM="ProjectCloseConfirm";
    public static final String CONTRACTPROJECTCHANGECONFIRM="ContractProjectChangeConfirm";
    
    //资料类型
    public static final String LOG_PROJECT="Project";
    public static final String LOG_DEPT="Dept";
    public static final String LOG_CUSTOMER="Customer";
}
