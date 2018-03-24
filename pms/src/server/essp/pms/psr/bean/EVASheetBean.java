package server.essp.pms.psr.bean;

import com.wits.excel.ICellStyleAware;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class EVASheetBean {
    private String period;
    private Double pv;
    private Double ev;
    private Double ac;
    private Double bac;
    private Double eac;
    private Double spi;
    private Double cpi;
    private Double planRate;
    private Double actualRate;
    public String getPeriod() {
        return period;
    }

    public Double getPv() {
        return pv;
    }

    public Double getEv() {
        return ev;
    }

    public Double getAc() {
        return ac;
    }

    public Double getBac() {
        return bac;
    }

    public Double getEac() {
        return eac;
    }

    public Double getSpi() {
        return spi;
    }

    public Double getCpi() {
        return cpi;
    }

    public Double getPlanRate() {
        return planRate;
    }

    public Double getActualRate() {
        return actualRate;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public void setPv(Double pv) {
        this.pv = pv;
    }

    public void setEv(Double ev) {
        this.ev = ev;
    }

    public void setAc(Double ac) {
        this.ac = ac;
    }

    public void setBac(Double bac) {
        this.bac = bac;
    }

    public void setEac(Double eac) {
        this.eac = eac;
    }

    public void setSpi(Double spi) {
        this.spi = spi;
    }

    public void setCpi(Double cpi) {
        this.cpi = cpi;
    }

    public void setPlanRate(Double planRate) {
        this.planRate = planRate;
    }

    public void setActualRate(Double actualRate) {
        this.actualRate = actualRate;
    }
}
