package client.essp.common.view;

import com.wits.util.Parameter;
import client.framework.common.Constant;
import client.framework.view.common.comMSG;
import c2s.dto.ReturnInfo;
import client.net.NetConnector;
import client.net.ConnectFactory;
import c2s.dto.InputInfo;
import c2s.dto.TransactionData;
import validator.ValidatorResult;
import client.framework.view.vwcomp.VWUtil;
import com.wits.util.TestPanel;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import client.framework.view.event.DataChangedListener;
import client.framework.view.event.DataCreateListener;
import client.essp.common.view.ui.MessageUtil;

public class VWGeneralWorkArea extends VWWorkArea {
    /**
     * define control variable
     */
    private boolean isRefresh = false;
    private boolean isPrompt = false;
    private boolean isAlwaysOk = false;

    /**
     * define common data
     */
    List dataChangedListenerList = new ArrayList();
    List dataCreateListenerList = new ArrayList();

    /////// ��1��������棺������棨����ؼ������ÿؼ����ƣ�������˳�򣩡���������¼�
    public VWGeneralWorkArea() {
        super();
    }

    /////// ��2�����ò�������ȡ�������
    /**
     * @todo: �������������������������ʱ�ȵ������������
     */
    public void setParameter(Parameter param) {
        this.isRefresh = true;
    }


    /////// ��3����ȡ���ݲ�ˢ�»���
    /**
     * @note: ���಻�������������
     */
    public void refreshWorkArea() {
        if (isRefresh == true) {

            VWUtil.clearUI(this);

            //reset data
            resetUI();

            isRefresh = false;
        }
    }

    /**
    * @todo: ���������������������
    */
    protected void resetUI(){}

    /////// ��4���¼�����

    /////// ��5����������
    /**
    * @todo: ���������������������
    */
    public void saveWorkArea() {
    }

    /**
    * �������ѯ�ʡ��Ƿ�Ҫ���棿��
    */
    protected boolean confirmSaveWorkArea() {
        return confirmSaveWorkArea( "Do you save the Data?" );
    }

   protected boolean confirmSaveWorkArea(String askMsg) {
       if (this.isPrompt == true) {
                   if (this.isAlwaysOk == true) {
                       return true;
                   }
               } else {
                   int option = comMSG.dispConfirmDialogByPrompt(askMsg);
                   if (option == Constant.ALWAYS_OK) {
                       this.isPrompt = true;
                       this.isAlwaysOk = true;
                   } else if (option == Constant.ALWAYS_CANCEL) {
                       this.isPrompt = true;
                       this.isAlwaysOk = false;
                   } else {
                       this.isPrompt = false;
                       this.isAlwaysOk = false;
                   }

                   if (option == Constant.ALWAYS_OK ||
                       option == Constant.OK) {
                       return true;
                   } else {
                   }
               }

        return false;
   }


    /**
    * ���ʷ����
    */
    protected ReturnInfo accessData(InputInfo inputInfo) {
        TransactionData transData = new TransactionData();
        transData.setInputInfo(inputInfo);
        
        //added by lipengxu at 20080630 for accessData wait cursor
        setWait(true);
        
        NetConnector connector = ConnectFactory.createConnector();
        connector.accessData(transData);
        
        //stop wait cursor
        setWait(false);

        ReturnInfo returnInfo = transData.getReturnInfo();

        if (returnInfo.isError() == true) {
            ValidatorResult result = returnInfo.getValidatorResult();

            if (!result.isValid()) {
                //comMSG.dispErrorDialog(result.g);
                StringBuffer msg = new StringBuffer();

                for (int i = 0; i < result.getAllMsg().length; i++) {
                    msg.append(result.getAllMsg()[i]);
                    msg.append("\r\n");
                }

                comMSG.dispErrorDialog(msg.toString());

                //��ѯ�����������λ�����ú�
                VWUtil.setErrorField(this, result);
            } else {
                comMSG.dispErrorDialog(getErrorMessage(returnInfo));

                //ȫ���ú�
            }
        }else{
            if( isShowing() ){
                //(new VWMessageTip()).show(this.getButtonPanel(), "Success.");
            }
        }
        return returnInfo;
    }
    
    private final static Cursor WAIT_CURSOR = Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR);
    private Cursor bakCursor = Cursor.getDefaultCursor();
    private Component cursorComponent;
    
    private void setWait(boolean isWait) {
    	if(cursorComponent == null) {
    		cursorComponent = this.getParentWindow();
	    	if(cursorComponent == null) {
	    		cursorComponent = this;
	    	}
    	}
    	
    	if(isWait) {
    		if(cursorComponent.getCursor() != null) {
    			bakCursor = cursorComponent.getCursor();
    		}
    		cursorComponent.setCursor(WAIT_CURSOR);
    	} else {
    		cursorComponent.setCursor(bakCursor);
    	}
    }

    /**
     * ��ȡ������Ϣ add by lipengxu 2007-09-26
     *     ��������������������Ի����ͷ��ع��ʻ���Ϣ��
     *     ���û����������Ի������ش�����Ϣ��
     * ������Ի����Ƚ׶�ʹ�ã�������е��쳣���Ѿ����ʻ����Ϳ�ֱ�ӷ���returnCode
     * @param returnInfo ReturnInfo
     * @return String
     */
    private static String getErrorMessage(ReturnInfo returnInfo) {
        String code = returnInfo.getReturnCode();
        String message = MessageUtil.getMessage(code);
        if(message.equals("") || message.equals(code)) {
            message = returnInfo.getReturnMessage();
        }
        return message;
    }

    public void addDataChangedListener(DataChangedListener listener){
        this.dataChangedListenerList.add( listener );
    }

    public void fireDataChanged(){
        for( int i = 0 ; i < dataChangedListenerList.size(); i++ ){
            ( (DataChangedListener)dataChangedListenerList.get(i) ).processDataChanged();
        }
    }

    public void addDataCreateListener(DataCreateListener listener){
        this.dataCreateListenerList.add( listener );
    }

    public void fireDataCreate(){
        for( int i = 0 ; i < dataCreateListenerList.size(); i++ ){
            ( (DataCreateListener)dataCreateListenerList.get(i) ).processDataCreate();
        }
    }

    public static void main(String args[]){
    	final VWGeneralWorkArea area = new VWGeneralWorkArea();
    	area.getButtonPanel().addSaveButton(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				area.setWait(true);
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				area.setWait(false);
			}
    	});
    	VWGeneralWorkArea a2 = new VWGeneralWorkArea();
    	a2.addTab("test" ,area);
        TestPanel.show( a2 );
    }
}
