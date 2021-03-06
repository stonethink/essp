package server.essp.projectpre.db;



/**
 * PpAcntTechinfoId generated by MyEclipse - Hibernate Tools
 */

public class AcntTechinfoId  implements java.io.Serializable {


    // Fields    

     private Long acntRid;
     private String kind;
     private String code;


    // Constructors

    /** default constructor */
    public AcntTechinfoId() {
    }

    

   
    // Property accessors

    public Long getAcntRid() {
        return this.acntRid;
    }
    
    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;
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
		 if ( !(other instanceof AcntTechinfoId) ) return false;
		 AcntTechinfoId castOther = ( AcntTechinfoId ) other; 
         
		 return ( (this.getAcntRid()==castOther.getAcntRid()) || ( this.getAcntRid()!=null && castOther.getAcntRid()!=null && this.getAcntRid().equals(castOther.getAcntRid()) ) )
 && ( (this.getKind()==castOther.getKind()) || ( this.getKind()!=null && castOther.getKind()!=null && this.getKind().equals(castOther.getKind()) ) )
 && ( (this.getCode()==castOther.getCode()) || ( this.getCode()!=null && castOther.getCode()!=null && this.getCode().equals(castOther.getCode()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getAcntRid() == null ? 0 : this.getAcntRid().hashCode() );
         result = 37 * result + ( getKind() == null ? 0 : this.getKind().hashCode() );
         result = 37 * result + ( getCode() == null ? 0 : this.getCode().hashCode() );
         return result;
   }   





}