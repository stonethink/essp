package client.essp.pms.wbs.process;

import client.essp.common.view.VWWorkArea;
import client.essp.pms.wbs.process.guideline.VwWbsGuideLine;
import client.essp.pms.wbs.process.checklist.VwCheckList;
import com.wits.util.Parameter;
import c2s.essp.common.code.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
public class VwWbsProcess extends VWWorkArea{
    VwWbsGuideLine wbsGuideLine;
    VwCheckList wbsCheckList;

    public VwWbsProcess() {
//        this.addTab("GuideLine", activityGuideLine);
//        this.addTab("QaCheckList", activityCheckList);
        try {
            jbInit();
            addUIEvent();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }
    private void jbInit() throws Exception {
        wbsGuideLine = new VwWbsGuideLine();
        wbsCheckList = new VwCheckList();
        addTab("Guideline", wbsGuideLine);
        addTab("  QA  ", wbsCheckList);
    }
    private void addUIEvent() {

        wbsGuideLine.btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                wbsCheckList.fireNeedRefresh();
            }
        }
        );

    }
    public void setParameter(Parameter param) {
        param.put(DtoKey.DTO, param.get("wbs"));
        wbsGuideLine.setParameter(param);
        wbsCheckList.setParameter(param);
    }
    public void refreshWorkArea() {
        this.getSelectedWorkArea().refreshWorkArea();
    }
}
