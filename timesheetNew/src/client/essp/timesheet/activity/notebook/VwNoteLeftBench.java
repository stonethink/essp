package client.essp.timesheet.activity.notebook;

import com.wits.util.IVariantListener;
import javax.swing.table.TableColumnModel;
import client.framework.model.VMColumnConfig;
import javax.swing.table.JTableHeader;
import client.essp.common.view.VWTableWorkArea;
import c2s.dto.DtoBase;
import c2s.dto.ReturnInfo;

import java.util.ArrayList;
import java.util.List;
import c2s.dto.InputInfo;
import com.wits.util.Parameter;
import c2s.essp.timesheet.activity.DtoActivityKey;
import client.framework.view.vwcomp.VWUtil;
/**
 * <p>Title: VwNoteLeftBench</p>
 *
 * <p>Description:NoteBook���⿨Ƭ��Ƭ </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author TBH
 * @version 1.0
 */
public class VwNoteLeftBench extends VWTableWorkArea implements
    IVariantListener {
    Object[][] configs = null;
    VwNoteRightBench vwNoteRightBech;
    private Long activityRid = null;
    static final String treeColumnName = "Main Record";
    static final String actionIdInit = "FTSNoteBookList";

    public VwNoteLeftBench() {
        jbInit();
    }

    //��ʼ��
    private void jbInit() {
        try {
            configs = new Object[][] { {"rsid.timesheet.noteBookTopic", "notebookTopicName",
                      VMColumnConfig.UNEDITABLE, null},
            };
            super.jbInit(configs, DtoBase.class);
            //���ó�ʼ�п�
            JTableHeader header = this.getTable().getTableHeader();
            //������
            this.getTable().setSortable(true);
            TableColumnModel tcModel = header.getColumnModel();
            tcModel.getColumn(0).setPreferredWidth(200);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //����
    protected void resetUI() {
        VWUtil.clearUI(this);
        if (activityRid == null) {
            return;
        }

        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdInit);
        inputInfo.setInputObj(DtoActivityKey.DTO_RID, activityRid);
        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == true) {
            return;
        }
        List noteBooklist = (List) returnInfo.getReturnObj(DtoActivityKey.NOTE_BOOK_LIST);
        getTable().setRows(noteBooklist);
        if (noteBooklist != null && noteBooklist.size() > 0) {
        	getTable().getSelectionModel().clearSelection();
            getTable().setSelectRow(0);
        } else {
       	 //add by lipengxu 071224, ����б�
       	 this.getTable().setRows(new ArrayList());
        }
    }
    //��������
    public void setParameter(Parameter param) {
        activityRid = (Long)param.get(DtoActivityKey.DTO_RID);
        super.setParameter(param);
    }

    public void dataChanged(String string, Object object) {
    }

}
