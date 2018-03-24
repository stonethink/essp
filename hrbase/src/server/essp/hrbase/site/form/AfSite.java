package server.essp.hrbase.site.form;

import java.util.Date;

import org.apache.struts.action.ActionForm;

public class AfSite extends ActionForm {
	
	private String rid;
    private String name;
    private String description;
    private String isEnable;
    private String seq;
  
   // Property accessors

   public String getRid() {
       return this.rid;
   }
   
   public void setRid(String rid) {
       this.rid = rid;
   }

   public String getName() {
       return this.name;
   }
   
   public void setName(String name) {
       this.name = name;
   }

   public String getDescription() {
       return this.description;
   }
   
   public void setDescription(String description) {
       this.description = description;
   }

   public String getIsEnable() {
       return this.isEnable;
   }
   
   public void setIsEnable(String isEnable) {
       this.isEnable = isEnable;
   }

   public String getSeq() {
       return this.seq;
   }
   
   public void setSeq(String seq) {
       this.seq = seq;
   }
}
