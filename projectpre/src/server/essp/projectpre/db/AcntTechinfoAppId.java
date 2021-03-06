package server.essp.projectpre.db;



/**
 * PpAcntTechinfoAppId generated by MyEclipse - Hibernate Tools
 */

public class AcntTechinfoAppId  implements java.io.Serializable {


    // Fields    

     private Long acntAppRid;
     private String kind;
     private String code;


    // Constructors

    /** default constructor */
    public AcntTechinfoAppId() {
    }

    

   
    // Property accessors

    public Long getAcntAppRid() {
        return this.acntAppRid;
    }
    
    public void setAcntAppRid(Long acntAppRid) {
        this.acntAppRid = acntAppRid;
    }

    public String getKind() {
        return this.kind;
    }
    
    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getCode() {
        return this.code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof AcntTechinfoAppId) ) return false;
		 AcntTechinfoAppId castOther = ( AcntTechinfoAppId ) other; 
         
		 return ( (this.getAcntAppRid()==castOther.getAcntAppRid()) || ( this.getAcntAppRid()!=null && castOther.getAcntAppRid()!=null && this.getAcntAppRid().equals(castOther.getAcntAppRid()) ) )
 && ( (this.getKind()==castOther.getKind()) || ( this.getKind()!=null && castOther.getKind()!=null && this.getKind().equals(castOther.getKind()) ) )
 && ( (this.getCode()==castOther.getCode()) || ( this.getCode()!=null && castOther.getCode()!=null && this.getCode().equals(castOther.getCode()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getAcntAppRid() == null ? 0 : this.getAcntAppRid().hashCode() );
         result = 37 * result + ( getKind() == null ? 0 : this.getKind().hashCode() );
         result = 37 * result + ( getCode() == null ? 0 : this.getCode().hashCode() );
         return result;
   }   





}