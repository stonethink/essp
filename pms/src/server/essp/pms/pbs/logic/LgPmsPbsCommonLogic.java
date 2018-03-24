package server.essp.pms.pbs.logic;

import c2s.dto.DtoUtil;

public class LgPmsPbsCommonLogic {
    public static void copyProperties(Object dest, Object orig ){
        try {
            DtoUtil.copyProperties(dest, orig);
        } catch (Exception ex) {
        }
    }
}
