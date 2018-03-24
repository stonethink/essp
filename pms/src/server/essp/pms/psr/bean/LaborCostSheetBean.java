package server.essp.pms.psr.bean;

import com.wits.excel.ICellStyleAware;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;

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
//　　人力分布根据PCB的定义，分为如下几类：
//    RD：需求
//    DE：设计
//    BD：编码＋WT＋CUT
//    TT：测试
//    AW：维护
//    PM：项目管理
//    CM：建构管理
//    CS：客户服务
//    QA：QA
//    OT：其它
//    OA:其它的Activity,未设定ActivityCode的活动，无法关联到以上类别中去

public class LaborCostSheetBean implements ICellStyleAware {
    private Double rd;
    private Double de;
    private Double bd;
    private Double tt;
    private Double aw;
    private Double pm;
    private Double cm;
    private Double cs;
    private Double qa;
    private Double ot;
    private String user;
    private Double total;
    private Double oa;
    public Double getRd() {
        return rd;
    }

    public Double getDe() {

        return de;
    }

    public Double getTt() {
        return tt;
    }

    public Double getAw() {
        return aw;
    }

    public Double getPm() {
        return pm;
    }

    public Double getCm() {
        return cm;
    }

    public Double getCs() {
        return cs;
    }

    public Double getQa() {
        return qa;
    }

    public Double getOt() {
        return ot;
    }

    public Double getBd() {

        return bd;
    }

    public String getUser() {
        return user;
    }

    public Double getTotal() {
        return total;
    }

    public Double getOa() {
        return oa;
    }

    public void setRd(Double rd) {
        this.rd = rd;
    }

    public void setDe(Double de) {

        this.de = de;
    }

    public void setTt(Double tt) {
        this.tt = tt;
    }

    public void setAw(Double aw) {
        this.aw = aw;
    }

    public void setPm(Double pm) {
        this.pm = pm;
    }

    public void setCm(Double cm) {
        this.cm = cm;
    }

    public void setCs(Double cs) {
        this.cs = cs;
    }

    public void setQa(Double qa) {
        this.qa = qa;
    }

    public void setOt(Double ot) {
        this.ot = ot;
    }

    public void setBd(Double bd) {

        this.bd = bd;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public void setOa(Double oa) {
        this.oa = oa;
    }

    public void setCellStyle(String propertyName, Object bean, HSSFWorkbook wb,
                             HSSFCellStyle cellStyle) {
        HSSFFont font = wb.createFont();

        LaborCostSheetBean b = (LaborCostSheetBean) bean;
        if (b.getUser() != null && b.getUser().equals("SUM")) {
            cellStyle.setFillPattern((short) HSSFCellStyle.SOLID_FOREGROUND);
            cellStyle.setFillForegroundColor(HSSFColor.BLUE_GREY.index);
            if (propertyName.equals("user")) {
                font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            }
        }
        cellStyle.setFont(font);
    }

}
