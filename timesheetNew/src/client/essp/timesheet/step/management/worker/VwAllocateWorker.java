package client.essp.timesheet.step.management.worker;

import java.awt.Container;
import java.awt.Dimension;

import client.essp.common.view.VWPopToolBar;

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
public class VwAllocateWorker extends VWPopToolBar {
    private VwWorkerList wkList;
    public VwAllocateWorker(Container parent) {
        super(null,parent,"Worker List", 220,300);
        try {
            wkList = new VwWorkerList();
            toolbar.add(wkList);
//            this.setPreferredSize(new Dimension(200,300));
//            showPopSelect();
        } catch (Exception ex) {
            log.error(ex);
        }
    }


    public void setParameter(Parameter param) {
        wkList.setParameter(param);
        wkList.resetUI();
    }
}
