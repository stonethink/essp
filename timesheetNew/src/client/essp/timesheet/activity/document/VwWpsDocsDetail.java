package client.essp.timesheet.activity.document;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;

import javax.swing.JButton;

import c2s.dto.DtoUtil;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.timesheet.activity.DtoActivityKey;
import c2s.essp.timesheet.activity.DtoDocument;
import c2s.essp.timesheet.activity.DtoDocumentDetail;
import client.essp.common.view.VWGeneralWorkArea;
import client.framework.view.vwcomp.*;

/**
 * <p>Title:VwWpsDocsDetail </p>
 *
 * <p>Description:Document详细信息卡片 </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author not attributable
 * @version 1.0
 */
public class VwWpsDocsDetail extends VWGeneralWorkArea implements  IVWPopupEditorEvent{
    static final String actionIdInit = "FTSDocumentDetail";
    private VWJLabel titleLabel = new VWJLabel();
    private VWJText inputTitle = new VWJText();
    private VWJLabel refNoLabel = new VWJLabel();
    private VWJText inputRefNo = new VWJText();
    private VWJLabel locationLabel = new VWJLabel();
    private VWJText inputLocation = new VWJText();
    private VWJLabel versionLabel = new VWJLabel();
    private VWJText inputVersion = new VWJText();
    private VWJLabel authorLabel = new VWJLabel();
    private VWJText inputAuthor = new VWJText();
    private VWJCheckBox inputDeliverable= new VWJCheckBox();
    private VWJLabel deliverableLabel = new VWJLabel();
    private VWJDate inputRevisionDate = new VWJDate();
    private VWJLabel revisionDateLabel = new VWJLabel();
    private VWJLabel statusLabel = new VWJLabel();
    private VWJText inputStatus = new VWJText();
    private VWJLabel descriptionLabel = new VWJLabel();
    private VWJEditorPane inputDescription = new VWJEditorPane();
    private JButton launchButton = new JButton();
    DtoDocument dtoDocument = new DtoDocument();
    DtoDocumentDetail dtoDetail = new DtoDocumentDetail();
    private Long documentRid = null;

    public VwWpsDocsDetail() {
        try {
            jbInit();
            setUIName();
            setStatus();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   //初始化
    private void jbInit() throws Exception {
        this.setPreferredSize(new Dimension(650, 300));
        this.setLayout(null);
        titleLabel.setBounds(new Rectangle(2, 12, 75, 20));
        titleLabel.setText("rsid.common.title");
        inputTitle.setBounds(new Rectangle(75, 10, 209, 20));

        refNoLabel.setBounds(new Rectangle(338, 12, 65, 20));
        refNoLabel.setText("rsid.timesheet.refNo");
        inputRefNo.setBounds(new Rectangle(408, 11, 142, 20));

        locationLabel.setBounds(new Rectangle(2, 52, 75, 20));
        locationLabel.setText("rsid.timesheet.location");
        inputLocation.setBounds(new Rectangle(75, 52, 209, 20));

        launchButton.setBounds(new Rectangle(336, 52, 100, 20));
        launchButton.setText("rsid.timesheet.launch");

        versionLabel.setBounds(new Rectangle(2, 99, 105, 20));
        versionLabel.setText("rsid.common.version");
        inputVersion.setBounds(new Rectangle(107, 102, 126, 19));
        
        authorLabel.setBounds(new Rectangle(250, 101, 62, 16));
        authorLabel.setText("rsid.common.author");
        inputAuthor.setBounds(new Rectangle(310, 100, 120, 20));

        deliverableLabel.setBounds(new Rectangle(506, 101, 90, 20));
        deliverableLabel.setText("rsid.timesheet.deliverable");
        inputDeliverable.setBounds(new Rectangle(480, 100, 25, 20));

        revisionDateLabel.setBounds(new Rectangle(2, 151, 105, 20));
        revisionDateLabel.setText("rsid.timesheet.revisionDate");
        inputRevisionDate.setBounds(new Rectangle(107, 152, 124, 20));

        statusLabel.setBounds(new Rectangle(250, 149, 60, 20));
        statusLabel.setText("rsid.common.status");
        inputStatus.setBounds(new Rectangle(310, 148, 119, 20));

        descriptionLabel.setBounds(new Rectangle(2, 215, 105, 20));
        descriptionLabel.setText("rsid.common.description");
        inputDescription.setBounds(new Rectangle(107, 206, 467, 81));
        

        this.add(inputTitle);
        this.add(locationLabel);
        this.add(inputLocation);
        this.add(versionLabel);
        this.add(authorLabel);
        this.add(inputDeliverable);
        this.add(deliverableLabel);
        this.add(inputAuthor);
        this.add(inputRevisionDate);
        this.add(inputStatus);
        this.add(descriptionLabel);
        this.add(inputDescription);
        this.add(inputVersion);
        this.add(statusLabel);
        this.add(inputRefNo);
        this.add(refNoLabel);
        this.add(launchButton);
        this.add(titleLabel);
        this.add(revisionDateLabel);

    }
   //设置状态
    private void setStatus(){
        inputTitle.setEnabled(false);
        inputRefNo.setEnabled(false);
        inputLocation.setEnabled(false);
        inputVersion.setEnabled(false);
        inputAuthor.setEnabled(false);
        inputDeliverable.setEnabled(false);
        inputRevisionDate.setEnabled(false);
        inputStatus.setEnabled(false);
        inputDescription.setEnabled(false);
        launchButton.setEnabled(false);
    }
    //设置对应的属性
    private void setUIName(){
      inputTitle.setName("title");
      inputRefNo.setName("refNo");
      inputLocation.setName("location");
      inputVersion.setName("version");
      inputAuthor.setName("author");
      inputDeliverable.setName("deliverable");
      inputRevisionDate.setName("revisionDate");
      inputStatus.setName("status");
      inputDescription.setName("description");
  }

   //参数设置
    public void setParameter(DtoDocument dtoDoc) {
           super.setParameter(null);
           this.dtoDocument = dtoDoc;
           DtoUtil.copyBeanToBean(dtoDocument, dtoDoc);
           if (this.dtoDocument == null) {
               this.dtoDocument = new DtoDocument();
           } else {
               this.documentRid = this.dtoDocument.getDocumentRid();
           }
     }
    //重置
    public void resetUI() {
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdInit);
        inputInfo.setInputObj(DtoActivityKey.DOCUMENT_RID, documentRid);
        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == true) {
            return;
        }
        dtoDetail = (DtoDocumentDetail) returnInfo.getReturnObj(DtoActivityKey.DTO_DOCUMENT_DETAIL);
        if(dtoDetail.getLocation()!=null && !dtoDetail.getLocation().trim().equals("")){
            launchButton.setEnabled(true);
        }
        VWUtil.bindDto2UI(dtoDetail, this);
    }
    //OK按钮事件
    public boolean onClickOK(ActionEvent actionEvent) {
        return true;
    }
    //Cancel按钮事件
    public boolean onClickCancel(ActionEvent actionEvent) {
        return true;
    }
}
