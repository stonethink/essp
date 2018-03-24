package server.essp.pwm.wp.logic;

import java.util.Date;
import c2s.dto.DtoUtil;
import c2s.dto.IDto;
import c2s.essp.pwm.wp.DtoPwWp;
import com.wits.util.Parameter;
import essp.tables.PwWp;
import essp.tables.PwWpchk;
import net.sf.hibernate.Query;
import server.framework.common.BusinessException;
import server.framework.logic.AbstractBusinessLogic;
import validator.ValidatorResult;
import java.util.Iterator;

public class FPW01010GeneralInsertLogic extends AbstractBusinessLogic {

    public void executeLogic(Parameter param) throws BusinessException {
        //get parameter
        DtoPwWp dtoPwWp = (DtoPwWp) param.get("dtoPwWp");
        ValidatorResult validatorResult = (ValidatorResult) param.get( "validatorResult" );

        try {
            PwWp wp = new PwWp();

            checkParameter(validatorResult, dtoPwWp);

            FPW01000CommonLogic.copyProperties(wp, dtoPwWp);
            wp.setWpAssigndate(new Date());
            FPW01000CommonLogic.setPwporwp(wp);
            getDbAccessor().save(wp);
            FPW01000CommonLogic.copyProperties(dtoPwWp, wp);

            //为该wp新增一个wp summary
            FPW01000CommonLogic.createPwWpsum(getDbAccessor(), dtoPwWp);

            //为该wp新增两个wp checkpoint
            createPwWpchk(wp, "Planning the WP");
            createPwWpchk(wp, "Delivery the WP");

        } catch (Exception ex) {
            if (ex instanceof BusinessException) {
                throw (BusinessException) ex;
            } else {
                log.error("", ex);
                throw new BusinessException("E000000", "Insert wp error.");
            }
        }

        //set parameter
        param.put("dtoPwWp", dtoPwWp);
        dtoPwWp.setOp(IDto.OP_NOCHANGE);
    }

    private void checkParameter(ValidatorResult validatorResult ,DtoPwWp dtoPwWp) throws BusinessException {
        try {
        	if( dtoPwWp == null ){
        		new BusinessException( "E0000000", "The wp is null." );
        	}

            //先作check:if "wp_code" is existed
            Query q2 = getDbAccessor().getSession().createQuery(
                "from PwWp t "
                + "where t.wpCode = :wpCode "
                );
            q2.setString("wpCode", dtoPwWp.getWpCode());
            for (Iterator it = q2.iterate(); it.hasNext(); ) {
                validatorResult.addMsg("wpCode", "The wp code is already exist.");
                throw new BusinessException("E000000", "The wp code is already exist.");
            }


//            if (q2.iterate().hasNext()) {
//                validatorResult.addMsg("wpCode", "The wp code is already exist.");
//                throw new BusinessException("E000000", "The wp code is already exist.");
//            }
        } catch (Exception ex) {
            if (ex instanceof BusinessException) {
                throw (BusinessException) ex;
            } else {
                log.error("", ex);
                throw new BusinessException("E000000", "When check wp's code, error.");
            }
        }
    }

    private void createPwWpchk(PwWp wp, String chkName) throws BusinessException {
        try {
            Date wpchkDate = wp.getWpPlanStart();
            if (wpchkDate == null) {
                wpchkDate = new Date();
            }

            PwWpchk wpchk = new PwWpchk();
            wpchk.setWpId(wp.getRid());
            wpchk.setWpchkName(chkName);
            wpchk.setWpchkDate(wpchkDate); //默认为wp 计划开始时间
            wpchk.setWpchkStatus("Waiting Check");
            log.debug(DtoUtil.dumpBean(wpchk));
            getDbAccessor().save(wpchk);
        } catch (Exception ex) {
            if (ex instanceof BusinessException) {
                throw (BusinessException) ex;
            } else {
                log.error("", ex);
                throw new BusinessException("E000000", "Create PwWpchk error.");
            }
        }
    }

}
