/*
 * Created on 2007-12-13
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.hrbase.bd.form;

import org.apache.struts.action.ActionForm;

public class AfBd extends ActionForm{

        private String bdCode;
        
        private String bdName;
        
        private String status;
        
        private String achieveBelong;
        
        private String description;
    
        public String getBdCode() {
            return bdCode;
        }
    
        public void setBdCode(String bdCode) {
            this.bdCode = bdCode;
        }
    
        public String getBdName() {
            return bdName;
        }
    
        public void setBdName(String bdName) {
            this.bdName = bdName;
        }
    
        public String getDescription() {
            return description;
        }
    
        public void setDescription(String description) {
            this.description = description;
        }
    
        public String getStatus() {
            return status;
        }
    
        public void setStatus(String status) {
            this.status = status;
        }

        public String getAchieveBelong() {
            return achieveBelong;
        }

        public void setAchieveBelong(String achieveBelong) {
            this.achieveBelong = achieveBelong;
        }

}
