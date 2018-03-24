package client.essp.common.loginId;

import java.awt.event.*;
import java.util.*;

import c2s.dto.*;
import client.framework.view.common.*;
import client.framework.view.vwcomp.*;
import client.net.*;
import com.wits.util.*;
import validator.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class VWJLoginId extends VWJText {

    private static final String actionId = "F00HumanAllocate";
    private String loginIds;
    private Map names = new HashMap();

    public VWJLoginId() {
        try {
            addUICEvent();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void addUICEvent() {
        this.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(FocusEvent e) {
                _focusGained(e);
            }
            public void focusLost(FocusEvent e) {
                _focusLost(e);
            }
        });

    }

    /**
     * 处理获得焦点事件,输入框内显示LoginId
     * @param e FocusEvent
     */
    private void _focusGained(FocusEvent e){
        this.setText(loginIds);
    }
    /**
     * 处理丢失焦点事件,取出输入框内的值,设置到控件
     * @param e FocusEvent
     */
    private void _focusLost(FocusEvent e){
        setUICValue(getText());
    }

    private String format(String loginId,String name ){
        if(name == null || "".equals(name.trim()))
            return loginId;
        return name;
    }
    /**
     * 为控件设值,设值时从设入字符串中截取LoginId的串,判断每个LoginId是否传入了姓名
     * 若没有,则至后台读取LoginId,最后拼出显示的字符串LoginId -- Name
     */
    public void setUICValue(Object value) {
        loginIds = null;
        String tempStr = StringUtil.nvl(value);
        String[] userArray = StringUtil.split(tempStr,",");
        if(userArray == null || userArray.length == 0){
            super.setUICValue(null);
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
                        String show = format(realLoginId,name);
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

        loginIds =  StringUtil.nvl(realValue);

        this.setText(showStr);
        this.setToolTipText(toolTipStr);
    }

    //始终返回LoginId
    public Object getUICValue() {
        return this.loginIds;
    }
    public String getText() {
        return super.getText();
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
//                comFORM.setFocus(this);
            } else {
                comMSG.dispErrorDialog(returnInfo.getReturnMessage());
                this.setErrorField(true);
//                comFORM.setFocus(this);
                //全部置红
            }
        }
        return returnInfo;
    }

    public IVWComponent duplicate() {
        VWJLoginId comp = new VWJLoginId();
        comp.setName(this.getName());
        comp.setDtoClass(this.getDtoClass());
        comp.setFont(this.getFont());
        comp.setUICValue(null);
        comp.setText("");
        return comp;
    }


    public static void main(String[] args) {
//        Global.todayDateStr = "2005-12-03";
//        Global.todayDatePattern = "yyyy-MM-dd";
//        Global.userId = "stone.shi";
//        DtoUser user = new DtoUser();
//        user.setUserID(Global.userId);
//        user.setUserLoginId(Global.userId);
//        HttpServletRequest request = new HttpServletRequestMock();
//        HttpSession session = request.getSession();
//        session.setAttribute(DtoUser.SESSION_USER, user);
//
//
//        VWJLoginId log = new VWJLoginId();
//        VWWorkArea workArea = new VWWorkArea();
//        workArea.add(log,BorderLayout.NORTH);
//        workArea.add(new VWJText(),BorderLayout.SOUTH);
//        TestPanel.show(workArea);
//        log.setUICValue("WH0607014");
//        System.out.println(log.getText());
    }

}
