package server.essp.issue.typedefine.risk.form;

import org.apache.struts.action.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: Wistron ITS Wuhan SDC</p>
 *
 * @author not attributable
 * @version 1.0
 */

public class AfRisk extends ActionForm {

      private String riskInfluence = "";
      private String riskMinLevel = "";
      private String riskMaxLevel = "";
      private String riskWeight = "";
      private String riskSequence = "";
      private String riskDescription = "";
      private String typeName = "";
      private String selectedRow="";

      public AfRisk() {
     }

      public String getRiskDescription() {
        return riskDescription;
      }

      public String getRiskInfluence() {
        return riskInfluence;
      }

      public String getRiskMaxLevel() {
        return riskMaxLevel;
      }

      public String getRiskMinLevel() {
        return riskMinLevel;
      }

      public String getRiskSequence() {
        return riskSequence;
      }

      public String getRiskWeight() {
        return riskWeight;
      }

      public String getTypeName() {
        return typeName;
      }

    public String getSelectedRow() {
        return selectedRow;
    }

    public void setRiskDescription(String riskDescription) {
        this.riskDescription = riskDescription;
      }

      public void setRiskInfluence(String riskInfluence) {
        this.riskInfluence = riskInfluence;
      }

      public void setRiskMaxLevel(String riskMaxLevel) {
        this.riskMaxLevel = riskMaxLevel;
      }

      public void setRiskMinLevel(String riskMinLevel) {
        this.riskMinLevel = riskMinLevel;
      }

      public void setRiskSequence(String riskSequence) {
        this.riskSequence = riskSequence;
      }

      public void setRiskWeight(String riskWeight) {
        this.riskWeight = riskWeight;
      }

      public void setTypeName(String typeName) {
        this.typeName = typeName;
      }

    public void setSelectedRow(String selectedRow) {
        this.selectedRow = selectedRow;
    }

}
