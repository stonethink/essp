package client.essp.pms.activity.process;

import client.essp.common.view.VWWorkArea;
import client.essp.pms.wbs.process.checklist.VwCheckList;
import c2s.essp.common.code.DtoKey;
import client.essp.pms.activity.process.guideline.VwAcGuideLine;
import com.wits.util.Parameter;
import c2s.essp.pms.wbs.DtoKEY;
import c2s.dto.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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
public class VwActivityProcess extends VWWorkArea {
    VwCheckList activityCheckList;
    VwAcGuideLine activityGuideLine;

    public VwActivityProcess() {
//        this.addTab("GuideLine", activityGuideLine);
//        this.addTab("QaCheckList", activityCheckList);
        try {
            jbInit();
            addUICEvent();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    private void jbInit() throws Exception {
        activityCheckList = new VwCheckList();
        activityGuideLine = new VwAcGuideLine();
        addTab("Guideline", activityGuideLine);
        addTab("  QA  ", activityCheckList);



    }
    public void addUICEvent() {
        activityGuideLine.btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                activityCheckList.fireNeedRefresh();
            }
        }
        );

    }
    public void setParameter(Parameter param) {
        param.put(DtoKey.DTO, ((ITreeNode)param.get(DtoKEY.WBSTREE)).getDataBean());
        activityCheckList.setParameter(param);
        activityGuideLine.setParameter(param);
    }

    public void refreshWorkArea() {
        this.getSelectedWorkArea().refreshWorkArea();
    }
}
