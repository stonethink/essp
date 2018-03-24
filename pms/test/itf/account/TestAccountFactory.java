package itf.account;

import junit.framework.*;

public class TestAccountFactory extends TestCase {
    private AccountFactory accountFactory = null;

    protected void setUp() throws Exception {
        super.setUp();
        accountFactory = new AccountFactory();
    }

    protected void tearDown() throws Exception {
        accountFactory = null;
        super.tearDown();
    }

    public void testCreateInfoUtil() {
        IAccountInfoUtil actualReturn = accountFactory.createInfoUtil();
        assertEquals("return value", IAccountInfoUtil.class, actualReturn.getClass().getInterfaces()[0]);
        /**@todo fill in the test code*/
    }

    public void testCreateListUtil() {
        IAccountListUtil actualReturn = accountFactory.createListUtil();
        assertEquals("return value", IAccountListUtil.class, actualReturn.getClass().getInterfaces()[0]);
        /**@todo fill in the test code*/
    }

}
