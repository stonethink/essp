package server.essp.pms.wbs.process.guideline.logic;

import db.essp.pms.PmsWbsGuideline;

/**
 * <p>Title: Wbs Guideline reference changed listener</p>
 *
 * <p>Description: Do some thing when wbs Guideline reference changed</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public interface IWbsReferenceChangeListener {

    /**
     * wbs guideline reference changed
     * @param oldRefAcntRid Long
     * @param oldRefWbsRid Long
     * @param newRefAcntRid Long
     * @param newRefWbsRid Long
     */
    public void wbsReferenceChange(PmsWbsGuideline wbsGuideLine, Long oldRefAcntRid, Long oldRefWbsRid);
}
