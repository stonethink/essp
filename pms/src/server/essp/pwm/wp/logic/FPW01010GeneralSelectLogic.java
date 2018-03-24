package server.essp.pwm.wp.logic;

import c2s.dto.IDto;
import c2s.essp.common.user.DtoUser;
import c2s.essp.pwm.wp.DtoPwWp;
import com.wits.util.Parameter;
import essp.tables.PwWp;
import essp.tables.PwWpsum;
import server.essp.framework.logic.AbstractESSPLogic;
import server.framework.common.BusinessException;

public class FPW01010GeneralSelectLogic extends AbstractESSPLogic {
    private static String[] wpTypes = new String[] {
                                      "RD", "RD Review", "AD", "AD Review", "HD",
                                      "HD Review", "DD", "DD Review", "Coding&UT",
                                      "WT", "IT", "RT", "AT", "Maintenance",
                                      "Project Management", "Others"
    };
    private static String[] wpSizeUnits = new String[] {"KLOC", "FP"};
    private static String[] wpDensityrateUnits = new String[] {"Case/KLOC", "Case/FP"};
    private static String[] wpDefectrateUnits = new String[] {
                                                "Defects/KLOC", "Defects/FP"
    };
    private static String[] wpWorkerStatus = new String[] {"Assigned", "Finish", "Rejected"};
    private static String[] wpAssignerStatus = new String[] {
                                               "Assigned", "Closed", "Rework",
                                               "Cancel"
    };

    String loginId = null;

    public FPW01010GeneralSelectLogic(){
        DtoUser user = this.getUser();
        if( user != null ){
            loginId = user.getUserLoginId();
        }else{
            throw new BusinessException("E001","Can't get user information from session.Please login first!");
        }
    }

    public void executeLogic(Parameter param) throws BusinessException {
        //get parameter
        Long inWpId = (Long) param.get("inWpId");
        //String inUserId = (String) param.get("inUserId");

        DtoPwWp dtoPwWp = null;
        try {
            checkParameter(inWpId);

            PwWp wp = new PwWp();
            PwWpsum wpsum = new PwWpsum();

            wp = (PwWp) FPW01000CommonLogic.load(getDbAccessor().getSession(), PwWp.class, inWpId);
            if (wp == null) {
                throw new BusinessException("E000000", "Not find the wp - " + inWpId + ".");
            }

            dtoPwWp = FPW01000CommonLogic.createDToPwWp(getDbAccessor(),wp,loginId);

            if( "Cancel".equals(dtoPwWp.getWpStatus()) || "Closed".equals(dtoPwWp.getWpStatus())){
                dtoPwWp.setWpCmpltrate(new Long(100));
            }
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BusinessException("E000000", "Select wp - " + inWpId + " error.", ex);
        }

        //set parameter
        param.put("dtoPwWp", dtoPwWp);
        //param.put("isAssignby", new Boolean(isAssignby));
        dtoPwWp.setOp(IDto.OP_NOCHANGE);
        //param.put("dtoAccountAcitivity", dtoAccountAcitivity);

    }

//    private PwWpsum getPwWpsum(Long wpId) throws BusinessException {
//        PwWpsum pwWpsum = new PwWpsum();
//        try {
//            Query q2 = getDbAccessor().getSession().createQuery(
//                "from essp.tables.PwWpsum t "
//                + "where t.wpId = :wpid and t.rst = 'N'  "
//                       );
//            q2.setString("wpid", wpId.toString());
//            for (Iterator it = q2.iterate(); it.hasNext(); ) {
//                pwWpsum = (PwWpsum) it.next();
//                break;
//            }
//        } catch (Exception ex) {
//            throw new BusinessException("E000000", "Select the wp summary of wp - " + wpId + " error.",ex);
//        }
//
//        return pwWpsum;
//    }

    private void checkParameter(Long inWpId) throws BusinessException {
        if (inWpId == null) {
            throw new BusinessException("E000000", "The wp id is null.");
        }
    }

    private void validateData(DtoPwWp dtoPwWp, boolean isAssignby) throws BusinessException {
        int i;
        //Wp type是否存在
//        String wpType = StringUtil.nvl( dtoPwWp.getWpType() );
//        if( wpType.equals("") == true ){
//            throw new BusinessException( "E000000", "The wp's type is null." );
//        }else{
//            if( FPW01000CommonLogic.findElement( wpType, wpTypes ) == false ){
//                throw new BusinessException( "E000000", "The wp's type -- " + wpType + " is not exist." );
//            }
//        }

        //Size unit是否存在
//        String wpSizeUnit = StringUtil.nvl( dtoPwWp.getWpSizeUnit() );
//        if( wpSizeUnit.equals("") == false ){
//            if( FPW01000CommonLogic.findElement( wpSizeUnit, wpSizeUnits ) == false ){
//                throw new BusinessException( "E000000", "The wp's size unit -- " + wpSizeUnit + " is not exist." );
//            }
//        }

        //densityrate  unit是否存在
//        String wpDensityrateUnit = StringUtil.nvl( dtoPwWp.getWpDensityrateUnit() );
//        if( wpDensityrateUnit.equals("") == false ){
//            if( FPW01000CommonLogic.findElement( wpDensityrateUnit, wpDensityrateUnits ) == false ){
//                throw new BusinessException( "E000000", "The wp's density rate unit -- " + wpDensityrateUnit + " is not exist." );
//            }
//        }

        //Defectrate unit是否存在
//        String wpDefectrateUnit = StringUtil.nvl( dtoPwWp.getWpDefectrateUnit() );
//        if( wpDefectrateUnit.equals("") == false ){
//            if( FPW01000CommonLogic.findElement( wpDefectrateUnit, wpDefectrateUnits ) == false ){
//                throw new BusinessException( "E000000", "The wp's defect rate unit -- " + wpDefectrateUnit + " is not exist." );
//            }
//        }

        //wp status 是否存在
//        String wpStatus = StringUtil.nvl(dtoPwWp.getWpStatus());
//        if (wpStatus.equals("") == true) {
//            throw new BusinessException("E000000", "The wp checkpoint 's status is null.");
//        } else {
//
//            if (isAssignby == true) {
//                if (FPW01000CommonLogic.findElement(wpStatus, wpAssignerStatus) == false) {
//                    throw new BusinessException("E000000", "The wp's status -- " + wpStatus + " is not exist.");
//                }
//            } else {
//                if (FPW01000CommonLogic.findElement(wpStatus, wpWorkerStatus) == false) {
//                    throw new BusinessException("E000000", "The wp's status -- " + wpStatus + " is not exist.");
//                }
//
//            }
//        }

    }
}
