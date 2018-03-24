package c2s.essp.pms.qa;

import c2s.dto.DtoBase;
import java.util.Date;
import c2s.essp.common.calendar.WrokCalendarFactory;
import c2s.essp.common.calendar.WorkCalendar;
import com.wits.excel.ICellStyleAware;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;

public class DtoNcrIssue extends DtoBase implements ICellStyleAware {
    private String issueId;
    private String issueName;
    private String principal;
    private String process;
    private Date fillDate;
    private Date dueDate;
    private Date finishDate;
    private String issueStatus;
    private String priority;
    public String getIssueId() {
        return issueId;
    }

    public String getIssueName() {
        return issueName;
    }

    public String getPrincipal() {
        return principal;
    }

    public String getProcess() {
        return process;
    }

    public Date getFillDate() {
        return fillDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public String getIssueStatus() {

        return issueStatus;
    }

    public String getPriority() {
        return priority;
    }

    public void setIssueId(String issueId) {
        this.issueId = issueId;
    }

    public void setIssueName(String issueName) {
        this.issueName = issueName;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public void setFillDate(Date fillDate) {
        this.fillDate = fillDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public void setIssueStatus(String issueStatus) {

        this.issueStatus = issueStatus;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Integer getVariantDays() {
        if(dueDate == null) {
            return null;
        }
        Date fDay = new Date();
        if(finishDate != null) {
            fDay = finishDate;
        }
        WorkCalendar wc = WrokCalendarFactory.serverCreate();
        return wc.getDayNum(dueDate, fDay);
    }

    public Integer getOpenDays() {
        if(fillDate == null) {
            return null;
        }
        Date fDay = new Date();
        if(finishDate != null) {
            fDay = finishDate;
        }
        WorkCalendar wc = WrokCalendarFactory.serverCreate();
        return wc.getDayNum(fillDate, fDay);
    }

    public void setCellStyle(String string, Object object,
                             HSSFWorkbook wb,
                             HSSFCellStyle cell) {
        if(!"variantDays".equals(string)) {
            return;
        }
        if(getVariantDays() != null && getVariantDays() > 0) {
            short colorIndex = getVariantDays() > 2 ? HSSFColor.RED.index : HSSFColor.PINK.index;
            cell.setFont(getFont(wb, cell.getFontIndex(), colorIndex));
        }
    }

    private HSSFFont getFont(HSSFWorkbook wb, short fontIndex, short colorIndex) {
        HSSFFont oldFont = wb.getFontAt(fontIndex);
        HSSFFont newFont = findColorFont(wb, fontIndex, colorIndex);
        if(newFont != null) {
            return newFont;
        }

        newFont = wb.createFont();
        newFont.setBoldweight(oldFont.getBoldweight());
        newFont.setCharSet(oldFont.getCharSet());
        newFont.setColor(colorIndex);
        newFont.setFontHeight(oldFont.getFontHeight());
        newFont.setFontHeightInPoints(oldFont.getFontHeightInPoints());
        newFont.setFontName(oldFont.getFontName());
        newFont.setItalic(oldFont.getItalic());
        newFont.setStrikeout(oldFont.getStrikeout());
        newFont.setTypeOffset(oldFont.getTypeOffset());
        newFont.setUnderline(oldFont.getUnderline());

        return newFont;
    }

     private HSSFFont findColorFont(HSSFWorkbook wb, short fontIndex, short colorIndex) {
         HSSFFont oldFont = wb.getFontAt(fontIndex);
         return wb.findFont(oldFont.getBoldweight(),colorIndex, oldFont.getFontHeight(), oldFont.getFontName(), oldFont.getItalic(), oldFont.getStrikeout(), oldFont.getTypeOffset(), oldFont.getUnderline());
     }
}
