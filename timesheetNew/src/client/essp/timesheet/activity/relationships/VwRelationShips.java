package client.essp.timesheet.activity.relationships;

import client.essp.common.view.VWTDWorkArea;
import java.awt.Dimension;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import client.framework.view.event.RowSelectedListener;
import c2s.essp.timesheet.activity.DtoRelationShips;
import com.wits.util.Parameter;
/**
 * <p>Title: VwRelationShips</p>
 *
 * <p>Description: RelationShip������Ŀ�Ƭ</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author TBH
 * @version 1.0
 */
public class VwRelationShips extends VWTDWorkArea{
    VwRelationShipsLeft vwLeftBench = new VwRelationShipsLeft();
    VwRelationShipsDetail vwRightBench = new VwRelationShipsDetail();

    public VwRelationShips() {
        super(300);
        try {
            jbInit();
            addUICEvent();
        } catch (Exception e) {
            log.error(e);
        }
    }
    //��ʼ��
    private void jbInit() throws Exception {
        this.setHorizontalSplit();
        this.setPreferredSize(new Dimension(650, 300));
        vwLeftBench = new VwRelationShipsLeft();
        vwLeftBench.setPreferredSize(new Dimension(400, 300));
        this.getTopArea().addTab("rsid.timesheet.relationship", vwLeftBench);

        vwRightBench = new VwRelationShipsDetail();
        vwRightBench.setPreferredSize(new Dimension(250, 300));
        this.getDownArea().addTab("rsid.timesheet.relationshipDetails", vwRightBench);
    }
  //�¼�
    private void addUICEvent() {
        vwLeftBench.getTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(e.getValueIsAdjusting() == false) {
					processRowSelectedAccList();
				}
            }
        });
    }
  //�����¼�
    public void processRowSelectedAccList() {
        DtoRelationShips dtoRelation = (DtoRelationShips) vwLeftBench.getTable().
                                       getSelectedData();
        if (dtoRelation != null) {
            this.vwRightBench.setParameter(dtoRelation);
        } else {
            this.vwRightBench.setParameter((DtoRelationShips)null);
        }
        vwRightBench.refreshWorkArea();
    }

    //���ò���
    public void setParameter(Parameter parameter) {
        vwLeftBench.setParameter(parameter);
    }

   //ˢ��
    public void refreshWorkArea() {
        vwLeftBench.refreshWorkArea();
    }

}
