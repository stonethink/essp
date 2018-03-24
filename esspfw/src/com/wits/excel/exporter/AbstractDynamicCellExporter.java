package com.wits.excel.exporter;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import com.wits.excel.IDynamicCellExporter;
import java.util.Vector;
import server.framework.common.BusinessException;
import c2s.dto.DtoUtil;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public abstract class AbstractDynamicCellExporter extends AbstractCellExporter implements IDynamicCellExporter{
    private Vector v = new Vector();
    private boolean isFix = true;
    public boolean isFix() {
        return isFix;
    }
    public void setFix(boolean isFix) {
        this.isFix = isFix ;
    }

    public void addTemplateChangeListner(ITemplateChangeListner l) {
        v.add(l);
    }

    public void removeTemplateChangeListner(ITemplateChangeListner l) {
        v.remove(l);
    }

    public void fireTemplateChangeListner(Object bean) {
        for(int i = 0;i < v.size();i ++){
            ITemplateChangeListner l = (ITemplateChangeListner) v.get(i);
            l.templateChange(this,bean);
        }
    }
}
