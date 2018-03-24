package server.essp.pwm.wp.logic;

import java.util.Iterator;
import java.util.Vector;

import c2s.essp.pwm.wp.DtoPwWpsum;
import com.wits.util.Parameter;
import essp.tables.PwParameter;
import essp.tables.PwWp;
import essp.tables.PwWpsum;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import server.framework.common.BusinessException;
import server.framework.logic.AbstractBusinessLogic;

public class FPW01040SummarySelectLogic extends AbstractBusinessLogic {

    public void executeLogic(Parameter param) throws BusinessException {
        //get parameter
        Long lWpId = (Long) param.get("inWpId");

        DtoPwWpsum dtoPwWpsum = new DtoPwWpsum();
        Vector selTechtype = new Vector();
        try {
            Session hbSession = getDbAccessor().getSession();
            PwWp pwWp = null;
            PwWpsum pwWpsum = null;

//        pwWp = (PwWp) hbComAccess.load(PwWp.class, lWpId);
            pwWp = (PwWp) FPW01000CommonLogic.load(hbSession, PwWp.class, lWpId);

//            Query q2 = hbSession.createQuery(
//                "from essp.tables.PwWpsum t "
//                + "where t.wpId = :wpid and t.rst = 'N'  "
//                );
//            q2.setString("wpid", lWpId.toString());
//            for (Iterator it = q2.iterate(); it.hasNext(); ) {
//                pwWpsum = (PwWpsum) it.next();
//                break;
//            }
            pwWpsum = pwWp.getWpSum();
            if (pwWpsum == null) {

                //ÐÂÔö
                pwWpsum = new PwWpsum();
                //pwWpsum.setWpId(lWpId);
            }

            //System.out.println(DtoUtil.dumpBean(pwWp));
            //System.out.println(DtoUtil.dumpBean(pwWpsum));
            FPW01000CommonLogic.copyProperties(dtoPwWpsum, pwWp);
            FPW01000CommonLogic.copyProperties(dtoPwWpsum, pwWpsum);
            if( pwWp.getAccount() != null ){
                dtoPwWpsum.setAcntCode(pwWp.getAccount().getId());
            }
            //System.out.println(DtoUtil.dumpBean(dtoPwWpsum));

            selTechtype = selectTechtype();

            //validateData(dtoPwWpsum, selTechtype);
        } catch (BusinessException ex) {
            throw (BusinessException) ex;
        } catch (Exception ex) {
            throw new BusinessException("E000000", "Select wp summary of wp - " + lWpId + " error.", ex);

        }

        //set parameter
        param.put("dtoPwWpsum", dtoPwWpsum);
        param.put("sel_Techtype", selTechtype);
    }

    public Vector selectTechtype() throws Exception {
        Vector rnt = new Vector();
        Session hbSession = getDbAccessor().getSession();
        String paraTypeIdWpTechType = "WP_TECHTYPE";

        Query q = hbSession.createQuery(
            "from essp.tables.PwParameter t "
            + "where t.paraTypeId = :paraTypeId "
            );
        q.setString("paraTypeId", paraTypeIdWpTechType);
        for (Iterator it = q.iterate(); it.hasNext(); ) {
            PwParameter pwParameter = (PwParameter) it.next();
            rnt.add(pwParameter.getParaName());
        }

        return rnt;
    }


    private void validateData( DtoPwWpsum dtoPwWpsum, Vector selTechtype ) throws BusinessException{
        String[] arySelTechtype;

//        String wpTechtype = StringUtil.nvl(dtoPwWpsum.getWpTechtype());
//        if (wpTechtype.equals("") == false) {
//            if (selTechtype == null) {
//                throw new BusinessException("E000000", "The wp summary 's techtype -- " + wpTechtype + " is not exist.");
//            }
//
//            arySelTechtype = new String[ selTechtype.size() ];
//            for( int i = 0; i < arySelTechtype.length; i++ ){
//                arySelTechtype[i] = (String)selTechtype.get(i);
//            }
//
//            if (FPW01000CommonLogic.findElement(wpTechtype, arySelTechtype) == false) {
//                throw new BusinessException("E000000", "The wp summary 's techtype -- " + wpTechtype + " is not exist.");
//            }
//        }
    }

}
