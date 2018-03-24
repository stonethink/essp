/*
 * Created on 2007-10-29
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.timesheet.aprm.form;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class AfAPRMTSImport extends ActionForm {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private FormFile localFile;
    
    public void setLocalFile(FormFile localFile) {
        this.localFile = localFile;
    }
    public FormFile getLocalFile() {
        return localFile;
    }
}

