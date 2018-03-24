package c2s.essp.common.code;

import c2s.dto.*;
import com.wits.util.StringUtil;

public class DtoCode extends DtoBase{
    /** identifier field */
    private Long rid;

    /** nullable persistent field */
    private String name;

    /** nullable persistent field */
    private String type;

    /** nullable persistent field */
    private String catalog;

    /** nullable persistent field */
    private String description;

    private boolean isCode = true;

    private boolean status;

    public String getCatalog() {
        return catalog;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public Long getRid() {
        return rid;
    }

    public String getType() {
        return type;
    }

    public boolean isStatus() {
        return status;
    }

    //    public Long getSeq() {
//        return seq;
//    }

    public boolean isCode() {
        return isCode;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public void setIsCode(boolean isCode) {
        this.isCode = isCode;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setStatus(Object status) {
        String str = StringUtil.nvl(status);
        if( str.equals("Y") || str.equals("true") || str.equals("1") ){
            //设置disabled状态为true需要显示地设置
            this.status = true;
        }else{
            this.status = false;
        }
    }
}
