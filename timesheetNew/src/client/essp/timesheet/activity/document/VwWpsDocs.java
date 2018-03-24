package client.essp.timesheet.activity.document;


import client.essp.common.view.VWTableWorkArea;
import client.essp.common.view.VWTDWorkArea;
import java.awt.Dimension;
import client.framework.view.event.RowSelectedListener;
import com.wits.util.Parameter;
import c2s.essp.timesheet.activity.DtoDocument;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * <p>Description:VwWpsDocs </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author not attributable
 * @version 1.0
 */
public class VwWpsDocs extends VWTDWorkArea{
    VwWpsDocsLeftBench vwDocument = null;
    JButton btnDetail = new JButton();

    public VwWpsDocs() {
        super(250);
      try {
          jbInit();
          addUICEvent();
      } catch (Exception ex) {
    }
}
//初始化
private void jbInit() throws Exception {
     btnDetail.setText("rsid.timesheet.details");
     btnDetail.setPreferredSize(new Dimension(180,20));
     btnDetail.setEnabled(false);
     vwDocument = new VwWpsDocsLeftBench();
     JPanel panelCheck = new JPanel();
     FlowLayout flowLayoutCheck = new FlowLayout();
     flowLayoutCheck.setAlignment(FlowLayout.RIGHT);
     panelCheck.setLayout(flowLayoutCheck);
     panelCheck.setPreferredSize(new Dimension(379, 35));

     JPanel panelDailyReport = new JPanel();
     FlowLayout flowLayout2 = new FlowLayout();
     flowLayout2.setAlignment(FlowLayout.RIGHT);
     flowLayout2.setHgap(0);
     panelDailyReport.setLayout(flowLayout2);
     panelDailyReport.add(btnDetail, null);
     panelDailyReport.add(panelCheck, null);

     this.add(vwDocument, BorderLayout.CENTER);
     this.add(panelDailyReport, BorderLayout.SOUTH);

}

//增加事件
  private void addUICEvent() {
     vwDocument.getTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(e.getValueIsAdjusting() == false) {
					processRowSelectedAccList();
				}
          }
    });

     this.btnDetail.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {
                 vwDocument.actionPerformedBtnDaily(e);
             }
         });
 }

//设置参数
  public void setParameter(Parameter parameter) {
     vwDocument.setParameter(parameter);
  }

//刷新
  public void refreshWorkArea() {
      vwDocument.refreshWorkArea();
  }

//选择某一条数据时传递参数到其他卡片
  public void processRowSelectedAccList() {
     DtoDocument dtoDocument = (DtoDocument) vwDocument.getTable().
                                         getSelectedData();
     if (dtoDocument != null) {
         btnDetail.setEnabled(true);
         this.vwDocument.setDtoDoc(dtoDocument);
     } else {
         this.vwDocument.setDtoDoc((DtoDocument)null);
          btnDetail.setEnabled(false);
     }
  }
}
