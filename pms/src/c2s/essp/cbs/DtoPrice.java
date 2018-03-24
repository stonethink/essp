package c2s.essp.cbs;

import c2s.dto.DtoBase;

public class DtoPrice extends DtoBase {
    private Long rid;
    private Long acntRid;
    private String catalog;
    private String id;
    private String name;
    private String subjectCode;
    private String unit;
    private String currency;
    private Double price;
    private String priceScope;
    /**
     * 系统价默认的acntRid,默认属于根Subject
     */
    public static final Long SYS_PRICE_ACNTRID = new Long(0);
    public DtoPrice() {
        priceScope = CbsConstant.SCOPE_GLOBAL;
        acntRid = SYS_PRICE_ACNTRID;
        subjectCode = DtoCbs.DEFAULT_ROOT;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPriceScope() {
        return priceScope;
    }

    public void setPriceScope(String priceScope) {
        this.priceScope = priceScope;
    }

    public Long getAcntRid() {
        return acntRid;
    }

    public Long getRid() {
        return rid;
    }

    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }
}
