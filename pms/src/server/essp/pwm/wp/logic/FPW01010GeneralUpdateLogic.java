package server.essp.pwm.wp.logic;

import java.util.Iterator;
import c2s.dto.IDto;
import c2s.essp.pwm.wp.DtoPwWp;
import com.wits.util.Parameter;
import essp.tables.PwWp;
import essp.tables.PwWpsum;
import net.sf.hibernate.Query;
import server.framework.common.BusinessException;
import server.framework.logic.AbstractBusinessLogic;
import com.wits.util.StringUtil;
import java.math.BigDecimal;
import c2s.dto.DtoUtil;

public class FPW01010GeneralUpdateLogic extends AbstractBusinessLogic {

    public void executeLogic(Parameter param) throws BusinessException {
        //get parameter
        DtoPwWp dtoPwWp = (DtoPwWp) param.get("dtoPwWp");
        try {
            checkParameter( dtoPwWp );

            PwWp wp = new PwWp();
            PwWpsum wpsum = new PwWpsum();
            Long lWpId = dtoPwWp.getRid();

            if( lWpId == null ){
                throw new BusinessException("E000000", "The wp's id is null.");
            }
            //update wp
            wp = (PwWp) FPW01000CommonLogic.load(getDbAccessor().getSession(), PwWp.class, lWpId);
            if (wp == null) {
                throw new BusinessException("E000000", "The wp - " + lWpId + " is not exist.");
            }
            DtoUtil.copyProperties(wp, dtoPwWp);
            FPW01000CommonLogic.setPwporwp(wp);
            getDbAccessor().update(wp);

            //update wp's summary
            //wpsum = getPwWpsum(lWpId);
            wpsum = wp.getWpSum();
            if (wpsum == null) {

                //if not exist, new one summary
                FPW01000CommonLogic.createPwWpsum(getDbAccessor(), dtoPwWp);
            } else {
                wpsum.setWpSizePlan( dtoPwWp.getWpSizePlan() );
                wpsum.setWpSizeUnit( dtoPwWp.getWpSizeUnit() );
                wpsum.setWpDensityratePlan( dtoPwWp.getWpDensityratePlan() );
                wpsum.setWpDensityrateUnit( dtoPwWp.getWpDensityrateUnit() );
                wpsum.setWpDefectratePlan( dtoPwWp.getWpDefectratePlan() );
                wpsum.setWpDefectrateUnit( dtoPwWp.getWpDefectrateUnit() );

                //计算其他PW_WPSUM数据
                double wpPlanWkhr = dtoPwWp.getWpPlanWkhr().doubleValue();
                double wpSizePlan = dtoPwWp.getWpSizePlan().doubleValue();
                String wpSizeUnit = StringUtil.nvl(dtoPwWp.getWpSizeUnit());

                double wpDensityratePlan = dtoPwWp.getWpDensityratePlan().doubleValue();
                double wpDefectratePlan = dtoPwWp.getWpDefectratePlan().doubleValue();

                //wp_productivity
                double wpProductivityPlan = 0;
                if (wpPlanWkhr != 0) {
                    wpProductivityPlan = wpSizePlan / wpPlanWkhr;
                }
                String wpProductivityUnit = wpSizeUnit + "/Hour";
                wpsum.setWpProductivityPlan(new BigDecimal(wpProductivityPlan));
                wpsum.setWpProductivityUnit(wpProductivityUnit);

                //density
                double wpDensityPlan = wpDensityratePlan * wpSizePlan;
                wpsum.setWpDensityPlan(new BigDecimal(wpDensityPlan));

                //defect
                double wpDefectPlan = wpDefectratePlan * wpSizePlan;
                wpsum.setWpDefectPlan(new Long( (long) wpDefectPlan));

                getDbAccessor().update(wpsum);
            }

            FPW01000CommonLogic.copyProperties(dtoPwWp, wpsum);
            FPW01000CommonLogic.copyProperties(dtoPwWp, wp);
        } catch (Exception ex) {
            if (ex instanceof BusinessException) {
                throw (BusinessException) ex;
            } else {
                log.error("", ex);
                throw new BusinessException("E000000", "Update wp error.");
            }
        }

        //set parameter
        param.put("dtoPwWp", dtoPwWp);
        dtoPwWp.setOp(IDto.OP_NOCHANGE);
    }

    private PwWpsum getPwWpsum(Long wpId) throws BusinessException {
        PwWpsum pwWpsum = null;
        try {
            Query q2 = getDbAccessor().getSession().createQuery(
                "from essp.tables.PwWpsum t "
                + "where t.rid = :wpid "
                );
            q2.setString("wpid", wpId.toString());
            for (Iterator it = q2.iterate(); it.hasNext(); ) {
                pwWpsum = (PwWpsum) it.next();
                break;
            }
        } catch (Exception ex) {
            log.error("", ex);
            throw new BusinessException("E000000", "Select wp summary error.");
        }

        return pwWpsum;
    }

    private void checkParameter( DtoPwWp dtoPwWp ) throws BusinessException{
        if( dtoPwWp == null ){
            throw new BusinessException("E000000", "The wp is null.");
        }else{
            if (dtoPwWp.getRid() == null) {
                throw new BusinessException("E000000", "The wp's id is null.");
            }
        }

    }

}
