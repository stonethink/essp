package server.essp.issue.issue.discuss.logic;


import server.framework.logic.AbstractBusinessLogic;
import java.util.List;
import net.sf.hibernate.*;
import server.essp.issue.issue.discuss.viewbean.VbIssueDiscussList;
import server.framework.common.*;
import server.essp.issue.common.logic.AbstractISSUELogic;
import db.essp.issue.IssueDiscussTitle;
import server.essp.issue.issue.discuss.viewbean.VbIssueDiscussTitle;
import java.util.Set;
import java.util.ArrayList;
import db.essp.issue.IssueDiscussReply;
import java.util.Iterator;
import server.essp.issue.common.logic.LgDownload;
import c2s.dto.FileInfo;
import server.essp.issue.common.logic.LgAccount;
import server.essp.issue.issue.discuss.viewbean.VbIssueDiscussReply;
import com.wits.util.comDate;


public class LgIssueDiscussList extends AbstractISSUELogic {
    private VbIssueDiscussList listViewBean = new VbIssueDiscussList();
    public LgIssueDiscussList() {
    }

    /**
     * ����issue discuss ҳ����ʾ��������
     * 1.Issue��Ӧ��IssueDiscussTitle��IssueDiscussReply
     * 2.�����û��Ƿ�Ϊ�����˻������ж��Ƿ���Ȩ���޸�ҳ��
     *          userId�Ǹ����ˣ������߻��ύ�ߣ������޸�
     * @param issueCode Long
     * @return List
     */
    public VbIssueDiscussList getVbIssueDiscussList(java.lang.Long issueRid) {

        return null;
    }

