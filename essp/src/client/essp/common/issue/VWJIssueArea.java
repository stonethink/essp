package client.essp.common.issue;

import client.essp.common.view.*;
import java.awt.Rectangle;
import com.wits.util.TestPanel;
import javax.swing.SwingConstants;
import client.framework.view.vwcomp.VWJLabel;
import client.framework.view.vwcomp.VWJText;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.vwcomp.VWJTextArea;
import client.essp.common.humanAllocate.HrAllocate;
import client.essp.common.humanAllocate.VWJHrAllocateButton;
import java.awt.*;
import client.framework.view.vwcomp.VWJComboBox;
import client.framework.model.VMComboBox;
import c2s.dto.ReturnInfo;
import c2s.dto.InputInfo;
import java.util.Vector;
import com.wits.util.Parameter;
import c2s.essp.common.code.DtoKey;
import com.wits.util.comDate;
import java.util.Date;
import c2s.essp.common.issue.IDtoIssue;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import client.essp.common.loginId.VWJLoginId;
import javax.swing.JCheckBox;
import client.framework.view.vwcomp.VWJCheckBox;

public class VWJIssueArea extends VWGeneralWorkArea {
    private static final String actionId = "F00IssueAddPreAction";
    private static final String selectTypeAction = "F00IssueSelectType";
    private static final String updatePreAction = "F00IssueUpdatePreAction";
    private Long acntRid;
    private String initIssueType;
    private String issueIdV;
    private boolean typeInitFlag = true;
    private boolean statusInitFlag = true;

