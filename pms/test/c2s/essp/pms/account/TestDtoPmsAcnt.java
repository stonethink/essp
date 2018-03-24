package c2s.essp.pms.account;

import junit.framework.*;
import c2s.essp.common.account.IDtoAccount;

public class TestDtoPmsAcnt extends TestCase {
    private DtoPmsAcnt dtoPmsAcnt = null;
    private String dash = "002645W -- ESSP";
    private String bracket = "(002645W)ESSP";
    protected void setUp() throws Exception {
        super.setUp();
        dtoPmsAcnt = new DtoPmsAcnt();
        dtoPmsAcnt.setId("002645W");
        dtoPmsAcnt.setName("ESSP");
    }

    protected void tearDown() throws Exception {
        dtoPmsAcnt = null;
        super.tearDown();
    }
    //≤‚ ‘√ª”–…Ë÷√showStyleµƒ◊¥øˆ
    public void testGetDisplayNameWithoutShowStyle() {
        dtoPmsAcnt.setShowStyle(IDtoAccount.SHOWSTYLE_DASHED);
        String actualReturn = dtoPmsAcnt.getDisplayName();
        assertEquals("dash style", dash, actualReturn);
        /**@todo fill in the test code*/
    }
    //≤‚ ‘ IDtoAccount.SHOWSTYLE_BRACKET
    public void testGetDisplayNameBRACKET() {
            dtoPmsAcnt.setShowStyle(IDtoAccount.SHOWSTYLE_BRACKET);
            String actualReturn = dtoPmsAcnt.getDisplayName();
            assertEquals("bracket style", bracket, actualReturn);
    }
    //≤‚ ‘ IDtoAccount.SHOWSTYLE_ONLYCODE
    public void testGetDisplayNameCODE(){
        dtoPmsAcnt.setShowStyle(IDtoAccount.SHOWSTYLE_ONLYCODE);
        String actualReturn = dtoPmsAcnt.getDisplayName();
        assertEquals("code style", "002645W", actualReturn);
    }
    //≤‚ ‘ IDtoAccount.SHOWSTYLE_ONLYNAME
    public void testGetDisplayNameNAME(){
        dtoPmsAcnt.setShowStyle(IDtoAccount.SHOWSTYLE_ONLYNAME);
        String actualReturn = dtoPmsAcnt.getDisplayName();
        assertEquals("name style", "ESSP", actualReturn);
    }
}
