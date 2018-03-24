package com.wits.excel.importer;

import java.util.Date;

public class TestImportBean {
        private String userLoginId;
        private String domain;
        private Date inDate;

        public void setUserLoginId(String userLoginId) {
            this.userLoginId = userLoginId;
        }

        public void setDomain(String domain) {
            this.domain = domain;
        }

        public void setInDate(Date inDate) {
            this.inDate = inDate;
        }

        public String getUserLoginId() {
            return this.userLoginId;
        }

        public String getDomain() {
            return this.domain;
        }

        public Date getInDate() {
            return this.inDate;
        }
    }
