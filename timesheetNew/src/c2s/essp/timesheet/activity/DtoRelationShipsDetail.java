package c2s.essp.timesheet.activity;

import java.util.Date;

import c2s.dto.DtoBase;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author not attributable
 * @version 1.0
 */
public class DtoRelationShipsDetail extends DtoBase{
   private String status;
   private String primaryResource;
   private Date startDate;
   private String email;
   private Date finishDate;
   private String officePhone;

    public Date getFinishDate() {
        return finishDate;
    }

    public String getEmail() {
        return email;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public Date getStartDate() {
        return startDate;
    }

    public String getPrimaryResource() {
        return primaryResource;
    }

    public String getStatus() {
        return status;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public void setPrimaryResource(String primaryResource) {
        this.primaryResource = primaryResource;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
