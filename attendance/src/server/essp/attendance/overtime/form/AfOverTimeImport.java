package server.essp.attendance.overtime.form;

import org.apache.struts.upload.FormFile;
import org.apache.struts.action.ActionForm;

public class AfOverTimeImport extends ActionForm {
    public AfOverTimeImport() {
    }
    private FormFile localFile;
    public void setLocalFile(FormFile localFile) {
        this.localFile = localFile;
    }
    public FormFile getLocalFile() {
        return localFile;
    }
}
