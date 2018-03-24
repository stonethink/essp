package c2s.essp.pwm.workbench;

import java.math.BigDecimal;

import c2s.dto.DtoBase;

public class DtoDailyReport extends DtoBase{
    private String wkitemOwner;
    private BigDecimal wkitemWkhours;
    private String wkitemName;

    public DtoDailyReport() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public DtoDailyReport(DtoDailyReport src) {
        this.setWkitemName(src.getWkitemName());
        this.setWkitemOwner(src.getWkitemOwner());
        double h = 0;
        BigDecimal sH = src.getWkitemWkhours();
        if( sH != null ){
            h = sH.doubleValue();
        }
        this.setWkitemWkhours(new BigDecimal(h));
    }

    private void jbInit() throws Exception {
    }

    public String getWkitemName() {
        return wkitemName;
    }

    public String getWkitemOwner() {
        return wkitemOwner;
    }

    public BigDecimal getWkitemWkhours() {
        return wkitemWkhours;
    }

    public void setWkitemWkhours(BigDecimal wkitemWkhours) {
        this.wkitemWkhours = wkitemWkhours;
    }

    public void setWkitemOwner(String wkitemOwner) {
        this.wkitemOwner = wkitemOwner;
    }

    public void setWkitemName(String wkitemName) {
        this.wkitemName = wkitemName;
    }

}
