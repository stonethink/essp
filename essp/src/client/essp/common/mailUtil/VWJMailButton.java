package client.essp.common.mailUtil;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import c2s.essp.common.mail.DtoMail;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.VWJButton;
import client.framework.view.vwcomp.VWUtil;
import client.net.ConnectFactory;
import client.net.NetConnector;
import validator.ValidatorResult;
import client.essp.common.view.VWWorkArea;
import com.wits.util.TestPanel;

public class VWJMailButton extends VWJButton {
    private final static String actionIdByEmailAddress = "F00MailSendByEmailAddressAction";
    private final static String actionIdByUserId = "F00MailSendByUserIdAction";
    public final static int BY_EMAIL_ADDRESS = 0;
    public final static int BY_USER_ID = 1;

    private String actionId;

    IMailProvider provider = null;

    public VWJMailButton() {
        this(null);
    }

    public VWJMailButton(IMailProvider provider) {
        setType(BY_USER_ID);

        this.setText("Mail");
        this.provider = provider;

        this.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendMail();
            }
        });
    }

    protected void sendMail() {
        if( provider.beforeSendMail() ){
            InputInfo inputInfo = new InputInfo();
            inputInfo.setActionId(getActionId());
            if (this.provider != null) {
                inputInfo.setInputObj(DtoMail.MAIL_KEY, provider.getMail());
            }

            ReturnInfo returnInfo = accessData(inputInfo);
            if (returnInfo.isError() == false) {
                comMSG.dispMessageDialog("Send mail ok.");
            }
        }
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

                //查询错误的输入栏位，并置红
                VWUtil.setErrorField(this, result);
            } else {
                comMSG.dispErrorDialog(returnInfo.getReturnMessage());

                //全部置红
            }
        } else {
            if (isShowing()) {
                //(new VWMessageTip()).show(this.getButtonPanel(), "Success.");
            }
        }
        return returnInfo;
    }

    public String getActionId() {
        return this.actionId;
    }

    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    public void setType(int type ){
        if( type == BY_EMAIL_ADDRESS ){
            setActionId(actionIdByEmailAddress);
        }else{
            setActionId(actionIdByUserId);
        }
    }

    public static void main(String args[]){
        VWWorkArea w = new VWWorkArea();
        w.setLayout(null);
        VWJMailButton btnMail = new VWJMailButton(new IMailProvider(){
            public boolean beforeSendMail(){
                return true;
            }
            public DtoMail getMail(){
                DtoMail dto = new DtoMail();
                dto.setToAddress("huaxiao@wistronits.com");
                dto.setTitle("Ha, I see you.");
                dto.setConfigFile("TestMail");
                dto.setContent("\nToday is sunday.Where are you now?\n\n");
                return dto;
            }
        });
        btnMail.setType(VWJMailButton.BY_EMAIL_ADDRESS);
        btnMail.setBounds(100,100,100,20);
        w.add(btnMail);
        TestPanel.show(w);
    }
}
