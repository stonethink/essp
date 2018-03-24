package server.essp.pwm.wp.logic;

import java.util.ArrayList;
import java.util.List;

import c2s.dto.DtoUtil;
import c2s.essp.common.user.DtoUser;
import com.wits.util.Parameter;
import com.wits.util.StringUtil;
import essp.tables.PwWp;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import server.essp.framework.logic.AbstractESSPLogic;
import server.framework.common.BusinessException;

public class FPW01022CheckpointSelectLogic extends AbstractESSPLogic {
    private static String[] assignByStatus = {"Waiting Check","Delivered","Rejected","Closed"};
    private static String[] noAssignByStatus = {"Waiting Check","Delivered"};
    String loginId = null;

    public FPW01022CheckpointSelectLogic() {
        DtoUser user = this.getUser();
        if (user != null) {
            loginId = user.getUserLoginId();
        } else {
            loginId = "stone.shi"; // for test
            //throw new BusinessException("E0000","Can't get user information.Please login in.");
        }
    }

    public void executeLogic(Parameter param) throws BusinessException {
        //get parameter
        Long lWpId = (Long) param.get("inWpId");
        //String inUserId = (String)param.get( "inUserId" );

        List checkpointList = new ArrayList();
        boolean isAssignby = false;
        try {
            checkParameter(lWpId);

            Session hbSession = getDbAccessor().getSession();
            Query q = hbSession.createQuery("from essp.tables.PwWpchk t " +
                    "where t.wpId = :wpid " +
                    "order by t.wpchkDate asc");
            q.setString("wpid", lWpId.toString());

            List ql = q.list();
            checkpointList = DtoUtil.list2List(ql,
                                               c2s.essp.pwm.wp.DtoPwWpchk.class);

            //get if user is assign by
            PwWp wp = new PwWp();
            wp = (PwWp) FPW01000CommonLogic.load(getDbAccessor().getSession(), PwWp.class, lWpId);
            if (wp == null) {
                throw new BusinessException("E000000", "Not find the wp - " + lWpId + ".");
            }
            String wpAssignby = StringUtil.nvl(wp.getWpAssignby());
            if( wpAssignby.equals( loginId ) == true ){
                isAssignby = true;
            }

            //validateData(checkpointList, isAssignby);
        } catch (BusinessException ex) {
                throw ex;
        }catch (Exception ex) {
                throw new BusinessException("E000000",
                    "Select checkpoints of wp - " + lWpId + " error.", ex);
         }

        //set parameter
        param.put("checkpointList", checkpointList);
        param.put("isAssignby", new Boolean(isAssignby) );
    }

    private void checkParameter(Long inWpId) throws BusinessException {
        if (inWpId == null) {
            throw new BusinessException("E000000", "The wp id is null.");
        }
    }


    private void validateData( List checkpointList, boolean isAssignby ) throws BusinessException{
//        //Wp checkpoint status ÊÇ·ñ´æÔÚ
//        if( checkpointList == null ){
//            return;
//        }
//
//        for( Iterator it = checkpointList.iterator(); it.hasNext(); ){
//            DtoPwWpchk dtoPwWpchk = (DtoPwWpchk) it.next();
//            String wpchkStatus = StringUtil.nvl(dtoPwWpchk.getWpchkStatus());
//            if (wpchkStatus.equals("") == true) {
//                throw new BusinessException("E000000", "The wp checkpoint 's status is null.");
//            } else {
//
//                if( isAssignby == true ){
//                    if (FPW01000CommonLogic.findElement(wpchkStatus, assignByStatus) == false) {
//                        throw new BusinessException("E000000", "The wp checkpoint 's status -- " + wpchkStatus + " is not exist.");
//                    }
//                }else{
//                    if (FPW01000CommonLogic.findElement(wpchkStatus, assignByStatus) == false) {
//                        throw new BusinessException("E000000", "The wp checkpoint 's status -- " + wpchkStatus + " is not exist.");
//                    }
//
//                }
//            }
//        }
    }

}
