package server.essp.issue.listexport.logic;

import server.essp.issue.issue.form.AfIssueSearch;
import server.essp.issue.issue.logic.LgIssueList;
import java.util.List;
import server.essp.issue.common.logic.AbstractISSUELogic;
import db.essp.issue.Issue;
import java.util.ArrayList;
import server.essp.issue.listexport.viewbean.vbListExport;
import com.wits.util.comDate;
import java.util.HashMap;
import server.essp.issue.common.logic.LgDownload;
import c2s.dto.FileInfo;
import server.essp.issue.common.logic.LgAccount;
import server.essp.issue.issue.resolution.logic.LgIssueResolution;
import db.essp.issue.IssueResolution;
import server.essp.issue.issue.discuss.logic.LgIssueDiscussList;
import server.essp.issue.issue.discuss.viewbean.VbIssueDiscussTitle;
import db.essp.issue.IssueConclusion;
import server.essp.issue.issue.conclusion.logic.LgIssueConclusion;
import server.essp.issue.issue.discuss.viewbean.VbIssueDiscussReply;
import server.essp.pms.account.logic.LgAccountUtilImpl;
import c2s.essp.common.account.IDtoAccount;
import c2s.dto.DtoTreeNode;
import c2s.dto.ITreeNode;
import java.util.Date;

public class lgGetExportData extends AbstractISSUELogic {
    public lgGetExportData() {
    }

