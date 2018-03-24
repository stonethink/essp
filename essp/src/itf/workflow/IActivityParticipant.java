package itf.workflow;

import c2s.essp.common.workflow.IParticipant;
import c2s.essp.common.workflow.IWkDefine;
import c2s.essp.common.workflow.IActivityDefine;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public interface IActivityParticipant {
    IParticipant loadParticipant(String sPartType);
    IParticipant loadParticipant(IParticipant IPart);
    IParticipant loadParticipant(IWkDefine iwkDefine , IActivityDefine currentActivityDef);
}
