package server.essp.workflow;
import itf.workflow.IActivityParticipant;
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
public class Participant implements IActivityParticipant {
    public Participant() {
    }

    public IParticipant loadParticipant(String sPartType) {
        return null;
    }

    public IParticipant loadParticipant(IParticipant IPart) {
        return null;
    }

    public IParticipant loadParticipant(IWkDefine iwkDefine,
                                        IActivityDefine currentActivityDef) {
        return null;
    }
}
