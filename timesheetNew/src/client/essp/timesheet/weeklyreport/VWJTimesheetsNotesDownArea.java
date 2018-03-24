package client.essp.timesheet.weeklyreport;

import client.framework.view.vwcomp.VWJTextArea;
import java.awt.Dimension;
import java.awt.BorderLayout;
import com.wits.util.TestPanel;
import client.essp.common.view.VWWorkArea;
/**
 *
 * <p>Title: VWJTimesheetsNotesDownArea</p>
 *
 * <p>Description: Notes�����°벿������������Ϣ�������ı���</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author wenhaizheng
 * @version 1.0
 */
public class VWJTimesheetsNotesDownArea extends VWWorkArea {

    private VWJTextArea downTextArea;

    public VWJTimesheetsNotesDownArea() {
        super();
        jbInit();
    }

    /**
     * ��ʼ������
     */
    private void jbInit() {
//        this.setLayout(null);
        downTextArea = new VWJTextArea();
//        downTextArea.setBounds(new Rectangle(10,10,200,200));
        setMinimumSize(new Dimension(200, 77));
        setPreferredSize(new Dimension(200, 77));
        this.add(downTextArea, BorderLayout.CENTER);
    }

    /**
     * ��ȡ��ʾ���ı�
     * @return String
     */
    public String getDisplayData() {
        return downTextArea.getText();
    }

    /**
     * ����Ҫ��ʾ���ı���Ϣ
     * @param value String
     */
    public void setDisplayValue(String value) {
        downTextArea.setText(value);
    }

    public void resetTextArea() {
        downTextArea.setText("");
        downTextArea.requestFocus();
    }

    public VWJTextArea getVWJTextArea() {
        return downTextArea;
    }
}
