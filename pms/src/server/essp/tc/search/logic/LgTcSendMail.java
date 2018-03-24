
package server.essp.tc.search.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import c2s.essp.tc.weeklyreport.DtoTcSearchResult;
import c2s.essp.tc.weeklyreport.DtoWeeklyReport;
import server.essp.common.mail.LgMailSend;
import java.util.Date;
import itf.hr.IHrUtil;
import itf.hr.HrFactory;

public class LgTcSendMail{
    Map notSubmitMail = new HashMap();
    Map notLockMail = new HashMap();
    Map rejectMail = new HashMap();
    Map lockMail = new HashMap();
    Map allMail = new HashMap();
    LgMailSend lgMailSend = new LgMailSend();
    IHrUtil hrUtil = HrFactory.create();

    public void send(List userList){
        for (Iterator iter = userList.iterator(); iter.hasNext(); ) {
            DtoTcSearchResult user = (DtoTcSearchResult) iter.next();
            if( DtoWeeklyReport.STATUS_NONE.equals( user.getStatus() ) ){
                addNotSubmit(user);
            }else if( DtoWeeklyReport.STATUS_UNLOCK.equals( user.getStatus() ) ){
                addNotLock(user);
            }else if( DtoWeeklyReport.STATUS_REJECTED.equals( user.getStatus() ) ){
                addReject(user);
            }else if( DtoWeeklyReport.STATUS_CONFIRMED.equals( user.getStatus() ) ){

            }else if( DtoWeeklyReport.STATUS_LOCK.equals( user.getStatus() ) ){
                addLock(user);
            }
        }

        Set userIdList = allMail.keySet();
        for (Iterator iter = userIdList.iterator(); iter.hasNext(); ) {
            String userId = (String) iter.next();
            sendToUser(userId);
        }
    }

    private void sendToUser(String userId){
        String title = "Weekly Report";
        String endText = "\n\tThis e-mail was automatically generated. Please, do not reply to this e-mail address.";
        String content = getMail(userId).toString();
        if( content.length() > 0 ){
            lgMailSend.setTitle(title);
            lgMailSend.setContent(content+endText);

            String toAddress = hrUtil.getUserEmail(userId);
            if( toAddress != null && toAddress.indexOf("@") > 0 ){
                lgMailSend.setToAddress(toAddress);

                lgMailSend.send();
            }
        }
    }

    //"In the week[]: Your don't write weekly report."
    private void addNotSubmit( DtoTcSearchResult user ){
        String userId = user.getUser();
        String period = user.getPeriod();

        Map mails = (Map)notSubmitMail.get(userId);
        if( mails == null ){
            mails = new HashMap();
            notSubmitMail.put( userId, mails );
        }

        StringBuffer mail = (StringBuffer)mails.get(period);
        if( mail == null ){
            mail = new StringBuffer();
            mails.put(period, mail);
            addToAllMailMap(userId, mail);
        }
        mail.append("In the week[");
        mail.append(period);
        mail.append("]: Your don't write weekly report.");
    }

    //"In the week[]: Your don't lock the weekly report."
    private void addNotLock( DtoTcSearchResult user ){
        String userId = user.getUser();
        String period = user.getPeriod();

        Map mails = (Map)notLockMail.get(userId);
        if( mails == null ){
            mails = new HashMap();
            notLockMail.put( userId, mails );
        }

        StringBuffer mail = (StringBuffer)mails.get(period);
        if( mail == null ){
            mail = new StringBuffer();
            mails.put(period, mail);
            addToAllMailMap(userId, mail);
        }
        mail.append("In the week[");
        mail.append(period);
        mail.append("]: Your don't lock the weekly report.");
    }

