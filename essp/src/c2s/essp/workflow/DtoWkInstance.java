package c2s.essp.workflow;

import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;
import c2s.dto.DtoBase;
import c2s.essp.common.workflow.IWkInstance;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class DtoWkInstance extends DtoBase implements IWkInstance {

        /** identifier field */
        private Long rid;

        /** nullable persistent field */
        private String subEmpLoginID;

        /** nullable persistent field */
        private String status;

        /** nullable persistent field */
        private Long steps;

        /** nullable persistent field */
        private Date startDate;

        /** nullable persistent field */
        private Date finishDate;

        /** nullable persistent field */
        private String instanceName;

        /** nullable persistent field */
        private String rst;

        /** nullable persistent field */
        private Date rct;

        /** nullable persistent field */
        private Date rut;

        /** full constructor */
        public DtoWkInstance(Long rid, String subEmpLoginID, String status, Long steps, Date startDate, Date finishDate, String instanceName, String rst, Date rct, Date rut) {
            this.rid = rid;
            this.subEmpLoginID = subEmpLoginID;
            this.status = status;
            this.steps = steps;
            this.startDate = startDate;
            this.finishDate = finishDate;
            this.instanceName = instanceName;
            this.rst = rst;
            this.rct = rct;
            this.rut = rut;
        }

        /** default constructor */
        public DtoWkInstance() {
        }

        /** minimal constructor */
        public DtoWkInstance(Long rid) {
            this.rid = rid;
        }

        /**
         *            @hibernate.id
         *             generator-class="assigned"
         *             type="java.lang.Long"
         *             column="RID"
         *
         */
        public Long getRid() {
            return this.rid;
        }

        public void setRid(Long rid) {
            this.rid = rid;
        }

        /**
         *            @hibernate.property
         *             column="SUB_EMP_LOGINID"
         *             length="100"
         *
         */
        public String getSubEmpLoginID() {
            return this.subEmpLoginID;
        }

        public void setSubEmpLoginID(String subEmpLoginID) {
            this.subEmpLoginID = subEmpLoginID;
        }

        /**
         *            @hibernate.property
         *             column="STATUS"
         *             length="10"
         *
         */
        public String getStatus() {
            return this.status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        /**
         *            @hibernate.property
         *             column="STEPS"
         *             length="8"
         *
         */
        public Long getSteps() {
            return this.steps;
        }

        public void setSteps(Long steps) {
            this.steps = steps;
        }

        /**
         *            @hibernate.property
         *             column="START_DATE"
         *             length="19"
         *
         */
        public Date getStartDate() {
            return this.startDate;
        }

        public void setStartDate(Date startDate) {
            this.startDate = startDate;
        }

        /**
         *            @hibernate.property
         *             column="FINISH_DATE"
         *             length="19"
         *
         */
        public Date getFinishDate() {
            return this.finishDate;
        }

        public void setFinishDate(Date finishDate) {
            this.finishDate = finishDate;
        }

        /**
         *            @hibernate.property
         *             column="INSTANCE_NAME"
         *             length="200"
         *
         */
        public String getInstanceName() {
            return this.instanceName;
        }

        public void setInstanceName(String instanceName) {
            this.instanceName = instanceName;
        }

        /**
         *            @hibernate.property
         *             column="RST"
         *             length="1"
         *
         */
        public String getRst() {
            return this.rst;
        }

        public void setRst(String rst) {
            this.rst = rst;
        }

        /**
         *            @hibernate.property
         *             column="RCT"
         *             length="19"
         *
         */
        public Date getRct() {
            return this.rct;
        }

        public void setRct(Date rct) {
            this.rct = rct;
        }

        /**
         *            @hibernate.property
         *             column="RUT"
         *             length="19"
         *
         */
        public Date getRut() {
            return this.rut;
        }

        public void setRut(Date rut) {
            this.rut = rut;
        }

        public String toString() {
            return new ToStringBuilder(this)
                .append("rid", getRid())
                .toString();
    }

    public Long getInstanceID() {
        return null;
    }

    public void setInstanceID(Long instanceID) {
    }
}
