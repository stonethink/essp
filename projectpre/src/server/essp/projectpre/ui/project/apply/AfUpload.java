/*
 * Created on 2006-11-21
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.projectpre.ui.project.apply;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class AfUpload extends ActionForm{
    private FormFile file;
    private String otherPerson;
    private String otherPersonId;
    private String otherPersonDomain;
    public String getOtherPersonDomain() {
        return otherPersonDomain;
    }
    public void setOtherPersonDomain(String otherPersonDomain) {
        this.otherPersonDomain = otherPersonDomain;
    }
    public String getOtherPersonId() {
        return otherPersonId;
    }
    public void setOtherPersonId(String otherPersonId) {
        this.otherPersonId = otherPersonId;
    }
    public FormFile getFile() {
        return file;
    }
    public void setFile(FormFile file1) {
        this.file = file1;
    }
    public String getOtherPerson() {
        return otherPerson;
    }
    public void setOtherPerson(String otherPerson) {
        this.otherPerson = otherPerson;
    } 
}
