package c2s.essp.cbs.budget;

import java.util.List;
import java.util.ArrayList;

public class DtoLaborBudgetList {
    List laborList = new ArrayList();

    public DtoLaborBudgetList() {
    }

    public List getLaborList() {
        return laborList;
    }

    public void setLaborList(List laborList) {
        this.laborList = laborList;
    }

    public double getPmSum(){
        return 0;
    }
}
