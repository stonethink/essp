package client.essp.timesheet.weeklyreport;

import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Date;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheet;
import client.essp.common.view.VWTDWorkArea;
import client.framework.common.Global;
import client.framework.view.vwcomp.IVWPopupEditorEvent;
import client.net.ConnectFactory;
import client.net.NetConnector;
import com.wits.util.comDate;

/**
 *
 * <p>Title: VWJTimesheetsNotesArea</p>
 *
 * <p>Description: Notes�������棬���������������֣�
 *                 ��ʾ��Ϣ���ֺ�������Ϣ����</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author wenhaizheng
 * @version 1.0
 */
public class VWJTimesheetsNotesArea extends VWTDWorkArea implements
        IVWPopupEditorEvent{
    private final static String actionId_SaveNotes = "FTSaveNotes";
    private VWJTimesheetsNotesTopArea topArea;
    private VWJTimesheetsNotesDownArea downArea;
    private Long tsRid;
    public VWJTimesheetsNotesArea(Long tsRid) {
        super(130);
        try {
            jbInit(tsRid);
        } catch (Exception ex) {
            log.error(ex);
        }
        this.setTsRid(tsRid);
    }

    //��ʼ���沼��
    private void jbInit(Long tsRid) throws Exception {
        topArea = new VWJTimesheetsNotesTopArea(tsRid);
        downArea = new VWJTimesheetsNotesDownArea();
        this.getTopArea().addTab("rsid.timesheet.notesHistory", topArea);
        this.getDownArea().addTab("rsid.timesheet.newNotes", downArea, true);
        downArea.getVWJTextArea().addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if(e.isControlDown() && e.getKeyCode() == e.VK_ENTER) {
                    processAdd();
                }
            }
        });
    }

    /**
     * ��Ӧ���Add��ťʱ�Ĳ���
     * @param actionEvent ActionEvent
     * @return boolean
     */
    public boolean onClickOK(ActionEvent actionEvent) {
        return processAdd();
    }

    /**
     * ������������������
     * 1.������������Ϊ����������
     * 2.��Ϊ�վͽ��������Ϣ���չ涨��ʽ�������ݿ���
     * ��ʽΪ:
     * yyyy-MM-dd HH:mm:ss UesrName
     * ��������
     * 3.���������Ϣ��ʾ���ϰ벿�ֵ���Ϣ��ʾ����
     * @return boolean
     */
    private boolean processAdd() {
        String addString = downArea.getDisplayData();
        if(addString.trim().equals("")){
           return false;
        }

        String day = comDate.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss");
        String note = day + "   " + Global.userName + "\n"
                      + addString
                      + "\n\n";
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(actionId_SaveNotes);
        inputInfo.setInputObj(DtoTimeSheet.DTO_RID, tsRid);
        inputInfo.setInputObj(DtoTimeSheet.DTO_NOTES, note);
        ReturnInfo returnInfo = this.accessData(inputInfo);
        String newNotes = "";
        if(returnInfo.isError() == false) {
            newNotes = (String) returnInfo.getReturnObj(DtoTimeSheet.DTO_NOTES);
        }
        topArea.setDisplayValue(newNotes);
        downArea.resetTextArea();
        return false;

    }
    /**
     * ��Ӧ���Cancel��ť�Ĳ���
     * @param actionEvent ActionEvent
     * @return boolean
     */
    public boolean onClickCancel(ActionEvent actionEvent) {
        return true;
    }
    /**
       * ���ʷ����
       */
       protected ReturnInfo accessData(InputInfo inputInfo) {
           TransactionData transData = new TransactionData();
           transData.setInputInfo(inputInfo);

           NetConnector connector = ConnectFactory.createConnector();
           connector.accessData(transData);

           ReturnInfo returnInfo = transData.getReturnInfo();

           return returnInfo;
    }

    public String getNotes() {
        return topArea.getDisplayData();
    }

    public void setTsRid(Long tsRid) {
        this.tsRid = tsRid;
    }

    public Long getTsRid() {
        return tsRid;
    }

}