    public VWJIssueArea() {
      try {
          jbInit();
          addUICEvent();
      } catch (Exception ex) {
          ex.printStackTrace();
      }
  }
  //添加下拉框事件
  private void addUICEvent(){
      this.issueType.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                if(!typeInitFlag) {
                    actionTypeChanged(e);
                }
            }
        });
      this.status.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                if (!statusInitFlag) {
                    String statusValue = (String) status.getUICValue();
                    if (statusValue.equals("Closed")) {
                        closeDate.setEnabled(true);
                        closeDate.setCanSelect(true);
                        closeDate.setUICValue(comDate.dateToString(new Date(),
                                "yyyy/MM/dd"));
                        closeDate.setText(comDate.dateToString(new Date(),
                                "yyyy/MM/dd"));
                    } else {
                        closeDate.setEnabled(false);
                        closeDate.setCanSelect(false);
                        closeDate.setUICValue("");
                        closeDate.setText("");
                    }
                }
            }
        });

  }
  //issueType下拉框事件实现
  public void actionTypeChanged(ActionEvent e){
      InputInfo inputInfo = new InputInfo();
      inputInfo.setActionId( this.selectTypeAction);
      inputInfo.setInputObj("acntRid", this.acntRid);
      inputInfo.setInputObj("issueType", issueType.getUICValue());
      ReturnInfo returnInfo = accessData(inputInfo);

      Vector issuePriority = null;
      Vector issueScope = null;
      Vector issueStatus = null;
      String issueIdValue = null;
      if(returnInfo.isError()==false){
          issuePriority = (Vector)returnInfo.getReturnObj("issuePriority");
          issueScope = (Vector)returnInfo.getReturnObj("issueScope");
          issueStatus = (Vector)returnInfo.getReturnObj("issueStatus");
          issueIdValue = (String)returnInfo.getReturnObj("issueIdValue");
      }
            if(issuePriority==null){
               issuePriority = new Vector();
            }
            VMComboBox vmIssuePriority = new VMComboBox(issuePriority);
            priority.setModel(vmIssuePriority);


            if(issueScope==null){
                issueScope = new Vector();
            }
            VMComboBox vmIssueScope = new VMComboBox(issueScope);
            scope.setModel(vmIssueScope);


            if(issueStatus==null){
                issueStatus = new Vector();
            }
            VMComboBox vmIssueStatus = new VMComboBox(issueStatus);
            status.setModel(vmIssueStatus);


            issueId.setUICValue(issueIdValue);
            issueId.setText(issueIdValue);

            String statusValue = (String)status.getUICValue();
                if(statusValue.equals("Closed")){
                   closeDate.setEnabled(true);
                   closeDate.setCanSelect(true);
                   closeDate.setUICValue(comDate.dateToString(new Date(), "yyyy/MM/dd"));
                   closeDate.setText(comDate.dateToString(new Date(), "yyyy/MM/dd"));
                } else {
                   closeDate.setEnabled(false);
                   closeDate.setCanSelect(false);
                   closeDate.setUICValue("");
                   closeDate.setText("");
                }


   }
  //初始化各控件值
  public void initUI(String type) {
      //判断是新增还是更新
      if(type.equals("add")){
          addPre();
      } else if(type.equals("update")){
          updatePre();
      }
    }
    //更新时初试化界面
    private void updatePre(){
                 InputInfo inputInfo = new InputInfo();
                 inputInfo.setActionId( this.updatePreAction );
                 inputInfo.setInputObj("acntRid", this.acntRid);
                 inputInfo.setInputObj("issueId", this.issueIdV);
                 ReturnInfo returnInfo = accessData(inputInfo);
                 Vector issueTypeItems = null;
                 Vector issuePriority = null;
                 Vector issueScope = null;
                 Vector issueStatus = null;
                 IDtoIssue dtoIssue = null;
                 String accountId = "";
                 if(returnInfo.isError()==false){
                     issueTypeItems = (Vector) returnInfo.getReturnObj(
                             "issueTypeItems");
                     issuePriority = (Vector) returnInfo.getReturnObj(
                             "issuePriority");
                     issueScope = (Vector) returnInfo.getReturnObj("issueScope");
                     issueStatus = (Vector) returnInfo.getReturnObj(
                             "issueStatus");
                     dtoIssue = (IDtoIssue)returnInfo.getReturnObj("dtoIssue");
                     accountId = (String)returnInfo.getReturnObj("accountId");
                 }
                 if (issueTypeItems == null) {
                    issueTypeItems = new Vector();
                }
                VMComboBox vmIssueType = new VMComboBox(issueTypeItems);
                issueType.setModel(vmIssueType);
                issueType.setUICValue(dtoIssue.getIssueType());
                issueType.setEnabled(false);

                if(issuePriority==null){
                   issuePriority = new Vector();
                }
                VMComboBox vmIssuePriority = new VMComboBox(issuePriority);
                priority.setModel(vmIssuePriority);
                priority.setUICValue(dtoIssue.getPriority());

                if(issueScope==null){
                    issueScope = new Vector();
                }
                VMComboBox vmIssueScope = new VMComboBox(issueScope);
                scope.setModel(vmIssueScope);
                scope.setUICValue(dtoIssue.getScope());

                if(issueStatus==null){
                    issueStatus = new Vector();
                }
                VMComboBox vmIssueStatus = new VMComboBox(issueStatus);
                status.setModel(vmIssueStatus);
                status.setUICValue(dtoIssue.getIssueStatus());

                issueName.setUICValue(dtoIssue.getIssueName());
                issueName.setText(dtoIssue.getIssueName());

                this.account.setUICValue(accountId);
                this.account.setText(accountId);

                filledDate.setUICValue(dtoIssue.getFilleDate());
                filledDate.setText(dtoIssue.getFilleDate());

                filledBy.setUICValue(dtoIssue.getFilleBy());
                filledBy.setText(dtoIssue.getFilleBy());

                principal.setUICValue(dtoIssue.getPrincipal());

                dueDate.setUICValue(dtoIssue.getDueDate());
                dueDate.setText(dtoIssue.getDueDate());

                description.setUICValue(dtoIssue.getDescription());
                description.setText(dtoIssue.getDescription());

                issueId.setUICValue(dtoIssue.getIssueId());
                issueId.setText(dtoIssue.getIssueId());
                issueId.setEnabled(false);

                closeDate.setUICValue(dtoIssue.getCloseDate());
                closeDate.setText(dtoIssue.getCloseDate());

                issueTitle.setText("Edit Issue");

    }
    //新增初始化界面
    private void addPre(){
            //获取初始的数据，包括： Combo{issueType}, Combo{priority}, Combo{scope}, Combo{status}
                  InputInfo inputInfo = new InputInfo();
                  inputInfo.setActionId( this.actionId );
                  inputInfo.setInputObj("acntRid", this.acntRid);
                  inputInfo.setInputObj("issueType", initIssueType);
                  ReturnInfo returnInfo = accessData(inputInfo);

                  Vector issueTypeItems = null;
                  Vector issuePriority = null;
                  Vector issueScope = null;
                  Vector issueStatus = null;
                  String issueIdValue = "";
                  String accountId = "";
                  String userName = "";
                  String userLoginId = "";
                  String principalLoginId = "";
                  if(returnInfo.isError()==false){
                       issueTypeItems = (Vector)returnInfo.getReturnObj("issueTypeItems");
                       issuePriority = (Vector)returnInfo.getReturnObj("issuePriority");
                       issueScope = (Vector)returnInfo.getReturnObj("issueScope");
                       issueStatus = (Vector)returnInfo.getReturnObj("issueStatus");
                       issueIdValue = (String)returnInfo.getReturnObj("issueIdValue");
                       accountId = (String)returnInfo.getReturnObj("accountId");
                       userName = (String)returnInfo.getReturnObj("userName");
                       userLoginId = (String)returnInfo.getReturnObj("userLoginId");
                       principalLoginId = (String)returnInfo.getReturnObj("manager");
                  }

                  if (issueTypeItems == null) {
                      issueTypeItems = new Vector();
                  }
                  VMComboBox vmIssueType = new VMComboBox(issueTypeItems);
                  typeInitFlag = true;
                  issueType.setModel(vmIssueType);
                  if(initIssueType != null) {
                      issueType.setUICValue(initIssueType);
                  }
                  typeInitFlag = false;

                  if(issuePriority==null){
                     issuePriority = new Vector();
                  }
                  VMComboBox vmIssuePriority = new VMComboBox(issuePriority);
                  priority.setModel(vmIssuePriority);


                  if(issueScope==null){
                      issueScope = new Vector();
                  }
                  VMComboBox vmIssueScope = new VMComboBox(issueScope);
                  scope.setModel(vmIssueScope);

                  if(issueStatus==null){
                      issueStatus = new Vector();
                  }
                  VMComboBox vmIssueStatus = new VMComboBox(issueStatus);
                  statusInitFlag = true;
                  status.setModel(vmIssueStatus);
                  statusInitFlag = false;

                  issueId.setUICValue(issueIdValue);
                  issueId.setText(issueIdValue);

                  this.account.setUICValue(accountId);
                  this.account.setText(accountId);

                  filledDate.setUICValue(comDate.dateToString(new Date(), "yyyy/MM/dd"));
                  filledDate.setText(comDate.dateToString(new Date(), "yyyy/MM/dd"));

                  filledBy.setUICValue(userLoginId);
                  filledBy.setText(userName);
                  principal.setUICValue(principalLoginId);

                  issueTitle.setText("Add Issue");
    }
    //设置传递的参数
    public void setParameter(Parameter param) {
      this.acntRid = (Long)param.get(DtoKey.ACNT_RID);
      this.initIssueType = (String)param.get("initIssueType");
      this.issueIdV = (String)param.get("issueIdValue");

    }
  //初试界面布局
  private void jbInit() throws Exception {
      this.setLayout(null);
      issueTitle.setHorizontalAlignment(SwingConstants.CENTER);
      issueTitle.setBounds(new Rectangle(219, 14, 120, 20));
      //第一行
      accountLabel.setText("Account");
      accountLabel.setBounds(new Rectangle(31, 54, 86, 20));

      account.setText("");
      account.setBounds(new Rectangle(104, 54, 162, 20));
      account.setEnabled(false);

      issueTypeLable.setText("Issue Type");
      issueTypeLable.setBounds(new Rectangle(282, 54, 86, 20));

      issueType.setBounds(new Rectangle(351, 54, 162, 20));
      //第二行
      scopeLable.setText("Scope");
      scopeLable.setBounds(new Rectangle(31, 88, 86, 20));

      scope.setBounds(new Rectangle(104, 88, 162, 20));

      priorityLable.setText("Priority");
      priorityLable.setBounds(new Rectangle(282, 88, 86, 20));

      priority.setBounds(new Rectangle(351, 88, 162, 20));
      //第三行
      issueIdLable.setText("Issue Id");
      issueIdLable.setBounds(new Rectangle(31, 122, 86, 20));

      issueId.setText("");
      issueId.setBounds(new Rectangle(104, 122, 162, 20));

      issueNameLable.setText("Issue Name");
      issueNameLable.setBounds(new Rectangle(282, 122, 86, 20));

      issueName.setText("");
      issueName.setBounds(new Rectangle(351, 122, 162, 20));
      //第四行
      descriptionLable.setText("Description");
      descriptionLable.setBounds(new Rectangle(31, 156, 86, 20));

      description.setText("");
      description.setBounds(new Rectangle(104, 156, 409, 70));
      //第五行
      filledByLable.setText("Filled By");
      filledByLable.setBounds(new Rectangle(282, 239, 86, 20));

      filledBy.setText("");
      filledBy.setBounds(new Rectangle(351, 239, 162, 20));
      filledBy.setEnabled(false);

      filledDateLable.setText("Filled Date");
      filledDateLable.setBounds(new Rectangle(31, 239, 86, 20));

      filledDate.setBounds(new Rectangle(104, 239, 162, 20));
      filledDate.setCanSelect(true);
      //第六行
      principalLable.setText("Principal");
      principalLable.setBounds(new Rectangle(31, 273, 86, 20));

      principal.setBounds(new Rectangle(104, 273, 162, 20));

      dueDateLable.setText("Due Date");
      dueDateLable.setBounds(new Rectangle(282, 273, 86, 20));

      dueDate.setBounds(new Rectangle(351, 273, 162, 20));
      dueDate.setCanSelect(true);
      //第七行
      statusLable.setText("Status");
      statusLable.setBounds(new Rectangle(31, 307, 86, 20));

      status.setBounds(new Rectangle(104, 307, 162, 20));

      closeDateLable.setText("Close Date");
      closeDateLable.setBounds(new Rectangle(282, 307, 97, 20));

      closeDate.setText("");
      closeDate.setBounds(new Rectangle(351, 307, 162, 20));
      closeDate.setCanSelect(false);
      closeDate.setEnabled(false);
      isMail.setBounds(new Rectangle(104, 338, 21, 24));
      isMail.setSelected(true);
      isMailLabel.setBounds(new Rectangle(31, 342, 59, 20));
      isMailLabel.setText("Is Mail");
      this.add(issueTitle);
      this.add(filledByLable);
      this.add(description);
      this.add(accountLabel);
      this.add(account);
      this.add(priorityLable);
      this.add(issueTypeLable);
      this.add(priority);
      this.add(issueType);
      this.add(status);
      this.add(closeDateLable);
      this.add(closeDate);
      this.add(dueDate);
      this.add(dueDateLable);
      this.add(principal);
      this.add(statusLable);
      this.add(principalLable);
      this.add(filledDate);
      this.add(filledDateLable);
      this.add(filledBy);
      this.add(scope);
      this.add(scopeLable);
      this.add(issueIdLable);
      this.add(issueId);
      this.add(issueName);
      this.add(issueNameLable);
      this.add(descriptionLable);
      this.add(isMail);
      this.add(isMailLabel);
    }

  VWJLabel issueTitle = new VWJLabel();
  VWJLabel accountLabel = new VWJLabel();
  VWJLabel issueTypeLable = new VWJLabel();
  VWJText account = new VWJText();
  VWJComboBox issueType = new VWJComboBox();
  VWJLabel filledDateLable = new VWJLabel();
  VWJDate filledDate = new VWJDate();
  VWJLabel priorityLable = new VWJLabel();
  VWJComboBox priority = new VWJComboBox();
  VWJLabel scopeLable = new VWJLabel();
  VWJLabel filledByLable = new VWJLabel();
  VWJComboBox scope = new VWJComboBox();
  VWJText filledBy = new VWJLoginId();
  VWJLabel issueIdLable = new VWJLabel();
  VWJLabel issueNameLable = new VWJLabel();
  VWJText issueId = new VWJText();
  VWJText issueName = new VWJText();
  VWJLabel descriptionLable = new VWJLabel();
  VWJTextArea description = new VWJTextArea();
  VWJLabel principalLable = new VWJLabel();
  VWJHrAllocateButton principal = new VWJHrAllocateButton( HrAllocate.ALLOC_SINGLE);
  VWJLabel dueDateLable = new VWJLabel();
  VWJDate dueDate = new VWJDate();
  VWJLabel statusLable = new VWJLabel();
  VWJComboBox status = new VWJComboBox();
  VWJLabel closeDateLable = new VWJLabel();
  VWJDate closeDate = new VWJDate();
  VWJCheckBox isMail = new VWJCheckBox();
  VWJLabel isMailLabel = new VWJLabel();

    public static void main(String[] arg) {
      TestPanel.show(new VWJIssueArea());
  }




}
