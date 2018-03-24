package server.essp.pms.quality;

import com.wits.util.Config;

/**
 * <p>Title: </p>
 *
 * <p>Description: quality config file read class</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class QualityConfig {
    private static final String CFG_FILE_NAME = "QualityConfig";
    private static final Config cfg = new Config(CFG_FILE_NAME);
    private static final String QUALIFICATION_RATE_PCB_ID = "qualificationRatePcbId";
    private static final String PCB_CUT_DEFECT_RATE_ID = "pcbCutDefectRateId";
    private static final String PCB_CUT_DENSITY_ID = "pcbCutDensityId";

    private static String qualificationRatePcbId = null;
    private static String pcbDensityIdPrefix = null;
    private static String pcbDefectRateIdPrefix = null;
    private static String pcbCutDefectRateId = null;
    private static String pcbCutDensityId = null;

    private static final String DENSITY_PREFIX = "Density_";
    private static final String DEFECT_RATE_PREFIX = "DefectRate_";
    public QualityConfig() {
    }

    public static String getQualificationRatePcbId() {
        if(qualificationRatePcbId == null) {
            qualificationRatePcbId = cfg.getValue(QUALIFICATION_RATE_PCB_ID);
        }
        return qualificationRatePcbId;
    }
    /**
     * 获得不同类型的品质活动的测试密度和缺陷率的PCB ID
     * @param activityType String
     * @return String
     */
    public static String getQulityActDensityPcbId(String activityType){
        String key = DENSITY_PREFIX + activityType.replaceAll(" ", "");
        return cfg.getValue(key);
    }

    public static String getQulityActDefectRatePcbId(String activityType){
        String key = DEFECT_RATE_PREFIX + activityType.replaceAll(" ", "");
        return cfg.getValue(key);
    }
    /**
     * 获得CUT的测试密度和缺陷率的PCB ID
     * @param activityType String
     * @return String
     */
    public static String getCutDefectRatePcbId() {
        if(pcbCutDefectRateId == null) {
           pcbCutDefectRateId = cfg.getValue(PCB_CUT_DEFECT_RATE_ID);
       }
        return pcbCutDefectRateId;
    }

    public static String getCutDensityPcbId() {
        if(pcbCutDensityId == null) {
           pcbCutDensityId = cfg.getValue(PCB_CUT_DENSITY_ID);
       }
        return pcbCutDensityId;
    }
    public static void main(String[] args) {
        System.out.println(QualityConfig.getQulityActDensityPcbId("DD Review"));
    }


}
