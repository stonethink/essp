package c2s.essp.common.code;

import c2s.dto.DtoBase;
import com.wits.util.Parameter;

public class DtoCodeValueChoose extends DtoBase{
    private Long codeRid;
    private Long oldValueRid; //可以改变codeValueRid,改变时先删除,再添加,故记之
    private Long valueRid;

    private String codeName;
    private String codeValue;
    private String codeValuePath;

    private String description;

    private boolean isCodeValue = true;

    public String getCodeName() {
        return codeName;
    }

    public Long getCodeRid() {
        return codeRid;
    }

    public String getCodeValuePath() {
        return codeValuePath;
    }

    public String getDescription() {
        return description;
    }

    public Long getValueRid() {
        return valueRid;
    }


    public String getCodeValue() {
        return codeValue;
    }

    public Long getOldValueRid() {

        return oldValueRid;
    }

    public boolean isCodeValue() {
        return isCodeValue;
    }

    public void setValueRid(Long valueRid) {
        this.valueRid = valueRid;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCodeValuePath(String codeValuePath) {
        this.codeValuePath = codeValuePath;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public void setCodeRid(Long codeRid) {
        this.codeRid = codeRid;
    }

    public void setCodeValue(String codeValue) {
        this.codeValue = codeValue;
    }

    public void setIsCodeValue(boolean isCodeValue) {
        this.isCodeValue = isCodeValue;
    }

    public void setOldValueRid(Long oldValueRid) {

        this.oldValueRid = oldValueRid;
    }

    public String getName(){
        if( isCodeValue == true ){
            return this.codeValue;
        }else{
            return this.codeName;
        }
    }
}
