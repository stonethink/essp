package server.essp.pwm.wp.logic;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import c2s.dto.DtoUtil;
import c2s.dto.IDto;
import c2s.essp.common.user.DtoUser;
import c2s.essp.pwm.PwmUtil;
import c2s.essp.pwm.wp.DtoPwWkitem;
import com.wits.util.Parameter;
import essp.tables.PwWkitem;
import essp.tables.PwWp;
import server.essp.framework.logic.AbstractESSPLogic;
import server.framework.common.BusinessException;

public class FPW01021WkitemUpdateLogic extends AbstractESSPLogic {
    String loginId = null;

    public FPW01021WkitemUpdateLogic() {
        DtoUser user = this.getUser();
        if (user != null) {
            loginId = user.getUserLoginId();
        } else {
            throw new BusinessException("E0000","Can't get user information.Please login in.");
        }
    }


    public void executeLogic(Parameter param) throws BusinessException {
        //get parameter
        Long inWpId = (Long) param.get("inWpId");
        List workItems = (List) param.get("workItems");
        //String inUserId = (String) (param.get("inUserId"));

        try {
            checkParameter(inWpId, workItems);
            if (workItems == null) {
                return;
            }

            PwWp wp = (PwWp) getDbAccessor().load(PwWp.class, inWpId);
            if (wp == null) {
                throw new BusinessException("E000000", "Cannot find the wp which contains these work items.");
            }

            //重计算所属的WP的实际工作时间
            updateWpActWkhr(inWpId, workItems);

            for (int i = 0; i < workItems.size(); i++) {
                DtoPwWkitem dtoPwWkitem = (DtoPwWkitem) workItems.get(i);

                PwWkitem wkitem = new PwWkitem();
                if (dtoPwWkitem.isInsert()) {
                    dtoPwWkitem.setProjectId(wp.getProjectId());
                    dtoPwWkitem.setActivityId(wp.getActivityId());
                    dtoPwWkitem.setWpId(wp.getRid());
                    dtoPwWkitem.setWkitemOwner(loginId);
                    dtoPwWkitem.setWkitemBelongto(wp.getWpCode() + " -- " + wp.getWpName());
                    if(dtoPwWkitem.getWkitemDate() == null ){
                        dtoPwWkitem.setWkitemDate(new Date());
                    }
                    dtoPwWkitem.setWkitemIsdlrpt("0");

                    try {
                        DtoUtil.copyProperties(wkitem, dtoPwWkitem);
                    } catch (Exception e) {
                        //
                    }
                    wkitem.setWkitemStarttime(new Timestamp(dtoPwWkitem.getWkitemStarttime().getTime()));
                    wkitem.setWkitemFinishtime(new Timestamp(dtoPwWkitem.getWkitemFinishtime().getTime()));
                    log.debug(wkitem.getRid() + "insert:" + wkitem.getWkitemName());
                    getDbAccessor().save(wkitem);

                    dtoPwWkitem.setOp(IDto.OP_NOCHANGE);
                } else if (dtoPwWkitem.isDelete()) {
//                    try {
//                        DtoUtil.copyProperties(wkitem, dtoPwWkitem);
//                    } catch (Exception e) {
//                        //
//                    }
//                    log.debug(wkitem.getRid() + ";delete:" + wkitem.getWkitemName());
//                    getDbAccessor().delete(wkitem);

                    workItems.remove(i);
                    i--;
                } else if (dtoPwWkitem.isChanged()) {
                    try {
                        DtoUtil.copyProperties(wkitem, dtoPwWkitem);
                    } catch (Exception e) {
                        //
                    }

                    wkitem.setWkitemStarttime(new Timestamp(dtoPwWkitem.getWkitemStarttime().getTime()));
                    wkitem.setWkitemFinishtime(new Timestamp(dtoPwWkitem.getWkitemFinishtime().getTime()));
                    dtoPwWkitem.setWkitemBelongto(wp.getWpCode() + " -- " + wp.getWpName());
                    log.debug(wkitem.getRid() + ";update:" + wkitem.getWkitemName());
                    getDbAccessor().update(wkitem);

                    dtoPwWkitem.setOp(IDto.OP_NOCHANGE);
                }
            }

            //重新获取wkitem
            Parameter param1 = new Parameter();
            param1.put("inWpId", inWpId);
            FPW01021WkitemSelectLogic lGPwWkitemSelect = new FPW01021WkitemSelectLogic();
            lGPwWkitemSelect.setDbAccessor(this.getDbAccessor());
            lGPwWkitemSelect.executeLogic(param1);
            workItems = (List) param1.get("workItems");
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BusinessException("E000000", "Update wp work item error.", ex);
        }

        //set parameter
        param.put("workItems", workItems);
    }

    private void updateWpActWkhr(Long inWpId, List workItems) throws Exception {
        BigDecimal wkhr = new BigDecimal(0);
        if (workItems == null || workItems.size() == 0) {
            return;
        }

        //重计算该WP的实际工作时间
        for (int i = 0; i < workItems.size(); i++) {
            DtoPwWkitem dtoPwWkitem = (DtoPwWkitem) workItems.get(i);
            if (dtoPwWkitem.getOp().equals(IDto.OP_DELETE) == false) {
                if( dtoPwWkitem.isdlrpt() == true ){
                    wkhr = wkhr.add(dtoPwWkitem.getWkitemWkhours());
                }
            }
        }

        PwWp pwWp = (PwWp) FPW01000CommonLogic.load(getDbAccessor().getSession(), PwWp.class, inWpId);
        if (pwWp != null) {
            pwWp.setWpActWkhr(wkhr);

            getDbAccessor().update(pwWp);
        }
    }

    private void checkParameter(Long lWpId, List workItems) throws BusinessException {
        if (lWpId == null) {
            throw new BusinessException("E000000", "The wp id is null.");
        }
    }

}
