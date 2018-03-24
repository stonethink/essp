package client.essp.pms.workerPop;

import java.awt.Container;

import com.wits.util.Parameter;
import client.essp.common.view.VWPopToolBar;

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
public class VwAllocateWorker extends VWPopToolBar {
    private VwWorkerList wkList;
    public VwAllocateWorker(Container parent) {
        super(null,parent,"Worker List");
        try {
            wkList = new VwWorkerList();
            toolbar.add(wkList);
            showPopSelect();
        } catch (Exception ex) {
            log.error(ex);
        }
    }


    public void setParameter(Parameter param) {
        wkList.setParameter(param);
        wkList.resetUI();
    }
}