    public HashMap getListExportDate(AfIssueSearch searchForm,
                                     String serverRoot) {
        LgIssueList lgil = new LgIssueList();
        List list = lgil.listAllForExport(searchForm);
        HashMap result = new HashMap();
        ArrayList destList = new ArrayList();
        String accountId = "NOTHISACCOUNT";

        LgIssueResolution lgir = new LgIssueResolution();
        LgIssueDiscussList lgidc = new LgIssueDiscussList();
        LgIssueConclusion lgic = new LgIssueConclusion();

        IssueResolution resolution;
        VbIssueDiscussTitle discTitle;
        VbIssueDiscussReply issueDiscussReply;
        IssueConclusion issueCon;

        for (int i = 0; i < list.size(); i++) {
            Issue issue = (Issue) list.get(i);
            vbListExport vble = new vbListExport();

            //ridΪ����Ϣ����֮����
            Long issueRid = issue.getRid();

            //��ø����ĵ�ַ
            String attURL = "";
            String issueIdContent = issue.getIssueId();
            if (issue.getAttachmentId() != null) {
                LgDownload downloadLogic = new LgDownload();
                FileInfo fileInfo = new FileInfo();
                fileInfo.setId(issue.getAttachmentId());
                String accountRid = issue.getAccountId().toString();
                LgAccount accountLogic = new LgAccount();
                String accountCode = accountLogic.getAccountId(accountRid);
                fileInfo.setAccountcode(accountCode);
                fileInfo.setFilename(issue.getAttachment());
                attURL = downloadLogic.getDownloadUrl(fileInfo);
                vble.setIssueId(attURL);
                //�������ӵ�ַ
                issueIdContent =
                    "=HYPERLINK(\"" + serverRoot + attURL + "\",\"" +
                    issueIdContent +
                    "\")";
            }
            // System.out.println("~~~~~~~~~~" + issueIdContent);

            //��������׼��-----------------------
            vble.setIssueId(issueIdContent);
            vble.setIssueTitle(issue.getIssueName());
            vble.setDesc(issue.getDescription() == null ? "" :
                         issue.getDescription());
            vble.setIssueType(issue.getIssueType());
            vble.setPriority(issue.getPriority());
            vble.setFilledBy(issue.getFilleBy());
            vble.setFilledDate(comDate.dateToString(issue.getFilleDate()));
            vble.setDueDate(comDate.dateToString(issue.getDueDate()));
            vble.setPrincipal(issue.getPrincipal());
            vble.setFilledBy(issue.getFilleBy());
            vble.setStatus(issue.getIssueStatus());

            //A&A��Ƭ�е�����
            resolution = lgir.get(issueRid);
            if (resolution != null) {
                vble.setResolution(resolution.getResolution() == null ? "" :
                                   resolution.getResolution());
                vble.setImpactLevel(resolution.getRiskLevel() == null ? "" :
                                    resolution.getRiskLevel().toString());
            } else {
                vble.setResolution("");
                vble.setImpactLevel("");
            }

            //�������������
            List discussList = lgidc.list(issueRid);
            int listSize = discussList.size();
            //�õ����һ��Topic
            if (listSize >= 1) {
//                System.out.println(discussList.get(listSize - 1).getClass().
//                                   toString());
                discTitle = (VbIssueDiscussTitle) discussList.get(listSize - 1);
                if (discTitle.getReplys() != null &&
                    discTitle.getReplys().size() > 0) {
                    List replyList = discTitle.getReplys();
                    issueDiscussReply = (VbIssueDiscussReply) replyList.get(
                        replyList.
                        size() - 1);
                    vble.setTopicDate(issueDiscussReply.getFilledDate());
                    vble.setTopicDesc(issueDiscussReply.getDescription() == null ?
                                      "" : issueDiscussReply.getDescription());
                    vble.setTopicWho(issueDiscussReply.getFilledBy());
                    vble.setTopicType("Response");
                    vble.setRemark(issueDiscussReply.getRemark() == null ? "" :
                                   issueDiscussReply.getRemark());
                    //�����и�����ReplyTitle
                    if (issueDiscussReply.getAttachment() != null &&
                        !issueDiscussReply.getAttachment().equals("")) {
                        String replyAttURL = issueDiscussReply.getAttachment();
                        String replyTitle = "=HYPERLINK(\"" + serverRoot +
                                            replyAttURL +
                                            "\",\"" +
                                            issueDiscussReply.getTitle() +
                                            "\")";
                        vble.setTopicTitle(replyTitle);
                    } else {
                        vble.setTopicTitle(issueDiscussReply.getTitle());
                    }

                } else {
                    vble.setTopicTitle(discTitle.getTitle());
                    vble.setTopicDate(discTitle.getFilledDate());
                    vble.setTopicDesc(discTitle.getDescription() == null ? "" :
                                      discTitle.getDescription());
                    vble.setTopicWho(discTitle.getFilledBy());
                    vble.setTopicType("Question");
                    vble.setRemark(discTitle.getRemark() == null ? "" :
                                   discTitle.getRemark());
                    //�����и�����TopicTitle
                    if (discTitle.getAttachment() != null &&
                        !discTitle.getAttachment().equals("")) {
                        String topicAttURL = discTitle.getAttachment();
                        String replyTitle = "=HYPERLINK(\"" + serverRoot +
                                            topicAttURL +
                                            "\",\"" +
                                            discTitle.getTitle() + "\")";
                        vble.setTopicTitle(replyTitle);
                    } else {
                        vble.setTopicTitle(discTitle.getTitle());
                    }

                }
            } else { //û�лظ�
                vble.setTopicTitle("");
                vble.setTopicDate("");
                vble.setTopicDesc("");
                vble.setTopicType("");
                vble.setTopicWho("");
            }

            //���۵�����
            issueCon = lgic.get(issueRid);
            if (issueCon != null) {
                vble.setSolvedDesc(issueCon.getSolvedDescription() == null ? "" :
                                   issueCon.getSolvedDescription());
                vble.setInstructionOfClosure(issueCon.getInstructionClosure() == null ?
                                             "" :
                                             issueCon.getInstructionClosure());
            } else {
                vble.setSolvedDesc("");
                vble.setInstructionOfClosure("");
            }
            if (issue.getIssueStatus().equals("Closed")) {
                if(issueCon.getConfirmDate()!=null) {
                    vble.setConfirmDate(comDate.dateToString(issueCon.
                        getConfirmDate()));
                    vble.setConfirmBy(issueCon.getConfirmBy());
                    //setOpenDays
                    int openDays =
                        comDate.getBetweenDays(issue.getFilleDate(),
                                               issueCon.getConfirmDate());
                    vble.setRound(String.valueOf(openDays));
                } else {
                    vble.setConfirmDate("No Date");
                    vble.setConfirmBy(issueCon.getConfirmBy());
                    //setOpenDays
                    int openDays =
                        comDate.getBetweenDays(issue.getFilleDate(),
                                               new Date());
                    vble.setRound(String.valueOf(openDays));
                }
            }else{
                vble.setConfirmDate("");
                vble.setConfirmBy("");
                //setOpenDays
                int openDays =
                    comDate.getBetweenDays(issue.getFilleDate(),new Date());
                vble.setRound(String.valueOf(openDays));

            }

            if (!issue.getAccountId().toString().equals(accountId)) {
                if (destList.size() > 0) {
                    IDtoAccount idtoa = new LgAccountUtilImpl().getAccountByRID(new
                        Long(accountId));
                    result.put(idtoa.getId(), destList);
                }
                //����֮���ٸ�������ؼ�¼����
                accountId = issue.getAccountId().toString();
                destList = new ArrayList();
                destList.add(vble);
            } else {
                destList.add(vble);
            }

            //�����һ��Ž�ȥ
            if ((i == (list.size() - 1)) && destList.size() > 0) {
                //    System.out.println("���һ���ID" + accountId);
                IDtoAccount idtoa = new LgAccountUtilImpl().getAccountByRID(new
                    Long(accountId));
                result.put(idtoa.getId(), destList);
            }

        }

        return result;
    }

