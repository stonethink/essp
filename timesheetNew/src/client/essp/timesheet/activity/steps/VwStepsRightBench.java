package client.essp.timesheet.activity.steps;

import java.awt.BorderLayout;
import java.awt.Dimension;

import c2s.essp.timesheet.activity.DtoActivityStep;
import client.essp.common.view.VWGeneralWorkArea;
import client.framework.view.vwcomp.VWJEditorPane;
import client.framework.view.vwcomp.VWUtil;
/**
 * <p>Title: VwStepsRightBench</p>
 *
 * <p>Description:Steps������Ƭ </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author TBH
 * @version 1.0
 */
public class VwStepsRightBench extends VWGeneralWorkArea{
    DtoActivityStep dtoStep = new DtoActivityStep();
    VWJEditorPane editorPane = new VWJEditorPane();

    public VwStepsRightBench() {
        try {
            jbInit();
        } catch (Exception e) {
            log.error(e);
        }
    }
    //��ʼ��
    private void jbInit() {
        this.setLayout(new BorderLayout());
        editorPane.setPreferredSize(new Dimension(300, 300));
        this.add(editorPane.getShower());
        editorPane.setName("description");
    }

    //����
    protected void resetUI() {
        VWUtil.bindDto2UI(dtoStep, this);
    }

    //��������
    public void setParameter(DtoActivityStep dtoStep) {
        super.setParameter(null);
        this.dtoStep = dtoStep;
        if (this.dtoStep == null) {
            this.dtoStep = new DtoActivityStep();
        }
    }
}
