package client.essp.pms.account.baseline;

import client.essp.common.view.VWWorkArea;
import client.framework.view.vwcomp.VWJTable;
import java.util.List;
import client.framework.model.VMTable2;
import javax.swing.JScrollPane;
import java.awt.AWTEvent;
import com.wits.util.TestPanel;
import client.essp.common.view.VWTableWorkArea;
import c2s.dto.ReturnInfo;
import c2s.essp.pms.account.DtoPmsAcnt;
import c2s.dto.InputInfo;
import com.wits.util.Parameter;
import c2s.essp.pms.account.DtoAcntBLLog;
import client.essp.common.file.VwJFileButton;
import javax.swing.BorderFactory;
import client.framework.model.VMColumnConfig;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import client.framework.view.event.RowSelectedListener;
import client.framework.view.common.comMSG;
import client.net.Download;
import client.framework.view.vwcomp.VWJText;
import client.framework.view.vwcomp.VWJDate;
import client.essp.common.loginId.VWJLoginId;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class VwAcntBLLog extends VWTableWorkArea {

    private String actionLog = "FAcntBaseLineLog";

    /**
      * input parameters
      */
     DtoPmsAcnt dtoAccount = null; //传入项目


    /**
     * define common data (globe)
     */

/////// 段1，定义界面：定义界面（定义控件，设置控件名称，光标控制顺序）、定义界面事件

    DtoAcntBLLog acntBLLog;
    VwJFileButton btnFile = null;

    public VwAcntBLLog() {

        btnFile=new VwJFileButton("PMS",false);
        btnFile.getTextComp().setBorder(BorderFactory.createEmptyBorder());
        Object[][]  configs =  new Object[][] {
                               {"Baseline", "baselineId",VMColumnConfig.UNEDITABLE,new VWJText()},
                               {"Application Date","appDate",VMColumnConfig.UNEDITABLE,new VWJDate()},
                               {"Approved Date", "approveDate",VMColumnConfig.UNEDITABLE,new VWJDate()},
                               {"Approved By", "approveUser",VMColumnConfig.UNEDITABLE,new VWJLoginId()},
                               {"Application Reason", "appReason",VMColumnConfig.UNEDITABLE,new VWJText()},
                               {"Attachment", "fileLink",VMColumnConfig.EDITABLE,btnFile}
        };

        try {
            jbInit(configs, DtoAcntBLLog.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

       //addUIEvent();
    }
    public void addUIEvent(){
//        btnFile.addMouseListener(new MouseAdapter(){
//            public void mouseClicked(MouseEvent me){
//                if(me.getClickCount()==2){
//
//                    //downLoadfile();
//                }
//            }
//
//            });
//            this.getTable().addRowSelectedListener(new RowSelectedListener(){
//            public void processRowSelected() {
//                processRowSelectedBl(getTable());
//            }
//          }
//);

    }

    /////// 段2，设置参数：获取传入参数
    public void setParameter(Parameter param) {
        super.setParameter(param);
        if (param.get("dtoAccount") != null) {
            dtoAccount = (DtoPmsAcnt) param.get("dtoAccount");
        }
    }


    protected void resetUI() {
        if( dtoAccount != null ){
            InputInfo inputInfo = new InputInfo();

            inputInfo.setActionId(this.actionLog);
            inputInfo.setInputObj("rid", dtoAccount.getRid());

            ReturnInfo returnInfo = accessData(inputInfo);

            if (returnInfo.isError() == false) {
                List lApp = (List) returnInfo.getReturnObj("BaseLineLog");
                String acntCode=(String)returnInfo.getReturnObj("acntCode");
                if (lApp != null) {
                    btnFile.setAcntCode(acntCode);
                    this.getTable().setRows(lApp);
                }
            }
        }
    }
//    public void downLoadfile(){
//        if(acntBLLog!=null&&acntBLLog.getFileLink()!=null){
//            String file = "";
//            file = acntBLLog.getFileLink();
//            if (!"".equals(file.trim())) {
//                if(comMSG.dispDialogProcessYN("Do you want to download baseLine History:" +
//                                           file + "?")==1){
//                  Download downLoad=new Download();
//                  downLoad.showFileDialog(this,"PMS","002645W",file,file);
//
//             }
//            }
//        }
//    }
    public void processRowSelectedBl(VWJTable Table){

        acntBLLog=(DtoAcntBLLog)Table.getSelectedData();
    }
    public static void main(String[] args) {
       DtoPmsAcnt dtoPmsAcnt = new DtoPmsAcnt();
        dtoPmsAcnt.setRid( new Long(4) );
        dtoPmsAcnt.setPm(false);
        Parameter param = new Parameter();
        param.put("dtoAccount", dtoPmsAcnt);

        VWWorkArea workArea = new VwAcntBLLog();
        workArea.setParameter(param );
        TestPanel.show(workArea);
        workArea.refreshWorkArea();

    }


}
