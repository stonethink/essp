package server.essp.timesheet.template.service;

import java.util.List;

import server.essp.timesheet.template.form.AfTemplate;

public interface ITemplateService {	

    public Long saveTemplate(AfTemplate form,String [] methods);  

    public AfTemplate getTemplate(Long rid);

    public void deleteTemplate(Long rid);
    
    public List getTemplateList();   


}
