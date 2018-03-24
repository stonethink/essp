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

            //rid为各信息关联之主键
            Long issueRid = issue.getRid();

            //获得附件的地址
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
                //加入链接地址
                issueIdContent =
                    "=HYPERLINK(\"" + serverRoot + attURL + "\",\"" +
                    issueIdContent +
                    "\")";
            }
            // System.out.println("~~~~~~~~~~" + issueIdContent);

            //基本数据准备-----------------------
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

            //A&A卡片中的内容
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

            //讨论情况的数据
            List discussList = lgidc.list(issueRid);
            int listSize = discussList.size();
            //得到最后一条Topic
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
                    //处理有附件的ReplyTitle
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
                    //处理有附件的TopicTitle
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
            } else { //没有回复
                vble.setTopicTitle("");
                vble.setTopicDate("");
                vble.setTopicDesc("");
                vble.setTopicType("");
                vble.setTopicWho("");
            }

            //结论的数据
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
                //加完之后再更更新相关记录变量
                accountId = issue.getAccountId().toString();
                destList = new ArrayList();
                destList.add(vble);
            } else {
                destList.add(vble);
            }

            //将最后一类放进去
            if ((i == (list.size() - 1)) && destList.size() > 0) {
                //    System.out.println("最后一类的ID" + accountId);
                IDtoAccount idtoa = new LgAccountUtilImpl().getAccountByRID(new
                    Long(accountId));
                result.put(idtoa.getId(), destList);
            }

        }

        return result;
    }

    /*
     *导出带有详细讨论信息的报表，并按每条ISSUE进行分组，按项目进行分页
     *@author:robin.zhang
     *@param:searchForm,serverRoot
     */
    public HashMap getListExportDateForDetail(AfIssueSearch searchForm,
                                              String serverRoot) {
        LgIssueList lgil = new LgIssueList();
        List list = lgil.listAllForExport(searchForm);
        HashMap result = new HashMap();
        DtoTreeNode rootTreePerSheet = null; // new DtoTreeNode(new Object()); //做一个空的根结点为每个SHEET页
        DtoTreeNode treeNodePerIssue = null; // = new DtoTreeNode(new Object()); //做为每一条ISSUE的结点
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

            //rid为各信息关联之主键
            Long issueRid = issue.getRid();

            //获得附件的地址
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
                //加入链接地址
                issueIdContent =
                    "=HYPERLINK(\"" + serverRoot + attURL + "\",\"" +
                    issueIdContent +
                    "\")";
            }
            // System.out.println("~~~~~~~~~~" + issueIdContent);

            //基本数据准备-----------------------
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

            //A&A卡片中的内容
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

            //讨论情况的数据
            List discussList = lgidc.list(issueRid);
            int listSize = discussList.size();

            if (listSize >= 1) {
                discTitle = (VbIssueDiscussTitle) discussList.get(0); //得到第一条
                vble.setTopicTitle(discTitle.getTitle());
                vble.setTopicDate(discTitle.getFilledDate());
                vble.setTopicDesc(discTitle.getDescription() == null ? "" :
                                  discTitle.getDescription());
                vble.setTopicWho(discTitle.getFilledBy());
                vble.setRemark(discTitle.getRemark() == null ? "" :
                               discTitle.getRemark());
                vble.setTopicType(""); //这个地方在第二种模版中是用在填REPLY标题的，所以此处为“”字符串
                //处理有附件的TopicTitle
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

            } else { //没有回复
                vble.setTopicTitle("");
                vble.setTopicDate("");
                vble.setTopicDesc("");
                vble.setTopicType("");
                vble.setTopicWho("");
                vble.setRemark("");
            }

            //结论的数据
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

            //放进每条ISSUE
            treeNodePerIssue = new DtoTreeNode(vble);

            //加完了根之后还应该加其子(如果有的话)，即加下面余下的讨论详情的东东
            if (listSize >= 1) { //说明有子
                for (int topicIndex = 0; topicIndex < listSize; topicIndex++) {
                    VbIssueDiscussTitle topic = (VbIssueDiscussTitle)
                                                discussList.get(topicIndex);
                    if (topicIndex > 0) { //说明已经不是最上面那条TOPIC了，也要加进去了
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
                        vbleWithoutBasicForTopic.setTopicType(""); //这个地方在第二种模版中是用在填REPLY标题的，所以此处为“”字符串
                        //处理有附件的TopicTitle
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
                        //将Topic加到树中去
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
                            vbleWithoutBasic.setTopicTitle(""); //Reply情况下的Title是空的

                            //处理有附件的ReplyTitle
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
                            //将RELEY加到树中去
                            treeNodePerIssue.addChild(new DtoTreeNode(
                                vbleWithoutBasic));
                        } //处理REPLY的FOR结束
                    } //对是否有RELEY进行判断的IF结束
                } //for end

            } //判断是否有讨论详情的IF结束

            //不等于上一项目的代号，所以加到HASHMAP中去
            if (!issue.getAccountId().toString().equals(accountId)) {
                if (rootTreePerSheet != null && !rootTreePerSheet.isLeaf()) {
                    IDtoAccount idtoa = new LgAccountUtilImpl().getAccountByRID(new
                        Long(accountId));
                    result.put(idtoa.getId(), rootTreePerSheet);
                }
                //加完之后再更更新相关记录变量
                accountId = issue.getAccountId().toString();
//                rootTreePerSheet = new DtoTreeNode(new Object());
                rootTreePerSheet = new DtoTreeNode(null);
                //处理完以后就加到每页的根里面去
                rootTreePerSheet.addChild(treeNodePerIssue);
            } else {
                //相同情况时就直接加到每页的根里面去
                rootTreePerSheet.addChild(treeNodePerIssue);
            }

            //将最后一个项目放进去
            if ((i == (list.size() - 1)) && !rootTreePerSheet.isLeaf()) {
                //    System.out.println("最后一类的ID" + accountId);
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
        //此测试不可用，差session中的用户信息
        HashMap hm = gd.getListExportDate(searchForm, "http://localhost:8080");
        System.out.println(hm);
    }
}
