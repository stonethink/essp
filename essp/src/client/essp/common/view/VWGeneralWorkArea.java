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

    /////// 段1，定义界面：定义界面（定义控件，设置控件名称，光标控制顺序）、定义界面事件
    public VWGeneralWorkArea() {
        super();
    }

    /////// 段2，设置参数：获取传入参数
    /**
     * @todo: 在子类中重载这个方法。重载时先调用这个方法。
     */
    public void setParameter(Parameter param) {
        this.isRefresh = true;
    }


    /////// 段3，获取数据并刷新画面
    /**
     * @note: 子类不用重载这个方法
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
    * @todo: 在子类中重载这个方法。
    */
    protected void resetUI(){}

    /////// 段4，事件处理

    /////// 段5，保存数据
    /**
    * @todo: 在子类中重载这个方法。
    */
    public void saveWorkArea() {
    }

    /**
    * 这个方法询问“是否要保存？”
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
    * 访问服务端
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

                //查询错误的输入栏位，并置红
                VWUtil.setErrorField(this, result);
            } else {
                comMSG.dispErrorDialog(getErrorMessage(returnInfo));

                //全部置红
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
     * 获取错误信息 add by lipengxu 2007-09-26
     *     如果错误代码有做多国语言化，就返回国际化信息。
     *     如果没有做多国语言化，返回错误信息。
     * 多国语言化过度阶段使用，如果所有的异常都已经国际化，就可直接返回returnCode
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
