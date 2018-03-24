package server.essp.pms.wbs.viewbean;

import java.util.ArrayList;
import java.util.List;

public class VbMileStoneReport {
    private List mileStoneList = new ArrayList();
    private List acntList = new ArrayList();
    private String selectedAcnt="";
    private int detailSize;
    public List getAcntList() {
        return acntList;
    }

    public List getMileStoneList() {
        return mileStoneList;
    }

    public void setAcntList(List acntList) {
        this.acntList = acntList;
    }

    public void setMileStoneList(List mileStoneList) {
        this.mileStoneList = mileStoneList;
        if(mileStoneList == null)
            setDetailSize(0);
        else
            setDetailSize(mileStoneList.size());
    }
    public void setDetailSize(int size){
        this.detailSize = size;
    }

    public void setSelectedAcnt(String selectedAcnt) {
        this.selectedAcnt = selectedAcnt;
    }

    public int getDetailSize() {
        return detailSize;
    }

    public String getSelectedAcnt() {
        return selectedAcnt;
    }
}
