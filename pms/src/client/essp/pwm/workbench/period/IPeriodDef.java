package client.essp.pwm.workbench.period;

import java.util.Date;
import java.util.Iterator;

public interface IPeriodDef{

	public Iterator getPeriodList(Date beginDate, long repTimes) ;

	public Iterator getPeriodList(Date beginDate, Date endDate) ;

}
