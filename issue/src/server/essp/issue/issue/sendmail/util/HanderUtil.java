package server.essp.issue.issue.sendmail.util;

import server.essp.common.mail.LgMailSend;
import java.util.HashMap;
import server.framework.logic.AbstractBusinessLogic;
import java.util.Date;
import server.framework.common.BusinessException;
import itf.hr.HrFactory;
import itf.hr.LgHrUtilImpl;
import java.util.List;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;

public class HanderUtil extends AbstractBusinessLogic {
    public HanderUtil() {
    }

    public static String contractUser(String a, String b) {
        if (a == null) {
            a = "";
        }
        if (b == null) {
            b = "";
        }
        if (a.equals("") && b.equals("")) {
            return "";
        }
        if (!a.equals("") && b.equals("")) {
            return a;
        }
        if (a.equals("") && !b.equals("")) {
            return b;
        }
        if (!a.equals("") && !b.equals("")) {
            return a + "," + b;
        }
        return "";
    }


    public void sendMail(String mailTo, String cc, String title, String from,
                         String cont,
                         HashMap atts) {
        try {
            LgMailSend logic = new LgMailSend();
            logic.setContent(cont);
            logic.setTitle(title);
            //附件发送
            if (atts != null) {
                logic.setAttachmentFiles(atts);
            }
            //发到此邮件地址
            logic.setToAddress(mailTo);
            if (cc != null && !cc.equals("")) {
                logic.setCcAddress(cc);
            }
            logic.setFrom(from);
            logic.setIsHtml(true);
            logic.send();
            //log信息
            log.info(new Date().getTime() + "--" + title + "--" + mailTo);
        } catch (Throwable tx) {
            String msg = "mail send error ";
            log.error(msg);
            throw new BusinessException("", msg, tx);
        }
    }


    public String convertLoginNameToEmailAdd(String loginName) {
        String result = "";
        if (loginName != null && !loginName.equals("")) {
            String[] users = loginName.split(",");
            List list = Arrays.asList(users);
            Set set = new HashSet(list);

            String[] tempUsers = new String[set.size()];
            Iterator ite = set.iterator();
            for (int i = 0; ite.hasNext(); i++) {
                tempUsers[i] = (String) ite.next();
            }
            LgHrUtilImpl ihui = (LgHrUtilImpl) HrFactory.create();
            for (int i = 0; i < tempUsers.length; i++) {
                if (result.equals("")) {
                    result = ihui.getUserEmail(tempUsers[i]);
                } else {
                    result = result + "," + ihui.getUserEmail(tempUsers[i]);
                }
            }
        }
        return result;
    }

    //用dest减去src字符串,然后去掉重复项
    public static String subtractString(String dest, String src) {
        String result = "";
        if (dest == null || dest.equals("")) {
            return result;
        } else if (dest != null && !dest.equals("") &&
                   (src == null || src.equals(""))) {
            return dest;
        }
        String[] strDest = dest.split(",");
        String[] strSrc = src.split(",");
        for (int i = 0; i < strDest.length; i++) {
            boolean flag = false;
            for (int j = 0; j < strSrc.length; j++) {
                if (strSrc[j].equals(strDest[i])) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                if (result.equals("")) {
                    result = strDest[i];
                } else {
                    result = result + "," + strDest[i];
                }

            }
        }

//        int ind = dest.indexOf(src);
//        result = dest.substring(0, ind) ;
//        if (ind + src.length() + 1 <= dest.length() - 1) {
//            result=result+dest.substring(ind + src.length() + 1);
//        } else {
//            result=result+dest.substring(ind + src.length());
//        }
        String[] strArr = result.split(",");
        List list = Arrays.asList(strArr);
        Set set = new HashSet(list);
//        strArr = (String[]) set.toArray();
        String[] tempUsers = new String[set.size()];
        Iterator ite = set.iterator();
        for (int i = 0; ite.hasNext(); i++) {
            tempUsers[i] = (String) ite.next();
        }
        strArr = tempUsers;
        result = "";
        for (int j = 0; j < strArr.length; j++) {
            if (result.equals("")) {
                result = strArr[j];
            } else {
                result = result + "," + strArr[j];
            }
        }

        return result;
    }

    public static String convertToHtmlFormatString(String src) {
        if (src == null || src.equals("")) {
            return "";
        }
        return src.replaceAll("\r\n", "<BR/>");
    }

    public static void main(String[] arg) {
        String a = "abcd,eft,fa.djfd,fdkdf";
        String b = "abcd";
        System.out.println(subtractString(a, b));

    }

}
