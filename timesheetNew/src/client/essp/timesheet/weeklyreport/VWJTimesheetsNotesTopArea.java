package client.essp.timesheet.weeklyreport;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheet;
import client.essp.common.view.VWGeneralWorkArea;
/**
 *
 * <p>Title: VWJTimesheetsNotesTopArea</p>
 *
 * <p>Description: Notes�����ϰ벿����ʾ�Ѵ��ڵ�Notes��Ϣ���ı���</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author wenhaizheng
 * @version 1.0
 */
public class VWJTimesheetsNotesTopArea extends VWGeneralWorkArea {

     private JTextArea topTextArea;
     private JScrollPane jsp;
     private final static String actionId_LoadNotes = "FTSLoadNotes";
    public VWJTimesheetsNotesTopArea(Long tsRid) {
        super();
        jbInit();
        initData(tsRid);
    }
    /**
     * ��ʼ������
     */
    private void jbInit(){
//        this.setLayout(null);
           topTextArea = new JTextArea();
           topTextArea.setEditable(false);
           jsp = new JScrollPane(topTextArea);
           jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
//        topTextPane.setBounds(new Rectangle(10,10,200,100));
           setMinimumSize(new Dimension(200, 77));
           setPreferredSize(new Dimension(200, 77));
           this.add(jsp, BorderLayout.CENTER);

    }
    private void initData(Long tsRid){
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(actionId_LoadNotes);
        inputInfo.setInputObj(DtoTimeSheet.DTO_RID, tsRid);
        ReturnInfo returnInfo = this.accessData(inputInfo);
        if(returnInfo.isError() == false) {
            String notes = (String) returnInfo.getReturnObj(DtoTimeSheet.DTO_NOTES);
            setDisplayValue(notes);
        }
    }
    /**
     * ��ȡ��ǰ��ʾ���ı���Ϣ
     * @return String
     */
    public String getDisplayData(){
       return topTextArea.getText();
    }
    /**
     * ���õ�ǰҪ��ʾ���ı���Ϣ
     * ���մ��������뵽֮ǰ�����˳����ʾ��Ϣ
     * @param value String
     */
    public void setDisplayValue(String value){
        topTextArea.setText(value);
        topTextArea.setSelectionStart(1);
        topTextArea.setSelectionEnd(1);
    }

}
