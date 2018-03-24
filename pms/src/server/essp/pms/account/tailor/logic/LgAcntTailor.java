package server.essp.pms.account.tailor.logic;

import server.essp.framework.logic.AbstractESSPLogic;
import net.sf.hibernate.*;
import db.essp.pms.*;
import server.framework.common.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class LgAcntTailor extends AbstractESSPLogic {
    public LgAcntTailor() {
    }
    /**
     * @param acntRid Long
     * @param description String
     * {@code gamble}
     * save the html description to database,use sql "update"
     */
    public void saveAccountTailor(Long acntRid,String description) {
        try {

//            Session s=this.getDbAccessor().getSession();
            String sql="update pms_acnt t set t.acnt_tailor='"+description+"' where t.rid= "+acntRid;

            this.getDbAccessor().executeUpdate(sql);

        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(ex);
            throw new BusinessException("AccountTailor",
                                        "error while saving AccountTailor!", ex);
        }
    }
    /**
     * @param acntRid Long
     * @return String £ºget description for the account,or wbs,or activity
     *{@code gamble}
     */

    public String getDescription(Long acntRid){
        String description=null;
        Session s = null;
        try {
            s = this.getDbAccessor().getSession();
            Account dbBean=(Account)s.load(Account.class,acntRid);

            description=dbBean.getAcntTailor();
        } catch (Exception ex) {
        }



        return description;

    }

}
