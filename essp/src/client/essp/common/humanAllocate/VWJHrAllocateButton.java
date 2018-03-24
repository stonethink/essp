package client.essp.common.humanAllocate;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import client.essp.common.view.VWWorkArea;
import client.framework.view.common.DefaultComp;
import client.framework.view.common.comFORM;
import client.framework.view.event.DataChangedListener;
import client.framework.view.vwcomp.IVWComponent;
import client.framework.view.vwcomp.VWJButton;
import client.framework.view.vwcomp.VWJText;
import com.wits.util.StringUtil;
import com.wits.util.TestPanel;
import validator.Validator;
import validator.ValidatorResult;
import client.image.ComImage;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.util.Map;
import java.util.HashMap;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.List;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import client.net.NetConnector;
import client.net.ConnectFactory;
import client.framework.view.common.comMSG;
import java.io.*;

/**
 * <p>Title: </p>
 * <p>Description: 现在的处理是显示loginId, 而非name</p>
 * 不再使用Human类
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Enovation</p>
 * @author yery
 * @version 1.0
 */

public class VWJHrAllocateButton extends JPanel implements IVWComponent{
    private static final String actionId = "F00HumanAllocate";

    private boolean bReshap = false;
    private int offset = 0;

    private VWJText textComp = new VWJText();
    private VWJButton buttonComp = new VWJButton();

    private String loginIds;
    private Map names = new HashMap();
    private int allocType;

    private Long acntRid;
    private String title;
    private boolean showName = true;
    private String loginName;

    public VWJHrAllocateButton() {
            this(HrAllocate.ALLOC_SINGLE );
    }

