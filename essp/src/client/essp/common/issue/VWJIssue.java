package client.essp.common.issue;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import c2s.essp.common.code.DtoKey;
import c2s.essp.common.issue.DtoIssue;
import c2s.essp.common.issue.IDtoIssue;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.IVWComponent;
import client.framework.view.vwcomp.IVWJIssue;
import client.framework.view.vwcomp.IVWPopupEditorEvent;
import client.framework.view.vwcomp.VWJPopupEditor;
import client.framework.view.vwcomp.VWJText;
import client.framework.view.vwcomp.VWUtil;
import client.net.ConnectFactory;
import client.net.NetConnector;
import com.wits.util.Parameter;
import com.wits.util.StringUtil;
import validator.ValidatorResult;


public class VWJIssue extends VWJText implements
        IVWPopupEditorEvent, IVWJIssue {
    private static final String actionId = "F00IssueAddAction";
    private static final String updateAction = "F00IssueUpdateAction";
    private static final String checkIssueAction = "F00IssueCheckAction";
    private VWJIssueArea issue;
    private Long acntRid;
    private String initIssueType;
    private boolean isEditing = false;
    String enterTitle = "";
    public VWJIssue() {
        this(null);
    }
    public VWJIssue(Long acntRid) {
        super();
        this.acntRid = acntRid;
        this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                showMe(e);
            }
        });
    }

    /**
     * Show出控件，使用前一定设置account rid
     * @param e MouseEvent
     */
    protected void showMe(MouseEvent e) {
        if (isEnabled() && e.getClickCount() == 2) {
            isEditing = true;
            Parameter param = new Parameter();
            if (getAcntRid() != null) {
                param.put(DtoKey.ACNT_RID, getAcntRid());
            } else {
                comMSG.dispErrorDialog(
                        "Acnt rid is null!!!");
                return;
            }
            if("".equals(StringUtil.nvl(getIssueType())) == false) {
                param.put("initIssueType", getIssueType());
            }
            String uiValue = (String)this.getUICValue();
            issue = new VWJIssueArea();
            param.put("issueIdValue", uiValue);
            issue.setParameter(param);
            InputInfo checkInputInfo = new InputInfo();
            checkInputInfo.setActionId(this.checkIssueAction);
            //判断是更新还是新增
            if (uiValue != null && !uiValue.equals("")) {
                checkInputInfo.setInputObj("issueId", uiValue);
                ReturnInfo checkReturnInfo = accessData(checkInputInfo);
                IDtoIssue dtoIssue = (IDtoIssue) checkReturnInfo.getReturnObj(
                        "dtoIssue");
                if (dtoIssue.getRid() != null && !dtoIssue.getRid().equals("")) {
                    issue.initUI("update");
                    enterTitle = "Edit Issue";
                } else {
                    issue.initUI("add");
                    enterTitle = "Add Issue";
                }
            } else {
                issue.initUI("add");
                enterTitle = "Add Issue";
            }
            issue.setPreferredSize(new Dimension(550, 380));
            java.awt.Frame owner = this.getParentWindow();
            VWJPopupEditor poputEditor = new VWJPopupEditor(owner, enterTitle,
                    issue, this);
            poputEditor.showConfirm();
            isEditing = false;
        }
    }

    protected java.awt.Frame getParentWindow() {
        java.awt.Container c = this.getParent();

        while (c != null) {
            if (c instanceof java.awt.Frame) {
                return (java.awt.Frame) c;
            }

            c = c.getParent();
        }

        return null;
    }

    public boolean onClickOK(ActionEvent actionEvent) {
        boolean checkOk = checkReq();
        if (!checkOk) {
            return false;
        }
        InputInfo inputInfo = new InputInfo();
        String uiValue = (String)this.getUICValue();
        InputInfo checkInputInfo = new InputInfo();
        checkInputInfo.setActionId(this.checkIssueAction);

        //判断是更新操作还是新增
        if (uiValue != null && !uiValue.equals("")) {
            checkInputInfo.setInputObj("issueId", uiValue);
            ReturnInfo checkReturnInfo = accessData(checkInputInfo);
            IDtoIssue dtoIssue = (IDtoIssue) checkReturnInfo.getReturnObj(
                    "dtoIssue");
            if (dtoIssue.getRid() != null && !dtoIssue.getRid().equals("")) {
                inputInfo.setActionId(this.updateAction);
                dtoIssue.setCloseDate(this.issue.closeDate.getText());
                dtoIssue.setDescription(this.issue.description.getText());
                dtoIssue.setDueDate(this.issue.dueDate.getText());
                dtoIssue.setIssueName((String)this.issue.issueName.getUICValue());
                dtoIssue.setIssueStatus((String)this.issue.status.getUICValue());
                dtoIssue.setPrincipal((String)this.issue.principal.getUICValue());
                dtoIssue.setPriority((String)this.issue.priority.getUICValue());
                dtoIssue.setScope((String)this.issue.scope.getUICValue());
                inputInfo.setInputObj("dtoIssue", dtoIssue);
            } else {
                checkInputInfo.setInputObj("issueId",
                                           this.issue.issueId.getUICValue());
                checkReturnInfo = accessData(checkInputInfo);
                dtoIssue = (IDtoIssue) checkReturnInfo.getReturnObj("dtoIssue");
                if (dtoIssue.getRid() != null && !dtoIssue.getRid().equals("")) {
                    comMSG.dispErrorDialog("Issue ID duplicate!!!");
                    return false;
                }
                inputInfo.setActionId(this.actionId);
                dtoIssue = new DtoIssue();
                inputInfo.setActionId(this.actionId);
                dtoIssue.setAccountId(String.valueOf(this.acntRid));
                dtoIssue.setCloseDate(this.issue.closeDate.getText());
                dtoIssue.setDescription(this.issue.description.getText());
                dtoIssue.setDueDate(this.issue.dueDate.getText());
                dtoIssue.setFilleBy((String)this.issue.filledBy.getUICValue());
                dtoIssue.setFilleDate(this.issue.filledDate.getText());
                dtoIssue.setIssueId((String)this.issue.issueId.getUICValue());
                dtoIssue.setIssueName((String)this.issue.issueName.getUICValue());
                dtoIssue.setIssueStatus((String)this.issue.status.getUICValue());
                dtoIssue.setIssueType((String)this.issue.issueType.getUICValue());
                dtoIssue.setPrincipal((String)this.issue.principal.getUICValue());
                dtoIssue.setPriority((String)this.issue.priority.getUICValue());
                dtoIssue.setScope((String)this.issue.scope.getUICValue());
                inputInfo.setInputObj("dtoIssue", dtoIssue);
            }
        } else {
            checkInputInfo.setInputObj("issueId",
                                       this.issue.issueId.getUICValue());
            ReturnInfo checkReturnInfo = accessData(checkInputInfo);
            IDtoIssue dtoIssue = (IDtoIssue) checkReturnInfo.getReturnObj(
                    "dtoIssue");
            if (dtoIssue.getRid() != null && !dtoIssue.getRid().equals("")) {
                comMSG.dispErrorDialog("Issue ID duplicate!!!");
                return false;
            }
            dtoIssue = new DtoIssue();
            inputInfo.setActionId(this.actionId);
            dtoIssue.setAccountId(String.valueOf(this.acntRid));
            dtoIssue.setCloseDate(this.issue.closeDate.getText());
            dtoIssue.setDescription(this.issue.description.getText());
            dtoIssue.setDueDate(this.issue.dueDate.getText());
            dtoIssue.setFilleBy((String)this.issue.filledBy.getUICValue());
            dtoIssue.setFilleDate(this.issue.filledDate.getText());
            dtoIssue.setIssueId((String)this.issue.issueId.getUICValue());
            dtoIssue.setIssueName((String)this.issue.issueName.getUICValue());
            dtoIssue.setIssueStatus((String)this.issue.status.getUICValue());
            dtoIssue.setIssueType((String)this.issue.issueType.getUICValue());
            dtoIssue.setPrincipal((String)this.issue.principal.getUICValue());
            dtoIssue.setPriority((String)this.issue.priority.getUICValue());
            dtoIssue.setScope((String)this.issue.scope.getUICValue());
            inputInfo.setInputObj("dtoIssue", dtoIssue);
        }
        inputInfo.setInputObj("isMail", issue.isMail.isSelected());
        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == false) {
            String issueId = (String) returnInfo.getReturnObj("issueId");
            this.setUICValue(issueId);
        } else {
            comMSG.dispErrorDialog(
                    "Error in saving issue!!!");
            return false;
        }
        return true;
    }

    public boolean onClickCancel(ActionEvent actionEvent) {
        return true;
    }

    private boolean checkReq() {
        String issueId = (String)this.issue.issueId.getUICValue();
        String issueName = (String)this.issue.issueName.getUICValue();
        if (issueId == null || issueId.equals("")) {
            comMSG.dispErrorDialog("Please fill Issue ID!!!");
            return false;
        }
        if (issueName == null || issueName.equals("")) {
            comMSG.dispErrorDialog("Please fill Issue Name!!!");
            return false;
        }
        return true;
    }

    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;
    }

    public void setIssueType(String issueType) {
        this.initIssueType = issueType;
    }

    public Long getAcntRid() {
        return acntRid;
    }

    public String getIssueType() {
        return initIssueType;
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

    public IVWComponent duplicate() {
        VWJIssue comp = new VWJIssue();
        comp.setAcntRid(this.getAcntRid());
        comp.setIssueType(this.getIssueType());
        comp.setName(this.getName());
        comp.setDtoClass(this.getDtoClass());
        comp.setFont(this.getFont());
        return comp;
    }

    public boolean isEditing() {
        return isEditing;
    }

}
