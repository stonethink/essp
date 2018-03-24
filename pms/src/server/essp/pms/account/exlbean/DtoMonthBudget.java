package server.essp.pms.account.exlbean;

import com.wits.excel.ICellStyleAware;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;


/**
 * Ã¿ÔÂµÄîAËã
 */
public class DtoMonthBudget implements ICellStyleAware {
    private Double budgetPm;
    private Double budgetAmt;
    private String month;
    public DtoMonthBudget() {
    }

    public String getMonth() {
        return month;
    }

    public Double getBudgetPm() {
        return budgetPm;
    }

    public Double getBudgetAmt() {
        return budgetAmt;
    }

    public void setBudgetAmt(Double budgetAmt) {
        this.budgetAmt = budgetAmt;
    }

    public void setBudgetPm(Double budgetPm) {
        this.budgetPm = budgetPm;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setCellStyle(String propertyName, Object bean, HSSFWorkbook wb,
                             HSSFCellStyle cellStyle) {
        if("SUM".equals(month)){
            HSSFFont font = wb.createFont();
            font.setBoldweight(font.BOLDWEIGHT_BOLD);
            font.setItalic(true);
            cellStyle.setFont(font);
        }
    }
}
