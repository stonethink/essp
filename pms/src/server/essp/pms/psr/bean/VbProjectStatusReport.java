package server.essp.pms.psr.bean;

import java.util.ArrayList;
import java.util.List;

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
public class VbProjectStatusReport {
    private List acntList = new ArrayList();
    private String selectedAcnt="";
    public List getAcntList() {
        return acntList;
    }

    public String getSelectedAcnt() {
        return selectedAcnt;
    }

    public void setAcntList(List acntList) {
        this.acntList = acntList;
    }

    public void setSelectedAcnt(String selectedAcnt) {
        this.selectedAcnt = selectedAcnt;
    }
}
