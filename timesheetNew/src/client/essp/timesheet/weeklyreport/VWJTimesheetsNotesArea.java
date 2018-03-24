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
 * <p>Description: Notes的主界面，包括上下两个部分：
 *                 显示信息部分和输入信息部分</p>
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

    //初始界面布局
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
     * 响应点击Add按钮时的操作
     * @param actionEvent ActionEvent
     * @return boolean
     */
    public boolean onClickOK(ActionEvent actionEvent) {
        return processAdd();
    }

    /**
     * 产生并保存新增数据
     * 1.如果输入的内容为空则不做处理
     * 2.不为空就将输入的信息按照规定格式存汝数据库中
     * 格式为:
     * yyyy-MM-dd HH:mm:ss UesrName
     * 输入内容
     * 3.将输入的信息显示在上半部分的信息显示框中
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
     * 响应点击Cancel按钮的操作
     * @param actionEvent ActionEvent
     * @return boolean
     */
    public boolean onClickCancel(ActionEvent actionEvent) {
        return true;
    }
    /**
       * 访问服务端
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