    public VWJHrAllocateButton( int allocType ) {
        this.allocType = allocType;
        try {
            jbInit();
            addUICEvent();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setLayout(new BorderLayout());

        textComp.setText("");
        buttonComp.setText("");
        buttonComp.setIcon(new ImageIcon( ComImage.getImage( "humanAllocate.gif" ) ) );

        this.add(textComp, BorderLayout.CENTER);
        this.add(buttonComp, BorderLayout.EAST);
    }

    private void addUICEvent() {
        buttonComp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedButton(e);
            }
        });

        textComp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(FocusEvent e) {
                this_focusGained(e);
            }
            public void focusLost(FocusEvent e) {
                this_focusLost(e);
            }
        });

    }

    /**
     * 处理获得焦点事件,输入框内显示LoginId
     * @param e FocusEvent
     */
    private void this_focusGained(FocusEvent e){
        loginName = textComp.getText();
        this.textComp.setText(loginIds);
    }
    /**
     * 处理丢失焦点事件,取出输入框内的值,设置到控件
     * @param e FocusEvent
     */
    private void this_focusLost(FocusEvent e){
        setUICValue(textComp.getText());
    }

    private String format(String loginId,String name ){
        if(name == null || "".equals(name.trim()))
            return loginId;
        this.setToolTipText(loginId);
        return name;
    }
    /**
     * 为控件设值,设值时从设入字符串中截取LoginId的串,判断每个LoginId是否传入了姓名
     * 若没有,则至后台读取LoginId,最后拼出显示的字符串LoginId -- Name
     */
    public void setUICValue(Object value) {
        String tempStr = StringUtil.nvl(value);
        if(tempStr.equals(loginIds)){
        	if(textComp.hasFocus()) {
        		textComp.setText(loginIds);
        	} else {
        		textComp.setText(loginName);
        	}
            return;
        }
        
        loginIds = null;
        String[] userArray = StringUtil.split(tempStr,",");
        if(userArray == null || userArray.length == 0){
            return;
        }
        List unNameIds = new ArrayList();//记录没有姓名对应的LoginId
        String showStr = null;//显示的字符串
        String toolTipStr = "";//显示toolTip
        String realValue = "";
        for(int i = 0;i < userArray.length ;i ++){
            String loginId = userArray[i];
            String name = (String)names.get(loginId);
            if( name != null ){//判断传入字串是否有姓名
                String show = format(loginId,name);
                if(showStr == null) {
                    showStr = show;
                    toolTipStr = loginId + "/" + show;
                    realValue = loginId;
                } else {
                   showStr = showStr + "," + show;
                   toolTipStr = toolTipStr + "," + loginId + "/" + show;
                   realValue = realValue + "," + loginId;
                }
            }else{
                unNameIds.add(loginId);
            }
        }
        //至服务端读取没有姓名的LoginId
        if( unNameIds.size() != 0){
            InputInfo inputInfo = new InputInfo();
            inputInfo.setActionId( this.actionId );
            inputInfo.setInputObj("unNameIds", unNameIds );
            ReturnInfo returnInfo = accessData(inputInfo);
            if(returnInfo.isError() == false){
                Map userNames = (Map) returnInfo.getReturnObj("userName");
                Map userLoginId = (Map) returnInfo.getReturnObj("userLoginId");
                if(userNames != null){
                    for (int i = 0; i < unNameIds.size(); i++) {
                        String loginId = (String) unNameIds.get(i);
                        String name = (String) userNames.get(loginId);
                        String realLoginId = (String) userLoginId.get(loginId);
                        String show = format(loginId,name);
                        if (showStr == null) {
                            showStr = show;
                            toolTipStr = realLoginId + "/" + show;
                            realValue = realLoginId;
                        } else {
                            showStr = showStr + "," + show;
                            toolTipStr = toolTipStr + "," + realLoginId + "/" + show;
                            realValue = realValue + "," + realLoginId;
                        }
                        names.put(realLoginId,name);
                    }
                }
            }
        }
        loginName = showStr;
        loginIds =  StringUtil.nvl(realValue);
        if(showName) {
            this.textComp.setText(showStr);
        } else {
            this.textComp.setText(loginIds);
        }
        this.textComp.setToolTipText(toolTipStr);
        textComp.fireDataChanged();
    }


    //始终返回LoginId
    public Object getUICValue() {
        return this.loginIds;
    }

    public void setBounds(int x, int y, int w, int h) {
        if (isBReshap() == true) {
            int newX = Math.max(x, getOffset());
            super.setBounds(newX, y, w - (newX - x), h);
        } else {
            super.setBounds(x, y, w, h);
        }
    }

    public void setEnabled(boolean prm_bValue) {
        this.textComp.setEnabled(prm_bValue);
        this.buttonComp.setEnabled(prm_bValue);
    }

    public void setValidator(Validator validator) {
        this.textComp.setValidator( validator );
    }


    public boolean validateValue() {
        return this.textComp.validateValue();
    }

    public ValidatorResult getValidatorResult() {
        return this.textComp.getValidatorResult();
    }

    public void setErrorField(boolean flag) {
        this.textComp.setErrorField( flag );
    }

    public IVWComponent duplicate() {
        VWJHrAllocateButton allocateButton = new VWJHrAllocateButton( this.allocType);
        VWJText tText = (VWJText)this.textComp.duplicate();
        allocateButton.setTextComp( tText );

        return allocateButton;
    }

    public void setTextComp(VWJText textComp){
        this.textComp = textComp;
    }

    public VWJText getTextComp(){
        return this.textComp;
    }

    public void setButtonComp(VWJButton button){
        this.buttonComp = button;
    }

    public VWJButton getButtonComp(){
        return this.buttonComp;
    }

    public void addDataChangedListener(DataChangedListener listener) {
        this.textComp.addDataChangedListener( listener );
    }

    public void actionPerformedButton(ActionEvent e){
        System.out.println( "begin actionPerformedButton()" );

        HrAllocate hrAllocate = new HrAllocate( this.loginIds );
        hrAllocate.setAcntRid(getAcntRid());
        hrAllocate.setTitle(getTitle());
        if( allocType == HrAllocate.ALLOC_MULTIPLE ){
            hrAllocate.allocMultiple();
        }else if( allocType == HrAllocate.ALLOC_SINGLE ){
            hrAllocate.allocSingle();
        }

        setUICValue( hrAllocate.getNewUserIds() );

        comFORM.setFocus( this.textComp );
        System.out.println( "end actionPerformedButton" );
    }

    public void setDtoClass(Class dtoClass) {
        this.textComp.setDtoClass( dtoClass );
    }

    public Class getDtoClass() {
        return this.textComp.getDtoClass();
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }


    public boolean isBReshap() {
        return bReshap;
    }

    public void setBReshap(boolean bReshap) {
        this.bReshap = bReshap;
    }

    public void setAllocType(int allocType){
        this.allocType = allocType;
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
                this.setErrorField(true);
                comFORM.setFocus(this.textComp);
            } else {
                comMSG.dispErrorDialog(returnInfo.getReturnMessage());
                this.setErrorField(true);
                comFORM.setFocus(this.textComp);
                //全部置红
            }
        }
        return returnInfo;
    }

    public int getHorizontalAlignment(){
        return SwingConstants.CENTER;
    }

    public boolean isEnabled(){
        return this.textComp.isEnabled() || this.buttonComp.isEnabled();
    }


    public void setAcntRid(Long acntRid){
        this.acntRid = acntRid;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public Long getAcntRid(){
        return this.acntRid;
    }

    public String getTitle(){
        return this.title;
    }
    public void setShowName(boolean isShow) {
        this.showName = isShow;
    }
    public boolean getShowName() {
        return this.showName;
    }

    public static void main(String[] args) {

        VWJHrAllocateButton btn2 = new VWJHrAllocateButton( HrAllocate.ALLOC_SINGLE);
        btn2.setBounds( 100,150,200,DefaultComp.TEXT_HEIGHT );

        VWJHrAllocateButton btn3 = new VWJHrAllocateButton( HrAllocate.ALLOC_SINGLE);
        btn3.setBounds( 100,200,200,DefaultComp.TEXT_HEIGHT );

        VWWorkArea workArea = new VWWorkArea();
        workArea.setLayout( null );
        workArea.add( btn2 );
        workArea.add( btn3 );

        TestPanel.show(workArea);
        btn2.setUICValue("000018");
        btn3.setUICValue("-1");

    }


}
