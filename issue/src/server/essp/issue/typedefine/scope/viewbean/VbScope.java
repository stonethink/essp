package server.essp.issue.typedefine.scope.viewbean;

import java.util.*;

import server.framework.taglib.util.*;

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
public class VbScope {
    private String description;
    private String scope;
    private String sequence;
    private String vision;
    private String typeName;
    /**
     * Vision to Customer 下拉列表
     */
    private List allVision = new ArrayList(2);

    public VbScope() {
        allVision.add(new SelectOptionImpl("Y", "Y"));
        allVision.add(new SelectOptionImpl("N", "N"));
    }

    public List getAllVision() {
        return allVision;
    }

    public String getDescription() {
        return description;
    }

    public String getScope() {
        return scope;
    }

    public String getSequence() {
        return sequence;
    }

    public String getTypeName() {
        return typeName;
    }

    public String getVision() {
        return vision;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public void setVision(String vision) {
        this.vision = vision;
    }

}
