import c2s.essp.common.account.IDtoAccount;
import server.essp.projectpre.db.PpSyncException;
import server.essp.projectpre.service.constant.Constant;
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
        
        private String LEADER =IDtoAccount.USER_TYPE_LEADER;
        private String TC_SINGER =IDtoAccount.USER_TYPE_TC_SIGNER;
        private String SALES =IDtoAccount.USER_TYPE_SALES;
        private String APPLICANT =IDtoAccount.USER_TYPE_APPLICANT;
        private String BD_MANAGER =IDtoAccount.USER_TYPE_BD_MANAGER;
        private String PM =IDtoAccount.USER_TYPE_PM;
        private String CUST_MANAGER =IDtoAccount.USER_TYPE_CUST_SERVICE_MANAGER;
        private String ENGAGE_MANAGER =IDtoAccount.USER_TYPE_ENGAGE_MANAGER;
        private String TECH_PROJECT_TYPE = Constant.PROJECT_TYPE;
        private String TECH_PRODUCT_TYPE = Constant.PRODUCT_TYPE;
        private String TECH_PRODUCT_ATTRIBUTE = Constant.PRODUCT_ATTRIBUTE;
        private String TECH_WORK_ITEM = Constant.WORK_ITEM;
        private String TECH_TECHNICAL_DOMAIN = Constant.TECHNICAL_DOMAIN;
        private String TECH_ORIGINAL_LANGUAGE = Constant.ORIGINAL_LANGUAGE;
        private String TECH_TRANSLATION_LANGUANGE = Constant.TRANSLATION_LANGUANGE;
        
        
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
            syncImp.getDbAccessor().followTx();
            
        }
    
        protected void tearDown() throws Exception {
            syncImp.getDbAccessor().endTxRollback();
            syncImp = null;
            super.tearDown();
        }
        
        public void testAddSuccessOne() {
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
        }
    
        public void testAddSuccessTwo() {
//            String sqlSync = "insert into Pp_Sync_Exception(acnt_rid,acnt_id,acnt_name,model,status,type)" +
//                    " values(888,'23','test','FinAccount','Active','Apply')";
//            syncImp.getDbAccessor().executeUpdate(sqlSync);
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
        
//        public void testUpdateSuccessTwo() {
//            String sqlSync = "insert into Pp_Sync_Exception(acnt_rid,acnt_id,acnt_name,model,status,type)" +
//            " values(888,'23','test','FinAccount','Active','Apply')";
//            syncImp.getDbAccessor().executeUpdate(sqlSync);
//            syncImp.updateStaus();
//        }
        
        public void testUpdateTimeSheetOrFinanceSuccess() {
            String sqlSync = "insert into Pp_Sync_Exception(acnt_rid,acnt_id,acnt_name,model,status,type,imp_class_name)" +
            " values(777,'23','test','FinAccount','Active','Apply','FinAccountWServiceImpl')";
            syncImp.getDbAccessor().executeUpdate(sqlSync);
            String sqlPerson1 = "insert into pp_Acnt_Person(acnt_rid,person_type,login_id,name)"+
                                " values(777,'"+LEADER+"','wh05014012','wwy')";
            String sqlPerson2 = "insert into pp_Acnt_Person(acnt_rid,person_type,login_id,name)"+
            " values(777,'" + TC_SINGER + "','wh05014013','wwg')";
            String sqlPerson3 = "insert into pp_Acnt_Person(acnt_rid,person_type,login_id,name)"+
            " values(777,'"+SALES+"','wh05014080','wwg')";
            String sqlPerson4 = "insert into pp_Acnt_Person(acnt_rid,person_type,login_id,name)"+
            " values(777,'"+APPLICANT+"','wh05014013','wwg')";
            String sqlPerson5 = "insert into Acnt_Person(acnt_rid,person_type,login_id,name)"+
            " values(777,'"+PM+"','wh05014016','wwg')";
            String sqlPerson6 = "insert into pp_Acnt_Person(acnt_rid,person_type,login_id,name)"+
            " values(777,'"+CUST_MANAGER+"','wh05014016','wwg')";
            String sqlPerson7 = "insert into pp_Acnt_Person(acnt_rid,person_type,login_id,name)"+
            " values(777,'"+ENGAGE_MANAGER+"','wh05014016','wwg')";
            
            String SqlTech1 = "insert into pp_Acnt_Techinfo(acnt_rid,kind,code)"+
                          " values(777,'" + TECH_PROJECT_TYPE +"','projectType')";
            String SqlTech2 = "insert into pp_Acnt_Techinfo(acnt_rid,kind,code)"+
            " values(777,'" + TECH_PRODUCT_TYPE +"','produtType')";
            String SqlTech3 = "insert into pp_Acnt_Techinfo(acnt_rid,kind,code)"+
            " values(777,'" + TECH_PRODUCT_ATTRIBUTE +"','projectAttribute')";
            String SqlTech4 = "insert into pp_Acnt_Techinfo(acnt_rid,kind,code)"+
            " values(777,'" + TECH_WORK_ITEM +"','workItem')";
            String SqlTech5 = "insert into pp_Acnt_Techinfo(acnt_rid,kind,code)"+
            " values(777,'" + TECH_TECHNICAL_DOMAIN +"','technicalDomain')";
            String SqlTech6 = "insert into pp_Acnt_Techinfo(acnt_rid,kind,code)"+
            " values(777,'" + TECH_ORIGINAL_LANGUAGE +"','orgLanguange')";
            String SqlTech7 = "insert into pp_Acnt_Techinfo(acnt_rid,kind,code)"+
            " values(777,'" + TECH_TRANSLATION_LANGUANGE +"','translateLanguange')";
        }
        
        
    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
