package server.essp.pms.account.exlbean;

import c2s.essp.pms.pbs.DtoPmsPbs;
import com.wits.excel.ICellStyleAware;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;

public class PbsBean extends DtoPmsPbs implements ICellStyleAware{
    public void setCellStyle(String propertyName, Object bean, HSSFWorkbook wb,
                             HSSFCellStyle cellStyle) {
        //设置根节点颜色
        if(getPbsParentRid() == null){
            cellStyle.setFillPattern((short) HSSFCellStyle.SOLID_FOREGROUND);
            cellStyle.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);
        }
    }

}
