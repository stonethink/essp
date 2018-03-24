package client.essp.timesheet.activity.notebook;

import java.awt.BorderLayout;
import java.awt.Dimension;

import c2s.essp.timesheet.activity.DtoNotebookTopic;
import client.essp.common.view.VWGeneralWorkArea;
import client.framework.view.vwcomp.VWJEditorPane;
import client.framework.view.vwcomp.VWUtil;
/**
 * <p>Title: VwNoteRightBench</p>
 *
 * <p>Description:NoteBook描述卡片 </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author TBH
 * @version 1.0
 */
public class VwNoteRightBench extends VWGeneralWorkArea{
    DtoNotebookTopic dtoNotebook = new DtoNotebookTopic();
    VWJEditorPane editorPane = new VWJEditorPane();

    public VwNoteRightBench() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    //初始化
    private void jbInit() {
        this.setLayout(new BorderLayout());
        editorPane.setPreferredSize(new Dimension(300, 300));
        this.add(editorPane.getShower());
        editorPane.setName("note");
    }
    //重置
    protected void resetUI() {
        VWUtil.bindDto2UI(dtoNotebook, this);
    }
    /**
     * 设置参数
     * @param dtoNotebook DtoNotebookTopic
     */
    public void setParameter(DtoNotebookTopic dtoNotebook) {
        super.setParameter(null);
        this.dtoNotebook = dtoNotebook;
        if (this.dtoNotebook == null) {
            this.dtoNotebook = new DtoNotebookTopic();
        }
    }
}
