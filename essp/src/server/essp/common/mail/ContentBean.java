package server.essp.common.mail;

import java.util.*;

/**
 *此类为每封EMAIL的通用数据Bean
 *author:Robin.Zhang
 */

public class ContentBean {
    private String user;
    private ArrayList mailcontent;
    private String email;
    private HashMap attachments;
    private String ccAddress;

    public String getUser() {

       return user;
   }

    public ArrayList getMailcontent() {

        return mailcontent;
    }

    public String getEmail() {
        return email;
    }

    public HashMap getAttachments() {
        return attachments;
    }

    public String getCcAddress() {
        return ccAddress;
    }

    public void setUser(String user) {

       this.user = user;
   }

    public void setMailcontent(ArrayList mailcontent) {

        this.mailcontent = mailcontent;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAttachments(HashMap attachments) {
        this.attachments = attachments;
    }

    public void setCcAddress(String ccAddress) {
        this.ccAddress = ccAddress;
    }
}