    /*
     *����������ϸ������Ϣ�ı�������ÿ��ISSUE���з��飬����Ŀ���з�ҳ
     *@author:robin.zhang
     *@param:searchForm,serverRoot
     */
    public HashMap getListExportDateForDetail(AfIssueSearch searchForm,
                                              String serverRoot) {
        LgIssueList lgil = new LgIssueList();
        List list = lgil.listAllForExport(searchForm);
        HashMap result = new HashMap();
        DtoTreeNode rootTreePerSheet = null; // new DtoTreeNode(new Object()); //��һ���յĸ����Ϊÿ��SHEETҳ
        DtoTreeNode treeNodePerIssue = null; // = new DtoTreeNode(new Object()); //��Ϊÿһ��ISSUE�Ľ��
        String accountId = "NOTHISACCOUNT";

        LgIssueResolution lgir = new LgIssueResolution();
        LgIssueDiscussList lgidc = new LgIssueDiscussList();
        LgIssueConclusion lgic = new LgIssueConclusion();

        IssueResolution resolution;
        VbIssueDiscussTitle discTitle;
        VbIssueDiscussReply issueDiscussReply;
        IssueConclusion issueCon;

        for (int i = 0; i < list.size(); i++) {
            Issue issue = (Issue) list.get(i);
            vbListExport vble = new vbListExport();

            //ridΪ����Ϣ����֮����
            Long issueRid = issue.getRid();

            //��ø����ĵ�ַ
            String attURL = "";
            String issueIdContent = issue.getIssueId();
            if (issue.getAttachmentId() != null) {
                LgDownload downloadLogic = new LgDownload();
                FileInfo fileInfo = new FileInfo();
                fileInfo.setId(issue.getAttachmentId());
                String accountRid = issue.getAccountId().toString();
                LgAccount accountLogic = new LgAccount();
                String accountCode = accountLogic.getAccountId(accountRid);
                fileInfo.setAccountcode(accountCode);
                fileInfo.setFilename(issue.getAttachment());
                attURL = downloadLogic.getDownloadUrl(fileInfo);
                vble.setIssueId(attURL);
                //�������ӵ�ַ
                issueIdContent =
                    "=HYPERLINK(\"" + serverRoot + attURL + "\",\"" +
                    issueIdContent +
                    "\")";
            }
            // System.out.println("~~~~~~~~~~" + issueIdContent);

            //��������׼��-----------------------
            vble.setIssueId(issueIdContent);
            vble.setIssueTitle(issue.getIssueName());
            vble.setDesc(issue.getDescription() == null ? "" :
                         issue.getDescription());
            vble.setIssueType(issue.getIssueType());
            vble.setPriority(issue.getPriority());
            vble.setFilledBy(issue.getFilleBy());
            vble.setFilledDate(comDate.dateToString(issue.getFilleDate()));
            vble.setDueDate(comDate.dateToString(issue.getDueDate()));
            vble.setPrincipal(issue.getPrincipal());
            vble.setFilledBy(issue.getFilleBy());
            vble.setStatus(issue.getIssueStatus());

            //A&A��Ƭ�е�����
            resolution = lgir.get(issueRid);
            if (resolution != null) {
                vble.setResolution(resolution.getResolution() == null ? "" :
                                   resolution.getResolution());
                vble.setImpactLevel(resolution.getRiskLevel() == null ? "" :
                                    resolution.getRiskLevel().toString());
            } else {
                vble.setResolution("");
                vble.setImpactLevel("");
            }

            //�������������
            List discussList = lgidc.list(issueRid);
            int listSize = discussList.size();

            if (listSize >= 1) {
                discTitle = (VbIssueDiscussTitle) discussList.get(0); //�õ���һ��
                vble.setTopicTitle(discTitle.getTitle());
                vble.setTopicDate(discTitle.getFilledDate());
                vble.setTopicDesc(discTitle.getDescription() == null ? "" :
                                  discTitle.getDescription());
                vble.setTopicWho(discTitle.getFilledBy());
                vble.setRemark(discTitle.getRemark() == null ? "" :
                               discTitle.getRemark());
                vble.setTopicType(""); //����ط��ڵڶ���ģ������������REPLY����ģ����Դ˴�Ϊ�����ַ���
                //�����и�����TopicTitle
                if (discTitle.getAttachment() != null &&
                    !discTitle.getAttachment().equals("")) {
                    String topicAttURL = discTitle.getAttachment();
                    String replyTitle = "=HYPERLINK(\"" + serverRoot +
                                        topicAttURL +
                                        "\",\"" +
                                        discTitle.getTitle() + "\")";
                    vble.setTopicTitle(replyTitle);
                } else {
                    vble.setTopicTitle(discTitle.getTitle());
                }

            } else { //û�лظ�
                vble.setTopicTitle("");
                vble.setTopicDate("");
                vble.setTopicDesc("");
                vble.setTopicType("");
                vble.setTopicWho("");
                vble.setRemark("");
            }

            //���۵�����
            issueCon = lgic.get(issueRid);
            if (issueCon != null) {
                vble.setSolvedDesc(issueCon.getSolvedDescription() == null ? "" :
                                   issueCon.getSolvedDescription());
                vble.setInstructionOfClosure(issueCon.getInstructionClosure() == null ?
                                             "" :
                                             issueCon.getInstructionClosure());
            } else {
                vble.setSolvedDesc("");
                vble.setInstructionOfClosure("");
            }

            if (issue.getIssueStatus().equals("Closed")) {
                if(issueCon.getConfirmDate()!=null) {
                    vble.setConfirmDate(comDate.dateToString(issueCon.
                        getConfirmDate()));
                    vble.setConfirmBy(issueCon.getConfirmBy());
                    //setOpenDays
                    int openDays =
                        comDate.getBetweenDays(issue.getFilleDate(),
                                               issueCon.getConfirmDate());
                    vble.setRound(String.valueOf(openDays));
                } else {
                    vble.setConfirmDate("No Date");
                    vble.setConfirmBy(issueCon.getConfirmBy());
                    //setOpenDays
                    int openDays =
                        comDate.getBetweenDays(issue.getFilleDate(),
                                               new Date());
                    vble.setRound(String.valueOf(openDays));
                }

            }else{
                vble.setConfirmDate("");
                vble.setConfirmBy("");
                //setOpenDays
                int openDays =
                    comDate.getBetweenDays(issue.getFilleDate(),new Date());
                vble.setRound(String.valueOf(openDays));

            }

            //�Ž�ÿ��ISSUE
            treeNodePerIssue = new DtoTreeNode(vble);

            //�����˸�֮��Ӧ�ü�����(����еĻ�)�������������µ���������Ķ���
            if (listSize >= 1) { //˵������
                for (int topicIndex = 0; topicIndex < listSize; topicIndex++) {
                    VbIssueDiscussTitle topic = (VbIssueDiscussTitle)
                                                discussList.get(topicIndex);
                    if (topicIndex > 0) { //˵���Ѿ���������������TOPIC�ˣ�ҲҪ�ӽ�ȥ��
                        vbListExport vbleWithoutBasicForTopic = new
                            vbListExport();
                        vbleWithoutBasicForTopic.setTopicTitle(topic.
                            getTitle());
                        vbleWithoutBasicForTopic.setTopicDate(topic.
                            getFilledDate());
                        vbleWithoutBasicForTopic.setTopicDesc(topic.
                            getDescription() == null ? "" :
                            topic.getDescription());
                        vbleWithoutBasicForTopic.setTopicWho(topic.
                            getFilledBy());
                        vbleWithoutBasicForTopic.setRemark(topic.getRemark() == null ?
                            "" : topic.getRemark());
                        vbleWithoutBasicForTopic.setTopicType(""); //����ط��ڵڶ���ģ������������REPLY����ģ����Դ˴�Ϊ�����ַ���
                        //�����и�����TopicTitle
                        if (topic.getAttachment() != null &&
                            !topic.getAttachment().equals("")) {
                            String topicAttURL = topic.getAttachment();
                            String replyTitle = "=HYPERLINK(\"" + serverRoot +
                                                topicAttURL +
                                                "\",\"" +
                                                topic.getTitle() + "\")";
                            vbleWithoutBasicForTopic.setTopicTitle(
                                replyTitle);
                        } else {
                            vbleWithoutBasicForTopic.setTopicTitle(topic.
                                getTitle());
                        }
                        //��Topic�ӵ�����ȥ
                        treeNodePerIssue.addChild(new DtoTreeNode(
                            vbleWithoutBasicForTopic));

                    }
                    if (topic.getReplys() != null &&
                        topic.getReplys().size() > 0) {
                        List replyList = topic.getReplys();
                        for (int j = 0; j < replyList.size(); j++) {
                            VbIssueDiscussReply reply = (
                                VbIssueDiscussReply) replyList.get(j);
                            vbListExport vbleWithoutBasic = new
                                vbListExport();
                            vbleWithoutBasic.setTopicDate(reply.
                                getFilledDate());
                            vbleWithoutBasic.setTopicDesc(reply.
                                getDescription() == null ?
                                "" : reply.getDescription());
                            vbleWithoutBasic.setTopicWho(reply.getFilledBy());
                            vbleWithoutBasic.setRemark(reply.getRemark() == null ?
                                "" : reply.getRemark());
                            vbleWithoutBasic.setTopicTitle(""); //Reply����µ�Title�ǿյ�

                            //�����и�����ReplyTitle
                            if (reply.getAttachment() != null &&
                                !reply.getAttachment().equals("")) {
                                String replyAttURL = reply.getAttachment();
                                String replyTitle = "=HYPERLINK(\"" +
                                    serverRoot +
                                    replyAttURL +
                                    "\",\"" +
                                    reply.getTitle() +
                                    "\")";
                                vbleWithoutBasic.setTopicType(replyTitle);
                            } else {
                                vbleWithoutBasic.setTopicType(reply.getTitle());
                            }
                            //��RELEY�ӵ�����ȥ
                            treeNodePerIssue.addChild(new DtoTreeNode(
                                vbleWithoutBasic));
                        } //����REPLY��FOR����
                    } //���Ƿ���RELEY�����жϵ�IF����
                } //for end

            } //�ж��Ƿ������������IF����

            //��������һ��Ŀ�Ĵ��ţ����Լӵ�HASHMAP��ȥ
            if (!issue.getAccountId().toString().equals(accountId)) {
                if (rootTreePerSheet != null && !rootTreePerSheet.isLeaf()) {
                    IDtoAccount idtoa = new LgAccountUtilImpl().getAccountByRID(new
                        Long(accountId));
                    result.put(idtoa.getId(), rootTreePerSheet);
                }
                //����֮���ٸ�������ؼ�¼����
                accountId = issue.getAccountId().toString();
//                rootTreePerSheet = new DtoTreeNode(new Object());
                rootTreePerSheet = new DtoTreeNode(null);
                //�������Ժ�ͼӵ�ÿҳ�ĸ�����ȥ
                rootTreePerSheet.addChild(treeNodePerIssue);
            } else {
                //��ͬ���ʱ��ֱ�Ӽӵ�ÿҳ�ĸ�����ȥ
                rootTreePerSheet.addChild(treeNodePerIssue);
            }

            //�����һ����Ŀ�Ž�ȥ
            if ((i == (list.size() - 1)) && !rootTreePerSheet.isLeaf()) {
                //    System.out.println("���һ���ID" + accountId);
                IDtoAccount idtoa = new LgAccountUtilImpl().getAccountByRID(new
                    Long(accountId));
                result.put(idtoa.getId(), rootTreePerSheet);
            }

        }

        return result;
    }


    public static void main(String[] args) {
        com.wits.util.ThreadVariant thread = com.wits.util.ThreadVariant.
                                             getInstance();
        c2s.essp.common.user.DtoUser user = new c2s.essp.common.user.DtoUser();
        user.setUserID("stone.shi");
        user.setUserLoginId("stone.shi");
        thread.set(c2s.essp.common.user.DtoUser.SESSION_USER, user);

        lgGetExportData gd = new lgGetExportData();
        AfIssueSearch searchForm = new AfIssueSearch();
        searchForm.setFillBy("stone.shi");
        //�˲��Բ����ã���session�е��û���Ϣ
        HashMap hm = gd.getListExportDate(searchForm, "http://localhost:8080");
        System.out.println(hm);
    }
}