    //"In the week[]: Your weekly report is rejected by 'pm' of account(), 'pm2' of account()."
    private void addReject( DtoTcSearchResult user ){
        String userId = user.getUser();
        String period = user.getPeriod();

        Map mails = (Map)rejectMail.get(userId);
        if( mails == null ){
            mails = new HashMap();
            rejectMail.put( userId, mails );
        }

        StringBuffer mail = (StringBuffer)mails.get(period);
        if( mail == null ){
            mail = new StringBuffer();
            mails.put(period, mail);
            addToAllMailMap(userId, mail);
        }
        if (mail.length() == 0) {
            mail.append("In the week[");
            mail.append(period);
            mail.append("]: Your weekly report is rejected by manager '");
            mail.append(user.getManager());
            mail.append("' of account(");
            mail.append(user.getAcntName());
            mail.append(").");
        }else{
            mail.delete(mail.length()-1, mail.length()-1);
            mail.append(", '");
            mail.append(user.getManager());
            mail.append("' of account(");
            mail.append(user.getAcntName());
            mail.append(").");
        }
    }

    //"In the week[]: 'worker' has lock the weekly report.Waiting for your confirm."
    private void addLock( DtoTcSearchResult user ){
        String userId = user.getUser();
        String manager = user.getManager();
        String period = user.getPeriod();

        Map mails = (Map)lockMail.get(manager);
        if( mails == null ){
            mails = new HashMap();
            lockMail.put( manager, mails );
        }

        StringBuffer mail = (StringBuffer)mails.get(period);
        if( mail == null ){
            mail = new StringBuffer();
            mails.put(period, mail);
            addToAllMailMap(manager, mail);
        }

        mail.append("In the week[");
        mail.append(period);
        mail.append("]: '");
        mail.append(userId);
        mail.append("' has lock the weekly report.Waiting for your confirm.");
    }


    private void addToAllMailMap(String userId, StringBuffer mail){
        List mailList = (List)allMail.get(userId);
        if( mailList == null ){
            mailList = new ArrayList();
            allMail.put(userId, mailList);
        }
        mailList.add(mail);
    }

    private StringBuffer getMail(String userId){
        StringBuffer mail = new StringBuffer();
        List mailList = (List)allMail.get(userId);
        if( mailList != null ){
            for (Iterator iter = mailList.iterator(); iter.hasNext(); ) {
                StringBuffer mailSb = (StringBuffer) iter.next();
                mail.append(mailSb);
                mail.append("\n");
            }
        }
        return mail;
    }

    void test() {
        DtoTcSearchResult dto1 = testUser("hua.xiao",
                                          new Date(105, 11, 3), new Date(105, 11, 9),
                                          "Essp", "stone.shi",
                                          DtoWeeklyReport.STATUS_NONE
                                 );
        DtoTcSearchResult dto2 = testUser("hua.xiao",
                                          new Date(105, 11, 10), new Date(105, 11, 16),
                                          "Essp", "stone.shi",
                                          DtoWeeklyReport.STATUS_UNLOCK
                                 );
        DtoTcSearchResult dto3 = testUser("hua.xiao",
                                          new Date(105, 11, 17), new Date(105, 11, 13),
                                          "Essp", "stone.shi",
                                          DtoWeeklyReport.STATUS_REJECTED
                                 );
        DtoTcSearchResult dto4 = testUser("hua.xiao",
                                          new Date(105, 11, 17), new Date(105, 11, 13),
                                          "Beijin", "bo.xiao",
                                          DtoWeeklyReport.STATUS_REJECTED
                                 );
        DtoTcSearchResult dto5 = testUser("rongxiao",
                                          new Date(105, 11, 17), new Date(105, 11, 13),
                                          "Beijin", "bo.xiao",
                                          DtoWeeklyReport.STATUS_REJECTED
                                 );


        List userList = new ArrayList();
        userList.add(dto1);
        userList.add(dto2);
        userList.add(dto3);
        userList.add(dto4);
        userList.add(dto5);
        send(userList);
    }

    DtoTcSearchResult testUser(String userId,
                  Date beginPeriod, Date endPeriod,
                  String acntName, String manager,
                  String status){
        DtoTcSearchResult dto1 = new DtoTcSearchResult();
        dto1.setAcntName(acntName);
        dto1.setManager(manager);
        dto1.setBeginPeriod(beginPeriod);
        dto1.setBeginPeriod(endPeriod);
        dto1.setStatus(status);
        dto1.setUser(userId);
        return dto1;
    }

    public static void main(String args[]){
        LgTcSendMail logic = new LgTcSendMail();
        logic.test();
    }
}
