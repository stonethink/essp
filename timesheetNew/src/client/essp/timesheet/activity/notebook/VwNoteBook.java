package client.essp.timesheet.activity.notebook;

import client.essp.common.view.VWTDWorkArea;
import java.awt.Dimension;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import client.framework.view.event.RowSelectedListener;
import com.wits.util.Parameter;
import c2s.essp.timesheet.activity.DtoNotebookTopic;
/**
 * <p>Title: VwNoteBook</p>
 *
 * <p>Description:NoteBook������Ŀ�ܿ�Ƭ </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author TBH
 * @version 1.0
 */
public class VwNoteBook extends VWTDWorkArea {
    DtoNotebookTopic dtoNote = new DtoNotebookTopic();
    VwNoteLeftBench vwLeftNote = new VwNoteLeftBench();
    VwNoteRightBench  vwNoteDes= new VwNoteRightBench();
    public VwNoteBook() {
        super(300);
        try {
            jbInit();
        } catch (Exception ex) {
        }
        addUICEvent();
    }

    //��ʼ��
    private void jbInit() throws Exception {
        this.setHorizontalSplit();
        this.setPreferredSize(new Dimension(650, 300));
        vwLeftNote = new VwNoteLeftBench();
        vwLeftNote.setPreferredSize(new Dimension(300, 300));
        this.getTopArea().add(vwLeftNote);

        vwNoteDes = new VwNoteRightBench();
        vwNoteDes.setPreferredSize(new Dimension(300, 300));
        this.getDownArea().addTab("", vwNoteDes);
    }
  //�����¼�
  private void addUICEvent() {
      vwLeftNote.getTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(e.getValueIsAdjusting() == false) {
					processRowSelectedAccList();
				}
          }
      });
  }
  //�����¼�
   public void processRowSelectedAccList() {
      dtoNote = (DtoNotebookTopic) vwLeftNote.getTable().getSelectedData();
      if (dtoNote != null) {
          this.vwNoteDes.setParameter(dtoNote);
          this.getDownArea().setTabTitle(0, dtoNote.getNotebookTopicName());
      } else {
          this.vwNoteDes.setParameter((DtoNotebookTopic)null);
          this.getDownArea().setTabTitle(0, "");
      }

      vwNoteDes.refreshWorkArea();
   }

   //���ò���
    public void setParameter(Parameter parameter) {
       vwLeftNote.setParameter(parameter);
   }

   //ˢ��
    public void refreshWorkArea() {
       vwLeftNote.refreshWorkArea();
   }
}
