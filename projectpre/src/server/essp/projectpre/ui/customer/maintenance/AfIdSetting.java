package server.essp.projectpre.ui.customer.maintenance;

import org.apache.struts.action.ActionForm;

public class AfIdSetting extends ActionForm {

    private String idType;

    private String length;

    private String length1;

    private String firstChar1;

    private String currentSeq;
    
    private String currentSeq1;

    private String firstChar;

    private String codingMethod;
    
    private String codingMethod1;

    private String codingGenerate;

    public String getCodingGenerate() {
        return codingGenerate;
    }

    public void setCodingGenerate(String codingGenerate) {
        this.codingGenerate = codingGenerate;
    }

    public String getCodingMethod() {
        return codingMethod;
    }

    public void setCodingMethod(String codingMethod) {
        this.codingMethod = codingMethod;
    }

   

    public String getFirstChar() {
        return firstChar;
    }

    public void setFirstChar(String firstChar) {
        this.firstChar = firstChar;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

   

    public String getFirstChar1() {
        return firstChar1;
    }

    public void setFirstChar1(String firstChar1) {
        this.firstChar1 = firstChar1;
    }

   

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getLength1() {
        return length1;
    }

    public void setLength1(String length1) {
        this.length1 = length1;
    }

    public String getCodingMethod1() {
        return codingMethod1;
    }

    public void setCodingMethod1(String codingMethod1) {
        this.codingMethod1 = codingMethod1;
    }

    public String getCurrentSeq() {
        return currentSeq;
    }

    public void setCurrentSeq(String currentSeq) {
        this.currentSeq = currentSeq;
    }

    public String getCurrentSeq1() {
        return currentSeq1;
    }

    public void setCurrentSeq1(String currentSeq1) {
        this.currentSeq1 = currentSeq1;
    }

   
}
