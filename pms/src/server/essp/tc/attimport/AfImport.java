package server.essp.tc.attimport;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;

public class AfImport extends ActionForm{
    private FormFile localfile;

    public void setLocalfile(FormFile formFile){
        this.localfile=formFile;
    }


    public FormFile getLocalfile(){
        return this.localfile;
    }


//    public ActionErrors validate(){
//        ActionErrors ae=new ActionErrors();
//        ActionMessages am=new ActionMessages();
//
//        ae.add(new ActionMessages());
//        return new ActionErrors();
//    }
}
