package client.essp.pms.activity.relation;

import client.essp.common.view.VWTDWorkArea;
import com.wits.util.Parameter;

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
public class VwRelationship extends VWTDWorkArea {
    VwRelationPredecessors activityPredecessors;
    VwRelationSuccessors activitySuccessors;
    public VwRelationship() {
        super(120);
        activityPredecessors = new VwRelationPredecessors();
        activitySuccessors = new VwRelationSuccessors();
        this.setVerticalSplit();
        this.getTopArea().addTab("Predecessors", activityPredecessors);
        this.getDownArea().addTab("Successors", activitySuccessors);
    }

    public void setParameter(Parameter param) {
        activityPredecessors.setParameter(param);
        activitySuccessors.setParameter(param);

        activityPredecessors.resetUI();
        activitySuccessors.resetUI();
    }

    public VwRelationPredecessors getPredecessor() {
        return activityPredecessors;
    }

    public VwRelationSuccessors getSuccessor() {
        return activitySuccessors;
    }


    public static void main(String[] args) {
        VwRelationship vwrelationship = new VwRelationship();
    }
}
