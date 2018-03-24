package server.essp.pwm.wp.logic;

import java.math.BigDecimal;
import java.util.Date;

import c2s.dto.DtoUtil;
import c2s.essp.pwm.wp.DtoPwWkitem;
import com.wits.util.Parameter;
import com.wits.util.StringUtil;
import essp.tables.PwWkitem;
import essp.tables.PwWp;
import server.framework.common.BusinessException;
import server.framework.logic.AbstractBusinessLogic;

public class FPW01021WkitemDeleteLogic extends AbstractBusinessLogic {

    public void executeLogic(Parameter param) throws BusinessException {
        //get parameter
        DtoPwWkitem dtoPwWkitem = (DtoPwWkitem) param.get("dtoPwWkitem");

        try {
            PwWkitem wkitem = new PwWkitem();
            DtoUtil.copyProperties(wkitem, dtoPwWkitem);

            log.debug(wkitem.getRid() + ";delete:" + wkitem.getWkitemName());
            getDbAccessor().delete(wkitem);

            //重计算所属的WP的实际工作时间
            updateWpActWkhr( dtoPwWkitem);
        } catch (BusinessException ex) {
            throw (BusinessException) ex;
        } catch (Exception ex) {
            throw new BusinessException("E000000", "Update wp work item error.", ex);
        }

    }

    private void updateWpActWkhr( DtoPwWkitem dtoPwWkitem ) throws Exception{
        BigDecimal wkhr = new BigDecimal(0);
        if( dtoPwWkitem == null ){
            return;
        }
        Long inWpId = dtoPwWkitem.getWpId();

        //实际工作时间 = (结束时间 - 开始时间)
        String isdlrpt = StringUtil.nvl(dtoPwWkitem.getWkitemIsdlrpt());
        if (isdlrpt.equals("1") == true) {
            wkhr = dtoPwWkitem.getWkitemWkhoursOld();
        }

        PwWp pwWp = (PwWp)FPW01000CommonLogic.load(getDbAccessor().getSession(), PwWp.class, inWpId );
        if( pwWp != null ){
            BigDecimal dbWkhr = pwWp.getWpActWkhr();
            dbWkhr = dbWkhr.subtract(wkhr);
            if( dbWkhr.doubleValue() > 0 ){
                pwWp.setWpActWkhr( dbWkhr );
            }else{
                pwWp.setWpActWkhr( new BigDecimal(0) );
            }

            getDbAccessor().update( pwWp );
        }
    }


}
