package db.essp.code;

import server.framework.hibernate.HBComAccess;

public class DBTest {
    public static void main(String[] args) throws Exception {
        HBComAccess dbcom=new HBComAccess();
        dbcom.followTx();
        /*
        SysParameterPK pk = new SysParameterPK("k1","c1");
        SysParameter param = new SysParameter(pk);
        param.setAlias("a1");
        param.setName("n1");
        dbcom.save(param);
        */
       SysCurrency cu = new SysCurrency();
       cu.setCurrency("US D");
       cu.setName("dollar");
       cu.setSymbol("$$");
       dbcom.save(cu);
        dbcom.endTxCommit();
    }
}
