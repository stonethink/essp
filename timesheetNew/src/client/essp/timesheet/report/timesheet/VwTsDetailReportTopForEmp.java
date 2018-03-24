package client.essp.timesheet.report.timesheet;

import java.util.Date;

import c2s.essp.timesheet.report.DtoQueryCondition;
import com.wits.util.Parameter;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author wenhaizheng
 * @version 1.0
 */
public class VwTsDetailReportTopForEmp extends VwTsDetailReportTopBase{
    private DtoQueryCondition dtoQueryCondition;
    public VwTsDetailReportTopForEmp() {
        jbInit();
    }
    protected DtoQueryCondition getDtoQueryCondition() {
        dtoQueryCondition = new DtoQueryCondition();
        dtoQueryCondition.setBegin((Date)inputBeginDate.getUICValue());
        dtoQueryCondition.setEnd((Date)inputEndDate.getUICValue());
        dtoQueryCondition.setQueryWay(DtoQueryCondition.DTO_QUERY_WAY_EMP);
        return dtoQueryCondition;
    }
    /**
       * ¼¤»îË¢ÐÂ
       */
      public void setParameter(Parameter param) {
          super.setParameter(param);
          this.refreshWorkArea();
  }
}
