package c2s.essp.pwm.wp;

import com.wits.util.StringUtil;

public class DtoWSWp extends DtoWSActivity {
    Long wpRid;
    String wpCode;
    String wpName;
    public String getWpCode() {
        return wpCode;
    }

    public Long getWpRid() {
        return wpRid;
    }

    public String getWpName() {
        return wpName;
    }

    public void setWpName(String wpName) {
        this.wpName = wpName;
    }

    public void setWpRid(Long wpRid) {
        this.wpRid = wpRid;
    }

    public void setWpCode(String wpCode) {
        this.wpCode = wpCode;
    }

    public String getScopeInfo(){
        if( this.wpCode != null ){
            return this.wpCode + " -- " + StringUtil.nvl(this.wpName);
        }else{
            return "";
        }
    }

    public String getDescription() {
        return this.wpName;
    }



}
