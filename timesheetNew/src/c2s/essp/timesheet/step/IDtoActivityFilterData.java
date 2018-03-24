package c2s.essp.timesheet.step;

import java.util.Date;

import c2s.dto.IDto;

/**
 * <p>Title: IDtoActivityFilter</p>
 *
 * <p>Description: data interface for activity filter</p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public interface IDtoActivityFilterData extends IDto {
	
	public final static String STATUS_AHEAD = "Ahead";
	public final static String STATUS_NORMAL = "Normal";
	public final static String STATUS_DELAY = "Delay";
	
	public String getWbsCode();
	
	public String getWbsName();
	
	public String getStatus();
	
	public Date getActualFinish();
	
	public Date getActualStart();
	
	public Date getPlanFinish();
	
	public Date getPlanStart();
	
	public String getManagerId();
	
	public String getResourceIds();

	public String getName();
	
}
