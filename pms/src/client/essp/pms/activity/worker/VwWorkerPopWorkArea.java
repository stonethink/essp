package client.essp.pms.activity.worker;

import java.awt.Dimension;

import c2s.essp.pms.wbs.DtoActivityWorker;
import client.essp.common.view.VWGeneralWorkArea;
import com.wits.util.Parameter;

public class VwWorkerPopWorkArea extends VWGeneralWorkArea {
    VwActivityWorkerGeneral worker = new VwActivityWorkerGeneral();

    public VwWorkerPopWorkArea() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void jbInit() throws Exception {
        this.setPreferredSize(new Dimension(680, 600));
        this.addTab("Worker", worker, true);
    }
    public void setParameter(Parameter param) {
        super.setParameter(param);
        worker.setParameter(param);
    }

    public DtoActivityWorker getDto(){
        return worker.getDto();
    }

    public void resetUI(){
        worker.resetUI();
    }
    public boolean saveData(){
        return worker.saveData();
    }
}
