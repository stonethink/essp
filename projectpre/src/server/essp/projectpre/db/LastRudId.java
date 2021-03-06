package server.essp.projectpre.db;



/**
 * PpLastRudId generated by MyEclipse - Hibernate Tools
 */

public class LastRudId  implements java.io.Serializable {


    // Fields    

     private String site;
     private String bizObj;


    // Constructors

    /** default constructor */
    public LastRudId() {
    }

    

   
    // Property accessors

    public String getSite() {
        return this.site;
    }
    
    public void setSite(String site) {
        this.site = site;
    }

    public String getBizObj() {
        return this.bizObj;
    }
    
    public void setBizObj(String bizObj) {
        this.bizObj = bizObj;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof LastRudId) ) return false;
		 LastRudId castOther = ( LastRudId ) other; 
         
		 return ( (this.getSite()==castOther.getSite()) || ( this.getSite()!=null && castOther.getSite()!=null && this.getSite().equals(castOther.getSite()) ) )
 && ( (this.getBizObj()==castOther.getBizObj()) || ( this.getBizObj()!=null && castOther.getBizObj()!=null && this.getBizObj().equals(castOther.getBizObj()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getSite() == null ? 0 : this.getSite().hashCode() );
         result = 37 * result + ( getBizObj() == null ? 0 : this.getBizObj().hashCode() );
         return result;
   }   





}