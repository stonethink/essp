package server.essp.sync;

import static org.easymock.EasyMock.*;

import itf.webservices.IAccountWService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.RowSet;

import c2s.essp.common.account.IDtoAccount;
import server.essp.projectpre.db.Acnt;
import server.essp.projectpre.db.AcntCustContactor;
import server.essp.projectpre.db.AcntPerson;
import server.essp.projectpre.db.AcntTechinfo;
import server.essp.projectpre.db.AcntTechinfoId;
import server.essp.projectpre.db.Parameter;
import server.essp.projectpre.db.ParameterId;
import server.essp.projectpre.db.PpSyncException;
import server.essp.projectpre.service.account.IAccountService;
import server.essp.projectpre.service.constant.Constant;
import server.essp.projectpre.service.parameter.IParameterService;
import server.essp.projectpre.service.syncException.SyncExceptionImp;
import junit.framework.TestCase;

/*
 * Created on 2007-10-25
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

public class SyncExceptionImpTest extends TestCase {
    
    private SyncExceptionImp syncImp;
    private IAccountService accountMock;
    private IAccountWService finAccountMock;
    private IAccountWService tsAccountMock; 
    private IParameterService parameterMock;
    
    private static String LEADER =IDtoAccount.USER_TYPE_LEADER;
    private static String TC_SINGER =IDtoAccount.USER_TYPE_TC_SIGNER;
    private static String SALES =IDtoAccount.USER_TYPE_SALES;
    private static String APPLICANT =IDtoAccount.USER_TYPE_APPLICANT;
    private static String BD_MANAGER =IDtoAccount.USER_TYPE_BD_MANAGER;
    private static String PM =IDtoAccount.USER_TYPE_PM;
    private static String CUST_MANAGER =IDtoAccount.USER_TYPE_CUST_SERVICE_MANAGER;
    private static String ENGAGE_MANAGER =IDtoAccount.USER_TYPE_ENGAGE_MANAGER;
    
    private static String TECH_PROJECT_TYPE = Constant.PROJECT_TYPE;
    private static String TECH_PRODUCT_TYPE = Constant.PRODUCT_TYPE;
    private static String TECH_PRODUCT_ATTRIBUTE = Constant.PRODUCT_ATTRIBUTE;
    private static String TECH_WORK_ITEM = Constant.WORK_ITEM;
    private static String TECH_TECHNICAL_DOMAIN = Constant.TECHNICAL_DOMAIN;
    private static String TECH_ORIGINAL_LANGUAGE = Constant.ORIGINAL_LANGUAGE;
    private static String TECH_TRANSLATION_LANGUANGE = Constant.TRANSLATION_LANGUANGE;
    private static String DEV_ENVIROMENT = Constant.DEVELOPENVIRONMENT;
    private static String JOB_SYS = Constant.JOBSYSTEM;
    private static String TRANS_PUBLISH = Constant.TRNSLATEPUBLISHTYPE;
    private static String DATA_BASE = Constant.DATABASE;
    private static String TOOL = Constant.TOOL;
    private static String NET_WORK = Constant.NETWORK;
    private static String PROGRAM_LANGUAGE  = Constant.PROGRAMLANGUAGE;
    private static String OTHERS = Constant.OTHERS;
    private static String CUST_CONTACTOR = IDtoAccount.CUSTOMER_CONTACTOR_CONTRACT;
    private static String CUST_EXE = IDtoAccount.CUSTOMER_CONTACTOR_EXE;
    private static String CUST_FINANCIAL = IDtoAccount.CUSTOMER_CONTACTOR_FINANCIAL;
    
    private static String HARD_REQ = Constant.HARDREQ;
    private static String SOFT_REQ = Constant.SOFTREQ;
    private static Map dataMap;
    static{
        dataMap = new HashMap();
        Acnt acnt = new Acnt();
        acnt.setRid(Long.valueOf(777));
        acnt.setAchieveBelong("TTT");
        acnt.setAcntBrief("GGG");
        acnt.setAcntFullName("FULLNAME");
        acnt.setAcntId("W111");
        acnt.setAcntOrganization("dada");
        acnt.setAcntName("acntName");
        acnt.setCostAttachBd("TTTTY");
        acnt.setExecUnitId("ExecutUnitId");
        acnt.setCustomerId("customerId");
        acnt.setEstManmonth(Double.valueOf(100));
        acnt.setAchieveBelong("achieveBelong");
        acnt.setActualManmonth(Double.valueOf(80));
        acnt.setProductName("productName");
        acnt.setAcntBrief("breif");
        acnt.setBizSource("BizSource");
        acnt.setContractAcntId("contactorAcntId");
        acnt.setAcntAttribute("attribute");
        acnt.setEstSize(Long.valueOf(10));
        dataMap.put("acnt",acnt);
        
        AcntPerson acntPerson = new AcntPerson();
        acntPerson.setAcntRid(Long.valueOf(777));
        acntPerson.setPersonType(LEADER);
        acntPerson.setLoginId("wh0201859");
        acntPerson.setName("dadd");
        dataMap.put("acntPerson1",acntPerson);
        
        acntPerson = new AcntPerson();
        acntPerson.setAcntRid(Long.valueOf(777));
        acntPerson.setPersonType(TC_SINGER);
        acntPerson.setLoginId("wh0201810");
        acntPerson.setName("ddddd");
        dataMap.put("acntPerson2",acntPerson);
        
        acntPerson = new AcntPerson();
        acntPerson.setAcntRid(Long.valueOf(777));
        acntPerson.setPersonType(SALES);
        acntPerson.setLoginId("wh020100");
        acntPerson.setName("ddaaa");
        dataMap.put("acntPerson3",acntPerson);
        
        acntPerson = new AcntPerson();
        acntPerson.setAcntRid(Long.valueOf(777));
        acntPerson.setPersonType(APPLICANT);
        acntPerson.setLoginId("wh020100");
        acntPerson.setName("applicant");
        dataMap.put("acntPerson4",acntPerson);
        
        acntPerson = new AcntPerson();
        acntPerson.setAcntRid(Long.valueOf(777));
        acntPerson.setPersonType(BD_MANAGER);
        acntPerson.setLoginId("wh021000");
        acntPerson.setName("bdManager");
        dataMap.put("acntPerson5",acntPerson);
        
        acntPerson = new AcntPerson();
        acntPerson.setAcntRid(Long.valueOf(777));
        acntPerson.setPersonType(PM);
        acntPerson.setLoginId("wh030100");
        acntPerson.setName("PM");
        dataMap.put("acntPerson6",acntPerson);
        
        acntPerson = new AcntPerson();
        acntPerson.setAcntRid(Long.valueOf(777));
        acntPerson.setPersonType(CUST_MANAGER);
        acntPerson.setLoginId("wh0201001");
        acntPerson.setName("custManager");
        dataMap.put("acntPerson7",acntPerson);
        
        acntPerson = new AcntPerson();
        acntPerson.setAcntRid(Long.valueOf(777));
        acntPerson.setPersonType(ENGAGE_MANAGER);
        acntPerson.setLoginId("wh0201041");
        acntPerson.setName("engageManager");
        dataMap.put("acntPerson8",acntPerson);
        
        
        AcntTechinfo tech = new AcntTechinfo();
        List techList = new ArrayList();
        AcntTechinfoId techId = new AcntTechinfoId();
        techId.setAcntRid(Long.valueOf(777));
        techId.setCode("projectType");
        techId.setKind(TECH_PROJECT_TYPE);
        tech.setRid(Long.valueOf(10));
        tech.setId(techId);
        techList.add(tech);
        dataMap.put("tech1",techList);
        
        tech = new AcntTechinfo();
        techList = new ArrayList();
        techId = new AcntTechinfoId();
        techId.setAcntRid(Long.valueOf(777));
        techId.setCode("projectType1");
        techId.setKind(TECH_PROJECT_TYPE);
        tech.setRid(Long.valueOf(10));
        tech.setId(techId);
        techList.add(tech);
        dataMap.put("tech0",techList);
        
        techList = new ArrayList();
        tech = new AcntTechinfo();
        techId = new AcntTechinfoId();
        techId.setAcntRid(Long.valueOf(777));
        techId.setCode("productType");
        techId.setKind(TECH_PRODUCT_TYPE );
        tech.setRid(Long.valueOf(11));
        tech.setId(techId);
        techList.add(tech);
        dataMap.put("tech2",techList);
        
        techList = new ArrayList();
        tech = new AcntTechinfo();
        techId = new AcntTechinfoId();
        techId.setAcntRid(Long.valueOf(777));
        techId.setCode("productAttribute");
        techId.setKind(TECH_PRODUCT_ATTRIBUTE );
        tech.setRid(Long.valueOf(12));
        tech.setId(techId);
        techList.add(tech);
        dataMap.put("tech3",techList);
        
        techList = new ArrayList();
        tech = new AcntTechinfo();
        techId = new AcntTechinfoId();
        techId.setAcntRid(Long.valueOf(777));
        techId.setCode("workItem");
        techId.setKind(TECH_WORK_ITEM );
        tech.setRid(Long.valueOf(13));
        tech.setId(techId);
        techList.add(tech);
        dataMap.put("tech4",techList);
        
        techList = new ArrayList();
        tech = new AcntTechinfo();
        techId = new AcntTechinfoId();
        techId.setAcntRid(Long.valueOf(777));
        techId.setCode("techDomain");
        techId.setKind(TECH_TECHNICAL_DOMAIN );
        tech.setRid(Long.valueOf(14));
        tech.setId(techId);
        techList.add(tech);
        dataMap.put("tech5",techList);
        
        techList = new ArrayList();
        tech = new AcntTechinfo();
        techId = new AcntTechinfoId();
        techId.setAcntRid(Long.valueOf(777));
        techId.setCode("techOrigLan");
        techId.setKind(TECH_ORIGINAL_LANGUAGE );
        tech.setRid(Long.valueOf(15));
        tech.setId(techId);
        techList.add(tech);
        dataMap.put("tech6",techList);
        
        techList = new ArrayList();
        tech = new AcntTechinfo();
        techId = new AcntTechinfoId();
        techId.setAcntRid(Long.valueOf(777));
        techId.setCode("transLan");
        techId.setKind(TECH_TRANSLATION_LANGUANGE );
        tech.setRid(Long.valueOf(16));
        tech.setId(techId);
        techList.add(tech);
        dataMap.put("tech7",techList);
        
        tech = new AcntTechinfo();
        techId = new AcntTechinfoId();
        techId.setAcntRid(Long.valueOf(777));
        techId.setCode(HARD_REQ);
        techId.setKind(HARD_REQ);
        tech.setRid(Long.valueOf(17));
        tech.setId(techId);
        dataMap.put("tech8",tech);
        
        tech = new AcntTechinfo();
        techId = new AcntTechinfoId();
        techId.setAcntRid(Long.valueOf(777));
        techId.setCode(SOFT_REQ );
        techId.setKind(SOFT_REQ );
        tech.setRid(Long.valueOf(18));
        tech.setId(techId);
        dataMap.put("tech9",tech);
        
        tech = new AcntTechinfo();
        techId = new AcntTechinfoId();
        techId.setAcntRid(Long.valueOf(777));
        techId.setCode(DEV_ENVIROMENT );
        techId.setKind(JOB_SYS );
        tech.setRid(Long.valueOf(19));
        tech.setId(techId);
        dataMap.put("tech10",tech);
        
        tech = new AcntTechinfo();
        techId = new AcntTechinfoId();
        techId.setAcntRid(Long.valueOf(777));
        techId.setCode(DEV_ENVIROMENT );
        techId.setKind(DATA_BASE);
        tech.setRid(Long.valueOf(20));
        tech.setId(techId);
        dataMap.put("tech11",tech);
        
        tech = new AcntTechinfo();
        techId = new AcntTechinfoId();
        techId.setAcntRid(Long.valueOf(777));
        techId.setCode(DEV_ENVIROMENT);
        techId.setKind(TOOL);
        tech.setRid(Long.valueOf(21));
        tech.setId(techId);
        dataMap.put("tech12",tech);
        
        tech = new AcntTechinfo();
        techId = new AcntTechinfoId();
        techId.setAcntRid(Long.valueOf(777));
        techId.setCode(DEV_ENVIROMENT);
        techId.setKind(NET_WORK);
        tech.setRid(Long.valueOf(22));
        tech.setId(techId);
        dataMap.put("tech13",tech);
        
        tech = new AcntTechinfo();
        techId = new AcntTechinfoId();
        techId.setAcntRid(Long.valueOf(777));
        techId.setCode(DEV_ENVIROMENT );
        techId.setKind(PROGRAM_LANGUAGE );
        tech.setRid(Long.valueOf(23));
        tech.setId(techId);
        dataMap.put("tech14",tech);
        
        tech = new AcntTechinfo();
        techId = new AcntTechinfoId();
        techId.setAcntRid(Long.valueOf(777));
        techId.setCode(DEV_ENVIROMENT);
        techId.setKind(OTHERS);
        tech.setRid(Long.valueOf(24));
        tech.setId(techId);
        dataMap.put("tech15",tech);
        
        tech = new AcntTechinfo();
        techId = new AcntTechinfoId();
        techId.setAcntRid(Long.valueOf(777));
        techId.setCode(TRANS_PUBLISH );
        techId.setKind(JOB_SYS );
        tech.setRid(Long.valueOf(25));
        tech.setId(techId);
        dataMap.put("tech16",tech);
        
        tech = new AcntTechinfo();
        techId = new AcntTechinfoId();
        techId.setAcntRid(Long.valueOf(777));
        techId.setCode(TRANS_PUBLISH );
        techId.setKind(DATA_BASE );
        tech.setRid(Long.valueOf(26));
        tech.setId(techId);
        dataMap.put("tech17",tech);
        
        tech = new AcntTechinfo();
        techId = new AcntTechinfoId();
        techId.setAcntRid(Long.valueOf(777));
        techId.setCode(TRANS_PUBLISH  );
        techId.setKind(TOOL);
        tech.setRid(Long.valueOf(27));
        tech.setId(techId);
        dataMap.put("tech18",tech);
        
        tech = new AcntTechinfo();
        techId = new AcntTechinfoId();
        techId.setAcntRid(Long.valueOf(777));
        techId.setCode(TRANS_PUBLISH);
        techId.setKind(NET_WORK);
        tech.setRid(Long.valueOf(28));
        tech.setId(techId);
        dataMap.put("tech19",tech);
        
        tech = new AcntTechinfo();
        techId = new AcntTechinfoId();
        techId.setAcntRid(Long.valueOf(777));
        techId.setCode(TRANS_PUBLISH);
        techId.setKind(PROGRAM_LANGUAGE  );
        tech.setRid(Long.valueOf(29));
        tech.setId(techId);
        dataMap.put("tech20",tech);
        
        tech = new AcntTechinfo();
        techId = new AcntTechinfoId();
        techId.setAcntRid(Long.valueOf(777));
        techId.setCode(TRANS_PUBLISH);
        techId.setKind(OTHERS);
        tech.setRid(Long.valueOf(30));
        tech.setId(techId);
        dataMap.put("tech21",tech);
        
        AcntCustContactor contactor = new AcntCustContactor();
        contactor.setRid(Long.valueOf(11));
        contactor.setAcntRid(Long.valueOf(777));
        contactor.setContactorType(CUST_CONTACTOR);
        contactor.setName("yyy");
        contactor.setTelephone("85324421");
        contactor.setEmail("aa@163.com");
        dataMap.put("contactor1",contactor);
        
        contactor = new AcntCustContactor();
        contactor.setRid(Long.valueOf(11));
        contactor.setAcntRid(Long.valueOf(777));
        contactor.setContactorType(CUST_EXE );
        contactor.setName("ttt");
        contactor.setTelephone("85324421");
        contactor.setEmail("aa@163.com");
        dataMap.put("contactor2",contactor);
        
        contactor = new AcntCustContactor();
        contactor.setRid(Long.valueOf(11));
        contactor.setAcntRid(Long.valueOf(777));
        contactor.setContactorType(CUST_FINANCIAL);
        contactor.setName("ggg");
        contactor.setTelephone("85324421");
        contactor.setEmail("aa@163.com");
        dataMap.put("contactor3",contactor);
        
        Parameter parameter = new Parameter();
        ParameterId parameterId = new ParameterId();
        parameterId.setCode(TECH_PROJECT_TYPE);
        parameterId.setKind("projectType1");
        parameter.setRid(Long.valueOf(11));
        parameter.setCompId(parameterId);
        dataMap.put("parameter",parameter);
       
    }
    
    
    public SyncExceptionImp getFixture() throws Exception {
        if (syncImp == null) {
            syncImp = new SyncExceptionImp();
        }
        return syncImp;
    }
    
    public SyncExceptionImpTest(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        super.setUp();
        syncImp = new SyncExceptionImp();
        accountMock = createMock(IAccountService.class);
        finAccountMock = createMock(IAccountWService.class);
        tsAccountMock = createMock(IAccountWService.class);
        parameterMock = createMock(IParameterService.class);
        syncImp.getDbAccessor().followTx();
       
    }

    protected void tearDown() throws Exception {
        syncImp.getDbAccessor().endTxRollback();
        syncImp = null;
        super.tearDown();
    }
    
    public void testAddSuccessOne() throws SQLException {
        PpSyncException syncExcp = new PpSyncException();
        syncExcp.setRid(998);
        syncExcp.setAcntId("23");
        syncExcp.setAcntRid(99);
        syncExcp.setAcntName("dafa");
        syncExcp.setModel("FinAccount");
        syncExcp.setStatus("Active");
        syncExcp.setType("Apply");
        syncExcp.setImpClassName("FinAccountWServiceImpl");
        syncImp.save(syncExcp);
//        String querySql = "select s.status from pp_sync_exception s where acnt_rid=998";
//        RowSet rs = syncImp.getDbAccessor().executeQuery(querySql);
//        String model = "";
//        String impClassName = "";
//        if(rs.next()){
//            model = (String)rs.getString("model");
//            impClassName = (String)rs.getString("impClassName");
//        }
//        assertEquals("Model is right","FinAccount",model);
//        assertEquals("ImpClassName is right","FinAccountWServiceImpl",impClassName);
    }

    public void testAddSuccessTwo() {
        PpSyncException syncExcp = new PpSyncException();
        syncExcp.setRid(9999);
        syncExcp.setAcntId("23");
        syncExcp.setAcntRid(888);
        syncExcp.setAcntName("dafa");
        syncExcp.setModel("FinAccount");
        syncExcp.setStatus("Active");
        syncExcp.setType("Apply");
        syncExcp.setImpClassName("FinAccountWServiceImpl");
        syncImp.save(syncExcp);
    }
    
    public void testUpdateSuccessTwo() throws SQLException {
        String sqlSync = "insert into pp_sync_exception(rid,acnt_rid,acnt_id,acnt_name,model,status,type)" +
        " values(9999999,888,'23','test','FinAccount','Active','Apply')";
        syncImp.getDbAccessor().executeUpdate(sqlSync);
        syncImp.updateStaus(Long.valueOf(9999999));
        
        String querySql = "select s.status from pp_sync_exception s where acnt_rid=888";
        RowSet rs = syncImp.getDbAccessor().executeQuery(querySql);
        String status = "";
        if(rs.next()){
            status = (String)rs.getString("status");
        }
        assertEquals("Status is right","Active",status);
    }
    
    public void testUpdateTimeSheetOrFinanceSuccess() throws RuntimeException, Exception {
        String sqlSync = "insert into pp_sync_exception(rid,acnt_rid,acnt_id,acnt_name,model,status,type,imp_class_name)" +
        " values(7777777,777,'23','test','FinAccount','Active','Apply','FinAccountWServiceImpl')";
        syncImp.getDbAccessor().executeUpdate(sqlSync);
        
        AcntPerson acntPerson1 = (AcntPerson) dataMap.get("acntPerson1");
        AcntPerson acntPerson2 = (AcntPerson) dataMap.get("acntPerson2");
        AcntPerson acntPerson3 = (AcntPerson) dataMap.get("acntPerson3");
        AcntPerson acntPerson4 = (AcntPerson) dataMap.get("acntPerson4");
        AcntPerson acntPerson5 = (AcntPerson) dataMap.get("acntPerson5");
        AcntPerson acntPerson6 = (AcntPerson) dataMap.get("acntPerson6");
        AcntPerson acntPerson7 = (AcntPerson) dataMap.get("acntPerson7");
        AcntPerson acntPerson8 = (AcntPerson) dataMap.get("acntPerson8");
        
        List tech0 = (List)dataMap.get("tech0");
        List tech1 = (List)dataMap.get("tech1");
        List tech2 = (List)dataMap.get("tech2");
        List tech3 = (List)dataMap.get("tech3");
        List tech4 = (List)dataMap.get("tech4");
        List tech5 = (List)dataMap.get("tech5");
        List tech6 = (List)dataMap.get("tech6");
        List tech7 = (List)dataMap.get("tech7");
        AcntTechinfo tech8 = (AcntTechinfo)dataMap.get("tech8");
        AcntTechinfo tech9 = (AcntTechinfo)dataMap.get("tech9");
        AcntTechinfo tech10 = (AcntTechinfo)dataMap.get("tech10");
        AcntTechinfo tech11 = (AcntTechinfo)dataMap.get("tech11");
        AcntTechinfo tech12 = (AcntTechinfo)dataMap.get("tech12");
        AcntTechinfo tech13 = (AcntTechinfo)dataMap.get("tech13");
        AcntTechinfo tech14 = (AcntTechinfo)dataMap.get("tech14");
        AcntTechinfo tech15 = (AcntTechinfo)dataMap.get("tech15");
        AcntTechinfo tech16 = (AcntTechinfo)dataMap.get("tech16");
        AcntTechinfo tech17 = (AcntTechinfo)dataMap.get("tech17");
        AcntTechinfo tech18 = (AcntTechinfo)dataMap.get("tech18");
        AcntTechinfo tech19 = (AcntTechinfo)dataMap.get("tech19");
        AcntTechinfo tech20 = (AcntTechinfo)dataMap.get("tech20");
        AcntTechinfo tech21 = (AcntTechinfo)dataMap.get("tech21");
        AcntCustContactor contactor1 = (AcntCustContactor)dataMap.get("contactor1");
        AcntCustContactor contactor2 = (AcntCustContactor)dataMap.get("contactor2");
        AcntCustContactor contactor3 = (AcntCustContactor)dataMap.get("contactor3");
        
        Parameter parameter = (Parameter)dataMap.get("parameter");
        
        Acnt acnt = (Acnt)dataMap.get("acnt");
        expect(accountMock.loadByRid(Long.valueOf(777))).andReturn(acnt);
        expect(accountMock.loadByRidFromPerson(Long.valueOf(777),LEADER)).andReturn(acntPerson1);
        expect(accountMock.loadByRidFromPerson(Long.valueOf(777),TC_SINGER)).andReturn(acntPerson2);
        expect(accountMock.loadByRidFromPerson(Long.valueOf(777),SALES)).andReturn(acntPerson3);
        expect(accountMock.loadByRidFromPerson(Long.valueOf(777),APPLICANT)).andReturn(acntPerson4);
        expect(accountMock.loadByRidFromPerson(Long.valueOf(777),BD_MANAGER)).andReturn(acntPerson5);
        expect(accountMock.loadByRidFromPerson(Long.valueOf(777),PM)).andReturn(acntPerson6);
        expect(accountMock.loadByRidFromPerson(Long.valueOf(777),CUST_MANAGER)).andReturn(acntPerson7);
        expect(accountMock.loadByRidFromPerson(Long.valueOf(777),ENGAGE_MANAGER)).andReturn(acntPerson8);
        
        expect(accountMock.listByRidKindFromTechInfo(Long.valueOf(777),TECH_PROJECT_TYPE )).andReturn(tech1);
        expect(accountMock.listByRidKindFromTechInfo(Long.valueOf(777),TECH_PROJECT_TYPE )).andReturn(tech0);
        expect(parameterMock.loadByKindCode(TECH_PROJECT_TYPE, "projectType1")).andReturn(parameter);
        expect(accountMock.listByRidKindFromTechInfo(Long.valueOf(777),TECH_PRODUCT_TYPE  )).andReturn(tech2);
        expect(accountMock.listByRidKindFromTechInfo(Long.valueOf(777),TECH_PRODUCT_ATTRIBUTE  )).andReturn(tech3);
        expect(accountMock.listByRidKindFromTechInfo(Long.valueOf(777),TECH_WORK_ITEM  )).andReturn(tech4);
        expect(accountMock.listByRidKindFromTechInfo(Long.valueOf(777),TECH_TECHNICAL_DOMAIN  )).andReturn(tech5);
        expect(accountMock.listByRidKindFromTechInfo(Long.valueOf(777),TECH_ORIGINAL_LANGUAGE  )).andReturn(tech6);
        expect(accountMock.listByRidKindFromTechInfo(Long.valueOf(777),TECH_TRANSLATION_LANGUANGE  )).andReturn(tech7);
        
        expect(accountMock.loadByRidKindCodeFromTechInfo(Long.valueOf(777),HARD_REQ,HARD_REQ)).andReturn(tech8);
        expect(accountMock.loadByRidKindCodeFromTechInfo(Long.valueOf(777),SOFT_REQ,SOFT_REQ )).andReturn(tech9);
        
        expect(accountMock.loadByRidKindCodeFromTechInfo(Long.valueOf(777),DEV_ENVIROMENT ,JOB_SYS)).andReturn(tech10);
        expect(accountMock.loadByRidKindCodeFromTechInfo(Long.valueOf(777),DEV_ENVIROMENT ,DATA_BASE)).andReturn(tech11);
        expect(accountMock.loadByRidKindCodeFromTechInfo(Long.valueOf(777),DEV_ENVIROMENT ,TOOL)).andReturn(tech12);
        expect(accountMock.loadByRidKindCodeFromTechInfo(Long.valueOf(777),DEV_ENVIROMENT ,NET_WORK)).andReturn(tech13);
        expect(accountMock.loadByRidKindCodeFromTechInfo(Long.valueOf(777),DEV_ENVIROMENT ,PROGRAM_LANGUAGE)).andReturn(tech14);
        expect(accountMock.loadByRidKindCodeFromTechInfo(Long.valueOf(777),DEV_ENVIROMENT ,OTHERS)).andReturn(tech15);
        expect(accountMock.loadByRidKindCodeFromTechInfo(Long.valueOf(777),TRANS_PUBLISH  ,JOB_SYS)).andReturn(tech16);
        expect(accountMock.loadByRidKindCodeFromTechInfo(Long.valueOf(777),TRANS_PUBLISH  ,DATA_BASE)).andReturn(tech17);
        expect(accountMock.loadByRidKindCodeFromTechInfo(Long.valueOf(777),TRANS_PUBLISH  ,TOOL)).andReturn(tech18);
        expect(accountMock.loadByRidKindCodeFromTechInfo(Long.valueOf(777),TRANS_PUBLISH  ,NET_WORK)).andReturn(tech19);
        expect(accountMock.loadByRidKindCodeFromTechInfo(Long.valueOf(777),TRANS_PUBLISH  ,PROGRAM_LANGUAGE)).andReturn(tech20);
        expect(accountMock.loadByRidKindCodeFromTechInfo(Long.valueOf(777),TRANS_PUBLISH  ,OTHERS)).andReturn(tech21);
        
        expect(accountMock.loadByRidTypeFromAcntCustContactor(Long.valueOf(777),CUST_CONTACTOR)).andReturn(contactor1);
        expect(accountMock.loadByRidTypeFromAcntCustContactor(Long.valueOf(777),CUST_EXE)).andReturn(contactor2);
        expect(accountMock.loadByRidTypeFromAcntCustContactor(Long.valueOf(777),CUST_FINANCIAL)).andReturn(contactor3);
        finAccountMock.addAccount(getMap());
        expectLastCall().once();
        replay(accountMock);
        replay(parameterMock);
        replay(finAccountMock);
        syncImp.setAccountLogic(accountMock);
        syncImp.setParameterLogic(parameterMock);
        syncImp.setFinAccountService(finAccountMock);
        syncImp.updateTimeSheetOrFinance(Long.valueOf(7777777));
        
    }
    
    private Map getMap(){
        Map map = new HashMap();
        map.put("projId","W111");
        map.put("projName","FULLNAME");
        map.put("nickName","acntName");
        map.put("manager","TTTTY");
        map.put("leader","wh0201859");
        map.put("costDept","ExecutUnitId");
        map.put("tcSigner","wh0201810");
        map.put("planFrom","");
        map.put("planTo","");
        map.put("custShort","customerId");
        map.put("estManmonth",Double.valueOf(100));
        map.put("achieveBelong","achieveBelong");
        map.put("sales","wh020100");
        map.put("actuFrom","");
        map.put("actuTo","");
        map.put("manMonth",Double.valueOf(80));
        map.put("productName","productName");
        map.put("projectDesc","breif");
        map.put("applicant","wh020100");
        map.put("divisionManager","wh021000");
        map.put("projectManager","wh030100");
        map.put("projectExecUnit","ExecutUnitId");
        map.put("custServiceManager","wh0201001");
        map.put("engageManager","wh0201041");
        map.put("bizSource","BizSource");
        map.put("masterProjects","contactorAcntId");
        map.put("projectProperty","attribute");
        map.put("estimatedWords",Long.valueOf(10));
        map.put("projectType","projectType");
        map.put("projTypeNo","projectType");
        map.put("AccountTypeName",null);
        map.put("productType","productType");
        map.put("productProperty","productAttribute");
        map.put("workItem","workItem");
        map.put("skillDomain","techDomain");
        map.put("originalLan","techOrigLan");
        map.put("transLan","transLan");
        map.put("hardWareReq",null);
        map.put("softWareReq",null);
        map.put("devEnvOs",null);
        map.put("devEnvDb",null);
        map.put("devEnvTool",null);
        map.put("devEnvNetwork",null);
        map.put("devEnvLang",null);
        map.put("devEnvOther",null);
        map.put("tranEnvOs",null);
        map.put("tranEnvDb",null);
        map.put("tranEnvTool",null);
        map.put("tranEnvNetwork",null);
        map.put("tranEnvLang",null);
        map.put("tranEnvOther",null);
        map.put("ccontact","yyy");
        map.put("ccontTel","85324421");
        map.put("ccontEmail","aa@163.com");
        map.put("pcontact","ttt");
        map.put("pcontTel","85324421");
        map.put("pcontEmail","aa@163.com");
        map.put("icontact","ggg");
        map.put("icontTel","85324421");
        map.put("icontEmail","aa@163.com");
        return map;
        
    }    
    
    private void delete(){
        String delTechSql = "delete from pp_acnt_techinfo where acnt_rid=777";
        syncImp.getDbAccessor().executeUpdate(delTechSql);
        
        String delCustSql = "delete from pp_acnt_cust_contactor where acnt_rid=777";
        syncImp.getDbAccessor().executeUpdate(delCustSql);
        
        String delPersonSql = "delete from pp_acnt_person where acnt_rid=777";
        syncImp.getDbAccessor().executeUpdate(delPersonSql);
        
        String delExceptionSql = "delete from pp_sync_exception where acnt_rid=777";
        syncImp.getDbAccessor().executeUpdate(delExceptionSql);
    }
    
    private void delException(){
        String delExceptionSql = "delete from pp_sync_exception where acnt_rid=888";
        syncImp.getDbAccessor().executeUpdate(delExceptionSql);
    }
    
/**
 * @param args
 */
public static void main(String[] args) {
    // TODO Auto-generated method stub

}

}
