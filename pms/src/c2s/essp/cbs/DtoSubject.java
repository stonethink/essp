package c2s.essp.cbs;

import c2s.dto.DtoBase;

public class DtoSubject extends DtoBase {
    public final static String COST_SUBJECT = "cost";
    public final static String INCOME_SUBJECT = "income";
    public final static String PROFIT_SUBJECT = "profit";

    public final static String TYPE_AUTO_CALCULATE = "AC";
    public final static String TYPE_ENTRY = "ET";
    public final static String TYPE_AUTO_CALCULATE_NAME = "Auto Caculate";
    public final static String TYPE_ENTRY_NAME = "Entry";

    public final static String ATTRI_LABOR_SUM = "Labor Sum";
    public final static String ATTRI_NONLABOR_SUM = "NonLabor Sum";
    public final static String ATTRI_EXPENSE_SUM = "Expense Sum";
    private String subjectCode;
    private String subjectName;
    private String subjectAttribute;
    private String budgetCalType;
    private String budgetCalFormula;//科目计算公式，待扩展
    private String costCalType;
    private String costCalFormula;
    private static final long serialVersionUID = 7628046918696109730L;
    public DtoSubject() {
        this.subjectAttribute = DtoSubject.COST_SUBJECT;
        this.budgetCalType = DtoSubject.TYPE_ENTRY;
        this.costCalType = DtoSubject.TYPE_ENTRY;
    }

    public DtoSubject(String subjectCode,String subjectName) {
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.subjectAttribute = DtoSubject.COST_SUBJECT;
        this.budgetCalType = DtoSubject.TYPE_ENTRY;
        this.costCalType = DtoSubject.TYPE_ENTRY;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectAttribute() {
        return subjectAttribute;
    }

    public void setSubjectAttribute(String subjectAttribute) {
        this.subjectAttribute = subjectAttribute;
    }

    public String getBudgetCalType() {
        return budgetCalType;
    }

    public void setBudgetCalType(String budgetCalType) {
        this.budgetCalType = budgetCalType;
    }

    public String getBudgetCalFormula() {
        return budgetCalFormula;
    }

    public void setBudgetCalFormula(String budgetCalFormula) {
        this.budgetCalFormula = budgetCalFormula;
    }

    public String getCostCalType() {
        return costCalType;
    }

    public void setCostCalType(String costCalType) {
        this.costCalType = costCalType;
    }

    public String getCostCalFormula() {
        return costCalFormula;
    }

    public void setCostCalFormula(String costCalFormula) {
        this.costCalFormula = costCalFormula;
    }
}
