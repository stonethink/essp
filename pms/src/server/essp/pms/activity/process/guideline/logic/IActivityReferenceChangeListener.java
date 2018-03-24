package server.essp.pms.activity.process.guideline.logic;

import db.essp.pms.PmsActivityGuideline;

/**
 * <p>Title: Activity Guideline reference changed listener</p>
 *
 * <p>Description: Do some thing when activity guideline reference changed</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public interface IActivityReferenceChangeListener {

    /**
     * activity guideline reference changed
     * @param activityGuideLine PmsActivityGuideline
     * @param oldRefAcntRid Long
     * @param oldRefActivityRid Long
     */
    public void activityReferenceChange(PmsActivityGuideline activityGuideLine, Long oldRefAcntRid, Long oldRefActivityRid);

}
