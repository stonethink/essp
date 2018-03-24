/*
 * Created on 2008-4-23
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.projectpre.db;

import java.util.Date;

public class MailPrivilege implements java.io.Serializable {

           private Long rid;
           private String loginId;
           private String loginName;
           private String domain;
           private Boolean addInform;
           private Boolean addAudit;
           private Boolean changeInform;
           private Boolean changeAudit;
           private Boolean closeAudit;
           private Boolean closeInform;
           private String dataScope;
           private String rst;
           private Date rct;
           private Date rut;
           private String rcu;
           private String ruu;
           
           public MailPrivilege(){
               
           }
           
           public MailPrivilege(Long rid, String loginId, String loginName,
                   String domain, Boolean addInform,Boolean addAudit,Boolean changeInform,
                   Boolean changeAudit,Boolean closeAudit,Boolean closeInform,String dataScope,
                   String rst,Date rct, Date rut,String rcu,String ruu) {
               this.rid = rid;
               this.loginId=loginId;
               this.loginName = loginName;
               this.domain = domain;
               this.addInform = addInform;
               this.addAudit = addAudit;
               this.changeInform = changeInform;
               this.changeAudit = changeAudit;
               this.closeInform = closeInform;
               this.closeAudit = closeAudit;
               this.dataScope = dataScope;
               this.rst = rst;
               this.rct = rct;
               this.rut = rut;
               this.rcu = rcu;
               this.ruu = ruu;
           }
           
            public Boolean getAddAudit() {
                return addAudit;
            }
            public void setAddAudit(Boolean addAudit) {
                this.addAudit = addAudit;
            }
            public Boolean getAddInform() {
                return addInform;
            }
            public void setAddInform(Boolean addInform) {
                this.addInform = addInform;
            }
            public Boolean getChangeAudit() {
                return changeAudit;
            }
            public void setChangeAudit(Boolean changeAudit) {
                this.changeAudit = changeAudit;
            }
            public Boolean getChangeInform() {
                return changeInform;
            }
            public void setChangeInform(Boolean changeInform) {
                this.changeInform = changeInform;
            }
            public Boolean getCloseAudit() {
                return closeAudit;
            }
            public void setCloseAudit(Boolean closeAudit) {
                this.closeAudit = closeAudit;
            }
            public Boolean getCloseInform() {
                return closeInform;
            }
            public void setCloseInform(Boolean closeInform) {
                this.closeInform = closeInform;
            }
            public String getDataScope() {
                return dataScope;
            }
            public void setDataScope(String dataScope) {
                this.dataScope = dataScope;
            }
            public String getDomain() {
                return domain;
            }
            public void setDomain(String domain) {
                this.domain = domain;
            }
            public String getLoginId() {
                return loginId;
            }
            public void setLoginId(String loginId) {
                this.loginId = loginId;
            }
            public String getLoginName() {
                return loginName;
            }
            public void setLoginName(String loginName) {
                this.loginName = loginName;
            }
            public Long getRid() {
                return rid;
            }
            public void setRid(Long rid) {
                this.rid = rid;
            }

            public Date getRct() {
                return rct;
            }

            public void setRct(Date rct) {
                this.rct = rct;
            }

            public String getRcu() {
                return rcu;
            }

            public void setRcu(String rcu) {
                this.rcu = rcu;
            }

            public String getRst() {
                return rst;
            }

            public void setRst(String rst) {
                this.rst = rst;
            }

            public Date getRut() {
                return rut;
            }

            public void setRut(Date rut) {
                this.rut = rut;
            }

            public String getRuu() {
                return ruu;
            }

            public void setRuu(String ruu) {
                this.ruu = ruu;
            }
       
}