    /**
     * ����issueRid��ѯ��Ӧ��IssueDiscussTitle��IssueDiscussReply
     * title���ύ������С����˳������,reply���ύ���ڴ�С����˳������
     * ��ѯ������
     *    from IssueDiscussTitle as title
     *    where title.issue.rid=:issueCode order by title.filledDate
     * Attention:IssueDicussTitle.hbm.xml�м���Ԫ��issueDiscussRepliesȥ����lazy="true"����
     * ��ѯIssueDiscussTitle���Զ������������Ӧ��IssueDiscussReply����
     * @param issueCode Long
     * @return List
     */
    public List list(java.lang.Long issueRid) {
        List result = null;
        try {
            //��ѯ���е�IssueDicuss
            Session session = this.getDbAccessor().getSession();
            Query query = session.createQuery(
                "from IssueDiscussTitle as title " +
                "where title.issue.rid=:issueRid " +
                "order by title.rid");
            query.setLong("issueRid", issueRid.longValue());
            // query.setString("rst", Constant.RST_NORMAL);
            List dbResult = query.list();
            result = new ArrayList();
            LgAccount accountLogic = new LgAccount();
            for (int i = 0; i < dbResult.size(); i++) {
                IssueDiscussTitle issueDiscussTitle =
                    (IssueDiscussTitle) dbResult.get(i);
                String accountRid = issueDiscussTitle.getIssue().getAccountId().
                                    toString();
                String accountCode = accountLogic.getAccountId(accountRid);

                VbIssueDiscussTitle vbIssueDiscussTitle =
                    new VbIssueDiscussTitle();
                /**
                 * get download url
                 */
                if (issueDiscussTitle.getAttachmentId() != null) {
                    LgDownload downloadLogic = new LgDownload();
                    FileInfo fileInfo = new FileInfo();
                    fileInfo.setAccountcode(accountCode);
                    fileInfo.setId(issueDiscussTitle.getAttachmentId());
                    fileInfo.setFilename(issueDiscussTitle.getAttachment());
                    vbIssueDiscussTitle.setAttachment(downloadLogic.
                        getDownloadUrl(fileInfo));
                } else {
                    vbIssueDiscussTitle.setAttachment("");
                }
                vbIssueDiscussTitle.setRid((issueDiscussTitle.getRid()).
                                           toString());
                vbIssueDiscussTitle.setIssueRid(
                    (issueDiscussTitle.getIssue().getRid()).toString());
                //check title
                String title = issueDiscussTitle.getTitle();
                if (title == null || title.equalsIgnoreCase("null")) {
                    vbIssueDiscussTitle.setTitle("");
                } else {
                    vbIssueDiscussTitle.setTitle(issueDiscussTitle.getTitle());
                }
                String description = issueDiscussTitle.getDescription();
                //check description
                if (description == null || description.equalsIgnoreCase("null")) {
                    description = "";
                }
                vbIssueDiscussTitle.setDescription(description);
                vbIssueDiscussTitle.setFilledDate((issueDiscussTitle.
                    getFilledDate()).toString());
                vbIssueDiscussTitle.setFilledBy(issueDiscussTitle.getFilledBy());
                vbIssueDiscussTitle.setFilledByScope(issueDiscussTitle.getFilledByScope());
                String attachment_desc = issueDiscussTitle.getAttachmentDesc();
                //check AttachmentDesc
                if (attachment_desc == null ||
                    attachment_desc.equalsIgnoreCase("null")) {
                    attachment_desc = "";
                }
                if (issueDiscussTitle.getRemark() != null) {
                    vbIssueDiscussTitle.setRemark(issueDiscussTitle.getRemark());
                }
                vbIssueDiscussTitle.setAttachmentDesc(attachment_desc);
                //set replys list with DiscussReplies Set
                Set ReplySet = issueDiscussTitle.getIssueDiscussReplies();
                Iterator iterator = ReplySet.iterator();
                List ReplyList = new ArrayList();

                while (iterator.hasNext()) {
                    VbIssueDiscussReply vbIssueDisRe = new VbIssueDiscussReply();
                    IssueDiscussReply issueDiscussReply =
                        (IssueDiscussReply) iterator.next();
                    if (issueDiscussReply.getAttachmentId() != null) {
                        LgDownload downloadLogic = new LgDownload();
                        FileInfo fileInfo = new FileInfo();
                        fileInfo.setAccountcode(accountCode);
                        fileInfo.setId(issueDiscussReply.getAttachmentId());
                        fileInfo.setFilename(issueDiscussReply.getAttachment());
                        vbIssueDisRe.setAttachment(downloadLogic.getDownloadUrl(
                            fileInfo));
//                         issueDiscussReply.setAttachment(downloadLogic.getDownloadUrl(fileInfo));
                    } else {
//                         issueDiscussReply.setAttachment("");
                        vbIssueDisRe.setAttachment("");
                    }
                    //check title of reply
                    if (issueDiscussReply.getTitle() == null ||
                        issueDiscussReply.getTitle().equalsIgnoreCase("null")) {
//                          issueDiscussReply.setTitle("");
                        vbIssueDisRe.setTitle("");
                    } else {
                        vbIssueDisRe.setTitle(issueDiscussReply.getTitle());
                    }
                    //check AttachmentDesc of reply
                    if (issueDiscussReply.getAttachmentDesc() == null ||
                        issueDiscussReply.getAttachmentDesc().equalsIgnoreCase(
                            "null")) {
//                        issueDiscussReply.setAttachmentDesc("");
                        vbIssueDisRe.setAttachmentDesc("");
                    } else {
                        vbIssueDisRe.setAttachmentDesc(issueDiscussReply.
                            getAttachmentDesc());
                    }
                    //check Description of reply
                    if (issueDiscussReply.getDescription() == null ||
                        issueDiscussReply.getDescription().equalsIgnoreCase(
                            "null")) {
//                         issueDiscussReply.setDescription("");
                        vbIssueDisRe.setDescription("");
                    } else {
                        vbIssueDisRe.setDescription(issueDiscussReply.
                            getDescription());
                    }
                    vbIssueDisRe.setFilledBy(issueDiscussReply.getFilledBy());
                    vbIssueDisRe.setFilledByScope(issueDiscussReply.getFilledByScope());
                    vbIssueDisRe.setFilledDate(comDate.dateToString(
                        issueDiscussReply.getFilledDate()));
                    if (issueDiscussReply.getRemark() != null) {
                        vbIssueDisRe.setRemark(issueDiscussReply.getRemark());
                    }
                    vbIssueDisRe.setTitleId(issueDiscussTitle.getRid().toString());
//                    ReplyList.add(issueDiscussReply);
                    vbIssueDisRe.setAccountRid(accountRid);
                    vbIssueDisRe.setRid(issueDiscussReply.getRid().toString());
                    ReplyList.add(vbIssueDisRe);
                }
                vbIssueDiscussTitle.setReplys(ReplyList);
                //save vbIssueDiscussTitle
                result.add(vbIssueDiscussTitle);
            }
            return result;
        } catch (Exception ex) {
            log.error(ex);
            ex.printStackTrace();
            throw new BusinessException("issue.discuss.list.exception",
                                        "List issueDiscuss  error!");
        }

    }
}
